package petermcneil.musiclibrary.services;

import petermcneil.domain.Song;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SongService {
    private final Map<Integer, Song> songDB;

    public SongService(){
        songDB = new ConcurrentHashMap<>();
    }

    public Song getSong(Integer id) {
        return songDB.get(id);
    }

    public List<Song> getSongs() {
        List<Song> songs = new ArrayList<>();

        for(Map.Entry<Integer, Song> entry: songDB.entrySet()){
            songs.add(entry.getValue());
        }
        return songs;
    }

    public Integer postSong(Song song) {
        Integer songId = songDB.size();
        songDB.put(songId, song);
        return songId;
    }

    //TODO: Change delete song to "delete" song
    public boolean deleteSong(Integer songId){
        if (songDB.containsKey(songId)){
            songDB.remove(songId);
            return true;
        }else {
            return false;
        }
    }
}
