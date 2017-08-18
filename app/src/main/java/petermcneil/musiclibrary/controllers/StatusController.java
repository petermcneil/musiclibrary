package petermcneil.musiclibrary.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import petermcneil.musiclibrary.services.StatusService;

@Controller
public class StatusController {
    public StatusService db;

    public StatusController(StatusService statusService){
        this.db = statusService;
    }

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public String getStatus(Model model){
        String status = db.getStatus() ? "Yey! The connection is alive!" : "Aw! The connection is dead :(";
        model.addAttribute("status", status);
        model.addAttribute("connected", db.getStatus());
        return "status";
    }

}
