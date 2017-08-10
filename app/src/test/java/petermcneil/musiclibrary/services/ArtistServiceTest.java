package petermcneil.musiclibrary.services;


import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import petermcneil.domain.Artist;
import petermcneil.domain.Recording;
import petermcneil.domain.Song;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.*;


public class ArtistServiceTest {
    Song song1 = Song.songBuilder().title("Song 1").build();
    Song song2 = Song.songBuilder().title("Song 2").build();

    Recording recording1 = Recording.recordingBuilder().tracks(ImmutableSet.of(song1, song2)).build();
    Recording recording2 = Recording.recordingBuilder().tracks(ImmutableSet.of(song1)).build();
    Recording recording3 = Recording.recordingBuilder().tracks(ImmutableSet.of(song1, song2, song2)).build();
    Recording recording4 = Recording.recordingBuilder().tracks(ImmutableSet.of(song1, song1, song2, song1)).build();

    Artist artist1 = Artist.artistBuilder().name("artist1").recordings(ImmutableSet.of(recording1)).build();
    Artist artist2 = Artist.artistBuilder().name("artist2").recordings(ImmutableSet.of(recording2, recording3, recording4)).build();
    Artist artist3 = Artist.artistBuilder().name("artist3").build();

    @Test
    public void getArtist() throws Exception {
        ArtistService as = new ArtistService();

        as.postArtist(artist1);
        assertNotNull(as.getArtist(0));

        as.postArtist(artist2);
        as.postArtist(artist3);
        as.postArtist(artist2);

        assertTrue(as.getArtist(0) == artist1);
        assertTrue(as.getArtist(2) == artist3);
    }

    @Test
    public void getArtistList() throws Exception {
        ArtistService as = new ArtistService();

        as.postArtist(artist1);
        as.postArtist(artist2);
        as.postArtist(artist3);
        as.postArtist(artist1);
        as.postArtist(artist2);
        as.postArtist(artist3);

        List artistList = new ArrayList(as.getArtistList());

        assertEquals(artist1, artistList.get(0));
        assertEquals(artist2, artistList.get(1));
        assertEquals(artist3, artistList.get(5));
    }

    @Test
    public void postArtist() throws Exception {
        ArtistService as = new ArtistService();

        assertTrue(as.postArtist(artist1) == 0);
        assertTrue(as.postArtist(artist1) == 1);
        assertTrue(as.postArtist(artist1) == 2);
        assertTrue(as.postArtist(artist1) == 3);
    }

    @Test
    public void putArtist() throws Exception {
        ArtistService as = new ArtistService();

        as.postArtist(artist1);
        as.postArtist(artist2);

        assertEquals(as.getArtist(0), artist1);
        assertEquals(as.getArtist(1), artist2);

        as.putArtist(artist2, 0);
        as.putArtist(artist1, 1);

        assertEquals(as.getArtist(0), artist2);
        assertEquals(as.getArtist(1), artist1);
    }

    @Test
    public void deleteArtist() throws Exception {
        ArtistService as = new ArtistService();

        as.postArtist(artist1);
        as.postArtist(artist2);

        assertEquals(as.getArtist(0), artist1);
        assertEquals(as.getArtist(1), artist2);

        as.deleteArtist(0);

        assertNull(as.getArtist(0));
    }

}