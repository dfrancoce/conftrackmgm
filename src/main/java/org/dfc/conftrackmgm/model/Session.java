package org.dfc.conftrackmgm.model;

import java.util.ArrayList;
import java.util.List;

public class Session {
    private List<Talk> talks;

    public Session() {
        talks = new ArrayList<>();
    }

    public void addTalk(Talk talk) {
        talks.add(talk);
    }

    public List<Talk> getTalks() {
        return talks;
    }

    public void clearTalks() {
        talks.clear();
    }
}
