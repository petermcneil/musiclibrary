package petermcneil.musiclibrary.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import petermcneil.domain.Playlist;
import petermcneil.musiclibrary.services.PlaylistService;
import petermcneil.musiclibrary.services.RecordingService;

import java.util.Set;

@Controller
public class PlaylistController {
    private final PlaylistService db;
    private static final Logger LOG = LoggerFactory.getLogger(PlaylistController.class);

    public PlaylistController(PlaylistService db){
        this.db = db;
    }

    @RequestMapping(value = "/playlists", method = RequestMethod.GET)
    public String getPlaylistList(Model model){
        LOG.info("REQUEST : GET a list of all the playlists");
        model.addAttribute("playlists", db.getPlaylistList());
        return "playlistList";
    }

    @RequestMapping(value = "/playlist/{playlistId}", method = RequestMethod.GET)
    public String getPlaylist(@PathVariable Integer playlistId, Model model){
        LOG.info("REQUEST : GET the playlist at the id: {}", playlistId);
        model.addAttribute(db.getPlaylist(playlistId));
        return "playlist";
    }

    @RequestMapping(value = "/playlist", method = RequestMethod.POST)
    public String postPlaylist(Playlist playlist){
        LOG.info("REQUEST : POST the playlist ({}) to the db", playlist.getTitle());
        Integer playlistId = db.postPlaylist(playlist);
        return "redirect:/playlist/{" + playlistId +"}";
    }

    @RequestMapping(value = "/playlist/{playlistId}", method = RequestMethod.PUT)
    public String putPlaylist(@PathVariable Integer playlistId, Playlist playlist, Model model){
        LOG.info("REQUEST : PUT the playlist ({}) to the id: {}", playlist.getTitle(), playlistId);
        db.putPlaylist(playlist, playlistId);
        model.addAttribute(playlist);
        return "playlist";
    }

    @RequestMapping(value = "/playlist/{playlistId}", method = RequestMethod.DELETE)
    public String deletePlaylist(@PathVariable Integer playlistId){
        LOG.info("REQUEST : DELETE the playlist at the id {}", playlistId);
        db.deletePlaylist(playlistId);
        return "redirect:/playlists";
    }
}
