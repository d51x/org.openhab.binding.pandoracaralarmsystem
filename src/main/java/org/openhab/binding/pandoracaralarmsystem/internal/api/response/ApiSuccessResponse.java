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

/**
 * The type Api success response.
 *
 * @author Dmitry P. - Initial contribution
 *
 */
public class ApiSuccessResponse extends ApiResponse {
    /**
     * The Message.
     */
    public String message;
    /**
     * The Status.
     */
    public String status;
}
