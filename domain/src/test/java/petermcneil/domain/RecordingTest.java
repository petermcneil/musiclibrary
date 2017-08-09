package petermcneil.domain;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class RecordingTest {
    String string1 = "Everything Now";

    private Recording everythingNow = Recording.recordingBuilder()
                .title(string1)
                .artist(Artist.artistBuilder().name("Arcade Fire").build())
                .type(Recording.RecordType.SINGLE)
                .build();

    @Test
    public void exactCopy() throws Exception {
        Recording somethingNow = everythingNow
                .copy()
                .build();

        assertEquals(everythingNow, somethingNow);
        assertFalse(everythingNow == somethingNow);
    }

    @Test
    public void copyAndChange() throws Exception{
        Recording somethingNow = everythingNow
                .copy()
                .build();

        Recording nothingNow = somethingNow
                .copy()
                .title("Nothing Now")
                .type(Recording.RecordType.LP)
                .build();

        assertNotEquals(somethingNow, nothingNow);
        assertTrue(somethingNow != nothingNow);
    }

    @Test
    public void attributeSaving() throws Exception{
        assertThat(everythingNow.getTitle(), is(equalTo(string1)));
    }
}