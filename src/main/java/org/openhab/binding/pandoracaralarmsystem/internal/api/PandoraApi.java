/**
 * Copyright (c) 2010-2023 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.pandoracaralarmsystem.internal.api;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jetty.util.Fields;
import org.openhab.binding.pandoracaralarmsystem.internal.api.response.ApiCommandResponse;
import org.openhab.binding.pandoracaralarmsystem.internal.api.response.ApiResponse;

/**
 * The {@link PandoraApi} is the JSON API methods that can be extended for different devices.
 *
 * @author Dmitry P. - Initial contribution
 */
@NonNullByDefault
public interface PandoraApi {
    /**
     * Update.
     *
     * @throws ApiException the api exception
     */
    void update() throws ApiException;

    /**
     * Initialize.
     *
     * @throws ApiException the api exception
     */
    void initialize() throws ApiException;

    /**
     * Send get request api response.
     *
     * @param path the path
     * @param params the params
     * @param sessionID the session id
     * @return the api response
     * @throws ApiException the api exception
     */
    ApiResponse sendGetRequest(String path, String params, String sessionID) throws ApiException;

    /**
     * Send post request api response.
     *
     * @param path the path
     * @param data the data
     * @return the api response
     * @throws ApiException the api exception
     */
    ApiResponse sendPostRequest(String path, String data) throws ApiException;

    ApiResponse sendPostRequest(String path, Fields fields) throws ApiException;

    void prolongSession() throws ApiException;

    ApiCommandResponse sendCommand(String deviceId, ApiCommands command) throws ApiException;
}
