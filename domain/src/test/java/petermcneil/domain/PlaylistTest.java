package petermcneil.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class PlaylistTest {
    String string1 = "Pop Playlist";
    String string2 = "Rock Playlist";

    Playlist pop = Playlist.playlistBuilder()
            .title(string1)
            .build();

    @Test
    public void exactCopy() throws Exception {
        Playlist offPop = pop
                .copy()
                .build();

        assertEquals(pop, offPop);
        assertFalse(pop == offPop);
    }

    @Test
    public void copyAndChange() throws Exception{
        Playlist offPop = pop
                .copy()
                .build();

        Playlist rock = offPop
                .copy()
                .title(string2)
                .build();

        assertNotEquals(offPop, rock);
        assertThat(rock.getTitle(), is(equalTo(string2)));
    }


    @Test
    public void attributeSaving() throws Exception{
        assertThat(pop.getTitle(), is(equalTo(string1)));
    }
}