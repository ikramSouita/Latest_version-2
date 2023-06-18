package com.projecttypea.typea.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.projecttypea.typea.bean.*;
import com.projecttypea.typea.dao.ManifestationDao;
import com.projecttypea.typea.dao.UserDao;
import com.projecttypea.typea.security.enums.DemandesState;

import net.sf.jasperreports.engine.JRException;

import org.apache.el.util.JreCompat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ManifestationService {

    @Autowired
    ManifestationDao manifestationDao;

    @Autowired
    formulaire form;

    @Autowired
    UserDao userDao;

    @Autowired
    SoutienService soutienService;

    @Autowired
    DemandeService demandeService;

    @Autowired
    MailMessagesService mailMssgsService;

    public Manifestation getById(Long id) {
        return manifestationDao.getById(id);
    }

    public Optional<Manifestation> findById(Long id) {
        return manifestationDao.findById(id);
    }

    public List<Manifestation> findAll() {
        return manifestationDao.findAll();
    }

    public Long ajoutManifestation(Manifestation manif, HttpSession session) {
        try {
            User currentUser = userDao.findByEmail((String) (session.getAttribute("session")));
            if (currentUser.getDonne() != null) {
                addManifestation(manif, session);
                Long manifId = manif.getId();
                soutienService.addSoutienManifestation(manifId, manif.getSoutien(), session);
                return manif.getId();
            } else {
                return Long.valueOf(-2);
            }
        } catch (Exception e) {
            return Long.valueOf(-1);
        }
    }

    public int addManifestation(Manifestation manif, HttpSession session) {
        User currentUser = userDao.findByEmail((String) session.getAttribute("session"));
        manif.setUser(currentUser);
        manif.setDemandeType("Manifestation");
        manif.setState(DemandesState.IDLE);
        manifestationDao.save(manif);
        return 1;
    }

    public int updateManifestation(Long id, Manifestation manifestation) {
        Manifestation currentManif = manifestationDao.getById(id);
        if (currentManif == null) {
            return -1;
        } else {
            currentManif.setDateDebut(manifestation.getDateDebut());
            currentManif.setDateDepart(manifestation.getDateDepart());
            currentManif.setDateFin(manifestation.getDateFin());
            currentManif.setDateRetour(manifestation.getDateRetour());
            currentManif.setNatureParticiaton(manifestation.getNatureParticiaton());
            currentManif.setPays(manifestation.getPays());
            currentManif.setSoutien(manifestation.getSoutien());
            currentManif.setTitreManifestation(manifestation.getTitreManifestation());
            currentManif.setTitreParticipation(manifestation.getTitreParticipation());
            currentManif.setVille(manifestation.getVille());
            currentManif.setDocuments(manifestation.getDocuments());
            manifestationDao.save(currentManif);
            return 1;
        }
    }

    public void deleteById(Long id) {
        manifestationDao.deleteById(id);
    }

    public List<Manifestation> findAllByUserEmail(HttpSession session) {
        String email = (String) session.getAttribute("session");
        return manifestationDao.findAllByUserEmail(email);
    }

    public int manifAccepted(Long manifId, MailMessages params, MultipartFile document) throws IOException, JRException {
        Manifestation currentManif = manifestationDao.getById(manifId);
        if (currentManif.getState() == DemandesState.APPROVED
                || currentManif.getState() == DemandesState.REFUSED) {
            return -1;
        } else {
            currentManif.getNvMnt().setEtat(1);
            currentManif.setState(DemandesState.APPROVED);
            manifestationDao.save(currentManif);
            //byte[] lettre = form.exportLettremanif(manifId);
            byte[] lettre = document.getBytes();
            System.out.println("file byte service " + document.getBytes());
            mailMssgsService.sendMail(params, lettre);
            return 1;
        }
    }

    public int manifRefused(Long manifId) {
        Manifestation currentManif = manifestationDao.getById(manifId);
        if (currentManif.getState() == DemandesState.APPROVED ||  currentManif.getState() == DemandesState.ANNULE) {
            return -1;
        } else {
            currentManif.getNvMnt().setEtat(-1);
            currentManif.setState(DemandesState.REFUSED);
            manifestationDao.save(currentManif);
            return 1;
        }
    }

    public int manifApproved(Long manifId) {
        Manifestation currentManif = manifestationDao.getById(manifId);
        if (currentManif.getState() == DemandesState.ANNULE
                || currentManif.getState() == DemandesState.REFUSED) {
            return -1;
        } else {
            currentManif.getNvMnt().setEtat(-1);
            currentManif.setState(DemandesState.APPROVED);
            manifestationDao.save(currentManif);
            return 1;
        }
    }

    public User getCurrentUser(Long manifId) {
        Manifestation currentManifestation = getById(manifId);
        User currentUser = currentManifestation.getUser();
        return currentUser;
    }

    public DoneesPro getCurrentDonne(Long manifId) {
        Manifestation currentManifestation = getById(manifId);
        User currentUser = currentManifestation.getUser();
        DoneesPro currentDonnePro = currentUser.getDonne();
        return currentDonnePro;
    }

    public Soutien getSoutienByMStage(Long manifId) {
        Manifestation currentManifestation = getById(manifId);
        Soutien currentSoutien = currentManifestation.getSoutien();
        return currentSoutien;
    }

    public List<Manifestation> findAllByState(DemandesState state) {
        return manifestationDao.findAllByState(state);
    }

    public Manifestation changeStatusToCanceled(Long manifId){
        Manifestation manif = getById(manifId);
        manif.setState(DemandesState.ANNULE);

        return manifestationDao.save(manif);
    }
}