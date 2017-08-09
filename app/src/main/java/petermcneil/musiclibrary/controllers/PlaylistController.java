package petermcneil.musiclibrary.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import petermcneil.domain.Playlist;
import petermcneil.musiclibrary.services.PlaylistService;

import java.util.Set;

@RestController
public class PlaylistController {
    private final PlaylistService db;

    public PlaylistController(PlaylistService db){
        this.db = db;
    }

    @RequestMapping(value = "/playlists", method = RequestMethod.GET)
    public String getPlaylistList(Model model){
        model.addAttribute("playlists", db.getPlaylistList());
        return "playlistList";
    }

    @RequestMapping(value = "/playlist/{playlistId}", method = RequestMethod.GET)
    public String getPlaylist(@PathVariable Integer playlistId, Model model){
        model.addAttribute(db.getPlaylist(playlistId));
        return "playlist";
    }

    @RequestMapping(value = "/playlist", method = RequestMethod.POST)
    public String postPlaylist(Playlist playlist){
        Integer playlistId = db.postPlaylist(playlist);
        return "redirect:/playlist/{" + playlistId +"}";
    }

    @RequestMapping(value = "/playlist/{playlistId}/{playlist}", method = RequestMethod.PUT)
    public String putPlaylist(@PathVariable Integer playlistId, @PathVariable Playlist playlist, Model model){
        db.putPlaylist(playlist, playlistId);
        model.addAttribute(playlist);
        return "playlist";
    }

    @RequestMapping(value = "/playlist/{playlistId}", method = RequestMethod.DELETE)
    public String deletePlaylist(@PathVariable Integer playlistId){
        db.deletePlaylist(playlistId);
        return "redirect:/playlists";
    }
}
