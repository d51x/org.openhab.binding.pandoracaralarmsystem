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

import static org.openhab.binding.pandoracaralarmsystem.internal.PandoraCarAlarmSystemBindingConstants.THING_TYPE_BRIDGE_API;
import static org.openhab.binding.pandoracaralarmsystem.internal.PandoraCarAlarmSystemBindingConstants.THING_TYPE_PANDORA_CAS;

import java.util.Set;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.pandoracaralarmsystem.internal.api.PandoraApiFactory;
import org.openhab.binding.pandoracaralarmsystem.internal.handlers.PandoraCarAlarmSystemBridgeHandler;
import org.openhab.binding.pandoracaralarmsystem.internal.handlers.PandoraCarAlarmThingHandler;
import org.openhab.core.thing.Bridge;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingTypeUID;
import org.openhab.core.thing.binding.BaseThingHandlerFactory;
import org.openhab.core.thing.binding.ThingHandler;
import org.openhab.core.thing.binding.ThingHandlerFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The {@link PandoraCarAlarmSystemHandlerFactory} is responsible for creating things and thing
 * handlers.
 *
 * @author Dmitry P. - Initial contribution
 */
@NonNullByDefault
@Component(configurationPid = "binding.pandoracaralarmsystem", service = ThingHandlerFactory.class)
public class PandoraCarAlarmSystemHandlerFactory extends BaseThingHandlerFactory {

    private static final Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Set.of(THING_TYPE_PANDORA_CAS,
            THING_TYPE_BRIDGE_API);

    private final PandoraApiFactory apiFactory;

    /**
     * Instantiates a new Pandora car alarm system handler factory.
     *
     * @param apiFactory the api factory
     */
    @Activate
    public PandoraCarAlarmSystemHandlerFactory(@Reference PandoraApiFactory apiFactory) {
        this.apiFactory = apiFactory;
    }

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID);
    }

    @Override
    protected @Nullable ThingHandler createHandler(Thing thing) {
        ThingTypeUID thingTypeUID = thing.getThingTypeUID();

        if (THING_TYPE_PANDORA_CAS.equals(thingTypeUID)) {
            return new PandoraCarAlarmThingHandler(thing);
        } else if (THING_TYPE_BRIDGE_API.equals(thingTypeUID)) {
            return new PandoraCarAlarmSystemBridgeHandler((Bridge) thing, apiFactory);
        }

        return null;
    }
}
