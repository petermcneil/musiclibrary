package petermcneil.musiclibrary.services;

import org.springframework.stereotype.Service;
import petermcneil.domain.Song;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class SongService {
    private final Map<Integer, Song> songDB;
    private AtomicLong highestInt = new AtomicLong(0);

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
        Long temp = highestInt.getAndIncrement();
        Integer songId = temp.intValue();
        songDB.put(songId, song);
        return songId;
    }

    public boolean deleteSong(Integer songId){
        if (songDB.containsKey(songId)){
            songDB.remove(songId);
            return true;
        }else {
            return false;
        }
    }

    public void putSong(Integer songId, Song song) {
        songDB.put(songId, song);
    }
}
