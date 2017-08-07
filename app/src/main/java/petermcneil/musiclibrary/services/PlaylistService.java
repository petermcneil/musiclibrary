package petermcneil.musiclibrary.services;

import petermcneil.domain.Playlist;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class PlaylistService {
    private final Map<Integer, Playlist> playlistDB;

    public PlaylistService(){
        playlistDB = new ConcurrentHashMap<>();
    }

    public Set<Playlist> getPlaylistList(){
        Set<Playlist> playlistSet = new HashSet();
        return playlistSet;
    }

    public Playlist getPlaylist(Integer playlistId){
        return playlistDB.get(playlistId);
    }

    public Integer postPlaylist(Playlist playlist){
        Integer playlistId = playlistDB.size();
        playlistDB.put(playlistId, playlist);
        return playlistId;
    }

}
