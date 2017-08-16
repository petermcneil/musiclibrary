package petermcneil.musiclibrary.controllers;

import com.google.common.collect.ImmutableSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import petermcneil.domain.Artist;
import petermcneil.mutable.MutableSong;
import petermcneil.domain.Song;
import petermcneil.musiclibrary.services.SongService;

@Controller
public class SongController {
    private final SongService db;
    private static final Logger LOG = LoggerFactory.getLogger(SongController.class);

    public SongController(SongService db){
        this.db = db;

        db.postSong(Song.songBuilder().title("song1").build());
        db.postSong(Song.songBuilder().title("song2").leadArtist(Artist.artistBuilder().name("Blur").build()).build());
        db.postSong(Song.songBuilder().title("song3").build());
        db.postSong(Song.songBuilder().title("song4").build());
    }

    @RequestMapping(value="/song/{songId}", method = RequestMethod.GET)
    public String getSong(@PathVariable Integer songId, Model model){
        LOG.info("REQUEST : GET the song at the id: {}", songId);
        model.addAttribute("song", db.getSong(songId));
        return "song";
    }

    @RequestMapping(value="/songs", method= RequestMethod.GET)
    public String getSongs(Model model){
        LOG.info("REQUEST : GET the list of all the songs");
        model.addAttribute("songs", db.getSongList());
        return "songList";
    }

    @RequestMapping(value="/song", method = RequestMethod.POST)
    public String postSong( MutableSong muteSong){
        LOG.info("REQUEST : POST the song ({}) to the library", muteSong.getTitle());

        Song song = Song.songBuilder()
                .title(muteSong.getTitle())
                .length(muteSong.getLength())
                .leadArtist(Artist.artistBuilder().name(muteSong.getLeadArtist()).build())
                .artwork(muteSong.getArtwork())
                .featuredArtists(ImmutableSet.of(Artist.artistBuilder().name(muteSong.getFeaturedArtists()).build()))
                .lyrics(muteSong.getLyrics())
                .playcount(muteSong.getPlaycount())
                .build();

        Integer songId = db.postSong(song);
        return "redirect:/song/" + songId;
    }

    @RequestMapping(value = "/song/{songId}", method = RequestMethod.PUT)
    public String putSong(@PathVariable Integer songId, Song song, Model model){
        LOG.info("REQUEST : PUT the song ({}) to the id: {}", song.getTitle(), songId);
        db.putSong(songId, song);
        model.addAttribute(song);
        return "song";
    }

    @RequestMapping(value = "/song/{songId}", method = RequestMethod.DELETE)
    public String deleteSong(@PathVariable Integer songId){
        LOG.info("REQUEST : DELETE the song at the id: {}", songId);
        boolean worked = db.deleteSong(songId);
        if(worked){
            return "redirect:/songs";
        }else{
            //throw error
            return "";
        }
    }

}
