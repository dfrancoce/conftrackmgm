package org.dfc.conftrackmgm.service;

import org.dfc.conftrackmgm.model.Session;
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
public class SessionServiceTest {
    @Autowired
    private SessionService sut;

    @Test
    @DirtiesContext
    public void fillSessionWithTalks() throws Exception {
        Session morningSession = new Session();
        sut.fillSessionWithTalks(morningSession, 180);

        assertThat(morningSession.getTalks().size(), is(4));

        Session afternoonSession = new Session();
        sut.fillSessionWithTalks(afternoonSession, 240);

        assertThat(afternoonSession.getTalks().size(), is(6));
    }
}
