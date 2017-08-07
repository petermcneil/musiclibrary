package petermcneil.musiclibrary.controllers;

import petermcneil.domain.Song;
import petermcneil.musiclibrary.services.SongService;

import java.util.List;
import java.util.Set;

public class SongController {

    private final SongService db;

    public SongController(SongService db){
        this.db = db;
    }

    public Song getSong(Integer id){
        return db.getSong(id);
    }

    public List<Song> getSongs(){
        return db.getSongs();
    }

    public Integer postSong(Song song){
        Integer songId = db.postSong(song);
        return songId;
    }

    public boolean deleteSong(Integer songId){
        return false;
    }

}
