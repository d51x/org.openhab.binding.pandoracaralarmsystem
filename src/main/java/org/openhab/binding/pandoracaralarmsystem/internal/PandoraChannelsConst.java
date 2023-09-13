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
 * The enum Pandora chennels const.
 *
 * @author Dmitry P. - Initial contribution
 */
public enum PandoraChannelsConst {
    /**
     * Channel device name pandora chennels const.
     */
    CHANNEL_DEVICE_NAME("name", CHANNEL_GROUP_DEVICE),
    /**
     * Channel device model pandora chennels const.
     */
    CHANNEL_DEVICE_MODEL("model", CHANNEL_GROUP_DEVICE),
    /**
     * Channel device firmware pandora chennels const.
     */
    CHANNEL_DEVICE_FIRMWARE("firmware", CHANNEL_GROUP_DEVICE),
    /**
     * Channel device status pandora chennels const.
     */
    CHANNEL_DEVICE_STATUS("status", CHANNEL_GROUP_DEVICE),
    /**
     * Channel device movement pandora chennels const.
     */
    CHANNEL_DEVICE_MOVEMENT("movement", CHANNEL_GROUP_DEVICE),
    /**
     * Channel balance pandora chennels const.
     */
    CHANNEL_BALANCE("balance", CHANNEL_GROUP_DEVICE),

    /**
     * Channel gsm level pandora chennels const.
     */
    CHANNEL_GSM_LEVEL("gsm_level", CHANNEL_GROUP_DEVICE),

    /**
     * Channel voltage pandora chennels const.
     */
    CHANNEL_VOLTAGE("voltage", CHANNEL_GROUP_TELEMETRY),
    /**
     * Channel engine rpm pandora chennels const.
     */
    CHANNEL_ENGINE_RPM("engine_rpm", CHANNEL_GROUP_TELEMETRY),
    /**
     * Channel engine temperature pandora chennels const.
     */
    CHANNEL_ENGINE_TEMPERATURE("engine_temp", CHANNEL_GROUP_TELEMETRY),
    /**
     * Channel outer temperature pandora chennels const.
     */
    CHANNEL_OUTER_TEMPERATURE("outer_temp", CHANNEL_GROUP_TELEMETRY),
    /**
     * Channel cabin temperature pandora chennels const.
     */
    CHANNEL_CABIN_TEMPERATURE("cabin_temp", CHANNEL_GROUP_TELEMETRY),
    /**
     * Channel fuel pandora chennels const.
     */
    CHANNEL_FUEL("fuel", CHANNEL_GROUP_TELEMETRY),
    /**
     * Channel speed pandora chennels const.
     */
    CHANNEL_SPEED("speed", CHANNEL_GROUP_TELEMETRY),
    /**
     * Channel mileage pandora chennels const.
     */
    CHANNEL_MILEAGE("mileage", CHANNEL_GROUP_TELEMETRY),
    /**
     * Channel latitude pandora chennels const.
     */
    CHANNEL_LATITUDE("lat", CHANNEL_GROUP_TELEMETRY),
    /**
     * Channel longitude pandora chennels const.
     */
    CHANNEL_LONGITUDE("lon", CHANNEL_GROUP_TELEMETRY),

    /**
     * Channel locked pandora chennels const.
     */
    CHANNEL_LOCKED("locked", CHANNEL_GROUP_STATES),
    /**
     * Channel alarm pandora chennels const.
     */
    CHANNEL_ALARM("alarm", CHANNEL_GROUP_STATES),
    /**
     * Channel engine pandora chennels const.
     */
    CHANNEL_ENGINE("engine", CHANNEL_GROUP_STATES),
    /**
     * Channel ignition pandora chennels const.
     */
    CHANNEL_IGNITION("ignition", CHANNEL_GROUP_STATES),
    /**
     * Channel autostart init pandora chennels const.
     */
    CHANNEL_AUTOSTART_INIT("autostart_init", CHANNEL_GROUP_STATES),
    /**
     * Channel immo pandora chennels const.
     */
    CHANNEL_IMMO("immo", CHANNEL_GROUP_STATES),
    /**
     * Channel light pandora chennels const.
     */
    CHANNEL_LIGHT("light", CHANNEL_GROUP_STATES),

    /**
     * Channel door front left pandora chennels const.
     */
    CHANNEL_DOOR_FRONT_LEFT("door_front_left", CHANNEL_GROUP_STATES),
    /**
     * Channel door front right pandora chennels const.
     */
    CHANNEL_DOOR_FRONT_RIGHT("door_front_right", CHANNEL_GROUP_STATES),
    /**
     * Channel door back left pandora chennels const.
     */
    CHANNEL_DOOR_BACK_LEFT("door_back_left", CHANNEL_GROUP_STATES),
    /**
     * Channel door back right pandora chennels const.
     */
    CHANNEL_DOOR_BACK_RIGHT("door_back_right", CHANNEL_GROUP_STATES),
    /**
     * Channel trunk pandora chennels const.
     */
    CHANNEL_TRUNK("trunk", CHANNEL_GROUP_STATES),
    /**
     * Channel hood pandora chennels const.
     */
    CHANNEL_HOOD("hood", CHANNEL_GROUP_STATES),

    /**
     * Channel handbrake pandora chennels const.
     */
    CHANNEL_HANDBRAKE("handbrake", CHANNEL_GROUP_STATES),
    /**
     * Channel brakes pandora chennels const.
     */
    CHANNEL_BRAKES("brakes", CHANNEL_GROUP_STATES),
    /**
     * Channel active secure pandora chennels const.
     */
    CHANNEL_ACTIVE_SECURE("active_secure", CHANNEL_GROUP_STATES),
    /**
     * Channel evaq pandora chennels const.
     */
    CHANNEL_EVAQ("evaq", CHANNEL_GROUP_STATES),
    /**
     * Channel maintenance pandora chennels const.
     */
    CHANNEL_MAINTENANCE("maintenance", CHANNEL_GROUP_STATES),

    /**
     * Channel handsfree lock pandora chennels const.
     */
    CHANNEL_HANDSFREE_LOCK("handsfree_lock", CHANNEL_GROUP_OPTIONS),
    /**
     * Channel handsfree unlock pandora chennels const.
     */
    CHANNEL_HANDSFREE_UNLOCK("handsfree_unlock", CHANNEL_GROUP_OPTIONS),
    /**
     * Channel gsm pandora chennels const.
     */
    CHANNEL_GSM("gsm", CHANNEL_GROUP_OPTIONS),
    /**
     * Channel gps pandora chennels const.
     */
    CHANNEL_GPS("gps", CHANNEL_GROUP_OPTIONS),
    /**
     * Channel tracking pandora chennels const.
     */
    CHANNEL_TRACKING("tracking", CHANNEL_GROUP_OPTIONS),

    /**
     * Channel extsensor alert zone pandora chennels const.
     */
    CHANNEL_EXTSENSOR_ALERT_ZONE("extsensor_alert_zone", CHANNEL_GROUP_OPTIONS),
    /**
     * Channel extsensor main zone pandora chennels const.
     */
    CHANNEL_EXTSENSOR_MAIN_ZONE("extsensor_main_zone", CHANNEL_GROUP_OPTIONS),
    /**
     * Channel sensor alert zone pandora chennels const.
     */
    CHANNEL_SENSOR_ALERT_ZONE("sensor_alert_zone", CHANNEL_GROUP_OPTIONS),
    /**
     * Channel sensor main zone pandora chennels const.
     */
    CHANNEL_SENSOR_MAIN_ZONE("sensor_main_zone", CHANNEL_GROUP_OPTIONS),

    /**
     * Channel states autostart pandora chennels const.
     */
    CHANNEL_STATES_AUTOSTART("autostart", CHANNEL_GROUP_OPTIONS),
    /**
     * Channel sms pandora chennels const.
     */
    CHANNEL_SMS("sms", CHANNEL_GROUP_OPTIONS),
    /**
     * Channel call pandora chennels const.
     */
    CHANNEL_CALL("call", CHANNEL_GROUP_OPTIONS),
    /**
     * Channel sound alert pandora chennels const.
     */
    CHANNEL_SOUND_ALERT("sound_alert", CHANNEL_GROUP_OPTIONS),
    /**
     * Channel sound main pandora chennels const.
     */
    CHANNEL_SOUND_MAIN("sound_main", CHANNEL_GROUP_OPTIONS),
    /**
     * Channel preheater pandora chennels const.
     */
    CHANNEL_PREHEATER("preheater", CHANNEL_GROUP_OPTIONS),
    /**
     * Channel programm preheat pandora chennels const.
     */
    CHANNEL_PROGRAMM_PREHEAT("programm_preheat", CHANNEL_GROUP_OPTIONS),
    /**
     * Channel stay home pandora chennels const.
     */
    CHANNEL_STAY_HOME("stay_home", CHANNEL_GROUP_OPTIONS),
    /**
     * Channel disable request metka pandora chennels const.
     */
    CHANNEL_DISABLE_REQUEST_METKA("disable_request_metka", CHANNEL_GROUP_OPTIONS),
    /**
     * Channel disable unlock without metka pandora chennels const.
     */
    CHANNEL_DISABLE_UNLOCK_WITHOUT_METKA("disable_unlock_without_metka", CHANNEL_GROUP_OPTIONS),

    /**
     * Channel command pandora chennels const.
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
