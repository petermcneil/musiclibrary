package petermcneil.musiclibrary.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import petermcneil.domain.Artist;
import petermcneil.musiclibrary.services.ArtistService;

@RestController
public class ArtistController {
    private final ArtistService db;

    public ArtistController(ArtistService db){
        this.db = db;
    }

    @RequestMapping(value = "/artist/{artistId}", method = RequestMethod.GET)
    public String getArtist(@PathVariable Integer artistId, Model model){
        model.addAttribute("artist", db.getArtist(artistId));
        return "artist";
    }

    @RequestMapping(value = "/artists", method = RequestMethod.GET)
    public String getArtistList(Model model){
        model.addAttribute("artists", db.getArtistList());
        return "artists";
    }

    @RequestMapping(value = "/artist/{artistId}/recordings", method = RequestMethod.GET)
    public String getArtistsRecording(@PathVariable Integer artistId, Model model){
        model.addAttribute(db.getArtistRecordingList(artistId));
        return "/artist/recordings";
    }

    @RequestMapping(value = "/artist", method = RequestMethod.POST)
    public String postArtist(Artist artist){
        Integer artistId = db.postArtist(artist);
        return "redirect:/artist/{" + artistId + "}";
    }

    @RequestMapping(value = "/artist/{artistId}", method = RequestMethod.PUT)
    public String putArtist(@PathVariable Integer artistId, Artist artist){
        db.putArtist(artist, artistId);
        return "artist/{artistId}";
    }

    @RequestMapping(value = "/artist/{artistId}", method = RequestMethod.DELETE)
    public String deleteArtist(@PathVariable Integer artistId){
        db.deleteArtist(artistId);
        return "artists";
    }


}
