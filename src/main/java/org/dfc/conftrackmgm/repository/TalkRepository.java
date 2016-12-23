package org.dfc.conftrackmgm.repository;

import org.dfc.conftrackmgm.model.Talk;
import org.dfc.conftrackmgm.model.TalkState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class TalkRepository {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private static final String LIGHTNING_TALK = "lightning";
    private List<Talk> talks;

    @Value("${inputFile}")
    private String inputFile;

    @PostConstruct
    public void init() {
        getTalksFromFile();
    }

    private void getTalksFromFile() {
        talks = new ArrayList<>();
        URL resource = getClass().getClassLoader().getResource(inputFile);

        try (Stream<String> stream = Files.lines(Paths.get(resource.toURI().getPath()))) {
            stream.forEach(t -> {
                Optional<Talk> talk = getTalkFromLine(t);
                talk.ifPresent(talks::add);
            });
        } catch (Exception e) {
            LOGGER.error("An error occurred trying to extract the talks from the input file: ", e);
        }
    }

    private Optional<Talk> getTalkFromLine(final String line) {
        String pattern = "[^0-9]*([0-9]+).*";
        Matcher m = Pattern.compile(pattern).matcher(line);

        Optional<Talk> talk = Optional.empty();
        if (m.matches()) {
            String minutes = m.group(1);
            talk = Optional.of(new Talk(line.substring(0, line.indexOf(minutes)), Integer.parseInt(minutes)));
        } else if (line.contains(LIGHTNING_TALK)) {
            talk = Optional.of(new Talk(line.substring(0, line.indexOf(LIGHTNING_TALK)), 5));
        }

        return talk;
    }

    /**
     * Finds the first talk with the same duration as the minutes passed by parameter
     * @param minutes Duration
     * @return The first talk found
     */
    public Optional<Talk> findNotScheduledTalkByMinutes(int minutes) {
        return talks.stream().filter(t -> t.getState().equals(TalkState.NOT_SCHEDULED)
                && t.getDuration() == minutes).findFirst();
    }

    /**
     * Reset the state of the talks to NOT_SCHEDULED and shuffles the list
     */
    public void resetTalksStateAndShuffleList() {
        talks.forEach(talk -> talk.setState(TalkState.NOT_SCHEDULED));
        Collections.shuffle(talks, new Random(System.nanoTime()));
    }

    /**
     * Get all talks
     * @return List of talks
     */
    public List<Talk> getTalks() {
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
