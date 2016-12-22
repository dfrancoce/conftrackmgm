package org.dfc.conftrackmgm.repository;

import org.dfc.conftrackmgm.model.Talk;
import org.dfc.conftrackmgm.model.TalkState;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TalkRepositoryTest {
    @Autowired
    private TalkRepository sut;

    @Test
    public void getTalks() throws Exception {
        assertThat(sut.getTalks().size(), is(19));
    }

    @Test
    public void getNotScheduledTalks() throws Exception {
        assertThat(sut.getNotScheduledTalks().size(), is(19));
    }

    @Test
    public void findNotScheduledTalkByMinutes() throws Exception {
        Optional<Talk> talk = sut.findNotScheduledTalkByMinutes(60);
        assertThat(talk.get().getDuration(), is(60));
    }

    @Test
    @DirtiesContext
    public void resetTalksStateAndShuffleListTestShuffle() throws Exception {
        Talk firstTalkElementBeforeShuffle = sut.getTalks().get(0);
        sut.resetTalksStateAndShuffleList();
        Talk firstTalkElementAfterShuffle = sut.getTalks().get(0);

        assertThat(firstTalkElementBeforeShuffle.getTitle(), not(firstTalkElementAfterShuffle.getTitle()));
    }

    @Test
    @DirtiesContext
    public void resetTalksStateAndShuffleListTestResetState() throws Exception {
        Talk talkElement = sut.getTalks().get(0);
        talkElement.setState(TalkState.SCHEDULED);
        sut.resetTalksStateAndShuffleList();

        assertThat(talkElement.getState(), is(TalkState.NOT_SCHEDULED));
    }
}