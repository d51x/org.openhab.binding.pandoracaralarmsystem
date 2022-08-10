package org.openhab.binding.pandoracaralarmsystem.internal.api;

public enum ApiCommands {
    CMD_UNKNOWN(-1),    // неизвестная команда
    CMD_LOCK(1),    //Постановка под охрану
    CMD_UNLOCK(2),  //Снятие с охраны
    CMD_ENGINE_START(4 ), //Запуск двигателя
    CMD_ENGINE_STOP(8 ), //Останов двигателя
    CMD_TRACKING_ON(16 ), //Включение трекинга
    CMD_TRACKING_OFF(32 ), //Выключение трекинга
    CMD_EXT_1(64 ), // доп.команда 1
    CMD_EXT_2(128 ), // доп.команда 2
    CMD_CHECK(255 ), // Команда CHECK
    CMD_ACTIVE_SECURE_ON(17 ), // Включение активной охраны
    CMD_ACTIVE_SECURE_OFF(18 ), // Отключение активной охраны
    CMD_PREHEATER_ON(21 ), // Включение подогревателя
    CMD_PREHEATER_OFF(22 ), // Отключение подогревателя
    CMD_HORN(23 ), // Подача звукового сигнала
    CMD_LIGHT(23 ), // Включение подсветки
    CMD_TIMER_CH_ON(33 ), // Включение таймерного канала
    CMD_TIMER_CH_OFF(34 ), // Выключение таймерного канала
    CMD_TRUNK_OPEN(35 ), // Открытие багажника
    CMD_MAINTENANCE_ON(40 ), // Включение режима ТО
    CMD_MAINTENANCE_OFF(41 ), // Выключение режима ТО
    ;

    private Integer id;

    ApiCommands(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
