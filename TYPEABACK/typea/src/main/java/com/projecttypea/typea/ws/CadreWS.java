package com.projecttypea.typea.ws;

import java.util.List;

import javax.validation.Valid;

import com.projecttypea.typea.bean.Cadre;
import com.projecttypea.typea.service.CadreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")

public class CadreWS {

    @Autowired
    CadreService cadreService;

    @GetMapping("/cadres")
    public List<Cadre> findAll() {
        return cadreService.findAll();
    }

    @PostMapping("/addcadre/{missionId}")
    public int addMissionStage(@Valid @PathVariable Long missionId, @RequestBody Cadre cadre) {
        return cadreService.addCadreMission(missionId, cadre);
    }

}
