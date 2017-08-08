package petermcneil.musiclibrary.services;

import org.springframework.stereotype.Service;
import petermcneil.domain.Recording;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class RecordingService {
    private final Map<Integer, Recording> recordingDB;
    AtomicLong highestInt = new AtomicLong(0);

    public RecordingService(){
        recordingDB = new ConcurrentHashMap<>();
    }

    public Recording getRecording(Integer recordingId){
        return recordingDB.get(recordingId);
    }

    public Set<Recording> getRecordingList(){
        Set<Recording> recordings = new HashSet<>();
        for(Map.Entry<Integer, Recording> entry: recordingDB.entrySet()){
            recordings.add(entry.getValue());
        }
        return recordings;
    }

    public Integer postRecording(Recording recording){
        Long tmp = highestInt.getAndIncrement();
        Integer recordingId = tmp.intValue();
        recordingDB.put(recordingId, recording);
        return recordingId;
    }

    public void putRecording(Recording recording, Integer recordingId){
        recordingDB.put(recordingId,recording);
    }

    public void deleteRecording(Integer recordingId){
        recordingDB.remove(recordingId);
    }

}
