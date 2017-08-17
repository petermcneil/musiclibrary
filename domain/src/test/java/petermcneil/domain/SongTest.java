package petermcneil.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class SongTest {
    String string1 = "Happy";
    String string2 = "Sad";

    Song happy = Song.songBuilder()
            .title(string1)
            .length(185)
            .build();

    @Test
    public void exactCopy() throws Exception {
        Song okay = happy
                .copy()
                .build();

        assertEquals(happy, okay);
        assertFalse(happy == okay);
    }

    @Test
    public void copyAndChange() throws Exception{
        Song okay = happy
                .copy()
                .build();

        Song sad = okay
                .copy()
                .title(string2)
                .length(360)
                .build();

        assertNotEquals(okay, sad);
    }

    @Test
    public void attributeSaving() throws Exception{
        assertThat(happy.getTitle(), is(equalTo(string1)));
    }

    @Test
    public void seraliseTest() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        String json = "{ \"title\" : \"SONG1\"}";

        Song mySong = mapper.readValue(json, Song.class);

        assertEquals(mySong.getTitle(), "SONG1" );

        String json2 = "{  \n" +
                "   \"title\":\"song2\",\n" +
                "   \"length\": \" 284\"}";

        Song song2 = mapper.readValue(json2, Song.class);

        assertEquals(song2.getTitle(), "song2");
        assertEquals(song2.getLength(), 284);
    }
}