# Pandora Car Alarm System Binding

Русское описание | [English description](README_EN.md)

![](doc/pandora.png)

Binding использует **_неофициальный API_**, полученный в результате reverse engineering, к официальному сайту Pandora https://p-on.ru.

Для настройки Вам следует использовать те же авторизационные данные, что вы используете на сайте Pandora.

## Supported Things

Можно создавать несколько Bridge и несколькко Things.

- `bridge`: отправляет запросы к Pandora и получает информацию об устройстве, событиях/опциях/состояниях. Не имеет каналов для настройки.

- `thing`: используя Bridge получает и отображает информацию для заданного устройства (ИД Системы)

## Discovery

Discovery не поддерживается

## Binding Configuration

Создайте биндинг и укажите логиин и пароль для доступа к Личному кабинету https://pro.p-on.ru

Бридж отправляет запросы к api с определенной периодичностью. По-умолчанию это значение составляет 5 сек. С такой периодичностью отправляет запросы сам Личный кабинет.

Вы можете поменять это значения в Advansed.

![](doc/bridge_config.png)

Минимальное значение 5 сек.

## Thing Configuration

После настройки бриджа вы можете создать и настроить Things.

Сначала в настройках Thing выберите ранее созданный Bridge.

Затем укажите ИД системы из Личного кабинета

![](doc/profile_en.png)

Скопируйте ИД системы и вставьте

![](doc/thing_config.png)

Если у вас в ЛК привязано несколько систем, то Вы их можете подключить как разные Thing.

## Каналы

Каналы разделены на группы для более удобного восприятия
- _**device**_ - предоставляет информацию о названии, модели, версии прошивки и т.д.
- _**telemetry**_ - предоставляет телеметрию с устройства, такую как напряжение бортовой сети, температуру двигателя, скорость, остаток топлива в баке и т.д. Не все каналы могут быть доступны (отображать актуальную информацию). Это зависит от того, может ли устройство получить такую инфорацию по CAN или другими способами.
- _**states**_ - предоставляет информацию о режиме охраны, состоянии дверей, багажника и т.д.
- _**options**_ - предоставляет информацию о различных опциях системы
- _**actions**_ - выполнение действий

##### Свойства системы (device)

Канал  | Название | Тип  | Advansed | Описание | Единицы измерения |
--- | --- | --- | --- | --- | --- 
device#name | Имя устройства | String |  | Имя устройства | |
device#model  | Модель | String |  | Модель устройства | |
device#firmware | Прошивка | String | | Версия прошивки устройства | |
device#balance | Баланс | String | | Доступный баланс системы | |
device#gsm_level | Уровень сигнала | Number | | Уровень GSM сигнала мобильной сети | Значение от 0 до 3 |
device#status | Статус устройства | Switch | | Статус устройства, установленного в автомобиле. Устройство "В сети" или "Не в сети" | В сети (ON)/ Не в сети (OFF)|
device#movement | Статус движения | Switch | | Отображает, движется автомобиль или нет | В движении (ON)/ Нет движения (OFF) |

##### Телеметрия системы (telemetry)

Канал  | Название                | Тип                | Advansed | Описание                                                                                                                                             | Единицы измерения |
--- |-------------------------|--------------------| --- |------------------------------------------------------------------------------------------------------------------------------------------------------| --- 
telemetry#voltage | Напряжение аккумулятора | Number             |  | Отображает напряжение бортовой сети                                                                                                                  | Вольты |
telemetry#fuel  | Уровень топлива         | Number             |  | Остаток топлва в баке. Система может как отдавать в литрах, так и в процентах. Это настраивается либо в личном кабинете, либо в мобильном приложении | Проценты или литры|
telemetry#engine_temp | Температура двигателя   | Number:Temperature |  | Данные берутся с датчика, обычно, установленного под капотом на патрубок системы охлаждения двигателя                                                | °C |
telemetry#cabin_temp | Температура салона      | Number:Temperature |  | Текущая температура в салоне                                                                                                                         | °C |
telemetry#outer_temp  | Уличная температура     | Number:Temperature | true | Температура окружающей среды при наличии установленого датчика                                                                                       | °C |
telemetry#engine_rpm | Обороты двигателя       | Number             | true | Обороты двигателя                                                                                                                                    | rpm |
telemetry#speed | Скорость                | Number:Speed       | true | Текущая скорость (у меня всегда 0, т.к. нет модуля ПЗЫ, может быть новые версии прошивки умеют читать этот параметр по CAN)                          | км/ч |
telemetry#mileage | Пробег по GPS           | Number             | true | У меня всегда 0, либо потому что нет модуля GPS                                                                                                      | км |
telemetry#mileage_can | Пробег по CAN           | Number             | true | У меня всегда 0, потому что прошивка не умеет забирать это по CAN, либо мозги не умеют отдавать это по CAN                                           | км |
telemetry#lat | Широта                  | Number             | true | Координата широты (нужен модуль GPS)                                                                                                                 |  |
telemetry#lon | Долгота                 | Number             | true | Координата долготы (нужен модуль GPS)                                                                                                                |  |
telemetry#location | Местоположение машины   | Location           | true | Местоположение машины (нужен модуль GPS)                                                                                                             |  |

##### Состояние системы (states)

Канал  | Название | Тип  | Advansed | Описание | Единицы измерения |
--- | --- | --- | --- | --- | --- 
states#locked | Статус охраны | Switch |  | Отображает состояние охраны | Под охраной (ON)/ Снято с охраны (OFF) |
states#alarm  | Статус тревоги | Switch |  | Отображает статус тревоги | Обнаружена тревога (ON)/ Нет тревоги (OFF)|
states#engine   | Статус двигателя | Switch |  | Отображает статус двигателя | Двигатель запущен (ON) / Двигатель заглушен (OFF)  |
states#ignition    | Состояние зажигания | Switch |  true | Отображает состояние зажигания | Зажигание включено (ON) / Зажигание выключено (OFF)  |
states#immo    | Статус блокировки двигателя | Switch |  | Отображает состояние блокировки двигателя | Не заблокирован (OFF) / Автоматически (Anti-Hi-Jack) (ON)  |
states#door_front_left | Лев.пер.дверь | Switch |  | Состояние левой передней двери | Закрыта (CLOSED) / Открыта (OPEN)  |
states#door_front_right | Прав.пер.дверь | Switch |  | Состояние правой передней двери | Закрыта (CLOSED) / Открыта (OPEN)  |
states#door_back_left | Лев.зад.дверь | Switch |  | Состояние левой задней двери | Закрыта (CLOSED) / Открыта (OPEN)  |
states#door_back_right | Прав.зад.дверь | Switch |  | Состояние правой задней двери | Закрыта (CLOSED) / Открыта (OPEN)  |
states#trunk | Багажник | Switch |  | Состояние багажника | Закрыт (CLOSED) / Открыт (OPEN)  |
states#hood | Капот | Switch | true  | Состояние капота | Закрыт (CLOSED) / Открыт (OPEN)  |
states#handbrake | Ручник | Switch | true  | Состояние ручного тормоза | Поднят (ON) / Опущен (OFF)  |
states#brakes | Педаль тормоза | Switch | true  | Состояние педали тормоза | Нажата (ON) / Отпущена (OFF)  |
states#active_secure | Активная охрана | Switch | true  | Состояние активной охраны | Вкл (ON) / Выкл (OFF)  |
states#maintenance | Режим ТО | Switch | true  | Режим ТО | Вкл (ON) / Выкл (OFF)  |

##### Действия (actions)

Канал  | Название | Тип      | Описание |
--- |----------|----------| ----------------------------------------
actions#command | Команда  | Command  | Отправляет выбранную команду из списка |

##### Команды, доступные в Лк Pandora

command  | Название              | id команды Pandora | Описание |
--- |-----------------------|--------------------| ----------------------------------------
lock | Постановка под охрану | 1                  |  |
unlock | Снятие с охраны       | 2                  |  |
engine_start | Запуск двигателя      | 4                  |  |
engine_stop | Останов двигателя     | 8                  |  |
tracking_on | Включение трекинга    | 16                 |  |
tracking_off | Выключение трекинга   | 32                 |  |
add_cmd_1 | доп.команда 1         | 64                 |  |
add_cmd_2 | доп.команда 2         | 128                |  |
prolong_conn | Продление связи         | 240                |  |
terminate_conn | Завершение связи         | 15                 |  |
check | Команда CHECK         | 255                |  |
active_protect_on | Включение активной охраны         | 17                 |  |
active_protect_off | Отключение активной охраны         | 18                 |  |
preheater_on | Включение подогревателя        | 21                 |  |
preheater_off | Отключение подогревателя        | 22                 |  |
horn | Подача звукового сигнала        | 23                 |  |
backlight | Включение подсветки        | 24                 |  |
timer_ch_enable | Включение таймерного канала        | 33                 |  |
timer_ch_disable | Выключение таймерного канала        | 34                 |  |
open_trunk | Открытие багажника       | 35                 |  |
maintenance_on | Включение режима ТО       | 40                 |  |
maintenance_off | Выключение режима ТО       | 41                 |  |


### PS В следующих версиях перечень каналов может быть изменен/переименован. А также может быть изменена группировка.

## Дополнительная информация

Перед тем как начать разрабатывать биндинг, я поизучал как Личный кабинет шлет запросы и получает ответы. Все есть в js-скриптах, которые можно посмотреть через интсрументы разработчика в браузере.

На создание биндинга натолкнула вот эта страничка [https://github.com/turbulator/pandora-cas](https://github.com/turbulator/pandora-cas)

## Отказ от ответственности

Данное программное обеспечение никак не связано и не одобрено ООО «НПО Телеметрия», владельца торговой марки Pandora. Используйте его на свой страх и риск. Автор ни при каких обстоятельствах не несет ответственности за порчу или утрату вашего имущества и возможного вреда в отношении третьих лиц.

Все названия брендов и продуктов принадлежат их законным владельцам.
