package org.dfc.conftrackmgm.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Talk {
    private String title;
    private int duration;
    private TalkState state;
    private Date time;

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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Talk talk = (Talk) o;

        if (duration != talk.duration) return false;
        if (title != null ? !title.equals(talk.title) : talk.title != null) return false;
        return state == talk.state;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + duration;
        return result;
    }

    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        return simpleDateFormat.format(time) + " " + title + " " + getDurationForPrinting(duration) + "\n";
    }

    private String getDurationForPrinting(int duration) {
        if (duration == 5) return "lightning";
        else return String.valueOf(duration) + "min";
    }
}
