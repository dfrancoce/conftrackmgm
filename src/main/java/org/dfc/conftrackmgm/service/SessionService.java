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
    @Autowired
    private TalkRepository repository;

    /**
     * Fills a session with the talks retrieved from the input file
     * @param session Session to be filled
     * @param sessionMinutes Duration of the session in minutes
     */
    public void fillSessionWithTalks(Session session, int sessionMinutes) {
        int totalSessionTalksMinutes = 0;
        final List<Talk> talks = repository.getNotScheduledTalks();

        for (Talk talk : talks) {
            int sessionLeftMinutes = sessionMinutes - totalSessionTalksMinutes;
            if (sessionLeftMinutes == 0) break;

            if (sessionLeftMinutes + talk.getDuration() > 0
                    && totalSessionTalksMinutes + talk.getDuration() <= sessionMinutes) {
                 totalSessionTalksMinutes += talk.getDuration();
                 talk.setState(TalkState.SCHEDULED);
                 session.addTalk(talk);
            } else {
                sessionLeftMinutes = Math.abs(sessionLeftMinutes);
                Optional<Talk> talkOptional = repository.findNotScheduledTalkByMinutes(sessionLeftMinutes);

                if (talkOptional.isPresent()) {
                    totalSessionTalksMinutes += talkOptional.get().getDuration();
                    talkOptional.get().setState(TalkState.SCHEDULED);
                    session.addTalk(talkOptional.get());
                } else {
                    repository.resetTalksStateAndShuffleList();
                    session.clearTalks();
                    fillSessionWithTalks(session, sessionMinutes);
                    return;
                }
            }
        }
    }
}
