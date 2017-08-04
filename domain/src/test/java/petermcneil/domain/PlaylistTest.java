package petermcneil.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlaylistTest {
    @Test
    public void copy() throws Exception {
        Playlist pop = Playlist.playlistBuilder()
                .title("Pop playlist")
                .build();

        Playlist offPop = pop
                .copy()
                .build();

        Playlist rock = offPop
                .copy()
                .title("Rock playlist")
                .build();

        assertEquals(pop, offPop);
        assertFalse(pop == offPop);
        assertNotEquals(offPop, rock);
    }

}