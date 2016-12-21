package org.dfc.conftrackmgm.repository;

import org.dfc.conftrackmgm.model.Talk;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class TalkRepository {
    private static final String LIGHTNING_TALK = "lightning";

    /**
     * Reads the input file and extracts the talks
     * @param filePath Path to the input file
     * @return A list of talks
     */
    public List<Talk> getTalksFromFile(final String filePath) {
        List<Talk> talks = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            stream.forEach(t -> {
                Optional<Talk> talk = getTalkFromLine(t);
                talk.ifPresent(talks::add);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return talks;
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
}
