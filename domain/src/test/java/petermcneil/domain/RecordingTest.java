package petermcneil.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class RecordingTest {
    @Test
    public void copy() throws Exception {
        Recording everythingNow = Recording.recordingBuilder()
                .title("Everthing Now")
                .artist(Artist.artistBuilder().name("Arcade Fire").build())
                .type(Recording.RecordType.SINGLE)
                .build();

        Recording somethingNow = everythingNow
                .copy()
                .build();

        Recording nothingNow = somethingNow
                .copy()
                .title("Nothing Now")
                .type(Recording.RecordType.LP)
                .build();

        assertEquals(everythingNow, somethingNow);
        assertFalse(everythingNow == somethingNow);
        assertNotEquals(somethingNow, nothingNow);
    }

}