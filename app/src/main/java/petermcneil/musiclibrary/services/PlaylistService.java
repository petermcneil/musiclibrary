package petermcneil.musiclibrary.services;

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

    public PlaylistService(){
        playlistDB = new ConcurrentHashMap<>();
    }

    public Set<Playlist> getPlaylistList(){
        Set<Playlist> playlistSet = new HashSet();

        for(Map.Entry<Integer, Playlist> entry: playlistDB.entrySet()){
            playlistSet.add(entry.getValue());
        }
        return playlistSet;
    }

    public Playlist getPlaylist(Integer playlistId){
        return playlistDB.get(playlistId);
    }

    public Integer postPlaylist(Playlist playlist){
        Long temp = highestInt.getAndIncrement();
        Integer playlistId = temp.intValue();
        playlistDB.put(playlistId, playlist);
        return playlistId;
    }

    public void putPlaylist(Playlist playlist, Integer playlistId) {
        playlistDB.put(playlistId, playlist);
    }

    public void deletePlaylist(Integer playlistId) {
        playlistDB.remove(playlistId);
    }
}
