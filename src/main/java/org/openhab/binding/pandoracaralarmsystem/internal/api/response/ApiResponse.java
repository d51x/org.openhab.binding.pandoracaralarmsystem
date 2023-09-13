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
package org.openhab.binding.pandoracaralarmsystem.internal.api.response;

import java.io.Serializable;

/**
 * The type Api response.
 *
 * @author Dmitry P. - Initial contribution
 *
 */
public class ApiResponse implements Serializable {
    /**
     * The Http code.
     */
    public Integer httpCode;
    /**
     * The Response.
     */
    public String response;
    /**
     * The Expires.
     */
    public Long expires;
}
