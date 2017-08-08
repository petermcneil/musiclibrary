package petermcneil.musiclibrary.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import petermcneil.domain.Artist;
import petermcneil.domain.Song;
import petermcneil.musiclibrary.services.SongService;

import java.util.List;

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
        model.addAttribute("songs", db.getSongs());
        return "/songs";
    }

    @RequestMapping(value="/song", method = RequestMethod.POST)
    public String postSong(Song song){
        Integer songId = db.postSong(song);
        return "redirect:/song/{" + songId + "}";
    }

    @RequestMapping(value = "/song/{songId}", method = RequestMethod.DELETE)
    public String deleteSong(@PathVariable Integer songId){
        boolean worked = db.deleteSong(songId);
        if(worked == true){
            return "/songs";
        }else{
            //throw error
            return "";
        }
    }

}
