package petermcneil.musiclibrary.controllers;

import petermcneil.domain.Playlist;
import petermcneil.musiclibrary.services.PlaylistService;

import java.util.Set;

public class PlaylistController {
    private final PlaylistService db;

    public PlaylistController(PlaylistService db){
        this.db = db;
    }

    public Set<Playlist> getPlaylistSet(){
        return db.getPlaylistList();
    }

    public Playlist getPlaylist(Integer playlistId){
        return db.getPlaylist(playlistId);
    }

    public Integer postPlaylist(Playlist playlist){
        return db.postPlaylist(playlist);
    }

    public boolean putPlaylist(Integer playlistId){
        return true;
    }

    public boolean deletePlaylist(Integer playlistId){
        return true;
    }
}
