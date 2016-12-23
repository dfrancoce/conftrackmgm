package org.dfc.conftrackmgm.service;

import org.dfc.conftrackmgm.model.Track;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrackServiceTest {
    @Autowired
    private TrackService sut;

    @Test
    @DirtiesContext
    public void createTracks() throws Exception {
        List<Track> tracks = sut.createTracks();
        assertThat(tracks.size(), not(0));

        final AtomicInteger numOfTracks = new AtomicInteger(1);
        tracks.forEach(track -> {
            System.out.printf("Track %d:\n", numOfTracks.get());
            System.out.println(track.toString());
            numOfTracks.getAndAdd(1);
        });
    }
}
