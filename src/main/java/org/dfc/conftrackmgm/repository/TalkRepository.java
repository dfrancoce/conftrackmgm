package org.dfc.conftrackmgm.repository;

import org.dfc.conftrackmgm.model.Talk;
import org.dfc.conftrackmgm.model.TalkState;
import org.dfc.conftrackmgm.util.TalkFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Repository
public class TalkRepository {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final List<Talk> talks;

    @Autowired
    public TalkRepository(final TalkFileUtils talkFileUtils) {
        this.talks = talkFileUtils.getTalksFromFile();
    }

    /**
     * Finds the first talk with the same duration as the minutes passed by parameter
     * @param minutes Duration
     * @return The first talk found
     */
    public Optional<Talk> findNotScheduledTalkByMinutes(int minutes) {
        LOGGER.info("TalkRepository - findNotScheduledTalkByMinutes - start");
        return talks.stream()
                .filter(t -> t.getState().equals(TalkState.NOT_SCHEDULED) && t.getDuration() == minutes)
                .findFirst();
    }

    /**
     * Reset the state of the talks to NOT_SCHEDULED and shuffles the list
     */
    public void resetTalksStateAndShuffleList() {
        LOGGER.info("TalkRepository - resetTalksStateAndShuffleList - start");
        talks.forEach(talk -> talk.setState(TalkState.NOT_SCHEDULED));
        Collections.shuffle(talks, new Random(System.nanoTime()));
    }

    /**
     * Get all talks
     * @return List of talks
     */
    List<Talk> getTalks() {
        return talks;
    }

    /**
     * Retrieves the talks with a NOT_SCHEDULED state
     * @return List of NOT_SCHEDULED talks
     */
    public List<Talk> getNotScheduledTalks() {
        return talks.stream()
                .filter(talk -> talk.getState().equals(TalkState.NOT_SCHEDULED))
                .collect(Collectors.toList());
    }

    /**
     * Removes the sublist of talks passed by parameter
     * @param talksToRemove Sublist to be removed
     */
    public void remove(List<Talk> talksToRemove) {
        talks.removeAll(talksToRemove);
    }
}
