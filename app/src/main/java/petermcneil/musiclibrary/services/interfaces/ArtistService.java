package petermcneil.musiclibrary.services.interfaces;

import petermcneil.domain.Artist;

import java.util.List;

public interface ArtistService {
    Artist getArtist(Integer artistId);

    List<Artist> getArtistList();

    Integer postArtist(Artist artist);

    void putArtist(Artist artist, Integer artistId);

    void deleteArtist(Integer artistId);
}
