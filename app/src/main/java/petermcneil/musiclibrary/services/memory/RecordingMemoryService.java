package petermcneil.musiclibrary.services.memory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import petermcneil.domain.Recording;
import petermcneil.musiclibrary.services.interfaces.RecordingService;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class RecordingMemoryService implements RecordingService {
    private final Map<Integer, Recording> recordingDB;
    AtomicLong highestInt = new AtomicLong(0);

    private static final Logger LOG = LoggerFactory.getLogger(RecordingMemoryService.class);

    public RecordingMemoryService(){
        recordingDB = new ConcurrentHashMap<>();
    }

    public Recording getRecording(Integer recordingId){
        LOG.info("RESPONSE: Returning the recording at id: {}", recordingId);
        return recordingDB.get(recordingId);
    }

    public Set<Recording> getRecordingList(){
        Set<Recording> recordings = new HashSet<>();
        for(Map.Entry<Integer, Recording> entry: recordingDB.entrySet()){
            recordings.add(entry.getValue());
        }

        LOG.info("RESPONSE: Returning the recording list");
        return recordings;
    }

    public Integer postRecording(Recording recording){
        Long tmp = highestInt.getAndIncrement();
        Integer recordingId = tmp.intValue();
        recordingDB.put(recordingId, recording);

        LOG.info("RESPONSE: Posted the recording {} at the id: {}", recording.getTitle(), recordingId);
        return recordingId;
    }

    public void putRecording(Recording recording, Integer recordingId){
        LOG.info("RESPONSE: Updated the recording {} at the id: {}", recording.getTitle(), recordingId);
        recordingDB.put(recordingId,recording);
    }

    public void deleteRecording(Integer recordingId){
        LOG.info("RESPONSE: Deleted the recording at the id: {}", recordingId);
        recordingDB.remove(recordingId);
    }

}
