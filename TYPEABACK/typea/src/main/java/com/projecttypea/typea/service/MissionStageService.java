package com.projecttypea.typea.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.projecttypea.typea.bean.*;
import com.projecttypea.typea.dao.MissionStageDao;
import com.projecttypea.typea.dao.UserDao;
import com.projecttypea.typea.security.enums.DemandesState;

import net.sf.jasperreports.engine.JRException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin()
@Service
public class MissionStageService {
    public MissionStage getById(Long aLong) {
        return missionStageDao.getById(aLong);
    }

    @Autowired
    formulaire form;

    @Autowired
    MissionStageDao missionStageDao;

    @Autowired
    DemandeService demandeService;

    @Autowired
    UserDao userDao;

    @Autowired
    SoutienService soutienService;

    @Autowired
    CadreService cadreService;

    @Autowired
    MailMessagesService mailMssgsService;

    public Long ajoutMissionStage(MissionStage mStage, HttpSession session) {

        User currentUser = userDao.findByEmail((String) (session.getAttribute("session")));
        if (currentUser.getDonne() != null) {
            addMissionStage(mStage, session);
            Long mStageId = mStage.getId();
            soutienService.addSoutienMission(mStageId, mStage.getSoutien(), session);
            cadreService.addCadreMission(mStageId, mStage.getCadre());
            return mStageId;
        } else {
            return Long.valueOf(-2);
        }

    }

    public Optional<MissionStage> findById(Long id) {
        return missionStageDao.findById(id);
    }

    public void deleteById(Long id) {
        missionStageDao.deleteById(id);
    }

    public List<MissionStage> findAll() {
        return missionStageDao.findAll();
    }

    public int addMissionStage(MissionStage mission, HttpSession session) {
        User currentUser = userDao.findByEmail((String) session.getAttribute("session"));
        mission.setState(DemandesState.IDLE);
        mission.setUser(currentUser);
        missionStageDao.save(mission);
        return 1;
    }

    public int updateMissionStage(Long id, MissionStage missionStage) {
        MissionStage currentMouStage = missionStageDao.getById(id);
        if (currentMouStage == null) {
            return -1;
        } else {
            currentMouStage.setCadre(missionStage.getCadre());
            currentMouStage.setDateDebut(missionStage.getDateDebut());
            currentMouStage.setDateDepart(missionStage.getDateDepart());
            currentMouStage.setDateFin(missionStage.getDateFin());
            currentMouStage.setDateRetour(missionStage.getDateRetour());
            currentMouStage.setDocuments(missionStage.getDocuments());
            currentMouStage.setObjetMission(missionStage.getObjetMission());
            currentMouStage.setPays(missionStage.getPays());
            currentMouStage.setSoutien(missionStage.getSoutien());
            currentMouStage.setVille(missionStage.getVille());
            missionStageDao.save(currentMouStage);
            return 1;
        }
    }

    public List<MissionStage> findAllByUserEmail(HttpSession session) {
        String email = (String) session.getAttribute("session");
        return missionStageDao.findAllByUserEmail(email);
    }

    public User getCurrentUser(Long mStageId) {
        MissionStage currentMStage = getById(mStageId);
        User currentUser = currentMStage.getUser();
        return currentUser;
    }

    public DoneesPro getCurrentDonne(Long mStageId) {
        MissionStage currentMStage = getById(mStageId);
        User currentUser = currentMStage.getUser();
        DoneesPro currentDonnePro = currentUser.getDonne();
        return currentDonnePro;
    }

    public Cadre getCadreByMStage(Long mStageId) {
        MissionStage currentMStage = getById(mStageId);
        Cadre currentCadre = currentMStage.getCadre();
        return currentCadre;
    }

    public Soutien getSoutienByMStage(Long mStageId) {
        MissionStage currentMStage = getById(mStageId);
        Soutien currentSoutien = currentMStage.getSoutien();
        return currentSoutien;
    }

    public int mStageRefused(Long missionId) {
        MissionStage currentMissionStage = missionStageDao.getById(missionId);
        if (currentMissionStage.getState() == DemandesState.APPROVED
                || currentMissionStage.getState() == DemandesState.ANNULE) {
            return -1;
        } else {
            currentMissionStage.getNvMnt().setEtat(-1);
            currentMissionStage.setState(DemandesState.REFUSED);
            missionStageDao.save(currentMissionStage);
            return 1;
        }
    }

    public int mStageApproved(Long missionId) {
        MissionStage currentMissionStage = missionStageDao.getById(missionId);
        if (currentMissionStage.getState() == DemandesState.ANNULE
                || currentMissionStage.getState() == DemandesState.REFUSED) {
            return -1;
        } else {
            currentMissionStage.getNvMnt().setEtat(-1);
            currentMissionStage.setState(DemandesState.APPROVED);
            missionStageDao.save(currentMissionStage);
            return 1;
        }
    }
/*
    public int mStageAccepted(Long missionId, MailMessages params, MultipartFile document) throws IOException, JRException {
        MissionStage currentMissionStage = missionStageDao.getById(missionId);
        if (currentMissionStage.getState() == DemandesState.APPROVED
                || currentMissionStage.getState() == DemandesState.REFUSED) {
            return -1;
        } else {
            currentMissionStage.getNvMnt().setEtat(1);
            currentMissionStage.setState(DemandesState.APPROVED);
            missionStageDao.save(currentMissionStage);
            byte[] lettre = document.getBytes();
            mailMssgsService.sendMail(params, lettre);
            return 1;
        }
    }*/
    public int mStageAccepted(Long missionId, MailMessages params, MultipartFile document) throws IOException, JRException {
        MissionStage currentMissionStage = missionStageDao.getById(missionId);
        if (currentMissionStage.getState() == DemandesState.APPROVED
                || currentMissionStage.getState() == DemandesState.REFUSED) {
            return -1;
        } else {
            currentMissionStage.getNvMnt().setEtat(1);
            currentMissionStage.setState(DemandesState.APPROVED);
            missionStageDao.save(currentMissionStage);
            //byte[] lettre = form.exportLettremission(missionId);
            byte[] lettre = document.getBytes();
            System.out.println("file byte service " + document.getBytes());
            mailMssgsService.sendMail(params, lettre);
            return 1;
        }
    }

    public List<MissionStage> findAllByState(DemandesState state) {
        return missionStageDao.findAllByState(state);
    }

    public MissionStage changeStatusToCanceled(Long mstageId){
        MissionStage mstage = getById(mstageId);
        mstage.setState(DemandesState.ANNULE);

        return missionStageDao.save(mstage);
    }
}
