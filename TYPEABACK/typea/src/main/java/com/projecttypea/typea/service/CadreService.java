package com.projecttypea.typea.service;

import java.util.List;

import com.projecttypea.typea.bean.Cadre;
import com.projecttypea.typea.bean.MissionStage;
import com.projecttypea.typea.dao.CadreDao;
import com.projecttypea.typea.dao.MissionStageDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadreService {
    @Autowired
    CadreDao cadreDao;

    @Autowired
    MissionStageDao missionStageDao;

    public Cadre findByMissionstage(MissionStage mission) {
        return cadreDao.findByMissionstage(mission);
    }

    public void deleteById(Long id) {
        cadreDao.deleteById(id);
    }

    public List<Cadre> findAll() {
        return cadreDao.findAll();
    }

    public int addCadreMission(Long missionId, Cadre cadre) {
        MissionStage curreMissionStage = missionStageDao.getById(missionId);
        if (curreMissionStage.getCadre() != null) {
            return -1;
        } else {
            cadre.setMissionstage(curreMissionStage);
            cadreDao.save(cadre);
            return 1;
        }
    }

}