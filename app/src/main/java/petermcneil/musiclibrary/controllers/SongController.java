package petermcneil.musiclibrary.controllers;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import petermcneil.domain.Artist;
import petermcneil.musiclibrary.services.CRUDService;
import petermcneil.mutable.MutableSong;
import petermcneil.domain.Song;
import petermcneil.musiclibrary.services.memory.SongMemoryService;

@Controller
public class SongController {
    private final CRUDService<Song> db;
    private static final Logger LOG = LoggerFactory.getLogger(SongController.class);

    public SongController(CRUDService<Song> db) {
        this.db = db;
    }

    @RequestMapping(value="/song/{songId}", method = RequestMethod.GET)
    public String getSong(@PathVariable Integer songId, Model model){
        LOG.info("REQUEST : GET the song at the id: {}", songId);
        model.addAttribute("song", db.get(songId));
        return "song";
    }

    @RequestMapping(value="/songs", method= RequestMethod.GET)
    public String getSongs(Model model){
        LOG.info("REQUEST : GET the list of all the songs");
        model.addAttribute("songs", db.getList());
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
                .featuredArtists(ImmutableList.of(Artist.artistBuilder().name(muteSong.getFeaturedArtists()).build()))
                .lyrics(muteSong.getLyrics())
                .playcount(muteSong.getPlaycount())
                .build();

        Integer songId = db.post(song);
        return "redirect:/song/" + songId;
    }

    @RequestMapping(value = "/song/{songId}", method = RequestMethod.POST)
    public String putSong(@PathVariable Integer songId, MutableSong muteSong){
        System.out.println("Putting famalamam");
        Song song = Song.songBuilder()
                .songId(songId)
                .title(muteSong.getTitle())
                .length(muteSong.getLength())
                .leadArtist(Artist.artistBuilder().name(muteSong.getLeadArtist()).build())
                .artwork(muteSong.getArtwork())
                .featuredArtists(ImmutableList.of(Artist.artistBuilder().name(muteSong.getFeaturedArtists()).build()))
                .lyrics(muteSong.getLyrics())
                .playcount(muteSong.getPlaycount())
                .build();

        LOG.info("REQUEST : PUT the song ({}) to the id: {}", muteSong.getTitle(), songId);
        db.put(song, songId);
        return "redirect:/song/" + songId;
    }

    @RequestMapping(value = "/song/{songId}", method = RequestMethod.DELETE)
    public void deleteSong(@PathVariable Integer songId){
        LOG.info("REQUEST : DELETE the song at the id: {}", songId);
        db.delete(songId);
    }

}
