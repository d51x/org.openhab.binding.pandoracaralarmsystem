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

import static org.openhab.binding.pandoracaralarmsystem.internal.PandoraCarAlarmSystemBindingConstants.*;

public enum PandoraChennelsConst {
    CHANNEL_DEVICE_NAME("name", CHANNEL_GROUP_DEVICE),
    CHANNEL_DEVICE_MODEL("model", CHANNEL_GROUP_DEVICE),
    CHANNEL_DEVICE_FIRMWARE("firmware", CHANNEL_GROUP_DEVICE),
    CHANNEL_DEVICE_STATUS("status", CHANNEL_GROUP_DEVICE),
    CHANNEL_DEVICE_MOVEMENT("movement", CHANNEL_GROUP_DEVICE),
    CHANNEL_BALANCE("balance", CHANNEL_GROUP_DEVICE),

    CHANNEL_GSM_LEVEL("gsm_level", CHANNEL_GROUP_DEVICE),

    CHANNEL_VOLTAGE("voltage", CHANNEL_GROUP_TELEMETRY),
    CHANNEL_ENGINE_RPM("engine_rpm", CHANNEL_GROUP_TELEMETRY),
    CHANNEL_ENGINE_TEMPERATURE("engine_temp", CHANNEL_GROUP_TELEMETRY),
    CHANNEL_OUTER_TEMPERATURE("outer_temp", CHANNEL_GROUP_TELEMETRY),
    CHANNEL_CABIN_TEMPERATURE("cabin_temp", CHANNEL_GROUP_TELEMETRY),
    CHANNEL_FUEL("fuel", CHANNEL_GROUP_TELEMETRY),
    CHANNEL_SPEED("speed", CHANNEL_GROUP_TELEMETRY),
    CHANNEL_MILEAGE("mileage", CHANNEL_GROUP_TELEMETRY),
    CHANNEL_LATITUDE("lat", CHANNEL_GROUP_TELEMETRY),
    CHANNEL_LONGITUDE("lon", CHANNEL_GROUP_TELEMETRY),

    CHANNEL_LOCKED("locked", CHANNEL_GROUP_STATES),
    CHANNEL_ALARM("alarm", CHANNEL_GROUP_STATES),
    CHANNEL_ENGINE("engine", CHANNEL_GROUP_STATES),
    CHANNEL_IGNITION("ignition", CHANNEL_GROUP_STATES),
    CHANNEL_AUTOSTART_INIT("autostart_init", CHANNEL_GROUP_STATES),
    CHANNEL_IMMO("immo", CHANNEL_GROUP_STATES),
    CHANNEL_LIGHT("light", CHANNEL_GROUP_STATES),

    CHANNEL_DOOR_FRONT_LEFT("door_front_left", CHANNEL_GROUP_STATES),
    CHANNEL_DOOR_FRONT_RIGHT("door_front_right", CHANNEL_GROUP_STATES),
    CHANNEL_DOOR_BACK_LEFT("door_back_left", CHANNEL_GROUP_STATES),
    CHANNEL_DOOR_BACK_RIGHT("door_back_right", CHANNEL_GROUP_STATES),
    CHANNEL_TRUNK("trunk", CHANNEL_GROUP_STATES),
    CHANNEL_HOOD("hood", CHANNEL_GROUP_STATES),

    CHANNEL_HANDBRAKE("handbrake", CHANNEL_GROUP_STATES),
    CHANNEL_BRAKES("brakes", CHANNEL_GROUP_STATES),
    CHANNEL_ACTIVE_SECURE("active_secure", CHANNEL_GROUP_STATES),
    CHANNEL_EVAQ("evaq", CHANNEL_GROUP_STATES),
    CHANNEL_MAINTENANCE("maintenance", CHANNEL_GROUP_STATES),

    CHANNEL_HANDSFREE_LOCK("handsfree_lock", CHANNEL_GROUP_OPTIONS),
    CHANNEL_HANDSFREE_UNLOCK("handsfree_unlock", CHANNEL_GROUP_OPTIONS),
    CHANNEL_GSM("gsm", CHANNEL_GROUP_OPTIONS),
    CHANNEL_GPS("gps", CHANNEL_GROUP_OPTIONS),
    CHANNEL_TRACKING("tracking", CHANNEL_GROUP_OPTIONS),

    CHANNEL_EXTSENSOR_ALERT_ZONE("extsensor_alert_zone", CHANNEL_GROUP_OPTIONS),
    CHANNEL_EXTSENSOR_MAIN_ZONE("extsensor_main_zone", CHANNEL_GROUP_OPTIONS),
    CHANNEL_SENSOR_ALERT_ZONE("sensor_alert_zone", CHANNEL_GROUP_OPTIONS),
    CHANNEL_SENSOR_MAIN_ZONE("sensor_main_zone", CHANNEL_GROUP_OPTIONS),

    CHANNEL_STATES_AUTOSTART("autostart", CHANNEL_GROUP_OPTIONS),
    CHANNEL_SMS("sms", CHANNEL_GROUP_OPTIONS),
    CHANNEL_CALL("call", CHANNEL_GROUP_OPTIONS),
    CHANNEL_SOUND_ALERT("sound_alert", CHANNEL_GROUP_OPTIONS),
    CHANNEL_SOUND_MAIN("sound_main", CHANNEL_GROUP_OPTIONS),
    CHANNEL_PREHEATER("preheater", CHANNEL_GROUP_OPTIONS),
    CHANNEL_PROGRAMM_PREHEAT("programm_preheat", CHANNEL_GROUP_OPTIONS),
    CHANNEL_STAY_HOME("stay_home", CHANNEL_GROUP_OPTIONS),
    CHANNEL_DISABLE_REQUEST_METKA("disable_request_metka", CHANNEL_GROUP_OPTIONS),
    CHANNEL_DISABLE_UNLOCK_WITHOUT_METKA("disable_unlock_without_metka", CHANNEL_GROUP_OPTIONS),

    CHANNEL_COMMAND("command", CHANNEL_GROUP_ACTIONS),
    ;

    private String channelId;
    private String groupId;

    PandoraChennelsConst(String channelId, String groupId) {
        this.channelId = channelId;
        this.groupId = groupId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return groupId + "#" + channelId;
    }
}
