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

import static org.openhab.binding.pandoracaralarmsystem.internal.PandoraCarAlarmSystemBindingConstants.*;

/**
 * The enum Pandora channels const.
 *
 * @author Dmitry P. - Initial contribution
 */
public enum PandoraChannelsConst {
    /**
     * Channel device name pandora channels const.
     */
    CHANNEL_DEVICE_NAME("name", CHANNEL_GROUP_DEVICE),
    /**
     * Channel device model pandora channels const.
     */
    CHANNEL_DEVICE_MODEL("model", CHANNEL_GROUP_DEVICE),
    /**
     * Channel device firmware pandora channels const.
     */
    CHANNEL_DEVICE_FIRMWARE("firmware", CHANNEL_GROUP_DEVICE),
    /**
     * Channel device status pandora channels const.
     */
    CHANNEL_DEVICE_STATUS("status", CHANNEL_GROUP_DEVICE),
    /**
     * Channel device movement pandora channels const.
     */
    CHANNEL_DEVICE_MOVEMENT("movement", CHANNEL_GROUP_DEVICE),
    /**
     * Channel balance pandora channels const.
     */
    CHANNEL_BALANCE("balance", CHANNEL_GROUP_DEVICE),

    /**
     * Channel gsm level pandora channels const.
     */
    CHANNEL_GSM_LEVEL("gsm_level", CHANNEL_GROUP_DEVICE),

    /**
     * Channel voltage pandora channels const.
     */
    CHANNEL_VOLTAGE("voltage", CHANNEL_GROUP_TELEMETRY),
    /**
     * Channel engine rpm pandora channels const.
     */
    CHANNEL_ENGINE_RPM("engine_rpm", CHANNEL_GROUP_TELEMETRY),
    /**
     * Channel engine temperature pandora channels const.
     */
    CHANNEL_ENGINE_TEMPERATURE("engine_temp", CHANNEL_GROUP_TELEMETRY),
    /**
     * Channel outer temperature pandora channels const.
     */
    CHANNEL_OUTER_TEMPERATURE("outer_temp", CHANNEL_GROUP_TELEMETRY),
    /**
     * Channel cabin temperature pandora channels const.
     */
    CHANNEL_CABIN_TEMPERATURE("cabin_temp", CHANNEL_GROUP_TELEMETRY),
    /**
     * Channel fuel pandora channels const.
     */
    CHANNEL_FUEL("fuel", CHANNEL_GROUP_TELEMETRY),
    /**
     * Channel speed pandora channels const.
     */
    CHANNEL_SPEED("speed", CHANNEL_GROUP_TELEMETRY),
    /**
     * Channel mileage pandora channels const.
     */
    CHANNEL_MILEAGE("mileage", CHANNEL_GROUP_TELEMETRY),
    /**
     * Channel mileage_can pandora channels const.
     */
    CHANNEL_MILEAGE_CAN("mileage_can", CHANNEL_GROUP_TELEMETRY),
    /**
     * Channel latitude pandora channels const.
     */
    CHANNEL_LATITUDE("lat", CHANNEL_GROUP_TELEMETRY),
    /**
     * Channel longitude pandora channels const.
     */
    CHANNEL_LONGITUDE("lon", CHANNEL_GROUP_TELEMETRY),

    /**
     * Channel locked pandora channels const.
     */
    CHANNEL_LOCKED("locked", CHANNEL_GROUP_STATES),
    /**
     * Channel alarm pandora channels const.
     */
    CHANNEL_ALARM("alarm", CHANNEL_GROUP_STATES),
    /**
     * Channel engine pandora channels const.
     */
    CHANNEL_ENGINE("engine", CHANNEL_GROUP_STATES),
    /**
     * Channel ignition pandora channels const.
     */
    CHANNEL_IGNITION("ignition", CHANNEL_GROUP_STATES),
    /**
     * Channel autostart init pandora channels const.
     */
    CHANNEL_AUTOSTART_INIT("autostart_init", CHANNEL_GROUP_STATES),
    /**
     * Channel immo pandora channels const.
     */
    CHANNEL_IMMO("immo", CHANNEL_GROUP_STATES),
    /**
     * Channel light pandora channels const.
     */
    CHANNEL_LIGHT("light", CHANNEL_GROUP_STATES),

    /**
     * Channel door front left pandora channels const.
     */
    CHANNEL_DOOR_FRONT_LEFT("door_front_left", CHANNEL_GROUP_STATES),
    /**
     * Channel door front right pandora channels const.
     */
    CHANNEL_DOOR_FRONT_RIGHT("door_front_right", CHANNEL_GROUP_STATES),
    /**
     * Channel door back left pandora channels const.
     */
    CHANNEL_DOOR_BACK_LEFT("door_back_left", CHANNEL_GROUP_STATES),
    /**
     * Channel door back right pandora channels const.
     */
    CHANNEL_DOOR_BACK_RIGHT("door_back_right", CHANNEL_GROUP_STATES),
    /**
     * Channel trunk pandora channels const.
     */
    CHANNEL_TRUNK("trunk", CHANNEL_GROUP_STATES),
    /**
     * Channel hood pandora channels const.
     */
    CHANNEL_HOOD("hood", CHANNEL_GROUP_STATES),

    /**
     * Channel handbrake pandora channels const.
     */
    CHANNEL_HANDBRAKE("handbrake", CHANNEL_GROUP_STATES),
    /**
     * Channel brakes pandora channels const.
     */
    CHANNEL_BRAKES("brakes", CHANNEL_GROUP_STATES),
    /**
     * Channel active secure pandora channels const.
     */
    CHANNEL_ACTIVE_SECURE("active_secure", CHANNEL_GROUP_STATES),
    /**
     * Channel evaq pandora channels const.
     */
    CHANNEL_EVAQ("evaq", CHANNEL_GROUP_STATES),
    /**
     * Channel maintenance pandora channels const.
     */
    CHANNEL_MAINTENANCE("maintenance", CHANNEL_GROUP_STATES),

    /**
     * Channel handsfree lock pandora channels const.
     */
    CHANNEL_HANDSFREE_LOCK("handsfree_lock", CHANNEL_GROUP_OPTIONS),
    /**
     * Channel handsfree unlock pandora channels const.
     */
    CHANNEL_HANDSFREE_UNLOCK("handsfree_unlock", CHANNEL_GROUP_OPTIONS),
    /**
     * Channel gsm pandora channels const.
     */
    CHANNEL_GSM("gsm", CHANNEL_GROUP_OPTIONS),
    /**
     * Channel gps pandora channels const.
     */
    CHANNEL_GPS("gps", CHANNEL_GROUP_OPTIONS),
    /**
     * Channel tracking pandora channels const.
     */
    CHANNEL_TRACKING("tracking", CHANNEL_GROUP_OPTIONS),

    /**
     * Channel extsensor alert zone pandora channels const.
     */
    CHANNEL_EXTSENSOR_ALERT_ZONE("extsensor_alert_zone", CHANNEL_GROUP_OPTIONS),
    /**
     * Channel extsensor main zone pandora channels const.
     */
    CHANNEL_EXTSENSOR_MAIN_ZONE("extsensor_main_zone", CHANNEL_GROUP_OPTIONS),
    /**
     * Channel sensor alert zone pandora channels const.
     */
    CHANNEL_SENSOR_ALERT_ZONE("sensor_alert_zone", CHANNEL_GROUP_OPTIONS),
    /**
     * Channel sensor main zone pandora channels const.
     */
    CHANNEL_SENSOR_MAIN_ZONE("sensor_main_zone", CHANNEL_GROUP_OPTIONS),

    /**
     * Channel states autostart pandora channels const.
     */
    CHANNEL_STATES_AUTOSTART("autostart", CHANNEL_GROUP_OPTIONS),
    /**
     * Channel sms pandora channels const.
     */
    CHANNEL_SMS("sms", CHANNEL_GROUP_OPTIONS),
    /**
     * Channel call pandora channels const.
     */
    CHANNEL_CALL("call", CHANNEL_GROUP_OPTIONS),
    /**
     * Channel sound alert pandora channels const.
     */
    CHANNEL_SOUND_ALERT("sound_alert", CHANNEL_GROUP_OPTIONS),
    /**
     * Channel sound main pandora channels const.
     */
    CHANNEL_SOUND_MAIN("sound_main", CHANNEL_GROUP_OPTIONS),
    /**
     * Channel preheater pandora channels const.
     */
    CHANNEL_PREHEATER("preheater", CHANNEL_GROUP_OPTIONS),
    /**
     * Channel programm preheat pandora channels const.
     */
    CHANNEL_PROGRAMM_PREHEAT("programm_preheat", CHANNEL_GROUP_OPTIONS),
    /**
     * Channel stay home pandora channels const.
     */
    CHANNEL_STAY_HOME("stay_home", CHANNEL_GROUP_OPTIONS),
    /**
     * Channel disable request metka pandora channels const.
     */
    CHANNEL_DISABLE_REQUEST_METKA("disable_request_metka", CHANNEL_GROUP_OPTIONS),
    /**
     * Channel disable unlock without metka pandora channels const.
     */
    CHANNEL_DISABLE_UNLOCK_WITHOUT_METKA("disable_unlock_without_metka", CHANNEL_GROUP_OPTIONS),

    /**
     * Channel command pandora channels const.
     */
    CHANNEL_COMMAND("command", CHANNEL_GROUP_ACTIONS),;

    private String channelId;
    private String groupId;

    PandoraChannelsConst(String channelId, String groupId) {
        this.channelId = channelId;
        this.groupId = groupId;
    }

    /**
     * Gets channel id.
     *
     * @return the channel id
     */
    public String getChannelId() {
        return channelId;
    }

    /**
     * Sets channel id.
     *
     * @param channelId the channel id
     */
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    /**
     * Gets group id.
     *
     * @return the group id
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * Sets group id.
     *
     * @param groupId the group id
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return groupId + "#" + channelId;
    }
}
