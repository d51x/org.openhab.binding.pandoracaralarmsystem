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
package org.openhab.binding.pandoracaralarmsystem.internal.handlers;

import static org.openhab.binding.pandoracaralarmsystem.internal.PandoraCarAlarmSystemBindingConstants.*;
import static org.openhab.binding.pandoracaralarmsystem.internal.PandoraChannelsConst.*;
import static org.openhab.binding.pandoracaralarmsystem.internal.api.ApiConstants.*;

import java.util.*;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.pandoracaralarmsystem.internal.PandoraCarAlarmSystemConfiguration;
import org.openhab.binding.pandoracaralarmsystem.internal.api.*;
import org.openhab.binding.pandoracaralarmsystem.internal.api.record.StatRecord;
import org.openhab.binding.pandoracaralarmsystem.internal.api.response.ApiDevicesResponse;
import org.openhab.binding.pandoracaralarmsystem.internal.api.response.ApiUpdateResponse;
import org.openhab.core.library.types.DecimalType;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.library.types.OpenClosedType;
import org.openhab.core.library.types.StringType;
import org.openhab.core.thing.*;
import org.openhab.core.thing.binding.BaseBridgeHandler;
import org.openhab.core.thing.binding.ThingHandler;
import org.openhab.core.thing.binding.ThingHandlerService;
import org.openhab.core.types.Command;
import org.openhab.core.types.RefreshType;
import org.openhab.core.types.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * The type Pandora car alarm system bridge handler.
 *
 * @author Dmitry P. - Initial contribution
 */
@NonNullByDefault
public class PandoraCarAlarmSystemBridgeHandler extends BaseBridgeHandler {
    /**
     * The Logger.
     */
    Logger logger = LoggerFactory.getLogger(PandoraCarAlarmSystemBridgeHandler.class);

    private @Nullable ScheduledFuture<?> pandoraPollingJob = null;
    private final PandoraApiFactory apiFactory;
    /**
     * The Pandora cas configuration.
     */
    public @Nullable PandoraCarAlarmSystemConfiguration pandoraCASConfiguration = new PandoraCarAlarmSystemConfiguration();
    /**
     * The Api.
     */
    public @Nullable PandoraApi api;

    private Map<String, PandoraCarAlarmThingHandler> mapPandoraThingHandler = new HashMap<String, PandoraCarAlarmThingHandler>();

    /**
     * Instantiates a new Pandora car alarm system bridge handler.
     *
     * @param bridge the bridge
     * @param apiFactory the api factory
     */
    public PandoraCarAlarmSystemBridgeHandler(Bridge bridge, PandoraApiFactory apiFactory) {
        super(bridge);
        this.apiFactory = apiFactory;
    }

    @Override
    public void initialize() {
        pandoraCASConfiguration = getConfigAs(PandoraCarAlarmSystemConfiguration.class);
        updateStatus(ThingStatus.UNKNOWN);
        this.pandoraPollingJob = scheduler.scheduleWithFixedDelay(this::pollState, 0,
                pandoraCASConfiguration != null ? pandoraCASConfiguration.pollingInterval
                        : DEFAULT_POLLING_INTERVAL_SEC,
                TimeUnit.SECONDS);
    }

    @Override
    public void childHandlerInitialized(final ThingHandler childHandler, final Thing childThing) {
        cleanOldHandlers();
        String deviceId = (String) childThing.getConfiguration().get("deviceId");
        logger.debug("Child Things with device ID {} initialized", deviceId);
        PandoraCarAlarmThingHandler pandoraChildHandler = (PandoraCarAlarmThingHandler) childHandler;
        pandoraChildHandler.setDeviceId(deviceId);
        mapPandoraThingHandler.putIfAbsent(deviceId, pandoraChildHandler);
    }

    @Override
    public void childHandlerDisposed(final ThingHandler childHandler, final Thing childThing) {
        cleanOldHandlers();
        String deviceId = (String) childThing.getConfiguration().get("deviceId");
        logger.debug("Child Things with device ID {} disposed", deviceId);
        PandoraCarAlarmThingHandler pandoraChildHandler = (PandoraCarAlarmThingHandler) childHandler;
        pandoraChildHandler.setDeviceId("");
        mapPandoraThingHandler.remove(deviceId);
    }

    @Override
    public void handleConfigurationUpdate(Map<String, Object> configurationParameters) {
        super.handleConfigurationUpdate(configurationParameters);
    }

    @Override
    public void thingUpdated(Thing thing) {
        super.thingUpdated(thing);
    }

    private void cleanOldHandlers() {
        List<String> devices = new ArrayList<>();
        getThing().getThings().forEach(th -> {
            String dId = (String) th.getConfiguration().get("deviceId");
            devices.add(dId);
        });

        if (!mapPandoraThingHandler.isEmpty()) {
            mapPandoraThingHandler.forEach((k, v) -> {
                if (!devices.isEmpty() && !devices.contains(k)) {
                    mapPandoraThingHandler.remove(k);
                }
            });
        }
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        if (api == null)
            return;
        PandoraApi localApi = api;
        logger.debug("Bridge: Send command {} to device {}", command.toString(), channelUID.getId());
    }

    public void sendCommand(ChannelUID channelUID, Command command, String deviceId) {
        if (api == null)
            return;
        PandoraApi localApi = api;
        ApiCommands apiCommand = ApiCommands.CMD_UNKNOWN;

        if (command == RefreshType.REFRESH) {
            return;
        }
        logger.debug("Bridge: Send command {} to device {}", command.toString(), channelUID.getId());
        if (CHANNEL_LOCKED.getName().equals(channelUID.getId())) {
            if (OnOffType.ON == command) {
                apiCommand = ApiCommands.CMD_LOCK;
            } else if (OnOffType.OFF == command) {
                apiCommand = ApiCommands.CMD_UNLOCK;
            }
        } else if (CHANNEL_ACTIVE_SECURE.getName().equals(channelUID.getId())) {
            if (OnOffType.ON == command) {
                apiCommand = ApiCommands.CMD_ACTIVE_SECURE_ON;
            } else if (OnOffType.OFF == command) {
                apiCommand = ApiCommands.CMD_ACTIVE_SECURE_OFF;
            }
        } else if (CHANNEL_ENGINE.getName().equals(channelUID.getId())) {
            if (OnOffType.ON == command) {
                apiCommand = ApiCommands.CMD_ENGINE_START;
            } else if (OnOffType.OFF == command) {
                apiCommand = ApiCommands.CMD_ENGINE_STOP;
            }
        } else if (CHANNEL_TRACKING.getName().equals(channelUID.getId())) {
            if (OnOffType.ON == command) {
                apiCommand = ApiCommands.CMD_TRACKING_ON;
            } else if (OnOffType.OFF == command) {
                apiCommand = ApiCommands.CMD_TRACKING_OFF;
            }
        } else if (CHANNEL_PREHEATER.getName().equals(channelUID.getId())) {
            if (OnOffType.ON == command) {
                apiCommand = ApiCommands.CMD_PREHEATER_ON;
            } else if (OnOffType.OFF == command) {
                apiCommand = ApiCommands.CMD_PREHEATER_OFF;
            }
        } else if (CHANNEL_MAINTENANCE.getName().equals(channelUID.getId())) {
            if (OnOffType.ON == command) {
                apiCommand = ApiCommands.CMD_MAINTENANCE_ON;
            } else if (OnOffType.OFF == command) {
                apiCommand = ApiCommands.CMD_MAINTENANCE_OFF;
            }
        } else if (CHANNEL_COMMAND.getName().equals(channelUID.getId())) {
            apiCommand = ApiCommands.getByCommand(command.toString());
        }

        try {
            localApi.sendCommand(deviceId, apiCommand);
        } catch (ApiException e) {
            throw new RuntimeException("Sending unknown command");
        }
    }

    private void pollState() {
        PandoraApi localApi = api;
        try {
            if (localApi == null) {
                api = localApi = apiFactory.getApi(this);
                localApi.initialize();
            }
            localApi.update();
        } catch (ApiException e) {
            api = null;
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, e.getMessage());
        }
    }

    @Override
    public Collection<Class<? extends ThingHandlerService>> getServices() {
        return super.getServices();
    }

    @Override
    public void dispose() {
        Future<?> future = this.pandoraPollingJob;
        if (future != null) {
            future.cancel(true);
            this.pandoraPollingJob = null;
        }
        api = null; // re-initialize api after configuration change

        super.dispose();
    }

    /**
     * Updates a channel with a new state for a child of this bridge using the deviceId
     *
     * @param deviceId the device id
     * @param channelID the channel id
     * @param state the state
     */
    public void update(String deviceId, String channelID, State state) {
        if (mapPandoraThingHandler.containsKey(deviceId)) {
            PandoraCarAlarmThingHandler thingHandler = mapPandoraThingHandler.get(deviceId);
            logger.debug("update channdel {} for device {}", channelID, deviceId);
            thingHandler.update(channelID, state);
        } else {
            logger.error("Can't update state for channel: {} for device: {}, handler not found", channelID, deviceId);
        }
    }

    /**
     * Updates the bridges channels with a new state.
     *
     * @param channelID the channel id
     * @param state the state
     */
    public void update(String channelID, State state) {
        updateState(channelID, state);
    }

    /**
     * Updates the bridges channels with a new state.
     *
     * @param status the status
     */
    public void updateThingStatus(ThingStatus status) {
        updateStatus(status);
    }

    /**
     * Update device info.
     *
     * @param device the device
     */
    public void updateDeviceInfo(ApiDevicesResponse device) {
        String deviceId = device.id.toString();
        if (mapPandoraThingHandler.containsKey(deviceId)) {
            PandoraCarAlarmThingHandler thingHandler = (PandoraCarAlarmThingHandler) mapPandoraThingHandler
                    .get(deviceId);
            if (thingHandler != null) {
                thingHandler.update(CHANNEL_DEVICE_NAME.getName(), new StringType(device.name));
                thingHandler.update(CHANNEL_DEVICE_MODEL.getName(), new StringType(device.model));
                thingHandler.update(CHANNEL_DEVICE_FIRMWARE.getName(), new StringType(device.firmware));
            }
        }
    }

    /**
     * Update stats.
     *
     * @param update the update
     */
    public void updateStats(ApiUpdateResponse update) {
        Object stats = update.stats;
        mapPandoraThingHandler.forEach((k, v) -> {
            Map<String, Object> statObj = (Map<String, Object>) stats;
            if (!statObj.containsKey(k)) {
                v.update(CHANNEL_DEVICE_STATUS.getName(), OnOffType.OFF);
                logger.error("Not found device Id = {} in updates", k);
                return;
            }
            StatRecord stat = new Gson().fromJson(statObj.get(k).toString(), StatRecord.class);
            if (stat == null) {
                v.update(CHANNEL_DEVICE_STATUS.getName(), OnOffType.OFF);
                logger.error("States update is empty for device Id = {}", k);
                return;
            }

            Map<String, State> mapChannelsState = new HashMap<>();
            mapChannelsState.put(CHANNEL_DEVICE_STATUS.getName(), OnOffType.from(stat.online == 1));
            mapChannelsState.put(CHANNEL_DEVICE_MOVEMENT.getName(), OnOffType.from(stat.move == 1));

            // v.update(CHANNEL_DEVICE_STATUS, OnOffType.from(stat.online == 1));
            mapChannelsState.put(CHANNEL_BALANCE.getName(), new StringType(stat.balance.value));
            mapChannelsState.put(CHANNEL_GSM_LEVEL.getName(), new DecimalType(stat.gsmLevel.longValue()));
            mapChannelsState.put(CHANNEL_VOLTAGE.getName(), new DecimalType(stat.voltage.floatValue()));
            mapChannelsState.put(CHANNEL_ENGINE_RPM.getName(), new DecimalType(stat.engineRPM.longValue() * 10));
            mapChannelsState.put(CHANNEL_ENGINE_TEMPERATURE.getName(),
                    new DecimalType(stat.engineTemperature.longValue()));
            mapChannelsState.put(CHANNEL_CABIN_TEMPERATURE.getName(),
                    new DecimalType(stat.cabinTemperature.longValue()));
            mapChannelsState.put(CHANNEL_OUTER_TEMPERATURE.getName(),
                    new DecimalType(stat.outerTemperature.longValue()));
            mapChannelsState.put(CHANNEL_FUEL.getName(), new DecimalType(stat.fuel.longValue()));
            mapChannelsState.put(CHANNEL_SPEED.getName(), new DecimalType(stat.speed.longValue()));
            mapChannelsState.put(CHANNEL_MILEAGE.getName(), new DecimalType(stat.mileage.floatValue()));
            mapChannelsState.put(CHANNEL_MILEAGE_CAN.getName(), new DecimalType(stat.mileageCAN.longValue()));
            mapChannelsState.put(CHANNEL_LATITUDE.getName(), new DecimalType(stat.lat.floatValue()));
            mapChannelsState.put(CHANNEL_LONGITUDE.getName(), new DecimalType(stat.lon.floatValue()));

            // bits
            long bitStates = stat.bitStates.longValue();

            mapChannelsState.put(CHANNEL_LOCKED.getName(), OnOffType.from(checkBit(bitStates, STATE_LOCKED)));
            mapChannelsState.put(CHANNEL_ALARM.getName(), OnOffType.from(checkBit(bitStates, STATE_ALARM)));
            mapChannelsState.put(CHANNEL_ENGINE.getName(), OnOffType.from(checkBit(bitStates, STATE_ENGINE)));
            mapChannelsState.put(CHANNEL_IGNITION.getName(), OnOffType.from(checkBit(bitStates, STATE_IGNITION)));

            mapChannelsState.put(CHANNEL_AUTOSTART_INIT.getName(),
                    OnOffType.from(checkBit(bitStates, STATE_AUTOSTART_INIT)));
            mapChannelsState.put(CHANNEL_HANDSFREE_LOCK.getName(),
                    OnOffType.from(checkBit(bitStates, STATE_HANDSFREE_LOCK)));
            mapChannelsState.put(CHANNEL_HANDSFREE_UNLOCK.getName(),
                    OnOffType.from(checkBit(bitStates, STATE_HANDSFREE_UNLOCK)));
            mapChannelsState.put(CHANNEL_GSM.getName(), OnOffType.from(checkBit(bitStates, STATE_GSM)));
            mapChannelsState.put(CHANNEL_GPS.getName(), OnOffType.from(checkBit(bitStates, STATE_GPS)));
            mapChannelsState.put(CHANNEL_TRACKING.getName(), OnOffType.from(checkBit(bitStates, STATE_TRACKING)));
            mapChannelsState.put(CHANNEL_IMMO.getName(), OnOffType.from(checkBit(bitStates, STATE_IMMO)));
            mapChannelsState.put(CHANNEL_EXTSENSOR_ALERT_ZONE.getName(),
                    OnOffType.from(checkBit(bitStates, STATE_EXT_SENSOR_ALERT_ZONE)));
            mapChannelsState.put(CHANNEL_EXTSENSOR_MAIN_ZONE.getName(),
                    OnOffType.from(checkBit(bitStates, STATE_EXT_SENSOR_MAIN_ZONE)));
            mapChannelsState.put(CHANNEL_SENSOR_ALERT_ZONE.getName(),
                    OnOffType.from(checkBit(bitStates, STATE_SENSOR_ALERT_ZONE)));
            mapChannelsState.put(CHANNEL_SENSOR_MAIN_ZONE.getName(),
                    OnOffType.from(checkBit(bitStates, STATE_SENSOR_MAIN_ZONE)));
            mapChannelsState.put(CHANNEL_STATES_AUTOSTART.getName(),
                    OnOffType.from(checkBit(bitStates, STATE_AUTOSTART)));
            mapChannelsState.put(CHANNEL_SMS.getName(), OnOffType.from(checkBit(bitStates, STATE_SMS)));
            mapChannelsState.put(CHANNEL_CALL.getName(), OnOffType.from(checkBit(bitStates, STATE_CALL)));
            mapChannelsState.put(CHANNEL_LIGHT.getName(), OnOffType.from(checkBit(bitStates, STATE_LIGHT)));
            mapChannelsState.put(CHANNEL_SOUND_ALERT.getName(), OnOffType.from(checkBit(bitStates, STATE_SOUND_ALERT)));
            mapChannelsState.put(CHANNEL_SOUND_MAIN.getName(), OnOffType.from(checkBit(bitStates, STATE_SOUND_MAIN)));

            mapChannelsState.put(CHANNEL_DOOR_FRONT_LEFT.getName(),
                    checkBit(bitStates, STATE_DOOR_FRONT_LEFT) ? OpenClosedType.OPEN : OpenClosedType.CLOSED);
            mapChannelsState.put(CHANNEL_DOOR_FRONT_RIGHT.getName(),
                    checkBit(bitStates, STATE_DOOR_FRONT_RIGHT) ? OpenClosedType.OPEN : OpenClosedType.CLOSED);
            mapChannelsState.put(CHANNEL_DOOR_BACK_LEFT.getName(),
                    checkBit(bitStates, STATE_DOOR_BACK_LEFT) ? OpenClosedType.OPEN : OpenClosedType.CLOSED);
            mapChannelsState.put(CHANNEL_DOOR_BACK_RIGHT.getName(),
                    checkBit(bitStates, STATE_DOOR_BACK_RIGHT) ? OpenClosedType.OPEN : OpenClosedType.CLOSED);
            mapChannelsState.put(CHANNEL_TRUNK.getName(),
                    checkBit(bitStates, STATE_TRUNK) ? OpenClosedType.OPEN : OpenClosedType.CLOSED);
            mapChannelsState.put(CHANNEL_HOOD.getName(),
                    checkBit(bitStates, STATE_HOOD) ? OpenClosedType.OPEN : OpenClosedType.CLOSED);

            mapChannelsState.put(CHANNEL_HANDBRAKE.getName(), OnOffType.from(checkBit(bitStates, STATE_HANDBRAKE)));
            mapChannelsState.put(CHANNEL_BRAKES.getName(), OnOffType.from(checkBit(bitStates, STATE_BRAKES)));
            mapChannelsState.put(CHANNEL_PREHEATER.getName(), OnOffType.from(checkBit(bitStates, STATE_PREHEATER)));
            mapChannelsState.put(CHANNEL_ACTIVE_SECURE.getName(),
                    OnOffType.from(checkBit(bitStates, STATE_ACTIVE_SECURE)));
            mapChannelsState.put(CHANNEL_PROGRAMM_PREHEAT.getName(),
                    OnOffType.from(checkBit(bitStates, STATE_PROGRAMM_PREHEAT)));
            mapChannelsState.put(CHANNEL_EVAQ.getName(), OnOffType.from(checkBit(bitStates, STATE_EVAQ)));
            mapChannelsState.put(CHANNEL_MAINTENANCE.getName(), OnOffType.from(checkBit(bitStates, STATE_MAINTENANCE)));
            mapChannelsState.put(CHANNEL_STAY_HOME.getName(), OnOffType.from(checkBit(bitStates, STATE_STAY_HOME)));
            mapChannelsState.put(CHANNEL_DISABLE_REQUEST_METKA.getName(),
                    OnOffType.from(checkBit(bitStates, STATE_DISABLE_REQUEST_METKA)));
            mapChannelsState.put(CHANNEL_DISABLE_UNLOCK_WITHOUT_METKA.getName(),
                    OnOffType.from(checkBit(bitStates, STATE_DISABLE_UNLOCK_WITHOUT_METKA)));

            v.updateByMap(mapChannelsState);
        });
    }

    private Boolean checkBit(Long value, int bit) {
        return ((value & (1L << bit)) != 0);
    }

    /**
     * Updates the bridges channels with a new state.
     *
     * @param status the status
     * @param statusDetail the status detail
     */
    public void updateThingStatus(ThingStatus status, ThingStatusDetail statusDetail) {
        updateStatus(status, statusDetail);
    }
}
