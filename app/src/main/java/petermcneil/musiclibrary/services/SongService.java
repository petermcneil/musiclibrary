package petermcneil.musiclibrary.services;

import petermcneil.domain.Song;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SongService {
    private final Map<Integer, Song> songDB;
    private Integer nextEntry =0;

    public SongService(){
        songDB = new ConcurrentHashMap<>();
    }

    public Song getSong(Integer id) {
        return songDB.get(id);
    }

    public Set<Song> getSongs() {
        Set<Song> songs = new HashSet();

        for(Map.Entry<Integer, Song> entry: songDB.entrySet()){
            songs.add(entry.getValue());
        }
        return songs;
    }

    public Integer postSong(Song song) {
        songDB.put(nextEntry, song);
        Integer oldEntry = nextEntry;
        nextEntry++;
        return oldEntry;
    }
}
