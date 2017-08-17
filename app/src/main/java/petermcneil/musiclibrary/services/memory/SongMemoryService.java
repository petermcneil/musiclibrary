package petermcneil.musiclibrary.services.memory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import petermcneil.domain.Song;
import petermcneil.musiclibrary.services.CRUDService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class SongMemoryService implements CRUDService<Song>{
    private final Map<Integer, Song> songDB;
    private AtomicLong highestInt = new AtomicLong(0);

    private static final Logger LOG = LoggerFactory.getLogger(SongMemoryService.class);

    public SongMemoryService(){
        songDB = new ConcurrentHashMap<>();
    }

    public Song get(Integer id) {
        LOG.info("RESPONSE: Returning the song at the id: {}", id);
        return songDB.get(id);
    }

    public List<Song> getList() {
        List<Song> songs = new ArrayList<>();
        for(Map.Entry<Integer, Song> entry: songDB.entrySet()){
            songs.add(entry.getValue());
        }
        LOG.info("RESPONSE: Returning the list of all songs with a size of {}", songs.size());
        return songs;
    }

    public Integer post(Song song) {
        Long temp = highestInt.getAndIncrement();
        Integer songId = temp.intValue();
        songDB.put(songId, song);
        LOG.info("RESPONSE: Added the song ({}) to the db at the id: {}", song.getTitle(), songId);
        return songId;
    }

    public void put(Song song, Integer songId) {
        songDB.put(songId, song);
        LOG.info("RESPONSE: Updated the song ({}) at the id: {}", song.getTitle(), songId );
    }

    public void delete(Integer songId) {
        songDB.remove(songId);
    }
}
