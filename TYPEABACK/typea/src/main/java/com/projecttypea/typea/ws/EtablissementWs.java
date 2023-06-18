package com.projecttypea.typea.ws;

import com.projecttypea.typea.bean.Etablissement;
import com.projecttypea.typea.service.EtablissementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")

public class EtablissementWs {
    @Autowired
    private EtablissementService etablissementService;

    @GetMapping("/admin/getetab/{etabId}")
    public Etablissement getById(@PathVariable Long etabId) {
        return etablissementService.getById(etabId);
    }

    public Etablissement findByNom(String nom) {
        return etablissementService.findByNom(nom);
    }

}
