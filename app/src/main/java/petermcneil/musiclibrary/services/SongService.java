package petermcneil.musiclibrary.services;

import petermcneil.domain.Song;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SongService {
    private final Map<Integer, Song> songDB;

    public SongService(){
        songDB = new ConcurrentHashMap<>();
    }

    public Song getSong(Integer id) {
        return songDB.get(id);
    }
}
