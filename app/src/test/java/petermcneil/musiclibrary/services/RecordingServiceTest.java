package petermcneil.musiclibrary.services;

import com.google.common.collect.ImmutableSet;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import petermcneil.domain.Recording;
import petermcneil.domain.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class RecordingServiceTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    Song song1 = Song.songBuilder().title("song1").build();
    Song song2 = Song.songBuilder().title("song2").build();
    Song song3 = Song.songBuilder().title("song3").build();
    Song song4 = Song.songBuilder().title("song4").build();

    Set<Song> songSet1 = ImmutableSet.of(song1, song2, song3);
    Set<Song> songSet2 = ImmutableSet.of(song2, song3);
    Set<Song> songSet3 = ImmutableSet.of(song1, song2, song3, song4);

    Recording recording1 = Recording.recordingBuilder().tracks(songSet1).build();
    Recording recording2 = Recording.recordingBuilder().tracks(songSet2).build();
    Recording recording3 = Recording.recordingBuilder().tracks(songSet3).build();

    @Test
    public void getRecording() throws Exception {
        RecordingService rs = new RecordingService();

        rs.postRecording(recording1);
        rs.postRecording(recording2);

        assertNull(rs.getRecording(3));

        assertEquals(rs.getRecording(0), recording1);
        assertEquals(rs.getRecording(1), recording2);
    }

    @Test
    public void getRecordingList() throws Exception {
        RecordingService rs = new RecordingService();

        rs.postRecording(recording1);
        rs.postRecording(recording2);
        rs.postRecording(recording3);

        List recordingList = new ArrayList<>(rs.getRecordingList());

        exception.expect(IndexOutOfBoundsException.class);
        recordingList.get(4);

        assertEquals(recordingList.get(0), recording1);
        assertEquals(recordingList.get(1), recording2);

        //TODO should really not allow this to post again. Fix this in general
        //rs.postRecording(recording1);
    }

    @Test
    public void postRecording() throws Exception {
        RecordingService rs = new RecordingService();

        assertTrue(rs.postRecording(recording2) == 0);
        assertTrue(rs.postRecording(recording1) == 1);
        assertTrue(rs.postRecording(recording3) == 2);
    }

    @Test
    public void putRecording() throws Exception {
        RecordingService rs = new RecordingService();

        rs.postRecording(recording3);
        rs.postRecording(recording2);
        rs.postRecording(recording1);

        assertEquals(rs.getRecording(0), recording3);
        assertEquals(rs.getRecording(1), recording2);
        assertEquals(rs.getRecording(2), recording1);

        rs.putRecording(recording1, 0);
        rs.putRecording(recording3, 1);
        rs.putRecording(recording3, 2);

        assertEquals(rs.getRecording(0), recording1);
        assertEquals(rs.getRecording(1), recording3);
        assertEquals(rs.getRecording(2), recording3);
    }

    @Test
    public void deleteRecording() throws Exception {
    }

}