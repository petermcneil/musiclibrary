package petermcneil.musiclibrary.services.interfaces;

import petermcneil.domain.Playlist;

import java.util.Set;

public interface PlaylistService {
    Playlist getPlaylist(Integer PlaylistId);

    Set<Playlist> getPlaylistList();

    Integer postPlaylist(Playlist Playlist);

    void putPlaylist(Playlist Playlist, Integer PlaylistId);

    void deletePlaylist(Integer PlaylistId);
}
