package com.projecttypea.typea.ws;

import com.projecttypea.typea.bean.Montant_par_labo;
import com.projecttypea.typea.service.Montant_par_laboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Montant_par_laboWes {
    @Autowired
    private Montant_par_laboService montant_par_laboService;

    @PostMapping("/admin/savethem")
    public int save(@RequestBody Montant_par_labo entity) {
        return montant_par_laboService.save(entity);
    }

    @GetMapping("/admin/getmontant_par_labo")
    public List<Montant_par_labo> findAll() {
        return montant_par_laboService.findAll();
    }

    @GetMapping("/admin/getmontant_par_labo_par_year/{year}")
    public List<Montant_par_labo> findByYear(@PathVariable int year) {
        return montant_par_laboService.findByYear(year);
    }
}
