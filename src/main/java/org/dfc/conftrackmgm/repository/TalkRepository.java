package org.dfc.conftrackmgm.repository;

import org.dfc.conftrackmgm.model.Talk;
import org.dfc.conftrackmgm.model.TalkState;
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
    private static final String LIGHTNING_TALK = "lightning";
    private static final String INPUT_FILE = "input.txt";
    private List<Talk> talks;

    @PostConstruct
    public void init() {
        getTalksFromFile();
    }

    /**
     * Extracts the talks from the input file
     */
    private void getTalksFromFile() {
        talks = new ArrayList<>();
        URL resource = getClass().getClassLoader().getResource(INPUT_FILE);

        try (Stream<String> stream = Files.lines(Paths.get(resource.toURI().getPath()))) {
            stream.forEach(t -> {
                Optional<Talk> talk = getTalkFromLine(t);
                talk.ifPresent(talks::add);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Extracts the talk (Title and duration) from a line of the input file
     * @param line
     * @return The Talk stored in the line
     */
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

    public List<Talk> getTalks() {
        return talks;
    }

    public List<Talk> getNotScheduledTalks() {
        return talks.stream()
                .filter(talk -> talk.getState().equals(TalkState.NOT_SCHEDULED))
                .collect(Collectors.toList());
    }
}
