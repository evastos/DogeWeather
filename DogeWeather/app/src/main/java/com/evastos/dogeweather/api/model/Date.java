package com.evastos.dogeweather.api.model;

import com.evastos.dogeweather.utils.DateUtils;

/**
 * The Class Date.
 *
 * @author Eva
 */
public class Date {

    /**
     * The Enum for date constants.
     */
    public enum DateConst {
        TODAY("today"), TOMORROW("tomorrow");

        private String dateConstString;

        private DateConst(String dateString) {
            this.dateConstString = dateString;
        }
        public String toString() {
            return dateConstString;
        }
    }

    private String dateString;

    public Date (java.util.Date date) {
        dateString = DateUtils.formatsDate(date);
    }

    public Date (DateConst dateConst) {
        dateString = dateConst.toString();
    }

    public String toString() {
        return dateString;
    }

}
