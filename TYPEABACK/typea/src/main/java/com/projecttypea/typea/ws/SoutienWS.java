package com.projecttypea.typea.ws;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.Valid;

import com.projecttypea.typea.bean.Soutien;
import com.projecttypea.typea.service.SoutienService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SoutienWS {

    @Autowired
    SoutienService soutienService;

    @PostMapping("/user/addsoutienmission/{missionId}")
    public int addSoutienMission(@Valid @PathVariable Long missionId, @RequestBody Soutien soutien,
            HttpSession session) {
        return soutienService.addSoutienMission(missionId, soutien, session);
    }

    @PostMapping("/user/addsoutienmanif/{manifId}")
    public int addSoutienManifestation(@Valid @PathVariable Long manifId, @RequestBody Soutien soutien,
            HttpSession session) {
        return soutienService.addSoutienManifestation(manifId, soutien, session);
    }

    @GetMapping("/soutiens")
    public List<Soutien> findAll() {
        return soutienService.findAll();
    }

    @Transactional
    @DeleteMapping("/deletesoutien/{id}")
    public void deleteById(@PathVariable Long id) {
        soutienService.deleteById(id);
    }

}