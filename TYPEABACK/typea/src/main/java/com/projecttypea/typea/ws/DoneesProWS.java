package com.projecttypea.typea.ws;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import com.projecttypea.typea.bean.Documents;
import com.projecttypea.typea.bean.DoneesPro;
import com.projecttypea.typea.service.DocumentsService;
import com.projecttypea.typea.service.DoneesProService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")

public class DoneesProWS {

    @Autowired
    DoneesProService doneesProService;

    @Transactional
    @DeleteMapping("/deletedonnes/{id}")
    public void deleteById(@PathVariable Long id) {
        doneesProService.deleteById(id);
    }

    @GetMapping("/admin/donnéespro")
    public List<DoneesPro> findAll() {
        return doneesProService.findAll();
    }

    @PostMapping("/user/adddonéespro")
    public int addDonesPro(@RequestBody DoneesPro donne, HttpSession session) {
        return doneesProService.addDonesPro(donne, session);
    }

    @PutMapping("/user/updatedonnepro")
    public int save(@RequestBody DoneesPro donne, HttpSession session) {
        return doneesProService.save(donne, session);
    }

    @GetMapping("/admin/getdonne/{id}")
    public DoneesPro findByUserId(@PathVariable Long id) {
        return doneesProService.findByUserId(id);
    }

    @PostMapping("/user/add_documentD/{donneId}")
    public int addDonneProFile(@PathVariable Long donneId,
            @RequestParam(value = "fichier", required = false) MultipartFile file) throws IOException {
        return doneesProService.addDonneProFile(donneId, file);
    }

    @GetMapping("/admin/viewlastdoc/{donneId}")
    public Map<String, String> findLastDoneeDoc(@PathVariable Long donneId) {
        return doneesProService.findLastDoneeDoc(donneId);
    }

}