package petermcneil.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class SongTest {
    @Test
    public void copy() throws Exception {
        Song happy = Song.songBuilder()
                .title("happy")
                .length(185)
                .build();

        Song okay = happy
                .copy()
                .build();

        Song sad = okay
                .copy()
                .title("sad")
                .length(360)
                .build();

        assertEquals(happy, okay);
        assertFalse(happy == okay);
        assertNotEquals(okay, sad);
    }

}