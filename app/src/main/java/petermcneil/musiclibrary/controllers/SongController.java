package petermcneil.musiclibrary.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import petermcneil.domain.Artist;
import petermcneil.domain.Song;
import petermcneil.musiclibrary.services.SongService;

@Controller
public class SongController {
    private final SongService db;

    public SongController(SongService db){
        this.db = db;

        db.postSong(Song.songBuilder().title("song1").build());
        db.postSong(Song.songBuilder().title("song2").leadArtist(Artist.artistBuilder().name("Blur").build()).build());
        db.postSong(Song.songBuilder().title("song3").build());
        db.postSong(Song.songBuilder().title("song4").build());
    }

    @RequestMapping(value="/song/{songId}", method = RequestMethod.GET)
    public String getSong(@PathVariable Integer songId, Model model){
        model.addAttribute("song", db.getSong(songId));
        return "song";
    }

    @RequestMapping(value="/songs", method= RequestMethod.GET)
    public String getSongs(Model model){
        model.addAttribute("songs", db.getSongList());
        return "songs";
    }

    @RequestMapping(value="/song", method = RequestMethod.POST)
    public String postSong(Song song){
        Integer songId = db.postSong(song);
        return "redirect:/song/{" + songId + "}";
    }

    @RequestMapping(value = "/song/{songId}", method = RequestMethod.PUT)
    public String putSong(@PathVariable Integer songId, Song song, Model model){
        db.putSong(songId, song);
        model.addAttribute(song);
        return "song";
    }

    @RequestMapping(value = "/song/{songId}", method = RequestMethod.DELETE)
    public String deleteSong(@PathVariable Integer songId){
        boolean worked = db.deleteSong(songId);
        if(worked){
            return "redirect:/songs";
        }else{
            //throw error
            return "";
        }
    }

}
