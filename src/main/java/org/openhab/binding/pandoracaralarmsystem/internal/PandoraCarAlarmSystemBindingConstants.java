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
package org.openhab.binding.pandoracaralarmsystem.internal;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.core.thing.ThingTypeUID;

/**
 * The {@link PandoraCarAlarmSystemBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Dmitry P. - Initial contribution
 */
@NonNullByDefault
public class PandoraCarAlarmSystemBindingConstants {

    private static final String BINDING_ID = "pandoracaralarmsystem";
    private static final String THING_ID = "thing";
    private static final String BRIDGE_ID = "bridge";

    /**
     * The constant THING_TYPE_PANDORA_CAS.
     */
// List of all Thing Type UIDs
    public static final ThingTypeUID THING_TYPE_PANDORA_CAS = new ThingTypeUID(BINDING_ID, THING_ID);
    /**
     * The constant THING_TYPE_BRIDGE_API.
     */
    public static final ThingTypeUID THING_TYPE_BRIDGE_API = new ThingTypeUID(BINDING_ID, BRIDGE_ID);

    /**
     * The constant API_URL.
     */
    public static final String API_URL = "https://pro.p-on.ru/api";
    /**
     * The constant MIN_POLLING_INTERVAL_SEC.
     */
    public static final Integer GET_DEVICES_POLLING_INTERVAL_MIN = 5;

    public static final Integer MIN_POLLING_INTERVAL_SEC = 5;
    /**
     * The constant DEFAULT_POLLING_INTERVAL_SEC.
     */
    public static final Integer DEFAULT_POLLING_INTERVAL_SEC = 30;

    /**
     * The constant CHANNEL_GROUP_PROPERTIES.
     */
    public static final String CHANNEL_GROUP_DEVICE = "device";
    /**
     * The constant CHANNEL_GROUP_TELEMETRY.
     */
    public static final String CHANNEL_GROUP_TELEMETRY = "telemetry";
    /**
     * The constant CHANNEL_GROUP_STATES.
     */
    public static final String CHANNEL_GROUP_STATES = "states";
    public static final String CHANNEL_GROUP_OPTIONS = "options";

}
