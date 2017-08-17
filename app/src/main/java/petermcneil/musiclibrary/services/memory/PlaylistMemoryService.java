package petermcneil.musiclibrary.services.memory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import petermcneil.domain.Playlist;
import petermcneil.musiclibrary.services.CRUDService;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PlaylistMemoryService implements CRUDService<Playlist>{
    private final Map<Integer, Playlist> playlistDB;
    private AtomicLong highestInt = new AtomicLong(0);

    private static final Logger LOG = LoggerFactory.getLogger(PlaylistMemoryService.class);

    public PlaylistMemoryService(){
        playlistDB = new ConcurrentHashMap<>();
    }

    public Playlist get(Integer playlistId){
        LOG.info("RESPONSE: Returning the playlist at the id: {}", playlistId);
        return playlistDB.get(playlistId);
    }

    public List<Playlist> getList(){
        List<Playlist> playlistSet = new ArrayList<>();
        for(Map.Entry<Integer, Playlist> entry: playlistDB.entrySet()){
            playlistSet.add(entry.getValue());
        }
        LOG.info("RESPONSE: Returning the list of playlists with a size of {}", playlistSet.size());
        return playlistSet;
    }

    public Integer post(Playlist playlist){
        Long temp = highestInt.getAndIncrement();
        Integer playlistId = temp.intValue();
        playlistDB.put(playlistId, playlist);
        LOG.info("RESPONSE: Added the playlist ({}) to the database at the id: {}", playlist.getTitle(), playlistId);
        return playlistId;
    }

    public void put(Playlist playlist, Integer playlistId) {
        playlistDB.put(playlistId, playlist);
        LOG.info("RESPONSE: Updated the playlist ({}) at the id: {}", playlist.getTitle(), playlistId);
    }

    public void delete(Integer playlistId) {
        playlistDB.remove(playlistId);
        LOG.info("RESPONSE: Deleted the playlist at the id: {}", playlistId);
    }
}
