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

import static org.openhab.binding.pandoracaralarmsystem.internal.PandoraChannelsConst.CHANNEL_DEVICE_STATUS;

import java.util.Map;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.pandoracaralarmsystem.internal.PandoraCarAlarmSystemConfiguration;
import org.openhab.binding.pandoracaralarmsystem.internal.api.PandoraApi;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.thing.*;
import org.openhab.core.thing.binding.BaseThingHandler;
import org.openhab.core.types.Command;
import org.openhab.core.types.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link PandoraCarAlarmThingHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Dmitry P. - Initial contribution
 */
@NonNullByDefault
public class PandoraCarAlarmThingHandler extends BaseThingHandler {
    private final Logger logger = LoggerFactory.getLogger(PandoraCarAlarmThingHandler.class);

    private @Nullable Bridge bridge;
    private String deviceId = "";

    /**
     * Gets device id.
     *
     * @return the device id
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * Sets device id.
     *
     * @param deviceId the device id
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * Instantiates a new Pandora car alarm system handler.
     *
     * @param thing the thing
     */
    public PandoraCarAlarmThingHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        logger.debug("Thing: Send command {} to channel {}", command.toString(), channelUID.getId());
        Bridge bridge = getBridge();
        if (bridge == null) {
            return;
        }
        PandoraCarAlarmSystemBridgeHandler pandoraBridgeHandler = (PandoraCarAlarmSystemBridgeHandler) bridge
                .getHandler();
        if (pandoraBridgeHandler == null) {
            return;
        }
        pandoraBridgeHandler.sendCommand(channelUID, command, deviceId);
    }

    @Override
    public void initialize() {
        @Nullable
        PandoraCarAlarmSystemConfiguration config = getConfigAs(PandoraCarAlarmSystemConfiguration.class);
        bridge = getBridge();
        logger.debug("initializing Pandora Car Alarm System binding");

        if (config == null) {
            logger.warn("config undefined");
        }
        if (bridge == null) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR, "No bridge is selected.");
        } else {
            if (bridge.getHandler() == null) {
                updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.BRIDGE_UNINITIALIZED);
                return;
            }
            bridge.getHandler().childHandlerInitialized(this, this.getThing());
            PandoraApi pandoraApi = ((PandoraCarAlarmSystemBridgeHandler) bridge.getHandler()).api;
            if (pandoraApi != null) {
                updateStatus(ThingStatus.UNKNOWN, ThingStatusDetail.NONE);
            } else {
                updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.BRIDGE_UNINITIALIZED);
            }
        }
    }

    private void updateThingStatus(State state) {
        if (state.equals(OnOffType.ON)) {
            updateStatus(ThingStatus.ONLINE);
        } else {
            updateStatus(ThingStatus.OFFLINE);
        }
    }

    /**
     * Update by map.
     *
     * @param mapChannelState the map channel state
     */
    public void updateByMap(Map<String, State> mapChannelState) {
        mapChannelState.forEach((channelId, state) -> {
            update(channelId, state);
        });
    }

    /**
     * Update.
     *
     * @param channelId the channel id
     * @param state the state
     */
    public void update(String channelId, State state) {
        if (isLinked(channelId)) {
            logger.debug("Device {}, channel {} set to {}", getDeviceId(), channelId, state);
            updateState(channelId, state);
        }
        if (CHANNEL_DEVICE_STATUS.getName().equals(channelId)) {
            updateThingStatus(state);
        }
    }
}
