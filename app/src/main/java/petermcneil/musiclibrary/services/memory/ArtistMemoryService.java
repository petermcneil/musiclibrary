package petermcneil.musiclibrary.services.memory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import petermcneil.domain.Artist;
import petermcneil.musiclibrary.services.interfaces.ArtistService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ArtistMemoryService implements ArtistService {
    private final Map<Integer, Artist> artistDB;
    AtomicLong highestInt = new AtomicLong(0);

    private static final Logger LOG = LoggerFactory.getLogger(ArtistMemoryService.class);

    public ArtistMemoryService(){
        artistDB = new ConcurrentHashMap<>();

        artistDB.put(0, Artist.artistBuilder().name("Pete").type("Solo").build());
        artistDB.put(1, Artist.artistBuilder().name("Bob").build());
        artistDB.put(2, Artist.artistBuilder().name("Jim").build());
        artistDB.put(3, Artist.artistBuilder().name("Lewis").type("Band").build());
        artistDB.put(4, Artist.artistBuilder().name("Iain").build());
    }

    public Artist getArtist(Integer artistId){
        LOG.info("RESPONSE: Return the artist at the id: {}", artistId);
        return artistDB.get(artistId);
    }

    public List<Artist> getArtistList(){
        List<Artist> artists = new ArrayList<>();
        for(Map.Entry<Integer, Artist> entry: artistDB.entrySet()){
            artists.add(entry.getValue());
        }
        LOG.info("RESPONSE: Returning a list of artists of size {}", artists.size());
        return artists;
    }

    public Integer postArtist(Artist artist){
        Long tmp = highestInt.getAndIncrement();
        Integer artistId = tmp.intValue();
        artistDB.put(artistId, artist);
        LOG.info("RESPONSE: Inserted artist ({}) successfully at the id: {}", artist.getName(), artistId);
        return artistId;
    }

    public void putArtist(Artist artist, Integer artistId){
        artistDB.put(artistId, artist);
        LOG.info("RESPONSE: Successfully updated {} at the id: {}", artist.getName(), artistId);
    }

    //TODO Artist deleted == Songs deleted
    public void deleteArtist(Integer artistId){
        artistDB.remove(artistId);
        LOG.info("RESPONSE: Successfully deleted the artist at the id: {}", artistId);
    }
}
