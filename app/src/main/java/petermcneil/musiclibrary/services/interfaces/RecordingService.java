package petermcneil.musiclibrary.services.interfaces;

import petermcneil.domain.Recording;

import java.util.Set;

public interface RecordingService {
    Recording getRecording(Integer RecordingId);

    Set<Recording> getRecordingList();

    Integer postRecording(Recording Recording);

    void putRecording(Recording Recording, Integer RecordingId);

    void deleteRecording(Integer RecordingId);
}
