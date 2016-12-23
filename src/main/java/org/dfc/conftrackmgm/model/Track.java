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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        getMorningSession().getTalks().forEach(talk -> stringBuilder.append(talk.toString()));
        stringBuilder.append("12:00 PM Lunch\n");
        getAfternoonSession().getTalks().forEach(talk -> stringBuilder.append(talk.toString()));
        stringBuilder.append("05:00 PM Networking Event");

        return stringBuilder.toString();
    }
}
