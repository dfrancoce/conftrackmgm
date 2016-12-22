package org.dfc.conftrackmgm.model;

public class Talk {
    private String title;
    private int duration;
    private TalkState state;

    public Talk(String title, int duration) {
        this.title = title;
        this.duration = duration;
        this.state = TalkState.NOT_SCHEDULED;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public TalkState getState() {
        return state;
    }

    public void setState(TalkState state) {
        this.state = state;
    }
}
