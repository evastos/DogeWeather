package com.evastos.dogeweather.api.model;

/**
 * The Class Request.
 *
 * @author Eva
 */
public class Request {

    private String query;

    @Override
    public String toString() {
        return "Request{" +
                "query='" + query + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    private String type;

    public String getQuery() {
        return query;
    }

    public String getType() {
        return type;
    }
}
