package petermcneil.musiclibrary.services.memory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import petermcneil.domain.Song;
import petermcneil.musiclibrary.services.interfaces.SongService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class SongMemoryService implements SongService{
    private final Map<Integer, Song> songDB;
    private AtomicLong highestInt = new AtomicLong(0);

    private static final Logger LOG = LoggerFactory.getLogger(SongMemoryService.class);

    public SongMemoryService(){
        songDB = new ConcurrentHashMap<>();
    }

    public Song getSong(Integer id) {
        LOG.info("RESPONSE: Returning the song at the id: {}", id);
        return songDB.get(id);
    }

    public List<Song> getSongList() {
        List<Song> songs = new ArrayList<>();
        for(Map.Entry<Integer, Song> entry: songDB.entrySet()){
            songs.add(entry.getValue());
        }
        LOG.info("RESPONSE: Returning the list of all songs with a size of {}", songs.size());
        return songs;
    }

    public Integer postSong(Song song) {
        Long temp = highestInt.getAndIncrement();
        Integer songId = temp.intValue();
        songDB.put(songId, song);
        LOG.info("RESPONSE: Added the song ({}) to the db at the id: {}", song.getTitle(), songId);
        return songId;
    }

    public boolean deleteSong(Integer songId){
        if (songDB.containsKey(songId)){
            songDB.remove(songId);
            LOG.info("RESPONSE: Deleted the song from the id: {}", songId);
            return true;
        }else {
            return false;
        }
    }

    public void putSong(Song song, Integer songId) {
        songDB.put(songId, song);
        LOG.info("RESPONSE: Updated the song ({}) at the id: {}", song.getTitle(), songId );
    }
}
