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

import java.util.Arrays;

/**
 * The {@link ApiCommands} ApiCommands.
 *
 * @author Dmitry P. - Initial contribution
 */
public enum ApiCommands {
    CMD_UNKNOWN(-1, "unknown"), // неизвестная команда
    CMD_LOCK(1, "lock"), // Постановка под охрану
    CMD_UNLOCK(2, "unlock"), // Снятие с охраны
    CMD_ENGINE_START(4, "engine_start"), // Запуск двигателя
    CMD_ENGINE_STOP(8, "engine_stop"), // Останов двигателя
    CMD_TRACKING_ON(16, "tracking_on"), // Включение трекинга
    CMD_TRACKING_OFF(32, "tracking_off"), // Выключение трекинга
    CMD_EXT_1(64, "add_cmd_1"), // доп.команда 1
    CMD_EXT_2(128, "add_cmd_2"), // доп.команда 2
    CMD_CONN_PROLONG(240, "prolong_conn"), // Продление связи
    CMD_CONN_TERMINATE(15, "terminate_conn"), // Завершение связи
    CMD_CHECK(255, "check"), // Команда CHECK
    CMD_ACTIVE_SECURE_ON(17, "active_protect_on"), // Включение активной охраны
    CMD_ACTIVE_SECURE_OFF(18, "active_protect_off"), // Отключение активной охраны
    CMD_PREHEATER_ON(21, "preheater_on"), // Включение подогревателя
    CMD_PREHEATER_OFF(22, "preheater_off"), // Отключение подогревателя
    CMD_HORN(23, "horn"), // Подача звукового сигнала
    CMD_BACKLIGHT(24, "backlight"), // Включение подсветки
    CMD_TIMER_CH_ON(33, "timer_ch_enable"), // Включение таймерного канала
    CMD_TIMER_CH_OFF(34, "timer_ch_disable"), // Выключение таймерного канала
    CMD_TRUNK_OPEN(35, "open_trunk"), // Открытие багажника
    CMD_MAINTENANCE_ON(40, "maintenance_on"), // Включение режима ТО
    CMD_MAINTENANCE_OFF(41, "maintenance_off"), // Выключение режима ТО
    ;

    private Integer id;
    private String command;

    ApiCommands(Integer id, String command) {
        this.command = command;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getCommand() {
        return command;
    }

    public static ApiCommands getByCommand(String command) {
        return Arrays.stream(ApiCommands.values()).filter(v -> v.getCommand().equals(command)).findFirst()
                .orElse(CMD_UNKNOWN);
    }

    public Integer getIdByName(String name) {
        return Arrays.stream(ApiCommands.values()).filter(v -> v.getCommand().equals(name)).findFirst()
                .orElse(CMD_UNKNOWN).getId();
    }
}
