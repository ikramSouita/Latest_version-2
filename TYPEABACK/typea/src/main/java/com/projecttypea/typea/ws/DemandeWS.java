package com.projecttypea.typea.ws;

import java.util.Map;

import com.projecttypea.typea.classes.Demande;
import com.projecttypea.typea.service.DemandeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")

public class DemandeWS {

    @Autowired
    DemandeService demandeService;

    @PostMapping("/admin/addemail")
    public void sendSimpleMail(@RequestBody Map<String, String> json) {
        demandeService.sendSimpleMail(json.get("toMail"), json.get("body"), json.get("subject"));
    }

    @GetMapping("/admin/demandeslist")
    public Demande findAll() {
        return demandeService.findAll();
    }
}
