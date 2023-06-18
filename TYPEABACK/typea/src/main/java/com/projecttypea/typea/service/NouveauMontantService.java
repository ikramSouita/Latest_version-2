package com.projecttypea.typea.service;

import com.projecttypea.typea.bean.*;
import com.projecttypea.typea.dao.ManifestationDao;
import com.projecttypea.typea.dao.MissionStageDao;
import com.projecttypea.typea.dao.NouveauMontantDao;

import com.projecttypea.typea.security.enums.DemandesState;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NouveauMontantService {

    @Autowired
    NouveauMontantDao nouveauMontantDao;

    @Autowired
    MissionStageDao missionStageDao;
    @Autowired
    UserService userService;
    @Autowired
    BudgetService budgetService;
    @Autowired
    DoneesProService doneesProService;
    @Autowired
    Montant_par_laboService montant_par_laboService;

    public NouveauMontant findByMissionstageId(Long id) {
        return nouveauMontantDao.findByMissionstageId(id);
    }

    public NouveauMontant findByManifestationId(Long id) {
        return nouveauMontantDao.findByManifestationId(id);
    }

    @Autowired
    ManifestationDao manifDao;

    public int addMontantsMS(Long mStageId, NouveauMontant nvMontant) {
        MissionStage currentMS = missionStageDao.getById(mStageId);
        User currentuser = userService.findByEmail(currentMS.getUser().getEmail());
        int newmontant = (int) (nvMontant.getNewautreMontant() + nvMontant.getNewmTitre()
                + nvMontant.getNewmHebergement() + nvMontant.getNewmFraisInscription());
        Budget currentbudget = budgetService.findByDate(LocalDate.now().getYear());
        if (currentMS.getNvMnt() != null) {
            return -1;
        } else {
            if (currentMS.getState() == DemandesState.IDLE) {
                nvMontant.setEtat(0);
            }
            if (currentMS.getState() == DemandesState.APPROVED) {
                nvMontant.setEtat(1);

            }
            if (currentMS.getState() == DemandesState.REFUSED) {
                nvMontant.setEtat(-1);
            }
            nvMontant.setYear(LocalDate.now().getYear());
            nvMontant.setBudget(currentbudget);
            nvMontant.setUser(currentuser);
            nvMontant.setMissionStage(currentMS);
            nvMontant.setNewMontant(newmontant);
            nvMontant.setMonth(String.valueOf(LocalDate.now().getMonth()));
            nouveauMontantDao.save(nvMontant);
            // CORRIGER DES MONTANTS PAR LABORATOIRES
            Montant_par_labo ml = montant_par_laboService.findByLabo(nvMontant.getUser().getDonne().getLabo());
            System.out.println(nvMontant.getUser().getDonne().getLabo());

            if (montant_par_labo(ml.getLabo(), ml.getYear()) == null) {
                System.out.print("null");
                ml.setMontant(0);
            } else {
                System.out.print("not null");
                ml.setMontant(Integer.parseInt(montant_par_labo(ml.getLabo(), ml.getYear())));
            }
            montant_par_laboService.save(ml);

            return 1;
        }
    }

    public int addMontantsM(Long manifId, NouveauMontant nvMontant) {

        Manifestation currentM = manifDao.getById(manifId);
        User currentuser = userService.findByEmail(currentM.getUser().getEmail());
        Budget currentbudget = budgetService.findByDate(LocalDate.now().getYear());
        int newmontant = (int) (nvMontant.getNewautreMontant() + nvMontant.getNewmTitre()
                + nvMontant.getNewmHebergement() + nvMontant.getNewmFraisInscription());
        if (currentM.getNvMnt() != null) {
            return -1;
        } else {
            if (currentM.getState() == DemandesState.IDLE) {
                nvMontant.setEtat(0);
            }
            if (currentM.getState() == DemandesState.APPROVED) {
                nvMontant.setEtat(1);
            }
            if (currentM.getState() == DemandesState.REFUSED) {
                nvMontant.setEtat(-1);
            }
            nvMontant.setYear(LocalDate.now().getYear());
            nvMontant.setBudget(currentbudget);
            nvMontant.setUser(currentuser);
            nvMontant.setManifestation(currentM);
            nvMontant.setNewMontant(newmontant);
            nvMontant.setMonth(String.valueOf(LocalDate.now().getMonth()));
            nouveauMontantDao.save(nvMontant);
            // CORRIGER DES MONTANTS PAR LABORATOIRES
            Montant_par_labo ml = montant_par_laboService.findByLabo(nvMontant.getUser().getDonne().getLabo());

            if (montant_par_labo(ml.getLabo(), ml.getYear()) == null) {
                System.out.print("null");
                ml.setMontant(0);
            } else {
                System.out.print("not null");
                ml.setMontant(Integer.parseInt(montant_par_labo(ml.getLabo(), ml.getYear())));
            }

            montant_par_laboService.save(ml);

            return 1;
        }
    }

    public List<String> grafbar(String e1, String e2, String e3, String e4, String e5, String e6, String e7, String e8,
            String e9, String e10, String e11, String e12, String e13, String e14, String e15, int date) {
        List<String> NUMBERS = new ArrayList<>();

        NUMBERS.add(nouveauMontantDao.montant_par_etab(e1, date));

        NUMBERS.add(nouveauMontantDao.montant_par_etab(e2, date));

        NUMBERS.add(nouveauMontantDao.montant_par_etab(e3, date));

        NUMBERS.add(nouveauMontantDao.montant_par_etab(e4, date));

        NUMBERS.add(nouveauMontantDao.montant_par_etab(e5, date));

        NUMBERS.add(nouveauMontantDao.montant_par_etab(e6, date));

        NUMBERS.add(nouveauMontantDao.montant_par_etab(e7, date));

        NUMBERS.add(nouveauMontantDao.montant_par_etab(e8, date));
        NUMBERS.add(nouveauMontantDao.montant_par_etab(e9, date));
        NUMBERS.add(nouveauMontantDao.montant_par_etab(e10, date));
        NUMBERS.add(nouveauMontantDao.montant_par_etab(e11, date));
        NUMBERS.add(nouveauMontantDao.montant_par_etab(e12, date));
        NUMBERS.add(nouveauMontantDao.montant_par_etab(e13, date));
        NUMBERS.add(nouveauMontantDao.montant_par_etab(e14, date));
        NUMBERS.add(nouveauMontantDao.montant_par_etab(e15, date));

        return NUMBERS;
    }

    public List<String> graph_monsuel(String e1, String e2, String e3, String e4, String e5, String e6, String e7,
            String e8, String e9, String e10, String e11, String e12, int date) {
        List<String> montant_mois = new ArrayList<>();
        montant_mois.add(nouveauMontantDao.montant_par_mois(e1, date));
        montant_mois.add(nouveauMontantDao.montant_par_mois(e2, date));
        montant_mois.add(nouveauMontantDao.montant_par_mois(e3, date));
        montant_mois.add(nouveauMontantDao.montant_par_mois(e4, date));
        montant_mois.add(nouveauMontantDao.montant_par_mois(e5, date));
        montant_mois.add(nouveauMontantDao.montant_par_mois(e6, date));
        montant_mois.add(nouveauMontantDao.montant_par_mois(e7, date));
        montant_mois.add(nouveauMontantDao.montant_par_mois(e8, date));
        montant_mois.add(nouveauMontantDao.montant_par_mois(e9, date));
        montant_mois.add(nouveauMontantDao.montant_par_mois(e10, date));
        montant_mois.add(nouveauMontantDao.montant_par_mois(e11, date));
        montant_mois.add(nouveauMontantDao.montant_par_mois(e12, date));
        return montant_mois;

    }

    public String montant_par_labo(String labo, int date) {
        return nouveauMontantDao.montant_par_labo(labo, date);
    }
}
