package petermcneil.musiclibrary.controllers;

import com.google.common.collect.ImmutableSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import petermcneil.domain.Artist;
import petermcneil.mutable.MutableSong;
import petermcneil.domain.Song;
import petermcneil.musiclibrary.services.memory.SongMemoryService;

@Controller
public class SongController {
    private final SongMemoryService db;
    private static final Logger LOG = LoggerFactory.getLogger(SongController.class);

    public SongController(SongMemoryService db){
        this.db = db;

        db.post(Song.songBuilder().title("You & Me").length(173).leadArtist(Artist.artistBuilder().name("Ryan Bluth").build()).genre("Dance").build());
        db.post(Song.songBuilder().title("Song 2").length(200).leadArtist(Artist.artistBuilder().name("Blur").build()).genre("90's").build());
        db.post(Song.songBuilder().title("Cash Out").length(150).leadArtist(Artist.artistBuilder().name("Calvin Harris").build()).genre("Pop").build());
        db.post(Song.songBuilder().title("Hello").length(100000).leadArtist(Artist.artistBuilder().name("Lionel Richie").build()).genre("Sad").build());
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
                .featuredArtists(ImmutableSet.of(Artist.artistBuilder().name(muteSong.getFeaturedArtists()).build()))
                .lyrics(muteSong.getLyrics())
                .playcount(muteSong.getPlaycount())
                .build();

        Integer songId = db.post(song);
        return "redirect:/song/" + songId;
    }

    @RequestMapping(value = "/song/{songId}", method = RequestMethod.PUT)
    public String putSong(@PathVariable Integer songId, Song song, Model model){
        LOG.info("REQUEST : PUT the song ({}) to the id: {}", song.getTitle(), songId);
        db.put(song, songId);
        model.addAttribute(song);
        return "song";
    }

    @RequestMapping(value = "/song/{songId}", method = RequestMethod.DELETE)
    public void deleteSong(@PathVariable Integer songId){
        LOG.info("REQUEST : DELETE the song at the id: {}", songId);
        db.delete(songId);
    }

}
