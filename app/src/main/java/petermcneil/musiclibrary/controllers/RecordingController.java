package petermcneil.musiclibrary.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import petermcneil.domain.Recording;
import petermcneil.musiclibrary.services.RecordingService;

@RestController
public class RecordingController {
    private RecordingService db;

    public RecordingController (RecordingService db){
        this.db = db;
    }

    @RequestMapping(value = "/recordings", method = RequestMethod.GET)
    public String getRecordingList(){
        db.getRecordingList();
        return "recordings";
    }

    @RequestMapping(value = "/recording/{recordingId}", method = RequestMethod.GET)
    public String getRecording(@PathVariable Integer recordingId, Model model){
        model.addAttribute(db.getRecording(recordingId));
        return "recording";
    }

    @RequestMapping(value = "/recording", method = RequestMethod.POST)
    public String postRecording(@PathVariable Recording recording){
        Integer recordingId = db.postRecording(recording);
        return "redirect:recording/{"+ recordingId +"}";
    }

    @RequestMapping(value = "/recording/{recordingId}", method = RequestMethod.PUT)
    public String putRecording(@PathVariable Integer recordingId, Recording recording, Model model){
        db.putRecording(recording, recordingId);
        model.addAttribute(db.getRecording(recordingId));
        return "redirect:recording/{" + recordingId + "}";
    }

    @RequestMapping(value = "/recording/{recordingId}", method = RequestMethod.DELETE)
    public String deleteRecording(@PathVariable Integer recordingId){
        db.deleteRecording(recordingId);
        return "redirect:recordings";
    }
}
