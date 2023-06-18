package com.projecttypea.typea.service;

import java.util.List;

import com.projecttypea.typea.bean.Manifestation;
import com.projecttypea.typea.bean.MissionStage;
import com.projecttypea.typea.classes.Demande;
import com.projecttypea.typea.dao.ManifestationDao;
import com.projecttypea.typea.dao.MissionStageDao;
import com.projecttypea.typea.security.enums.DemandesState;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class DemandeService {
    @Autowired
    ManifestationDao manifestationDao;

    @Autowired
    MissionStageDao missionStageDao;

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleMail(String toEmail, String body, String subject) {
        SimpleMailMessage mssg = new SimpleMailMessage();

        mssg.setFrom("spring.email.from@gmail.com");
        mssg.setTo(toEmail);
        mssg.setText(body);
        mssg.setSubject(subject);

        mailSender.send(mssg);
    }

    public Demande findAll() {
        List<Manifestation> listManif = manifestationDao.findAll();
        List<MissionStage> listMStage = missionStageDao.findAll();
        return new Demande(listManif, listMStage);
    }

}
