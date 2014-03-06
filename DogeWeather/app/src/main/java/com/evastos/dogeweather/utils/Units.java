package com.evastos.dogeweather.utils;

/**
 * The Enum Units.
 * <p/>
 * Represents enum for imperial and metric units.
 *
 * @author Eva
 */
public enum Units {
    METRIC("Metric"), IMPERIAL("Imperial");

    private String unitString;

    Units(String unitString) {
        this.unitString = unitString;
    }

    @Override
    public String toString() {
        return this.unitString;
    }
}
