package com.projecttypea.typea.service;

import java.util.ArrayList;
import java.util.List;

import com.projecttypea.typea.bean.Manifestation;
import com.projecttypea.typea.bean.MissionStage;
import com.projecttypea.typea.bean.Soutien;
import com.projecttypea.typea.bean.User;
import com.projecttypea.typea.dao.ManifestationDao;
import com.projecttypea.typea.dao.MissionStageDao;
import com.projecttypea.typea.dao.SoutienDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class SoutienService {
    public Soutien getById(Long aLong) {
        return soutienDao.getById(aLong);
    }

    @Autowired
    SoutienDao soutienDao;

    @Autowired
    MissionStageDao missionStageDao;

    @Autowired
    ManifestationDao manifestationDao;
    @Autowired
    UserService userService;

    public List<Soutien> findAll() {
        return soutienDao.findAll();
    }






    public int addSoutienMission(Long missionId, Soutien soutien, HttpSession session) {
        User currentUser = userService.findByEmail((String) session.getAttribute("session"));
        MissionStage currentMission = missionStageDao.getById(missionId);
        soutien.setMissionstage(currentMission);
        int montant = (int) (soutien.getmAutre() + soutien.getmFraisInscription() + soutien.getmHebergement()
                + soutien.getmTitreTransport());
        soutien.setMontant(montant);
        if (soutien.getIsBenfTypeA().equals("non")) {
            soutien.setDatederniersoutien(null);
            soutien.setMontantderniersoutien(0);
        }

        soutienDao.save(soutien);
        return 1;
    }

    public int addSoutienManifestation(Long manifId, Soutien soutien, HttpSession session) {
        User currentUser = userService.findByEmail((String) session.getAttribute("session"));
        Manifestation curentManifestation = manifestationDao.getById(manifId);
        soutien.setManifestation(curentManifestation);
        int montant = (int) (soutien.getmAutre() + soutien.getmFraisInscription() + soutien.getmHebergement()
                + soutien.getmTitreTransport());
        soutien.setMontant(montant);
        if (soutien.getIsBenfTypeA().equals("non")) {
            soutien.setDatederniersoutien(null);
            soutien.setMontantderniersoutien(0);
        }

        soutienDao.save(soutien);
        return 1;
    }

    public void deleteById(Long id) {
        soutienDao.deleteById(id);
    }

}