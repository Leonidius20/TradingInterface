package ua.leonidius.trdinterface.utils;

import ua.leonidius.trdinterface.Message;

public enum TimeUnit {

    MINUTES(60, Message.MINUTES.getText()),
    HOURS(60 * 60, Message.HOURS.getText()),
    DAYS(24 * 60 * 60, Message.DAYS.getText()),
    MONTHS(30 * 24 * 60 * 60, Message.MONTHS.getText());

    /**
     * time, measured in the unit * multiplier = time in seconds
     */
    private final long multiplier;

    private final String name;

    TimeUnit(long multiplier, String name) {
        this.multiplier = multiplier;
        this.name = name;
    }

    public long getMultiplier() {
        return multiplier;
    }

    public String getName() {
        return name;
    }

}
