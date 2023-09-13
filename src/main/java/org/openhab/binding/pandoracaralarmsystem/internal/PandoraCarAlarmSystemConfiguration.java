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
package org.openhab.binding.pandoracaralarmsystem.internal;

import static org.openhab.binding.pandoracaralarmsystem.internal.PandoraCarAlarmSystemBindingConstants.API_URL;
import static org.openhab.binding.pandoracaralarmsystem.internal.PandoraCarAlarmSystemBindingConstants.DEFAULT_POLLING_INTERVAL_SEC;

import org.eclipse.jdt.annotation.NonNullByDefault;

/**
 * The {@link PandoraCarAlarmSystemConfiguration} class contains fields mapping thing configuration parameters.
 *
 * @author Dmitry P. - Initial contribution
 */
@NonNullByDefault
public class PandoraCarAlarmSystemConfiguration {

    /**
     * Sample configuration parameters. Replace with your own.
     */
    public String login = "";
    /**
     * The Password.
     */
    public String password = "";
    /**
     * The Device id.
     */
    public String deviceId = "";
    /**
     * The Api url.
     */
    public String apiUrl = API_URL;
    /**
     * The Polling interval.
     */
    public int pollingInterval = DEFAULT_POLLING_INTERVAL_SEC;
}
