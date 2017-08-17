package petermcneil.musiclibrary.services;

import com.google.common.collect.ImmutableSet;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import petermcneil.domain.Recording;
import petermcneil.domain.Song;
import petermcneil.musiclibrary.services.memory.RecordingMemoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class RecordingMemoryServiceTest {
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
        RecordingMemoryService rs = new RecordingMemoryService();

        rs.post(recording1);
        rs.post(recording2);

        assertNull(rs.get(3));

        assertEquals(rs.get(0), recording1);
        assertEquals(rs.get(1), recording2);
    }

    @Test
    public void getRecordingList() throws Exception {
        RecordingMemoryService rs = new RecordingMemoryService();

        rs.post(recording1);
        rs.post(recording2);
        rs.post(recording3);

        List recordingList = new ArrayList<>(rs.getList());

        exception.expect(IndexOutOfBoundsException.class);
        recordingList.get(4);

        assertEquals(recordingList.get(0), recording1);
        assertEquals(recordingList.get(1), recording2);

        //TODO should really not allow this to post again. Fix this in general
        //rs.post(recording1);
    }

    @Test
    public void postRecording() throws Exception {
        RecordingMemoryService rs = new RecordingMemoryService();

        assertTrue(rs.post(recording2) == 0);
        assertTrue(rs.post(recording1) == 1);
        assertTrue(rs.post(recording3) == 2);
    }

    @Test
    public void putRecording() throws Exception {
        RecordingMemoryService rs = new RecordingMemoryService();

        rs.post(recording3);
        rs.post(recording2);
        rs.post(recording1);

        assertEquals(rs.get(0), recording3);
        assertEquals(rs.get(1), recording2);
        assertEquals(rs.get(2), recording1);

        rs.put(recording1, 0);
        rs.put(recording3, 1);
        rs.put(recording3, 2);

        assertEquals(rs.get(0), recording1);
        assertEquals(rs.get(1), recording3);
        assertEquals(rs.get(2), recording3);
    }

    @Test
    public void deleteRecording() throws Exception {
        RecordingMemoryService rs = new RecordingMemoryService();

        rs.post(recording3);
        rs.post(recording2);
        rs.post(recording1);

        assertEquals(rs.get(0), recording3);
        rs.delete(0);
        assertNull(rs.get(0));

        assertEquals(rs.getList().size(), 2);

    }

}