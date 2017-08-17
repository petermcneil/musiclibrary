package petermcneil.musiclibrary.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import petermcneil.domain.Recording;
import petermcneil.musiclibrary.services.memory.RecordingMemoryService;

@Controller
public class RecordingController {
    private RecordingMemoryService db;
    private static final Logger LOG = LoggerFactory.getLogger(RecordingController.class);

    public RecordingController (RecordingMemoryService db){
        this.db = db;
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
    public String postRecording(@PathVariable Recording recording){
        LOG.info("REQUEST : POST this recording {}", recording.getTitle());
        Integer recordingId = db.postRecording(recording);
        return "redirect:/recording/{"+ recordingId +"}";
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
