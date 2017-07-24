package org.dfc.conftrackmgm.service;

import org.dfc.conftrackmgm.model.Session;
import org.dfc.conftrackmgm.model.Talk;
import org.dfc.conftrackmgm.model.TalkState;
import org.dfc.conftrackmgm.repository.TalkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SessionService {
    private final TalkRepository repository;

    @Autowired
    public SessionService(TalkRepository repository) {
        this.repository = repository;
    }

    /**
     * Fills a session with the talks retrieved from the input file
     * @param session Session to be filled
     * @param sessionMinutes Duration of the session in minutes
     */
    void fillSessionWithTalks(final Session session, int sessionMinutes) {
        int totalMinutes = 0;
        final List<Talk> talks = repository.getNotScheduledTalks();

        for (Talk talk : talks) {
            int minutesLeft = getMinutesLeft(sessionMinutes, totalMinutes);
            if (minutesLeft == 0) break;

            if (canAddAnotherTalkToSession(talk, sessionMinutes, totalMinutes, minutesLeft)) {
                totalMinutes = addTalkToSession(session, totalMinutes, talk);
            } else {
                Optional<Talk> talkOptional = getNotScheduledTalk(minutesLeft);

                if (talkOptional.isPresent()) {
                    totalMinutes = addTalkToSession(session, totalMinutes, talkOptional.get());
                } else {
                    resetAndClearTalks(session);
                    fillSessionWithTalks(session, sessionMinutes);
                    return;
                }
            }
        }
    }

    private void resetAndClearTalks(Session session) {
        repository.resetTalksStateAndShuffleList();
        session.clearTalks();
    }

    private Optional<Talk> getNotScheduledTalk(int minutesLeft) {
        minutesLeft = Math.abs(minutesLeft);
        return repository.findNotScheduledTalkByMinutes(minutesLeft);
    }

    private int addTalkToSession(final Session session, int totalMinutes, final Talk talk) {
        totalMinutes += talk.getDuration();
        talk.setState(TalkState.SCHEDULED);
        session.addTalk(talk);

        return totalMinutes;
    }

    private boolean canAddAnotherTalkToSession(final Talk talk, int sessionMinutes, int totalMinutes, int minutesLeft) {
        return minutesLeft + talk.getDuration() > 0 && totalMinutes + talk.getDuration() <= sessionMinutes;
    }

    private int getMinutesLeft(int sessionMinutes, int totalMinutes) {
        return sessionMinutes - totalMinutes;
    }
}
