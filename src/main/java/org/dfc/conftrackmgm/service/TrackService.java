package org.dfc.conftrackmgm.service;

import org.dfc.conftrackmgm.model.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrackService {
    @Autowired
    private SessionService sessionService;

    public Track createTrack() {
        Track track = new Track();
        sessionService.fillSessionWithTalks(track.getMorningSession(), 180);
        sessionService.fillSessionWithTalks(track.getAfternoonSession(), 240);

        return track;
    }
}
