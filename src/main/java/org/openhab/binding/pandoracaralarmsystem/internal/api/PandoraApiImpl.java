/*
 * Copyright (c) 2010-2022 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package org.openhab.binding.pandoracaralarmsystem.internal.api;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.FormContentProvider;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.util.Fields;
import org.openhab.binding.pandoracaralarmsystem.internal.api.response.*;
import org.openhab.binding.pandoracaralarmsystem.internal.handlers.PandoraCarAlarmSystemBridgeHandler;
import org.openhab.core.library.types.StringType;
import org.openhab.core.thing.ThingStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.openhab.binding.pandoracaralarmsystem.internal.PandoraCarAlarmSystemBindingConstants.*;
import static org.openhab.binding.pandoracaralarmsystem.internal.PandoraChennelsConst.*;

/**
 * The {@link PandoraApiImpl} is the json Api methods for Pandora Base versions (DXL-series)
 *
 * @author Dmitry P. - Initial contribution
 */
@NonNullByDefault
public class PandoraApiImpl implements PandoraApi {
    /**
     * The constant PANDORA_USER_AGENT.
     */
    public static final String PANDORA_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36";
    /**
     * The constant API_PATH_AUTH.
     */
    public static final String API_PATH_AUTH = "/users/login";
    /**
     * The constant API_PATH_DEVICES.
     */
    public static final String API_PATH_DEVICES = "/devices";
    /**
     * The constant API_PATH_UPDATES.
     */
    public static final String API_PATH_UPDATES = "/updates"; // ts=-1&from=" + from + "&to=" + to

    /**
     * The Logger.
     */
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * The Gson.
     */
    protected final Gson gson = new Gson();
    /**
     * The Http client.
     */
    protected final HttpClient httpClient;
    /**
     * The Api url.
     */
    protected String apiUrl;
    /**
     * The Handler.
     */
    protected final PandoraCarAlarmSystemBridgeHandler handler;

    private String sessionId = "";
    private Instant lastAuthTimestamp = Instant.now();
    private Instant lastGetDevicesTimestamp = Instant.EPOCH;

    private Long expires = Date.from(Instant.now()).getTime();

    /**
     * Gets expires.
     *
     * @return the expires
     */
    public Long getExpires() {
        return expires;
    }

    /**
     * Sets expires.
     *
     * @param expires the expires
     */
    public void setExpires(Long expires) {
        this.expires = expires;
    }

    /**
     * Instantiates a new Pandora api.
     *
     * @param handler    the handler
     * @param httpClient the http client
     * @throws ApiException the api exception
     */
    public PandoraApiImpl(PandoraCarAlarmSystemBridgeHandler handler, HttpClient httpClient) throws ApiException {
        this.httpClient = httpClient;
        this.handler = handler;
        this.apiUrl = handler.pandoraCASConfiguration != null ? handler.pandoraCASConfiguration.apiUrl : API_URL;
    }

    /**
     * Gets session id.
     *
     * @return the session id
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Sets session id.
     *
     * @param sessionId the session id
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public void initialize() throws ApiException {
        // AUTH
        auth();
        update();
    }

    private boolean isEmptyOrExpiredSession() {
        return sessionId.isEmpty() || expires < Instant.now().toEpochMilli();
    }

    @Override
    public void update() throws ApiException {
        if (isEmptyOrExpiredSession()) {
            logger.warn("Session expired, try authorize...");
            auth();
        }

        prolongSession();

        if (Instant.now().isAfter(lastGetDevicesTimestamp.plus(GET_DEVICES_POLLING_INTERVAL_MIN, ChronoUnit.MINUTES))) {
            List<ApiDevicesResponse> apiDevicesResponseList = getDevices();
            apiDevicesResponseList.forEach(handler::updateDeviceInfo);
        }

        ApiUpdateResponse response = getUpdates();
        handler.updateStats(response);
    }

    private void setHeaders(Request request) {
        request.timeout(60, TimeUnit.SECONDS);
        request.header(HttpHeader.USER_AGENT, null);
        request.header(HttpHeader.USER_AGENT, PANDORA_USER_AGENT);
        request.header(HttpHeader.CONTENT_TYPE, "application/x-www-form-urlencoded");
        request.header(HttpHeader.CONNECTION, "keep-alive");
        request.header(HttpHeader.ACCEPT, "*/*");
        request.header(HttpHeader.ACCEPT_ENCODING, null);
        request.header(HttpHeader.ACCEPT_ENCODING, "gzip, deflate, br");
        //request.header(HttpHeader.CACHE_CONTROL, "no-cache");
        request.followRedirects(true);
    }

    @Override
    public ApiResponse sendGetRequest(String path, String params, String sessionId) throws ApiException {
        ApiResponse result = new ApiResponse();
        String url = apiUrl + path;
        if (!params.isEmpty()) {
            url += "?" + params;
        }
        httpClient.setConnectTimeout(60*1000);
        Request request = httpClient.newRequest(url);
        setHeaders(request);
        request.method(HttpMethod.GET);
//        if (!sessionId.isEmpty()) {
//            setCookie(request, "sid=" + sessionId);
//        }
        String errorReason;
        try {
            ContentResponse contentResponse = request.send();
            result.httpCode = contentResponse.getStatus();
            if (result.httpCode == 200 ||
                    result.httpCode >= 400 && result.httpCode < 500) {
                result.response = contentResponse.getContentAsString();
                return result;
            } else {
                errorReason = String.format("Pandora API request failed with %d: %s", contentResponse.getStatus(),
                        contentResponse.getReason());
            }
        } catch (TimeoutException e) {
            errorReason = "TimeoutException: Pandora API was not reachable on your network";
        } catch (ExecutionException e) {
            errorReason = String.format("ExecutionException: %s", e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            errorReason = String.format("InterruptedException: %s", e.getMessage());
        }
        throw new ApiException(errorReason);
    }

    @Override
    public ApiResponse sendPostRequest(String path, String data) throws ApiException {
        if (Objects.isNull(handler.pandoraCASConfiguration)) {
            throw new ApiException("Configuration is empty");
        }
        ApiResponse result = new ApiResponse();

        Request request = httpClient.newRequest(apiUrl + path);
        setHeaders(request);
        httpClient.setConnectTimeout(60*1000);
        httpClient.setAddressResolutionTimeout(60*1000);

        Fields fields = new Fields();
        fields.add("login", handler.pandoraCASConfiguration.login);
        fields.add("password", handler.pandoraCASConfiguration.password);
        fields.add("lang", "ru");
        request.content(new FormContentProvider(fields));
        request.method(HttpMethod.POST);

        String errorReason;

        try {
            ContentResponse contentResponse = request.send();
            result.httpCode = contentResponse.getStatus();
            if (result.httpCode == 200 ||
                    result.httpCode >= 400 && result.httpCode < 500) {
                Optional<HttpField> cookie = contentResponse.getHeaders().stream().filter(f->f.getName().equalsIgnoreCase("set-cookie")).findFirst();
                if (cookie.isPresent()) {
                    String[] cookieData = cookie.get().getValue().split(";");
                    String expire = Arrays.stream(cookieData).filter(f->f.contains("expires")).findFirst().orElse("");
                    if (!expire.isEmpty()) {
                        result.expires = Date.parse(expire.split("=")[1]);
                    }
                }
                result.response = contentResponse.getContentAsString();
                return result;
            } else {
                errorReason = String.format("Pandora request failed with %d: %s", contentResponse.getStatus(),
                        contentResponse.getReason());
            }
        } catch (InterruptedException e) {
            errorReason = String.format("InterruptedException: %s", e.getMessage());
        } catch (TimeoutException e) {
            errorReason = "TimeoutException: Pandora Api was not reachable on your network";
        } catch (ExecutionException e) {
            errorReason = String.format("ExecutionException: %s", e.getMessage());
        }
        throw new ApiException(errorReason);
    }

    private void setSession(String sid, Long expires) {
        setSessionId(sid);
        setExpires(expires);
    }
    private void resetSession() {
        setSession("", 0L);
    }

    private ApiAuthResponse auth() throws ApiException {
        if (handler.pandoraCASConfiguration == null) {
            throw new ApiException("Configuration is null");
        }
        StringBuilder formData  = new StringBuilder();
        formData.append("login=");
        formData.append(handler.pandoraCASConfiguration.login);
        formData.append("&");
        formData.append("password=");
        formData.append(handler.pandoraCASConfiguration.password);
        formData.append("&lang=ru");

        logger.debug("auth for {}", formData);

        resetSession();

        try {
            ApiResponse response = sendPostRequest(API_PATH_AUTH, formData.toString());
            if (response.httpCode == 200) {
                handler.updateThingStatus(ThingStatus.ONLINE);
                ApiAuthResponse authResponse = new Gson().fromJson(response.response, ApiAuthResponse.class);
                authResponse.expires = response.expires;
                logger.debug("auth: sid: {}, expires: {}", authResponse.sessionId, authResponse.expires);
                setSession(authResponse.sessionId, authResponse.expires);
                lastAuthTimestamp = Instant.now();
                return authResponse;
            } else {
                throw processErrorResponse("auth", response);
            }
        } catch (JsonSyntaxException e) {
            handler.updateThingStatus(ThingStatus.OFFLINE);
            throw new ApiException("JsonSyntaxException:{}", e);
        }
    }

    private ApiException processErrorResponse(String tag, ApiResponse response) {
        handler.updateThingStatus(ThingStatus.OFFLINE);
        if (response.httpCode >= 400 && response.httpCode < 500) {
            if (response.httpCode == 401) {
                resetSession();
            }
            ApiFailResponse failResponse = new Gson().fromJson(response.response, ApiFailResponse.class);
            logger.error(String.format("Pandora API (%s) error: %d - %s",
                    tag, response.httpCode, failResponse.errorText));
            return new ApiException(String.format("Pandora API (%s) error: %d - %s",
                    tag, response.httpCode, failResponse.errorText));
        } else {
            logger.error(String.format("Pandora API (%s) error: %d - %s",
                    tag, response.httpCode, response.response));
            return new ApiException(String.format("Pandora API (%s) error: %d - %s",
                    tag, response.httpCode, response.response));
        }
    }

    private List<ApiDevicesResponse> getDevices() throws ApiException {
        if (handler.pandoraCASConfiguration == null) {
            throw new ApiException("Configuration is null");
        }
        logger.debug("getDevices for System Id: {}", handler.pandoraCASConfiguration.deviceId);
        try {
            ApiResponse response = sendGetRequest(API_PATH_DEVICES, "", getSessionId());
            if (response.httpCode == 200) {
                Type listType = new TypeToken<ArrayList<ApiDevicesResponse>>(){}.getType();
                List<ApiDevicesResponse> apiDevicesResponseList = new Gson().fromJson(response.response, listType);
                if (!apiDevicesResponseList.isEmpty()) {
                    lastGetDevicesTimestamp = Instant.now();
                    return apiDevicesResponseList;
                } else {
                    logger.error("getDevices: Pandora API (devices) error: device list is null");
                    throw new ApiException("Pandora API (devices) error: device list is null");
                }
            } else {
                logger.error("getDevices: code: {}, error: {}", response.httpCode, response.response);
                throw processErrorResponse("devices", response);
            }
        } catch (JsonSyntaxException e) {
            logger.error("getDevices: {}", e.getMessage());
            throw new ApiException("JsonSyntaxException:{}", e);
        }
    }

    private ApiUpdateResponse getUpdates() throws ApiException {
        if (handler.pandoraCASConfiguration == null) {
            throw new ApiException("Configuration is null");
        }

        String params = "ts=-1" +
                "&from=" + Instant.now().minus(5, ChronoUnit.MINUTES).getEpochSecond() +
                "&to=" + Instant.now().getEpochSecond();

        logger.debug("getUpdates: for {}", params);

        ApiResponse response = sendGetRequest(API_PATH_UPDATES, params, getSessionId());
        if (response.httpCode == 200) {
            try {
                return Optional.ofNullable(new Gson().fromJson(response.response, ApiUpdateResponse.class))
                        .orElseThrow(()-> {
                                logger.error("getUpdate: Pandora API (updates) returns empty result");
                                return new RuntimeException("Pandora API (updates) returns empty result");
                            }
                        );
            } catch (JsonSyntaxException e) {
                logger.error("getUpdates: {}", e.getMessage());
                throw new RuntimeException("Incorrect response: {}", e);
            }
        } else {
            logger.error("getUpdate: code: {}, response: {}", response.httpCode, response.response);
            throw processErrorResponse("updates", response);
        }
    }

    /**
     * Process bridge states.
     *
     * @param updatesResponse the updates response
     */
    protected void processBridgeStates(ApiUpdateResponse updatesResponse) {
       Object times = updatesResponse.time;
       Object stats = updatesResponse.stats;

       logger.debug("stats: {}", stats);
       // handler.update(CHANNEL_DEVICE_STATUS, updatesResponse.);
    }

    /**
     * Process bridge states.
     *
     * @param devicesResponse the devices response
     */
    protected void processBridgeStates(ApiDevicesResponse devicesResponse) {
        handler.update(CHANNEL_DEVICE_NAME.getName(), new StringType(devicesResponse.name));
        handler.update(CHANNEL_DEVICE_MODEL.getName(), new StringType(devicesResponse.model));
        handler.update(CHANNEL_DEVICE_FIRMWARE.getName(), new StringType(devicesResponse.firmware));
    }

    @Override
    public void prolongSession() throws ApiException {
        if (Instant.now().isAfter(lastAuthTimestamp.plus(6, ChronoUnit.HOURS))) {
            logger.warn("The last authorization was more 6 hours ago. Trying to get a new session");
            auth();
        }
    }
}
