package petermcneil.musiclibrary.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import petermcneil.domain.Playlist;
import petermcneil.musiclibrary.services.PlaylistService;

import java.util.Set;

public class PlaylistController {
    private final PlaylistService db;

    public PlaylistController(PlaylistService db){
        this.db = db;
    }

    @RequestMapping("/playlists")
    public String getPlaylistSet(Model model){
        model.addAttribute("playlists", db.getPlaylistList());
        return "/playlists";
    }

    @RequestMapping(value = "/playlist/{playlistId}", method = RequestMethod.GET)
    public String getPlaylist(@PathVariable Integer playlistId, Model model){
        model.addAttribute(db.getPlaylist(playlistId));
        return "/playlist/";
    }

    @RequestMapping(value = "/playlist", method = RequestMethod.POST)
    public String postPlaylist(Playlist playlist){
        Integer playlistId = db.postPlaylist(playlist);
        return "/playlist/{" + playlistId +"}";
    }

    @RequestMapping(value = "/playlist/{playlistId}/{playlist}", method = RequestMethod.PUT)
    public String putPlaylist(@PathVariable Integer playlistId, @PathVariable Playlist playlist){
        db.putPlaylist(playlist, playlistId);
        return "/playlist/{playlistId}";
    }

    @RequestMapping(value = "/playlist/{playlistId}", method = RequestMethod.DELETE)
    public String deletePlaylist(@PathVariable Integer playlistId){
        db.deletePlaylist(playlistId);
        return "/playlists";
    }
}
