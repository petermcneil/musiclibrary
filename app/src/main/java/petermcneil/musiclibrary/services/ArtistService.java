package petermcneil.musiclibrary.services;

import org.springframework.stereotype.Service;
import petermcneil.domain.Artist;
import petermcneil.domain.Recording;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ArtistService {
    private final Map<Integer, Artist> artistDB;
    AtomicLong highestInt = new AtomicLong(0);

    public ArtistService(){
        artistDB = new ConcurrentHashMap<>();
    }

    public Artist getArtist(Integer artistId){
        return artistDB.get(artistId);
    }

    public List<Artist> getArtistList(){
        List<Artist> artists = new ArrayList<>();
        for(Map.Entry<Integer, Artist> entry: artistDB.entrySet()){
            artists.add(entry.getValue());
        }
        return artists;
    }

    public Integer postArtist(Artist artist){
        Long tmp = highestInt.getAndIncrement();
        Integer artistId = tmp.intValue();
        artistDB.put(artistId, artist);
        return artistId;
    }

    public void putArtist(Artist artist, Integer artistId){
        artistDB.put(artistId, artist);
    }

    //TODO Artist deleted == Songs deleted
    public void deleteArtist(Integer artistId){
        artistDB.remove(artistId);
    }
}
