package petermcneil.domain;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class LibraryTest {
    @Test
    public void copy() throws Exception {
        Set<Recording> recordings = new HashSet<Recording>();
        Set<Playlist> playlists = new HashSet<Playlist>();

        List<Song> songs = new ArrayList<Song>();
        List<Song> songs2 = new ArrayList<Song>();
        Song song1 = Song.songBuilder().title("Song 1").length(200).build();
        Song song2 = Song.songBuilder().title("Song 2").length(122).build();
        songs.add(song1); songs.add(song2); songs2.add(song2);

        Playlist playlist1 = Playlist.playlistBuilder()
                .title("Playlist 1")
                .tracks(songs)
                .build();
        playlists.add(playlist1);

        Playlist playlist2 = Playlist.playlistBuilder()
                .title("Playlist 2")
                .tracks(songs2)
                .build();

        Recording album1 = Recording.recordingBuilder()
                .type(Recording.RecordType.EP)
                .title("Blur: The Definitive Collection")
                .build();
        recordings.add(album1);

        //Building libraries
        Library library1 = Library.libraryBuilder()
                .playlists(playlists)
                .recordings(recordings)
                .build();

        Library library2 = library1
                .copy()
                .build();

        playlists.add(playlist2);
        Library library3 = library2
                .copy()
                .playlists(playlists)
                .build();

        assertEquals(library1, library2);
        assertFalse(library1 == library2);
        assertNotEquals(library2, library3);
    }

}