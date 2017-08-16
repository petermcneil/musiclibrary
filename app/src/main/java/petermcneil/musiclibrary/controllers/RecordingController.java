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
import petermcneil.domain.Song;
import petermcneil.musiclibrary.services.RecordingService;
import petermcneil.mutable.MutableRecording;

@Controller
public class RecordingController {
    private RecordingService db;
    private static final Logger LOG = LoggerFactory.getLogger(RecordingController.class);

    public RecordingController (RecordingService db){
        this.db = db;

        db.postRecording(Recording.recordingBuilder()
                .title("Recording 1")
                .tracks(ImmutableSet.of(Song.songBuilder().title("Song 1").length(20).playcount(30).build(), Song.songBuilder().title("Song 2").length(200).playcount(9000).build()))
                .build());
    }

    @RequestMapping(value = "/recordings", method = RequestMethod.GET)
    public String getRecordingList(){
        LOG.info("REQUEST: GET recording list");
        db.getRecordingList();
        return "recordingList";
    }

    @RequestMapping(value = "/recording/{recordingId}", method = RequestMethod.GET)
    public String getRecording(@PathVariable Integer recordingId, Model model){
        LOG.info("REQUEST : GET the recording at the id: {}", recordingId);
        model.addAttribute("recording", db.getRecording(recordingId));
        return "recording";
    }

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
        Integer recordingId = db.postRecording(recording);

        return "redirect:/recording/"+ recordingId;
    }

    @RequestMapping(value = "/recording/{recordingId}", method = RequestMethod.PUT)
    public String putRecording(@PathVariable Integer recordingId, Recording recording, Model model){
        LOG.info("REQUEST : PUT this recording {} to the id: {}", recording.getTitle(), recordingId);
        db.putRecording(recording, recordingId);
        model.addAttribute(db.getRecording(recordingId));
        return "recording";
    }

    @RequestMapping(value = "/recording/{recordingId}", method = RequestMethod.DELETE)
    public String deleteRecording(@PathVariable Integer recordingId){
        LOG.info("REQUEST : DELETE the recording at the id: {}", recordingId);
        db.deleteRecording(recordingId);
        return "redirect:/recordings";
    }
}
