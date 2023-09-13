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
package org.openhab.binding.pandoracaralarmsystem.internal.api.record;

import com.google.gson.annotations.SerializedName;

/**
 * The {@link StatRecord} State Description of device.
 *
 * @author Dmitry P. - Initial contribution
 */
public class StatRecord {
    /**
     * The Online.
     */
    public Long online;
    /**
     * The Move.
     */
    public Long move;
    /**
     * The Dtime.
     */
    public Long dtime;
    /**
     * The Dtime rec.
     */
    public Long dtime_rec;
    /**
     * The Voltage.
     */
    public Float voltage;

    /**
     * The Engine temperature.
     */
    @SerializedName("engine_temp")
    public Long engineTemperature;

    /**
     * The Lat.
     */
    @SerializedName("x")
    public Float lat;

    /**
     * The Lon.
     */
    @SerializedName("y")
    public Float lon;

    /**
     * The Bit states.
     */
    @SerializedName("bit_state_1")
    public Long bitStates;

    /**
     * The Outer temperature.
     */
    @SerializedName("out_temp")
    public Long outerTemperature;

    /**
     * The Balance.
     */
    public Balance balance;

    /**
     * The Phone number.
     */
    public String phoneNumber;

    /**
     * The Speed.
     */
    public Long speed;

    /**
     * The Engine rpm.
     */
    @SerializedName("engine_rpm")
    public Long engineRPM;

    /**
     * The Fuel.
     */
    public Long fuel;

    /**
     * The Cabin temperature.
     */
    @SerializedName("cabin_temp")
    public Long cabinTemperature;

    /**
     * The Gsm level.
     */
    @SerializedName("gsm_level")
    public Long gsmLevel;

    /**
     * The Evaq.
     */
    public Long evaq;
    /**
     * The Mileage.
     */
    public Long mileage;

    /**
     * The Mileage can.
     */
    @SerializedName("mileage_CAN")
    public Long mileageCAN;

    /**
     * The Metka.
     */
    public Long metka;
    /**
     * The Brelok.
     */
    public Long brelok;
    /**
     * The Relay.
     */
    public Long relay;
    /**
     * The Smeter.
     */
    public Long smeter;
    /**
     * The Tconsum.
     */
    public Long tconsum;
    /**
     * The Land.
     */
    public Long land;
    /**
     * The Bunker.
     */
    public Long bunker;
    /**
     * The Ex status.
     */
    public Long ex_status;

    /**
     * The Engine remains.
     */
    @SerializedName("engine_remains")
    public Long engineRemains;

    /**
     * The type Balance.
     */
    public static class Balance {
        /**
         * The Value.
         */
        public String value;
        /**
         * The Cur.
         */
        public String cur;
    }
}
