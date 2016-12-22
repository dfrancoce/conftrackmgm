package org.dfc.conftrackmgm.model;

public class Track {
    private Session morningSession;
    private Session afternoonSession;

    public Track() {
        morningSession = new Session();
        afternoonSession = new Session();
    }

    public Session getMorningSession() {
        return morningSession;
    }

    public Session getAfternoonSession() {
        return afternoonSession;
    }
}
