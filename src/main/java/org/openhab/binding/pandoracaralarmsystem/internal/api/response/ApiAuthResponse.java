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
package org.openhab.binding.pandoracaralarmsystem.internal.api.response;

import com.google.gson.annotations.SerializedName;

import javax.validation.constraints.NotNull;


/**
 * The type Api auth response.
 *
 * @author Dmitry P. - Initial contribution
 *
 */
@NotNull
public class ApiAuthResponse extends ApiSuccessResponse {
    /**
     * The User id.
     */
    @SerializedName("user_id")
    public Long userId;
    /**
     * The Session id.
     */
    @SerializedName("session_id")
    public String sessionId;
}
