package petermcneil.musiclibrary.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import petermcneil.domain.Artist;
import petermcneil.musiclibrary.services.ArtistService;
import petermcneil.musiclibrary.services.RecordingService;

@Controller
public class ArtistController {
    private final ArtistService db;
    private static final Logger LOG = LoggerFactory.getLogger(RecordingService.class);

    public ArtistController(ArtistService db){
        this.db = db;
    }

    @RequestMapping(value = "/artist/{artistId}", method = RequestMethod.GET)
    public String getArtist(@PathVariable Integer artistId, Model model){
        LOG.info("REQUEST : GET Artist object at the id: {}", artistId);
        model.addAttribute("artist", db.getArtist(artistId));
        return "artist";
    }

    @RequestMapping(value = "/artists", method = RequestMethod.GET)
    public String getArtistList(Model model){
        LOG.info("REQUEST : GET List of all the Artists");
        model.addAttribute("artists", db.getArtistList());
        return "artistList";
    }

    @RequestMapping(value = "/artist", method = RequestMethod.POST)
    public String postArtist(Artist artist){
        LOG.info("REQUEST : POST the artist {} to the library", artist.getName());
        Integer artistId = db.postArtist(artist);
        return "redirect:/artist/{" + artistId + "}";
    }

    @RequestMapping(value = "/artist/{artistId}", method = RequestMethod.PUT)
    public String putArtist(@PathVariable Integer artistId, Artist artist){
        LOG.info("REQUEST : PUT the artist {} to the id: {}", artist.getName(), artistId);
        db.putArtist(artist, artistId);
        return "redirect:artist/{artistId}";
    }

    @RequestMapping(value = "/artist/{artistId}", method = RequestMethod.DELETE)
    public String deleteArtist(@PathVariable Integer artistId){
        LOG.info("REQUEST : DELETE the artist at the id: {}", artistId);
        db.deleteArtist(artistId);
        return "redirect:/artists";
    }


}
