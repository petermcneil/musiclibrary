package petermcneil.musiclibrary.services.memory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import petermcneil.domain.Recording;
import petermcneil.musiclibrary.services.CRUDService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public
class RecordingMemoryService implements CRUDService<Recording> {
    private final Map<Integer, Recording> recordingDB;
    AtomicLong highestInt = new AtomicLong(0);

    private static final Logger LOG = LoggerFactory.getLogger(RecordingMemoryService.class);

    public RecordingMemoryService(){
        recordingDB = new ConcurrentHashMap<>();
    }

    @Override
    public Recording get(Integer recordingId){
        LOG.info("RESPONSE: Returning the recording at id: {}", recordingId);
        return recordingDB.get(recordingId);
    }

    @Override
    public List<Recording> getList(){
        List<Recording> recordings = new ArrayList<>();
        for(Map.Entry<Integer, Recording> entry: recordingDB.entrySet()){
            recordings.add(entry.getValue());
        }

        LOG.info("RESPONSE: Returning the recording list of size {}", recordings.size());
        return recordings;
    }

    @Override
    public Integer post(Recording recording){
        Long tmp = highestInt.getAndIncrement();
        Integer recordingId = tmp.intValue();
        recordingDB.put(recordingId, recording);

        LOG.info("RESPONSE: Posted the recording {} at the id: {}", recording.getTitle(), recordingId);
        return recordingId;
    }

    @Override
    public void put(Recording recording, Integer recordingId){
        LOG.info("RESPONSE: Updated the recording {} at the id: {}", recording.getTitle(), recordingId);
        recordingDB.put(recordingId,recording);
    }

    @Override
    public void delete(Integer recordingId){
        LOG.info("RESPONSE: Deleted the recording at the id: {}", recordingId);
        recordingDB.remove(recordingId);
    }

}
