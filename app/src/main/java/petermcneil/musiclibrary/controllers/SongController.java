package petermcneil.musiclibrary.controllers;

import petermcneil.domain.Song;
import petermcneil.musiclibrary.services.SongService;

public class SongController {

    private final SongService db;

    public SongController(SongService db){
        this.db = db;
    }

    public Song getSong(Integer id){
        return db.getSong(id);
    }
}
