package com.projecttypea.typea.ws;

import com.projecttypea.typea.bean.NouveauMontant;
import com.projecttypea.typea.service.NouveauMontantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NouveauMontantWS {
    @Autowired
    NouveauMontantService nouveauMontantService;

    @PostMapping("/admin/addnewmontantMS/{mStageId}")
    public int addMontantsMS(@PathVariable Long mStageId, @RequestBody NouveauMontant nvMontant) {
        return nouveauMontantService.addMontantsMS(mStageId, nvMontant);
    }

    @PostMapping("/admin/addnewmontant/{manifId}")
    public int addMontantsM(@PathVariable Long manifId, @RequestBody NouveauMontant nvMontant) {
        return nouveauMontantService.addMontantsM(manifId, nvMontant);
    }

    @GetMapping("/admin/statistic_graph_bar/{e1}/{e2}/{e3}/{e4}/{e5}/{e6}/{e7}/{e8}/{e9}/{e10}/{e11}/{e12}/{e13}/{e14}/{e15}/{date}")
    public List<String> grafbar(@PathVariable String e1, @PathVariable String e2, @PathVariable String e3,
            @PathVariable String e4, @PathVariable String e5, @PathVariable String e6, @PathVariable String e7,
            @PathVariable String e8, @PathVariable String e9, @PathVariable String e10, @PathVariable String e11,
            @PathVariable String e12, @PathVariable String e13, @PathVariable String e14, @PathVariable String e15,
            @PathVariable int date) {
        return nouveauMontantService.grafbar(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e13, e14, e15, date);
    }

    @GetMapping("/admin/statistic_graph_monsuel/{e1}/{e2}/{e3}/{e4}/{e5}/{e6}/{e7}/{e8}/{e9}/{e10}/{e11}/{e12}/{date}")
    public List<String> graph_monsuel(@PathVariable String e1, @PathVariable String e2, @PathVariable String e3,
            @PathVariable String e4, @PathVariable String e5, @PathVariable String e6, @PathVariable String e7,
            @PathVariable String e8, @PathVariable String e9, @PathVariable String e10, @PathVariable String e11,
            @PathVariable String e12, @PathVariable int date) {
        return nouveauMontantService.graph_monsuel(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, date);
    }
}
