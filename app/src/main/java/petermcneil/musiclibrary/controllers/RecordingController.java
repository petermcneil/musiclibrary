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
import petermcneil.domain.Recording;
import petermcneil.musiclibrary.services.memory.RecordingMemoryService;
import petermcneil.domain.Song;

import petermcneil.mutable.MutableRecording;

@Controller
public class RecordingController {
    private RecordingMemoryService db;
    private static final Logger LOG = LoggerFactory.getLogger(RecordingController.class);

    public RecordingController (RecordingMemoryService db){
        this.db = db;

        db.post(Recording.recordingBuilder()
                .title("Cash Out")
                .tracks(ImmutableSet.of( Song.songBuilder().title("Cash Out").length(150).leadArtist(Artist.artistBuilder().name("Calvin Harris").build()).genre("Pop").build()))
                .type(Recording.RecordType.SINGLE)
                .artist(Artist.artistBuilder().name("Calvin Harris").type("Solo").build())
                .build());

        db.post(Recording.recordingBuilder()
                .title("Random")
                .tracks(ImmutableSet.of( Song.songBuilder().title("Cash Out").length(150).leadArtist(Artist.artistBuilder().name("Calvin Harris").build()).genre("Pop").build(),
                        Song.songBuilder().title("Song 2").length(200).leadArtist(Artist.artistBuilder().name("Blur").build()).genre("90's").build(),
                        Song.songBuilder().title("You & Me").length(173).leadArtist(Artist.artistBuilder().name("Ryan Bluth").build()).genre("Dance").build()))
                .type(Recording.RecordType.LP)
                .build());
    }

    @RequestMapping(value = "/recordings", method = RequestMethod.GET)
    public String getRecordingList(Model model){
        LOG.info("REQUEST: GET recording list");
        model.addAttribute("recordings", db.getList());
        return "recordingList";
    }

    @RequestMapping(value = "/recording/{recordingId}", method = RequestMethod.GET)
    public String getRecording(@PathVariable Integer recordingId, Model model){
        LOG.info("REQUEST : GET the recording at the id: {}", recordingId);
        model.addAttribute("recording", db.get(recordingId));
        return "recording";
    }

    //TODO Fix the artist, type, and tracks
    @RequestMapping(value = "/recording", method = RequestMethod.POST)
    public String postRecording(MutableRecording muteRecording){

        Recording recording = Recording.recordingBuilder()
                .title(muteRecording.getTitle())
                .tracks(ImmutableSet.of(Song.songBuilder().title(muteRecording.getTracks()).build()))
                .type(Recording.RecordType.EP)
                .artist(Artist.artistBuilder().name(muteRecording.getArtist()).build())
                .artwork(muteRecording.getArtwork())
                .label(muteRecording.getLabel())
                .build();

        LOG.info("REQUEST : POST this recording {}", recording.getTitle());
        Integer recordingId = db.post(recording);

        return "redirect:/recording/"+ recordingId;
    }

    @RequestMapping(value = "/recording/{recordingId}", method = RequestMethod.PUT)
    public String putRecording(@PathVariable Integer recordingId, Recording recording, Model model){
        LOG.info("REQUEST : PUT this recording {} to the id: {}", recording.getTitle(), recordingId);
        db.put(recording, recordingId);
        model.addAttribute(db.get(recordingId));
        return "recording";
    }

    @RequestMapping(value = "/recording/{recordingId}", method = RequestMethod.DELETE)
    public String deleteRecording(@PathVariable Integer recordingId){
        LOG.info("REQUEST : DELETE the recording at the id: {}", recordingId);
        db.delete(recordingId);
        return "redirect:/recordings";
    }
}
