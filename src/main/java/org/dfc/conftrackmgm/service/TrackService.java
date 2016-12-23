package org.dfc.conftrackmgm.service;

import org.dfc.conftrackmgm.model.Track;
import org.dfc.conftrackmgm.repository.TalkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TrackService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private static final String MORNING_START_DATE = "09:00 AM";
    private static final String AFTERNOON_START_DATE = "01:00 PM";

    @Autowired
    private SessionService sessionService;
    @Autowired
    private TalkRepository talkRepository;

    /**
     * Creates the tracks from the talks
     * @return List of Tracks
     */
    public List<Track> createTracks() {
        List<Track> tracks = new ArrayList<>();
        while (talkRepository.getNotScheduledTalks().size() > 0) {
            tracks.add(createTrack());
        }

        setTimesToTracksTalks(tracks);
        return tracks;
    }

    private Track createTrack() {
        Track track = new Track();
        sessionService.fillSessionWithTalks(track.getMorningSession(), 180);
        talkRepository.remove(track.getMorningSession().getTalks());
        sessionService.fillSessionWithTalks(track.getAfternoonSession(), 240);
        talkRepository.remove(track.getAfternoonSession().getTalks());

        return track;
    }

    private void setTimesToTracksTalks(List<Track> tracks) {
        tracks.forEach(track -> {
            Optional<Calendar> morningCalendar = getMorningCalendar();
            Optional<Calendar> afternoonCalendar = getAfternoonCalendar();

            morningCalendar.ifPresent(calendar -> track.getMorningSession().getTalks().forEach(talk -> {
                talk.setTime(calendar.getTime());
                calendar.add(Calendar.MINUTE, talk.getDuration());
            }));

            afternoonCalendar.ifPresent(calendar -> track.getAfternoonSession().getTalks().forEach(talk -> {
                talk.setTime(calendar.getTime());
                calendar.add(Calendar.MINUTE, talk.getDuration());
            }));
        });
    }

    private Optional<Calendar> getMorningCalendar() {
        return getCalendar(MORNING_START_DATE);
    }

    private Optional<Calendar> getAfternoonCalendar() {
        return getCalendar(AFTERNOON_START_DATE);
    }

    private Optional<Calendar> getCalendar(String time) {
        Optional<Calendar> calendarOptional = Optional.empty();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");
            Date startDate = formatter.parse(time);
            calendarOptional = Optional.of(Calendar.getInstance());
            calendarOptional.get().setTime(startDate);
        } catch (ParseException e) {
            LOGGER.error("An error occurred trying to parse the date " + time, e);
        }

        return calendarOptional;
    }
}
