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
    /**
     * The constant CHANNEL_DEVICE_NAME.
     */
// List of all Channel ids
    public static final String CHANNEL_DEVICE_NAME = CHANNEL_GROUP_DEVICE + "#name";
    /**
     * The constant CHANNEL_DEVICE_MODEL.
     */
    public static final String CHANNEL_DEVICE_MODEL = CHANNEL_GROUP_DEVICE + "#model";
    /**
     * The constant CHANNEL_DEVICE_FIRMWARE.
     */
    public static final String CHANNEL_DEVICE_FIRMWARE = CHANNEL_GROUP_DEVICE + "#firmware";
    /**
     * The constant CHANNEL_DEVICE_STATUS.
     */
    public static final String CHANNEL_DEVICE_STATUS = CHANNEL_GROUP_DEVICE + "#status";
    public static final String CHANNEL_DEVICE_MOVEMENT = CHANNEL_GROUP_DEVICE + "#movement";
    /**
     * The constant CHANNEL_DEVICE_BALANCE.
     */
    public static final String CHANNEL_DEVICE_BALANCE = CHANNEL_GROUP_DEVICE + "#balance";
    /**
     * The constant CHANNEL_DEVICE_GSM_LEVEL.
     */
    public static final String CHANNEL_DEVICE_GSM_LEVEL = CHANNEL_GROUP_DEVICE + "#gsm_level";
    /**
     * The constant CHANNEL_TELEMETRY_VOLTAGE.
     */
    public static final String CHANNEL_TELEMETRY_VOLTAGE =  CHANNEL_GROUP_TELEMETRY + "#voltage";
    /**
     * The constant CHANNEL_TELEMETRY_ENGINE_RPM.
     */
    public static final String CHANNEL_TELEMETRY_ENGINE_RPM = CHANNEL_GROUP_TELEMETRY + "#engine_rpm";
    /**
     * The constant CHANNEL_TELEMETRY_ENGINE_TEMPERATURE.
     */
    public static final String CHANNEL_TELEMETRY_ENGINE_TEMPERATURE = CHANNEL_GROUP_TELEMETRY + "#engine_temp";
    /**
     * The constant CHANNEL_TELEMETRY_OUTER_TEMPERATURE.
     */
    public static final String CHANNEL_TELEMETRY_OUTER_TEMPERATURE = CHANNEL_GROUP_TELEMETRY + "#outer_temp";
    /**
     * The constant CHANNEL_TELEMETRY_CABIN_TEMPERATURE.
     */
    public static final String CHANNEL_TELEMETRY_CABIN_TEMPERATURE = CHANNEL_GROUP_TELEMETRY + "#cabin_temp";
    /**
     * The constant CHANNEL_TELEMETRY_FUEL_REMAIN.
     */
    public static final String CHANNEL_TELEMETRY_FUEL_REMAIN = CHANNEL_GROUP_TELEMETRY + "#fuel";
    /**
     * The constant CHANNEL_TELEMETRY_SPEED.
     */
    public static final String CHANNEL_TELEMETRY_SPEED = CHANNEL_GROUP_TELEMETRY + "#speed";
    /**
     * The constant CHANNEL_TELEMETRY_MILEAGE.
     */
    public static final String CHANNEL_TELEMETRY_MILEAGE = CHANNEL_GROUP_TELEMETRY + "#mileage";
    public static final String CHANNEL_TELEMETRY_LATITUDE = CHANNEL_GROUP_TELEMETRY + "#lat";
    public static final String CHANNEL_TELEMETRY_LONGITUDE = CHANNEL_GROUP_TELEMETRY + "#lon";
    /**
     * The constant CHANNEL_TELEMETRY_STATE_LOCKED.
     */
    public static final String CHANNEL_STATES_LOCKED = CHANNEL_GROUP_STATES + "#locked";
    /**
     * The constant CHANNEL_TELEMETRY_STATE_ALARM.
     */
    public static final String CHANNEL_STATES_ALARM = CHANNEL_GROUP_STATES + "#alarm";
    /**
     * The constant CHANNEL_TELEMETRY_STATE_ENGINE.
     */
    public static final String CHANNEL_STATES_ENGINE = CHANNEL_GROUP_STATES + "#engine";
    /**
     * The constant CHANNEL_TELEMETRY_STATE_IGNITION.
     */
    public static final String CHANNEL_STATES_IGNITION = CHANNEL_GROUP_STATES + "#ignition";

    /**
     * The constant CHANNEL_STATES_AUTOSTART_INIT.
     */
    public static final String CHANNEL_STATES_AUTOSTART_INIT = CHANNEL_GROUP_STATES + "#autostart_init";
    /**
     * The constant CHANNEL_STATES_HANDSFREE_LOCK.
     */
    public static final String CHANNEL_STATES_HANDSFREE_LOCK = CHANNEL_GROUP_STATES + "#handsfree_lock";
    /**
     * The constant CHANNEL_STATES_HANDSFREE_UNLOCK.
     */
    public static final String CHANNEL_STATES_HANDSFREE_UNLOCK = CHANNEL_GROUP_STATES + "#handsfree_unlock";
    /**
     * The constant CHANNEL_STATES_GSM.
     */
    public static final String CHANNEL_STATES_GSM = CHANNEL_GROUP_STATES + "#gsm";
    /**
     * The constant CHANNEL_STATES_GPS.
     */
    public static final String CHANNEL_STATES_GPS = CHANNEL_GROUP_STATES + "#gps";
    /**
     * The constant CHANNEL_STATES_TRACKING.
     */
    public static final String CHANNEL_STATES_TRACKING = CHANNEL_GROUP_STATES + "#tracking";
    /**
     * The constant CHANNEL_STATES_IMMO.
     */
    public static final String CHANNEL_STATES_IMMO = CHANNEL_GROUP_STATES + "#immo";
    /**
     * The constant CHANNEL_EXTSENSOR_ALERT_ZONE.
     */
    public static final String CHANNEL_EXTSENSOR_ALERT_ZONE = CHANNEL_GROUP_STATES + "#extsensor_alert_zone";
    /**
     * The constant CHANNEL_EXTSENSOR_MAIN_ZONE.
     */
    public static final String CHANNEL_EXTSENSOR_MAIN_ZONE = CHANNEL_GROUP_STATES + "#extsensor_main_zone";
    /**
     * The constant CHANNEL_SENSOR_ALERT_ZONE.
     */
    public static final String CHANNEL_SENSOR_ALERT_ZONE = CHANNEL_GROUP_STATES + "#sensor_alert_zone";
    /**
     * The constant CHANNEL_SENSOR_MAIN_ZONE.
     */
    public static final String CHANNEL_SENSOR_MAIN_ZONE = CHANNEL_GROUP_STATES + "#sensor_main_zone";
    /**
     * The constant CHANNEL_STATES_AUTOSTART.
     */
    public static final String CHANNEL_STATES_AUTOSTART = CHANNEL_GROUP_STATES + "#autostart";
    /**
     * The constant CHANNEL_STATES_SMS.
     */
    public static final String CHANNEL_STATES_SMS = CHANNEL_GROUP_STATES + "#sms";
    /**
     * The constant CHANNEL_STATES_CALL.
     */
    public static final String CHANNEL_STATES_CALL = CHANNEL_GROUP_STATES + "#call";
    /**
     * The constant CHANNEL_STATES_LIGHT.
     */
    public static final String CHANNEL_STATES_LIGHT = CHANNEL_GROUP_STATES + "#light";
    /**
     * The constant CHANNEL_STATES_SOUND_ALERT.
     */
    public static final String CHANNEL_STATES_SOUND_ALERT = CHANNEL_GROUP_STATES + "#sound_alert";
    /**
     * The constant CHANNEL_STATES_SOUND_MAIN.
     */
    public static final String CHANNEL_STATES_SOUND_MAIN = CHANNEL_GROUP_STATES + "#sound_main";

    /**
     * The constant CHANNEL_TELEMETRY_STATE_DOOR_FRONT_LEFT.
     */
    public static final String CHANNEL_STATES_DOOR_FRONT_LEFT = CHANNEL_GROUP_STATES + "#door_front_left";
    /**
     * The constant CHANNEL_TELEMETRY_STATE_DOOR_FRONT_RIGHT.
     */
    public static final String CHANNEL_STATES_DOOR_FRONT_RIGHT = CHANNEL_GROUP_STATES + "#door_front_right";
    /**
     * The constant CHANNEL_TELEMETRY_STATE_DOOR_BACK_LEFT.
     */
    public static final String CHANNEL_STATES_DOOR_BACK_LEFT = CHANNEL_GROUP_STATES + "#door_back_left";
    /**
     * The constant CHANNEL_TELEMETRY_STATE_DOOR_BACK_RIGHT.
     */
    public static final String CHANNEL_STATES_DOOR_BACK_RIGHT = CHANNEL_GROUP_STATES + "#_door_back_right";
    /**
     * The constant CHANNEL_TELEMETRY_STATE_TRUNK.
     */
    public static final String CHANNEL_STATES_TRUNK = CHANNEL_GROUP_STATES + "#trunk";
    /**
     * The constant CHANNEL_TELEMETRY_STATE_HOOD.
     */
    public static final String CHANNEL_STATES_HOOD = CHANNEL_GROUP_STATES + "#hood";

    /**
     * The constant CHANNEL_STATES_HANDBRAKE.
     */
    public static final String CHANNEL_STATES_HANDBRAKE = CHANNEL_GROUP_STATES + "#handbrake";
    /**
     * The constant CHANNEL_STATES_BRAKES.
     */
    public static final String CHANNEL_STATES_BRAKES = CHANNEL_GROUP_STATES + "#brakes";
    /**
     * The constant CHANNEL_STATES_PREHEATER.
     */
    public static final String CHANNEL_STATES_PREHEATER = CHANNEL_GROUP_STATES + "#preheater";
    /**
     * The constant CHANNEL_STATES_ACTIVE_SECURE.
     */
    public static final String CHANNEL_STATES_ACTIVE_SECURE = CHANNEL_GROUP_STATES + "#active_secure";
    /**
     * The constant CHANNEL_STATES_PROGRAMM_PREHEAT.
     */
    public static final String CHANNEL_STATES_PROGRAMM_PREHEAT = CHANNEL_GROUP_STATES + "#programm_preheat";
    /**
     * The constant CHANNEL_STATES_EVAQ.
     */
    public static final String CHANNEL_STATES_EVAQ = CHANNEL_GROUP_STATES + "#evaq";
    /**
     * The constant CHANNEL_STATES_MAINTENANCE.
     */
    public static final String CHANNEL_STATES_MAINTENANCE = CHANNEL_GROUP_STATES + "#maintenance";
    /**
     * The constant CHANNEL_STATES_STAY_HOME.
     */
    public static final String CHANNEL_STATES_STAY_HOME = CHANNEL_GROUP_STATES + "#stay_home";
    /**
     * The constant CHANNEL_STATES_DISABLE_REQUEST_METKA.
     */
    public static final String CHANNEL_STATES_DISABLE_REQUEST_METKA = CHANNEL_GROUP_STATES + "#disable_request_metka";
    /**
     * The constant CHANNEL_STATES_DISABLE_UNLOCK_WITHOUT_METKA.
     */
    public static final String CHANNEL_STATES_DISABLE_UNLOCK_WITHOUT_METKA = CHANNEL_GROUP_STATES + "#disable_unlock_without_metka";

}
