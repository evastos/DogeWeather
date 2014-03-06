package com.evastos.dogeweather.api.model;

/**
 * The Class LocationQuery.
 *
 * @author Eva
 */
public class LocationQuery {

    private String query;

    public LocationQuery(String query) {
        this.query = query;
    }

    @Override
    public String toString() {
        return query.replace(" ", "+");
    }
}
