package petermcneil.musiclibrary.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import petermcneil.domain.Playlist;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PlaylistService {
    private final Map<Integer, Playlist> playlistDB;
    private AtomicLong highestInt = new AtomicLong(0);

    private static final Logger LOG = LoggerFactory.getLogger(PlaylistService.class);

    public PlaylistService(){
        playlistDB = new ConcurrentHashMap<>();
    }

    public Set<Playlist> getPlaylistList(){
        Set<Playlist> playlistSet = new HashSet();
        for(Map.Entry<Integer, Playlist> entry: playlistDB.entrySet()){
            playlistSet.add(entry.getValue());
        }
        LOG.info("RESPONSE: Returning the list of playlists with a size of {}", playlistSet.size());
        return playlistSet;
    }

    public Playlist getPlaylist(Integer playlistId){
        LOG.info("RESPONSE: Returning the playlist at the id: {}", playlistId);
        return playlistDB.get(playlistId);
    }

    public Integer postPlaylist(Playlist playlist){
        Long temp = highestInt.getAndIncrement();
        Integer playlistId = temp.intValue();
        playlistDB.put(playlistId, playlist);
        LOG.info("RESPONSE: Added the playlist ({}) to the database at the id: {}", playlist.getTitle(), playlistId);
        return playlistId;
    }

    public void putPlaylist(Playlist playlist, Integer playlistId) {
        playlistDB.put(playlistId, playlist);
        LOG.info("RESPONSE: Updated the playlist ({}) at the id: {}", playlist.getTitle(), playlistId);
    }

    public void deletePlaylist(Integer playlistId) {
        playlistDB.remove(playlistId);
        LOG.info("RESPONSE: Deleted the playlist at the id: {}", playlistId);
    }
}
