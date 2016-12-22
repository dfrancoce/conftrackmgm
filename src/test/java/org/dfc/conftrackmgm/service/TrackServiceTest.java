package org.dfc.conftrackmgm.service;

import org.dfc.conftrackmgm.model.Track;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrackServiceTest {
    @Autowired
    private TrackService sut;

    @Test
    @DirtiesContext
    public void createTrack() throws Exception {
        Track track = sut.createTrack();

        assertThat(track.getMorningSession().getTalks().size(), is(4));
        assertThat(track.getAfternoonSession().getTalks().size(), is(6));
    }
}
