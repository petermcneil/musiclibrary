package petermcneil.musiclibrary.services.interfaces;

import petermcneil.domain.Song;

import java.util.List;

public interface SongService {
    Song getSong(Integer SongId);

    List<Song> getSongList();

    Integer postSong(Song song);

    void putSong(Song song, Integer songId);

    boolean deleteSong(Integer songId);
}
