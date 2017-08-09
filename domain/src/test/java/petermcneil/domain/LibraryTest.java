package petermcneil.domain;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import org.junit.Test;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class LibraryTest {
    Song song1 = Song.songBuilder().title("Song 1").length(200).build();
    Song song2 = Song.songBuilder().title("Song 2").length(122).build();

    Playlist playlist1 = Playlist.playlistBuilder()
            .title("Playlist 1")
            .tracks(ImmutableList.of(song1, song2))
            .build();

    Playlist playlist2 = Playlist.playlistBuilder()
            .title("Playlist 2")
            .tracks(ImmutableList.of(song2, song1, song1))
            .build();

    Recording album1 = Recording.recordingBuilder()
            .type(Recording.RecordType.EP)
            .title("Blur: The Definitive Collection")
            .tracks(ImmutableSet.of(song1, song2))
            .build();

    Set<Recording> recordings = ImmutableSet.of(album1);
    Set<Playlist> playlists = ImmutableSet.of(playlist1);
    Set<Playlist> playlists2 = ImmutableSet.of(playlist1, playlist2);


    Library library1 = Library.libraryBuilder()
            .playlists(playlists)
            .recordings(recordings)
            .build();
    @Test
    public void exactCopy() throws Exception {
        Library library2 = library1
                .copy()
                .build();

        assertEquals(library1, library2);
        assertFalse(library1 == library2);
    }

    @Test
    public void copyAndChange() throws Exception{
        Library library2 = library1
                .copy()
                .build();

        Library library3 = library2
                .copy()
                .playlists(playlists2)
                .build();

        assertNotEquals(library2, library3);
    }

    @Test
    public void attributeSaving() throws Exception{
        assertTrue(library1.getPlaylists().contains(playlist1));
    }
}