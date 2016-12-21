package org.dfc.conftrackmgm.repository;

import org.dfc.conftrackmgm.model.Talk;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TalkRepositoryTest {
    private static final String TEST_FILE = "input.txt";
    private TalkRepository sut;

    @Before
    public void setUp() throws Exception {
        sut = new TalkRepository();
    }

    @Test
    public void getTalksFromFile() throws Exception {
        URL resource = getClass().getClassLoader().getResource(TEST_FILE);
        List<Talk> talks = sut.getTalksFromFile(resource.toURI().getPath());
        assertThat(talks.size(), is(19));
    }
}