package com.projecttypea.typea.ws;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.Valid;

import com.projecttypea.typea.bean.*;
import com.projecttypea.typea.security.enums.DemandeType;
import com.projecttypea.typea.security.enums.DemandesState;
import com.projecttypea.typea.service.MissionStageService;

import com.projecttypea.typea.service.PDFService;
import net.sf.jasperreports.engine.JRException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")

public class MissionStageWS {
    @Autowired
    MissionStageService missionStageService;
    @Autowired
    PDFService pdfService;

    @PostMapping("/user/missionstageadd")
    public Long ajoutMissionStage(@RequestBody MissionStage mStage, HttpSession session) {
        return missionStageService.ajoutMissionStage(mStage, session);
    }

    @GetMapping("/user/missionstage/{id}")
    public Optional<MissionStage> findById(@PathVariable Long id) {
        return missionStageService.findById(id);
    }

    @Transactional
    @DeleteMapping("/deletemission/{id}")
    public void deleteById(Long id) {
        missionStageService.deleteById(id);
    }

    @Transactional
    @DeleteMapping("/user/cancelmission/{id}")
    public int deleteByIdForUser(@PathVariable Long id) {
        missionStageService.changeStatusToCanceled(id);
        return 0;
    }

    /*
     * @PutMapping("/user/updatemission/{id}")
     * public int updateMissionStage(Long id, MissionStage missionStage) {
     * return missionStageService.updateMissionStage(id, missionStage);
     * }
     */

    @GetMapping("/admin/missions")
    public List<MissionStage> findAll() {
        return missionStageService.findAll();
    }

    @PostMapping("/user/addmission")
    public int addMissionStage(@Valid @RequestBody MissionStage mission, HttpSession session) {
        return missionStageService.addMissionStage(mission, session);
    }

    @GetMapping("/user/getmStage")
    public List<MissionStage> findAllByUserEmail(HttpSession session) {
        return missionStageService.findAllByUserEmail(session);
    }

    @GetMapping("/admin/getmstage/{id}")
    public MissionStage getById(@PathVariable Long id) {
        return missionStageService.getById(id);
    }

    @GetMapping("/admin/theuser/{mStageId}")
    public User getCurrentUser(@PathVariable Long mStageId) {
        return missionStageService.getCurrentUser(mStageId);
    }

    @GetMapping("/admin/userdonne/{mStageId}")
    public DoneesPro getCurrentDonne(@PathVariable Long mStageId) {
        return missionStageService.getCurrentDonne(mStageId);
    }

    @GetMapping("/admin/refusestage/{missionId}")
    public int mStageRefused(@PathVariable Long missionId) {
        return missionStageService.mStageRefused(missionId);
    }

    @GetMapping("/admin/mstage-approved/{missionId}")
    public int ChangeStatusToAPPROVED(@PathVariable Long missionId) {
        return missionStageService.mStageApproved(missionId);
    }

    /*
    @PostMapping(value = "/admin/acceptstage/{missionId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public int mStageAccepted(@PathVariable Long missionId, Map<MailMessages, MultipartFile> body)
            throws IOException, JRException {
        MailMessages params = (MailMessages) body.get("params");
        MultipartFile document = body.get("document") ;
        System.out.println("--- data doc " + document.getBytes());
        return missionStageService.mStageAccepted(missionId, params, document);
    }*/
    @PostMapping("/admin/acceptstage/{missionId}")
    public int mStageAccepted(@PathVariable Long missionId,
                              @RequestParam("file") MultipartFile file,
                              @RequestParam("toEmail") String toEmail,
                              @RequestParam("subject") String subject,
                              @RequestParam("body") String body)throws IOException, JRException {

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
        return missionStageService.mStageAccepted(missionId, params, file);
    }

    @GetMapping("/admin/getcadrebystage/{mStageId}")
    public Cadre getCadreByMStage(@PathVariable Long mStageId) {
        return missionStageService.getCadreByMStage(mStageId);
    }

    @GetMapping("/admin/getsoutienbystage/{mStageId}")
    public Soutien getSoutienByMStage(@PathVariable Long mStageId) {
        return missionStageService.getSoutienByMStage(mStageId);
    }

    @GetMapping("/admin/findallmstages/{state}")
    public List<MissionStage> findAllByState(@PathVariable DemandesState state) {
        return missionStageService.findAllByState(state);
    }

    @GetMapping("/admin/mission-stage/pdf/{etat}/{mstageId}")
    public void downloadPdf(HttpServletResponse response, @PathVariable Long mstageId, @PathVariable String etat){
        System.out.println("--- generate pdf ....");
        try{
            Path file = Paths.get(pdfService.generateLettre(mstageId, DemandeType.MISSION_STAGE, etat).getAbsolutePath());
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