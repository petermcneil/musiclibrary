package petermcneil.musiclibrary.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import petermcneil.domain.Artist;
import petermcneil.musiclibrary.services.CRUDService;
import petermcneil.musiclibrary.services.memory.ArtistMemoryService;
import petermcneil.domain.Bio;
import petermcneil.mutable.MutableArtist;

@Controller
public class ArtistController {
    private final CRUDService<Artist> db;
    private static final Logger LOG = LoggerFactory.getLogger(ArtistController.class);

    public ArtistController(CRUDService<Artist> db){
        this.db = db;
/*
        db.post(Artist.artistBuilder()
                .name("Calvin Harris")
                .type("Solo")
                .bio(Bio.bioBuilder()
                        .biography("Calvin is an excellent man, blah blah blah")
                        .build())
                .build());

        db.post(Artist.artistBuilder()
                .name("Lionel Richie")
                .type("Solo")
                .bio(Bio.bioBuilder()
                        .biography("Famed for his ballads.... lalalalalalala")
                        .build())
                .build());*/
    }

    @RequestMapping(value = "/artist/{artistId}", method = RequestMethod.GET)
    public String getArtist(@PathVariable Integer artistId, Model model){
        LOG.info("REQUEST : GET Artist object at the id: {}", artistId);
        model.addAttribute("artist", db.get(artistId));
        return "artist";
    }

    @RequestMapping(value = "/artists", method = RequestMethod.GET)
    public String getArtistList(Model model){
        LOG.info("REQUEST : GET List of all the Artists");
        model.addAttribute("artists", db.getList());
        return "artistList";
    }

    @RequestMapping(value = "/artist", method = RequestMethod.POST)
    public String postArtist(MutableArtist muteArtist){

        Artist artist = Artist.artistBuilder()
                .name(muteArtist.getName())
                .type(muteArtist.getType())
                .bio(Bio.bioBuilder().biography(muteArtist.getBio()).build())
                .build();

        LOG.info("REQUEST : POST the artist {} to the library", artist.getName());
        Integer artistId = db.post(artist);

        return "redirect:/artist/" + artistId;
    }

    @RequestMapping(value = "/artist/{artistId}", method = RequestMethod.PUT)
    public String putArtist(@PathVariable Integer artistId, Artist artist){
        LOG.info("REQUEST : PUT the artist {} to the id: {}", artist.getName(), artistId);
        db.put(artist, artistId);
        return "redirect:artist/" + artistId;
    }

    @RequestMapping(value = "/artist/{artistId}", method = RequestMethod.DELETE)
    public String deleteArtist(@PathVariable Integer artistId){
        LOG.info("REQUEST : DELETE the artist at the id: {}", artistId);
        db.delete(artistId);
        return "redirect:/artists";
    }


}
