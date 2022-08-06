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
package org.openhab.binding.pandoracaralarmsystem.internal.handlers;

import com.google.gson.Gson;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.pandoracaralarmsystem.internal.PandoraCarAlarmSystemConfiguration;
import org.openhab.binding.pandoracaralarmsystem.internal.api.ApiException;
import org.openhab.binding.pandoracaralarmsystem.internal.api.PandoraApi;
import org.openhab.binding.pandoracaralarmsystem.internal.api.PandoraApiFactory;
import org.openhab.binding.pandoracaralarmsystem.internal.api.record.StatRecord;
import org.openhab.binding.pandoracaralarmsystem.internal.api.response.ApiDevicesResponse;
import org.openhab.binding.pandoracaralarmsystem.internal.api.response.ApiUpdateResponse;
import org.openhab.core.library.types.DecimalType;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.library.types.StringType;
import org.openhab.core.thing.*;
import org.openhab.core.thing.binding.BaseBridgeHandler;
import org.openhab.core.thing.binding.ThingHandler;
import org.openhab.core.thing.binding.ThingHandlerService;
import org.openhab.core.types.Command;
import org.openhab.core.types.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static org.openhab.binding.pandoracaralarmsystem.internal.PandoraCarAlarmSystemBindingConstants.*;
import static org.openhab.binding.pandoracaralarmsystem.internal.api.ApiConstants.*;

/**
 * The type Pandora car alarm system bridge handler.
 *
 * @author Dmitry P. - Initial contribution
 */
@NonNullByDefault
public class PandoraCarAlarmSystemBridgeHandler extends BaseBridgeHandler {
    /**
     * The Logger.
     */
    Logger logger = LoggerFactory.getLogger(PandoraCarAlarmSystemBridgeHandler.class);

    private @Nullable ScheduledFuture<?> pandoraPollingJob = null;
    private final PandoraApiFactory apiFactory;
    /**
     * The Pandora cas configuration.
     */
    public @Nullable PandoraCarAlarmSystemConfiguration pandoraCASConfiguration = new PandoraCarAlarmSystemConfiguration();
    /**
     * The Api.
     */
    public @Nullable PandoraApi api;

    private Map<String, PandoraCarAlarmThingHandler> mapPandoraThingHandler = new HashMap<String, PandoraCarAlarmThingHandler>();

    /**
     * Instantiates a new Pandora car alarm system bridge handler.
     *
     * @param bridge     the bridge
     * @param apiFactory the api factory
     */
    public PandoraCarAlarmSystemBridgeHandler(Bridge bridge, PandoraApiFactory apiFactory) {
        super(bridge);
        this.apiFactory = apiFactory;
    }

    @Override
    public void initialize() {
        pandoraCASConfiguration = getConfigAs(PandoraCarAlarmSystemConfiguration.class);
        updateStatus(ThingStatus.UNKNOWN);
        this.pandoraPollingJob = scheduler.scheduleWithFixedDelay(this::pollState, 0,
                pandoraCASConfiguration != null
                        ? pandoraCASConfiguration.pollingInterval
                : DEFAULT_POLLING_INTERVAL_SEC,
                TimeUnit.SECONDS);
    }

    @Override
    public void childHandlerInitialized(final ThingHandler childHandler, final Thing childThing) {
        cleanOldHandlers();
        String deviceId = (String) childThing.getConfiguration().get("deviceId");
        logger.debug("Child Things with device ID {} initialized", deviceId);
        PandoraCarAlarmThingHandler pandoraChildHandler = (PandoraCarAlarmThingHandler) childHandler;
        pandoraChildHandler.setDeviceId(deviceId);
        mapPandoraThingHandler.putIfAbsent(deviceId, pandoraChildHandler);
    }

    @Override
    public void childHandlerDisposed(final ThingHandler childHandler, final Thing childThing) {
        cleanOldHandlers();
        String deviceId = (String) childThing.getConfiguration().get("deviceId");
        logger.debug("Child Things with device ID {} disposed", deviceId);
        PandoraCarAlarmThingHandler pandoraChildHandler = (PandoraCarAlarmThingHandler) childHandler;
        pandoraChildHandler.setDeviceId("");
        mapPandoraThingHandler.remove(deviceId);
    }

    @Override
    public void handleConfigurationUpdate(Map<String, Object> configurationParameters) {
        super.handleConfigurationUpdate(configurationParameters);
    }

    @Override
    public void thingUpdated(Thing thing) {
        super.thingUpdated(thing);
    }

    private void cleanOldHandlers() {
        List<String> devices = new ArrayList<>();
        getThing().getThings().forEach(th->{
            String dId = (String) th.getConfiguration().get("deviceId");
            devices.add(dId);
        });

        if (!mapPandoraThingHandler.isEmpty()) {
            mapPandoraThingHandler.forEach((k,v)->{
                if (!devices.contains(k)) {
                    mapPandoraThingHandler.remove(k);
                }
            });
        }
    }
    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
//        if (api == null) return;
//        PandoraApi localApi = api;
//        try {
//            channelUID.getId();
//        } catch (ApiException e) {
//            logger.debug("Exception occured when Channel:{}, Command:{}, Error:{}", channelUID.getId(), command,
//                    e.getMessage());
//        }
    }

    private void pollState() {
        PandoraApi localApi = api;
        try {
            if (localApi == null) {
                api = localApi = apiFactory.getApi(this);
                localApi.initialize();
            }
            localApi.update();
        } catch (ApiException e) {
            api = null;
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, e.getMessage());
        }
    }

    @Override
    public Collection<Class<? extends ThingHandlerService>> getServices() {
        return super.getServices();
    }

    @Override
    public void dispose() {
        Future<?> future = this.pandoraPollingJob;
        if (future != null) {
            future.cancel(true);
            this.pandoraPollingJob = null;
        }
        api = null; // re-initialize api after configuration change

        super.dispose();
    }

    /**
     * Updates a channel with a new state for a child of this bridge using the deviceId
     *
     * @param deviceId  the device id
     * @param channelID the channel id
     * @param state     the state
     */
    public void update(String deviceId, String channelID, State state) {
        if (mapPandoraThingHandler.containsKey(deviceId)) {
            PandoraCarAlarmThingHandler thingHandler = mapPandoraThingHandler.get(deviceId);
            logger.debug("update channdel {} for device {}", channelID, deviceId);
            thingHandler.update(channelID, state);
        } else {
            logger.error("Can't update state for channel: {} for device: {}, handler not found", channelID, deviceId);
        }
    }

    /**
     * Updates the bridges channels with a new state.
     *
     * @param channelID the channel id
     * @param state     the state
     */
    public void update(String channelID, State state) {
        updateState(channelID, state);
    }

    /**
     * Updates the bridges channels with a new state.
     *
     * @param status the status
     */
    public void updateThingStatus(ThingStatus status) {
        updateStatus(status);
    }

    /**
     * Update device info.
     *
     * @param device the device
     */
    public void updateDeviceInfo(ApiDevicesResponse device) {
        String deviceId = device.id.toString();
        if (mapPandoraThingHandler.containsKey(deviceId)) {
            PandoraCarAlarmThingHandler thingHandler = (PandoraCarAlarmThingHandler) mapPandoraThingHandler.get(deviceId);
            if (thingHandler != null) {
                thingHandler.update(CHANNEL_DEVICE_NAME, new StringType(device.name));
                thingHandler.update(CHANNEL_DEVICE_MODEL, new StringType(device.model));
                thingHandler.update(CHANNEL_DEVICE_FIRMWARE, new StringType(device.firmware));
            }
        }
    }

    /**
     * Update stats.
     *
     * @param update the update
     */
    public void updateStats(ApiUpdateResponse update) {
        Object stats = update.stats;
        mapPandoraThingHandler.forEach((k,v)-> {
            Map<String, Object> statObj = (Map<String, Object>) stats;
            if (!statObj.containsKey(k)) {
                v.update(CHANNEL_DEVICE_STATUS,  OnOffType.OFF);
                logger.error("Not found device Id = {} in updates", k);
                return;
            }
            StatRecord stat = new Gson().fromJson(statObj.get(k).toString(), StatRecord.class);
            if (stat == null) {
                v.update(CHANNEL_DEVICE_STATUS,  OnOffType.OFF);
                logger.error("States update is empty for device Id = {}", k);
                return;
            }

            Map<String, State> mapChannelsState = new HashMap<>();
            mapChannelsState.put(CHANNEL_DEVICE_STATUS, OnOffType.from(stat.online == 1));

            //v.update(CHANNEL_DEVICE_STATUS,  OnOffType.from(stat.online == 1));
            mapChannelsState.put(CHANNEL_DEVICE_BALANCE,  new StringType(stat.balance.value));
            mapChannelsState.put(CHANNEL_DEVICE_GSM_LEVEL,  new DecimalType(stat.gsmLevel.longValue()));
            mapChannelsState.put(CHANNEL_TELEMETRY_VOLTAGE,  new DecimalType(stat.voltage.longValue()));
            mapChannelsState.put(CHANNEL_TELEMETRY_ENGINE_RPM,  new DecimalType(stat.engineRPM.longValue() * 10));
            mapChannelsState.put(CHANNEL_TELEMETRY_ENGINE_TEMPERATURE,  new DecimalType(stat.engineTemperature.longValue()));
            mapChannelsState.put(CHANNEL_TELEMETRY_CABIN_TEMPERATURE,  new DecimalType(stat.cabinTemperature.longValue()));
            mapChannelsState.put(CHANNEL_TELEMETRY_OUTER_TEMPERATURE,  new DecimalType(stat.outerTemperature.longValue()));
            mapChannelsState.put(CHANNEL_TELEMETRY_FUEL_REMAIN,  new DecimalType(stat.fuel.longValue()));
            mapChannelsState.put(CHANNEL_TELEMETRY_SPEED,  new DecimalType(stat.speed.longValue()));
            mapChannelsState.put(CHANNEL_TELEMETRY_MILEAGE,  new DecimalType(stat.mileage.longValue()));

            // bits
            long bitStates = stat.bitStates.longValue();

            mapChannelsState.put(CHANNEL_STATES_LOCKED,  OnOffType.from(checkBit(bitStates, STATE_LOCKED)));
            mapChannelsState.put(CHANNEL_STATES_ALARM,  OnOffType.from(checkBit(bitStates, STATE_ALARM)));
            mapChannelsState.put(CHANNEL_STATES_ENGINE,  OnOffType.from(checkBit(bitStates, STATE_ENGINE)));
            mapChannelsState.put(CHANNEL_STATES_IGNITION,  OnOffType.from(checkBit(bitStates, STATE_IGNITION)));

            mapChannelsState.put(CHANNEL_STATES_AUTOSTART_INIT,  OnOffType.from(checkBit(bitStates, STATE_AUTOSTART_INIT)));
            mapChannelsState.put(CHANNEL_STATES_HANDSFREE_LOCK,  OnOffType.from(checkBit(bitStates, STATE_HANDSFREE_LOCK)));
            mapChannelsState.put(CHANNEL_STATES_HANDSFREE_UNLOCK,  OnOffType.from(checkBit(bitStates, STATE_HANDSFREE_UNLOCK)));
            mapChannelsState.put(CHANNEL_STATES_GSM,  OnOffType.from(checkBit(bitStates, STATE_GSM)));
            mapChannelsState.put(CHANNEL_STATES_GPS,  OnOffType.from(checkBit(bitStates, STATE_GPS)));
            mapChannelsState.put(CHANNEL_STATES_TRACKING,  OnOffType.from(checkBit(bitStates, STATE_TRACKING)));
            mapChannelsState.put(CHANNEL_STATES_IMMO,  OnOffType.from(checkBit(bitStates, STATE_IMMO)));
            mapChannelsState.put(CHANNEL_EXTSENSOR_ALERT_ZONE,  OnOffType.from(checkBit(bitStates, STATE_EXT_SENSOR_ALERT_ZONE)));
            mapChannelsState.put(CHANNEL_EXTSENSOR_MAIN_ZONE,  OnOffType.from(checkBit(bitStates, STATE_EXT_SENSOR_MAIN_ZONE)));
            mapChannelsState.put(CHANNEL_SENSOR_ALERT_ZONE,  OnOffType.from(checkBit(bitStates, STATE_SENSOR_ALERT_ZONE)));
            mapChannelsState.put(CHANNEL_SENSOR_MAIN_ZONE,  OnOffType.from(checkBit(bitStates, STATE_SENSOR_MAIN_ZONE)));
            mapChannelsState.put(CHANNEL_STATES_AUTOSTART,  OnOffType.from(checkBit(bitStates, STATE_AUTOSTART)));
            mapChannelsState.put(CHANNEL_STATES_SMS,  OnOffType.from(checkBit(bitStates, STATE_SMS)));
            mapChannelsState.put(CHANNEL_STATES_CALL,  OnOffType.from(checkBit(bitStates, STATE_CALL)));
            mapChannelsState.put(CHANNEL_STATES_LIGHT,  OnOffType.from(checkBit(bitStates, STATE_LIGHT)));
            mapChannelsState.put(CHANNEL_STATES_SOUND_ALERT,  OnOffType.from(checkBit(bitStates, STATE_SOUND_ALERT)));
            mapChannelsState.put(CHANNEL_STATES_SOUND_MAIN,  OnOffType.from(checkBit(bitStates, STATE_SOUND_MAIN)));

            mapChannelsState.put(CHANNEL_STATES_DOOR_FRONT_LEFT,  OnOffType.from(checkBit(bitStates, STATE_DOOR_FRONT_LEFT)));
            mapChannelsState.put(CHANNEL_STATES_DOOR_FRONT_RIGHT,  OnOffType.from(checkBit(bitStates, STATE_DOOR_FRONT_RIGHT)));
            mapChannelsState.put(CHANNEL_STATES_DOOR_BACK_LEFT,  OnOffType.from(checkBit(bitStates, STATE_DOOR_BACK_LEFT)));
            mapChannelsState.put(CHANNEL_STATES_DOOR_BACK_RIGHT,  OnOffType.from(checkBit(bitStates, STATE_DOOR_BACK_RIGHT)));
            mapChannelsState.put(CHANNEL_STATES_TRUNK,  OnOffType.from(checkBit(bitStates, STATE_TRUNK)));
            mapChannelsState.put(CHANNEL_STATES_HOOD,  OnOffType.from(checkBit(bitStates, STATE_HOOD)));

            mapChannelsState.put(CHANNEL_STATES_HANDBRAKE,  OnOffType.from(checkBit(bitStates, STATE_HANDBRAKE)));
            mapChannelsState.put(CHANNEL_STATES_BRAKES,  OnOffType.from(checkBit(bitStates, STATE_BRAKES)));
            mapChannelsState.put(CHANNEL_STATES_PREHEATER,  OnOffType.from(checkBit(bitStates, STATE_PREHEATER)));
            mapChannelsState.put(CHANNEL_STATES_ACTIVE_SECURE,  OnOffType.from(checkBit(bitStates, STATE_ACTIVE_SECURE)));
            mapChannelsState.put(CHANNEL_STATES_PROGRAMM_PREHEAT,  OnOffType.from(checkBit(bitStates, STATE_PROGRAMM_PREHEAT)));
            mapChannelsState.put(CHANNEL_STATES_EVAQ,  OnOffType.from(checkBit(bitStates, STATE_EVAQ)));
            mapChannelsState.put(CHANNEL_STATES_MAINTENANCE,  OnOffType.from(checkBit(bitStates, STATE_MAINTENANCE)));
            mapChannelsState.put(CHANNEL_STATES_STAY_HOME,  OnOffType.from(checkBit(bitStates, STATE_STAY_HOME)));
            mapChannelsState.put(CHANNEL_STATES_DISABLE_REQUEST_METKA,  OnOffType.from(checkBit(bitStates, STATE_DISABLE_REQUEST_METKA)));
            mapChannelsState.put(CHANNEL_STATES_DISABLE_UNLOCK_WITHOUT_METKA,  OnOffType.from(checkBit(bitStates, STATE_DISABLE_UNLOCK_WITHOUT_METKA)));

            v.updateByMap(mapChannelsState);
        });
    }

    private boolean checkBit(Long value, int bit) {
        return  ((value & (1L << bit)) != 0);
    }


    /**
     * Updates the bridges channels with a new state.
     *
     * @param status       the status
     * @param statusDetail the status detail
     */
    public void updateThingStatus(ThingStatus status, ThingStatusDetail statusDetail) {
        updateStatus(status, statusDetail);
    }

}