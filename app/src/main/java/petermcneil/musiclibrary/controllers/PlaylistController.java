package petermcneil.musiclibrary.controllers;

import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import petermcneil.domain.Artist;
import petermcneil.domain.Playlist;
import petermcneil.musiclibrary.services.memory.PlaylistMemoryService;
import petermcneil.domain.Song;
import petermcneil.mutable.MutablePlaylist;

@Controller
public class PlaylistController {
    private final PlaylistMemoryService db;
    private static final Logger LOG = LoggerFactory.getLogger(PlaylistController.class);

    public PlaylistController(PlaylistMemoryService db){
        this.db = db;

        db.post(Playlist.playlistBuilder()
                .title("Pop tunes")
                .tracks(ImmutableList.of(
                        Song.songBuilder().title("Cash Out").length(150).leadArtist(Artist.artistBuilder().name("Calvin Harris").build()).genre("Pop").build(),
                        Song.songBuilder().title("Hello").length(100000).leadArtist(Artist.artistBuilder().name("Lionel Richie").build()).genre("Sad").build(),
                        Song.songBuilder().title("You & Me").length(173).leadArtist(Artist.artistBuilder().name("Ryan Bluth").build()).genre("Dance").build(),
                        Song.songBuilder().title("Song 2").length(200).leadArtist(Artist.artistBuilder().name("Blur").build()).genre("90's").build()
                ))
                .build());
    }

    @RequestMapping(value = "/playlists", method = RequestMethod.GET)
    public String getPlaylistList(Model model){
        LOG.info("REQUEST : GET a list of all the playlists");
        model.addAttribute("playlists", db.getList());
        return "playlistList";
    }

    @RequestMapping(value = "/playlist/{playlistId}", method = RequestMethod.GET)
    public String getPlaylist(@PathVariable Integer playlistId, Model model){
        LOG.info("REQUEST : GET the playlist at the id: {}", playlistId);
        model.addAttribute(db.get(playlistId));
        return "playlist";
    }

    //TODO Fix tracks
    @RequestMapping(value = "/playlist", method = RequestMethod.POST)
    public String postPlaylist(MutablePlaylist mutePlaylist){

        Playlist playlist = Playlist.playlistBuilder()
                .title(mutePlaylist.getTitle())
                .tracks(ImmutableList.of(Song.songBuilder().title(mutePlaylist.getTracks()).build()))
                .build();

        LOG.info("REQUEST : POST the playlist ({}) to the db", playlist.getTitle());
        Integer playlistId = db.post(playlist);

        return "redirect:/playlist/" + playlistId;
    }

    @RequestMapping(value = "/playlist/{playlistId}", method = RequestMethod.PUT)
    public String putPlaylist(@PathVariable Integer playlistId, Playlist playlist, Model model){
        LOG.info("REQUEST : PUT the playlist ({}) to the id: {}", playlist.getTitle(), playlistId);
        db.put(playlist, playlistId);
        model.addAttribute(playlist);
        return "playlist";
    }

    @RequestMapping(value = "/playlist/{playlistId}", method = RequestMethod.DELETE)
    public String deletePlaylist(@PathVariable Integer playlistId){
        LOG.info("REQUEST : DELETE the playlist at the id {}", playlistId);
        db.delete(playlistId);
        return "redirect:/playlists";
    }
}
