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
package org.openhab.binding.pandoracaralarmsystem.internal.api;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jetty.client.HttpClient;
import org.openhab.binding.pandoracaralarmsystem.internal.handlers.PandoraCarAlarmSystemBridgeHandler;
import org.openhab.core.io.net.http.HttpClientFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link PandoraApiFactory} is responsible for creating an instance of the API that is optimized for different
 * firmware versions.
 *
 * @author Dmitry P. - Initial contribution
 */
@Component(service = PandoraApiFactory.class)
@NonNullByDefault
public class PandoraApiFactory {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final HttpClient httpClient;

    /**
     * Instantiates a new Pandora api factory.
     *
     * @param httpClientFactory the http client factory
     */
    @Activate
    public PandoraApiFactory(@Reference HttpClientFactory httpClientFactory) {
        this.httpClient = httpClientFactory.getCommonHttpClient();
    }

    /**
     * Gets api.
     *
     * @param handler the handler
     * @return the api
     * @throws ApiException the api exception
     */
    public PandoraApi getApi(PandoraCarAlarmSystemBridgeHandler handler) throws ApiException {
        return new PandoraApiImpl(handler, httpClient);
    }
}