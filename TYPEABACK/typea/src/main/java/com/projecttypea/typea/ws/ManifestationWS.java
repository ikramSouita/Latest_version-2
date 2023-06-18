package com.projecttypea.typea.ws;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.Valid;

import com.projecttypea.typea.bean.DoneesPro;
import com.projecttypea.typea.bean.MailMessages;
import com.projecttypea.typea.bean.Manifestation;
import com.projecttypea.typea.bean.Soutien;
import com.projecttypea.typea.bean.User;
import com.projecttypea.typea.security.enums.DemandeType;
import com.projecttypea.typea.security.enums.DemandesState;
import com.projecttypea.typea.service.ManifestationService;

import com.projecttypea.typea.service.PDFService;
import net.sf.jasperreports.engine.JRException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController

@RequestMapping("/api")

public class ManifestationWS {

    @Autowired
    ManifestationService manifestationService;
    @Autowired
    PDFService pdfService;

    @PostMapping("/user/manifestationadd")
    public Long ajoutManifestation(@RequestBody Manifestation manif, HttpSession session) {
        return manifestationService.ajoutManifestation(manif, session);
    }

    @PutMapping("/user/updatemanif/{id}")
    public int updateManifestation(@PathVariable Long id, @RequestBody Manifestation manifestation) {
        return manifestationService.updateManifestation(id, manifestation);
    }

    @Transactional
    @DeleteMapping("/deletemanif/{id}")
    public void deleteById(Long id) {
        manifestationService.deleteById(id);
    }

    @Transactional
    @DeleteMapping("/user/cancelmanif/{id}")
    public int deleteByIdForUser(@PathVariable Long id) {
        manifestationService.changeStatusToCanceled(id);
        return 0;
    }

    @GetMapping("/admin/manifestations")
    public List<Manifestation> findAll() {
        return manifestationService.findAll();
    }

    @PostMapping("/user/addmanifestation")
    public int addManifestation(@Valid @RequestBody Manifestation manifestation, HttpSession session) {
        return manifestationService.addManifestation(manifestation, session);
    }

    @GetMapping("/user/getmanifestations")
    public List<Manifestation> findAllByUserEmail(HttpSession session) {
        return manifestationService.findAllByUserEmail(session);
    }

    @GetMapping("/admin/getmanifbyid/{id}")
    public Manifestation getById(@PathVariable Long id) {
        return manifestationService.getById(id);
    }

    @GetMapping("/admin/getuserbymanif/{manifId}")
    public User getCurrentUser(@PathVariable Long manifId) {
        return manifestationService.getCurrentUser(manifId);
    }

    @GetMapping("/admin/getdonnebymanif/{manifId}")
    public DoneesPro getCurrentDonne(@PathVariable Long manifId) {
        return manifestationService.getCurrentDonne(manifId);
    }

    @GetMapping("/admin/getsoutienbymanif/{manifId}")
    public Soutien getSoutienByMStage(@PathVariable Long manifId) {
        return manifestationService.getSoutienByMStage(manifId);
    }

    @PostMapping(value = "/admin/acceptmanif/{manifId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public int manifAccepted(@PathVariable Long manifId,
                             @RequestParam("file") MultipartFile file,
                             @RequestParam("toEmail") String toEmail,
                             @RequestParam("subject") String subject,
                             @RequestParam("body") String body)
            throws IOException, JRException {

        MailMessages params = new MailMessages();
        params.setBody(body); params.setSubject(subject); params.setToEmail(toEmail);
        if (file == null) {
            throw new RuntimeException("You must select the a file for uploading");
        }
        InputStream inputStream = file.getInputStream();
        String originalName = file.getOriginalFilename();
        String name = file.getName();
        String contentType = file.getContentType();
        long size = file.getSize();
        System.out.println("inputStream: " + inputStream);
        System.out.println("originalName: " + originalName);
        System.out.println("name: " + name);
        System.out.println("contentType: " + contentType);
        System.out.println("size: " + size);
        return manifestationService.manifAccepted(manifId, params, file);

    }

    @GetMapping("/admin/refusemanif/{manifId}")
    public int manifRefused(@PathVariable Long manifId) {
        return manifestationService.manifRefused(manifId);
    }

    @GetMapping("/admin/manif-approved/{manifId}")
    public int ChangeStatusToAPPROVED(@PathVariable Long manifId) {
        return manifestationService.manifApproved(manifId);
    }

    @GetMapping("/admin/findallmanifs/{state}")
    public List<Manifestation> findAllByState(@PathVariable DemandesState state) {
        return manifestationService.findAllByState(state);
    }

    @GetMapping("/admin/manifestation/pdf/{etat}/{manifId}")
    public void downloadPdf(HttpServletResponse response, @PathVariable Long manifId, @PathVariable String etat){
        System.out.println("--- generate pdf ....");
        try{
            Path file = Paths.get(pdfService.generateLettre(manifId, DemandeType.MANIFESTATION, etat).getAbsolutePath());
            if (Files.exists(file)){
                response.setContentType("application/pdf");
                response.addHeader("Content-Disposition",
                        "attachment; filename" + file.getFileName());
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

}
