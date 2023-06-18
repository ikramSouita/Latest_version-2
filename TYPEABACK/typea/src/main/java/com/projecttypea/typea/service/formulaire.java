package com.projecttypea.typea.service;

import com.projecttypea.typea.bean.*;
import com.projecttypea.typea.dao.ManifestationDao;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class formulaire {

    public byte[] exportReport(long id) throws FileNotFoundException, JRException {

        Manifestation manifestation = getById(id);
        User user = userService.getById(manifestation.getUser().getId());
        DoneesPro doneesPro = doneesProService.findByUser(user);

        Soutien soutien = soutienService.getById(manifestation.getSoutien().getId());
        JasperReport jasperReport = JasperCompileManager
                .compileReport(getClass().getResourceAsStream("/formulaire.jrxml"));
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
                Collections.singleton(manifestation));
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("nom", user.getNom());
        parameters.put("prenom", user.getPrenom());
        parameters.put("telephone", user.getTelephone());
        parameters.put("email", user.getEmail());

        parameters.put("id", manifestation.getId());
        parameters.put("titremanifestation", manifestation.getTitreManifestation());
        parameters.put("titreparticipation", manifestation.getTitreParticipation());
        parameters.put("pays", manifestation.getPays());
        parameters.put("datedebut", manifestation.getDateDebut());
        parameters.put("datefin", manifestation.getDateFin());
        parameters.put("datedepert", manifestation.getDateDepart());
        parameters.put("dateretour", manifestation.getDateRetour());
        parameters.put("ville", manifestation.getVille());

        parameters.put("niveau", doneesPro.getNiveau());
        parameters.put("grade", doneesPro.getGrade());
        parameters.put("ced", doneesPro.getCed());
        parameters.put("etablissement", doneesPro.getEtablissement().getNom());
        parameters.put("entitérecherche", doneesPro.getEntiteRecherche());
        parameters.put("respoentité", doneesPro.getRespoEntite());

        parameters.put("nature", soutien.getNature());
        parameters.put("mtitretransport", soutien.getmTitreTransport());
        parameters.put("mhebergement", soutien.getmHebergement());
        parameters.put("mtotal", soutien.getMontant());
        parameters.put("mfraisinscription", soutien.getmFraisInscription());
        parameters.put("montant autre", soutien.getmAutre());
        parameters.put("isbénf", soutien.getIsBenfTypeA());
        parameters.put("devise", soutien.getDevise());

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        return JasperExportManager.exportReportToPdf(jasperPrint);

    }

    public byte[] exportNvmontantmanif(long id) throws FileNotFoundException, JRException {

        Manifestation manifestation = getById(id);
        User user = userService.getById(manifestation.getUser().getId());
        DoneesPro doneesPro = doneesProService.findByUser(user);
        NouveauMontant nouveauMontant = nouveauMontantService.findByManifestationId(id);

        Soutien soutien = soutienService.getById(manifestation.getSoutien().getId());
        JasperReport jasperReport = JasperCompileManager
                .compileReport(getClass().getResourceAsStream("/Nvmontantmanif.jrxml"));
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
                Collections.singleton(manifestation));
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("nom", user.getNom());
        parameters.put("prenom", user.getPrenom());
        parameters.put("telephone", user.getTelephone());
        parameters.put("email", user.getEmail());

        parameters.put("id", manifestation.getId());
        parameters.put("titremanifestation", manifestation.getTitreManifestation());
        parameters.put("titreparticipation", manifestation.getTitreParticipation());
        parameters.put("pays", manifestation.getPays());
        parameters.put("datedebut", manifestation.getDateDebut());
        parameters.put("datefin", manifestation.getDateFin());
        parameters.put("datedepert", manifestation.getDateDepart());
        parameters.put("dateretour", manifestation.getDateRetour());
        parameters.put("ville", manifestation.getVille());

        parameters.put("niveau", doneesPro.getNiveau());
        parameters.put("grade", doneesPro.getGrade());
        parameters.put("ced", doneesPro.getCed());
        parameters.put("etablissement", doneesPro.getEtablissement().getNom());
        parameters.put("entitérecherche", doneesPro.getEntiteRecherche());
        parameters.put("respoentité", doneesPro.getRespoEntite());

        parameters.put("nature", soutien.getNature());
        parameters.put("mtitretransport", soutien.getmTitreTransport());
        parameters.put("mhebergement", soutien.getmHebergement());
        parameters.put("mtotal", soutien.getMontant());
        parameters.put("mfraisinscription", soutien.getmFraisInscription());
        parameters.put("montant autre", soutien.getmAutre());
        parameters.put("isbénf", soutien.getIsBenfTypeA());
        parameters.put("devise", soutien.getDevise());

        parameters.put("mtitretransported", nouveauMontant.getNewmTitre());
        parameters.put("mhebergemented", nouveauMontant.getNewmHebergement());
        parameters.put("mtotaled", nouveauMontant.getNewMontant());
        parameters.put("montant autreed", nouveauMontant.getNewautreMontant());

        parameters.put("mfraisinscriptioned", nouveauMontant.getNewmFraisInscription());

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        return JasperExportManager.exportReportToPdf(jasperPrint);

    }

    public byte[] exportNvmontantmission(long id) throws FileNotFoundException, JRException {

        MissionStage mission = missionStageService.getById(id);
        User user = userService.getById(mission.getUser().getId());
        DoneesPro doneesPro = doneesProService.findByUser(user);

        NouveauMontant nouveauMontant = nouveauMontantService.findByMissionstageId(id);
        Soutien soutien = soutienService.getById(mission.getSoutien().getId());
        JasperReport jasperReport = JasperCompileManager
                .compileReport(getClass().getResourceAsStream("/Nvmontantmis.jrxml"));
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singleton(mission));
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("nom", user.getNom());
        parameters.put("prenom", user.getPrenom());
        parameters.put("telephone", user.getTelephone());
        parameters.put("email", user.getEmail());

        parameters.put("id", mission.getId());

        parameters.put("titreparticipation", mission.getObjetMission());
        parameters.put("pays", mission.getPays());
        parameters.put("datedebut", mission.getDateDebut());
        parameters.put("datefin", mission.getDateFin());
        parameters.put("datedepert", mission.getDateDepart());
        parameters.put("dateretour", mission.getDateRetour());
        parameters.put("ville", mission.getVille());

        parameters.put("niveau", doneesPro.getNiveau());
        parameters.put("grade", doneesPro.getGrade());
        parameters.put("ced", doneesPro.getCed());
        parameters.put("etablissement", doneesPro.getEtablissement().getNom());
        parameters.put("entitérecherche", doneesPro.getEntiteRecherche());
        parameters.put("respoentité", doneesPro.getRespoEntite());

        parameters.put("nature", soutien.getNature());
        parameters.put("mtitretransport", soutien.getmTitreTransport());
        parameters.put("mhebergement", soutien.getmHebergement());
        parameters.put("mtotal", soutien.getMontant());
        parameters.put("mfraisinscription", soutien.getmFraisInscription());
        parameters.put("montant autre", soutien.getmAutre());
        parameters.put("isbénf", soutien.getIsBenfTypeA());
        parameters.put("devise", soutien.getDevise());

        parameters.put("mtitretransported", nouveauMontant.getNewmTitre());
        parameters.put("mhebeed", nouveauMontant.getNewmHebergement());
        parameters.put("mtotaled", nouveauMontant.getNewMontant());
        parameters.put("mfraised", nouveauMontant.getNewmFraisInscription());
        parameters.put("mautreed", nouveauMontant.getNewautreMontant());
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        return JasperExportManager.exportReportToPdf(jasperPrint);

    }

    public byte[] exportReportMission(long id) throws FileNotFoundException, JRException {

        MissionStage mission = missionStageService.getById(id);
        User user = userService.getById(mission.getUser().getId());
        DoneesPro doneesPro = doneesProService.findByUser(user);
        Cadre cadre = cadreService.findByMissionstage(mission);

        Soutien soutien = soutienService.getById(mission.getSoutien().getId());
        JasperReport jasperReport = JasperCompileManager
                .compileReport(getClass().getResourceAsStream("/formmission.jrxml"));
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singleton(mission));
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("nom", user.getNom());
        parameters.put("prenom", user.getPrenom());
        parameters.put("telephone", user.getTelephone());
        parameters.put("email", user.getEmail());

        parameters.put("id", mission.getId());

        parameters.put("titreparticipation", mission.getObjetMission());
        parameters.put("pays", mission.getPays());
        parameters.put("datedebut", mission.getDateDebut());
        parameters.put("datefin", mission.getDateFin());
        parameters.put("datedepert", mission.getDateDepart());
        parameters.put("dateretour", mission.getDateRetour());
        parameters.put("ville", mission.getVille());

        parameters.put("niveau", doneesPro.getNiveau());
        parameters.put("grade", doneesPro.getGrade());
        parameters.put("ced", doneesPro.getCed());
        parameters.put("etablissement", doneesPro.getEtablissement().getNom());
        parameters.put("entitérecherche", doneesPro.getEntiteRecherche());
        parameters.put("respoentité", doneesPro.getRespoEntite());

        parameters.put("nature", soutien.getNature());
        parameters.put("mtitretransport", soutien.getmTitreTransport());
        parameters.put("mhebergement", soutien.getmHebergement());
        parameters.put("mtotal", soutien.getMontant());
        parameters.put("mfraisinscription", soutien.getmFraisInscription());
        parameters.put("montant autre", soutien.getmAutre());
        parameters.put("isbénf", soutien.getIsBenfTypeA());
        parameters.put("devise", soutien.getDevise());

        parameters.put("1", cadre.getTitreCadre());
        parameters.put("3", cadre.getRespoMarDuProjet());
        parameters.put("4", cadre.getPartenaireEtranger());
        parameters.put("2", cadre.getTitreProjet());

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        return JasperExportManager.exportReportToPdf(jasperPrint);

    }

    public byte[] exportLettremanif(long id) throws FileNotFoundException, JRException {
        Manifestation manifestation = manifestationDao.getById(id);
        NouveauMontant nouveauMontant = nouveauMontantService.findByManifestationId(id);
        User user = userService.getById(manifestation.getUser().getId());
        Etablissement etab = etablissementService.findByNom(user.getDonne().getEtablissement().getNom());
        JasperReport jasperReport = JasperCompileManager
                .compileReport(getClass().getResourceAsStream("/Lettremanif.jrxml"));
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singleton(manifestation));
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("titremanifestation", manifestation.getTitreManifestation());
        parameters.put("ville", manifestation.getVille());
        parameters.put("datedebut", manifestation.getDateDebut());
        parameters.put("datefin", manifestation.getDateFin());
        parameters.put("montant", nouveauMontant.getNewMontant());
        if (user.getGender().equals("male")) {
            parameters.put("gender", "Monsieur");

        } else {
            parameters.put("gender", "Madame");
        }
        parameters.put("nom", user.getNom());
        parameters.put("penom", user.getPrenom());

        if (etab.getGender().equals("male")) {
            parameters.put("genderdir", "Monsieur");
            parameters.put("la/le", "le");
            parameters.put("directeur", "Directeur");

        } else {
            parameters.put("genderdir", "Madame");
            parameters.put("la/le", "la");
            parameters.put("directeur", "Directrice");

        }

        parameters.put("etab", etab.getNom());

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        return JasperExportManager.exportReportToPdf(jasperPrint);

    }

    public byte[] exportLettremission(long id) throws FileNotFoundException, JRException {
        MissionStage mission = missionStageService.getById(id);
        NouveauMontant nouveauMontant = nouveauMontantService.findByMissionstageId(id);
        User user = userService.getById(mission.getUser().getId());
        Etablissement etab = etablissementService.findByNom(user.getDonne().getEtablissement().getNom());
        JasperReport jasperReport = JasperCompileManager
                .compileReport(getClass().getResourceAsStream("/Lettremission.jrxml"));
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singleton(mission));
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("titremanifestation", mission.getObjetMission());
        parameters.put("ville", mission.getVille());
        parameters.put("datedebut", mission.getDateDebut());
        parameters.put("datefin", mission.getDateFin());
        parameters.put("montant", nouveauMontant.getNewMontant());
        if (user.getGender().equals("male")) {
            parameters.put("gender", "Monsieur");

        } else {
            parameters.put("gender", "Madame");
        }
        parameters.put("nom", user.getNom());
        parameters.put("penom", user.getPrenom());
        if (etab.getGender().equals("male")) {
            parameters.put("genderdir", "Monsieur");
            parameters.put("la/le", "le");
            parameters.put("directeur", "Directeur");

        } else {
            parameters.put("genderdir", "Madame");
            parameters.put("la/le", "la");
            parameters.put("directeur", "Directrice");

        }

        parameters.put("etab", etab.getNom());

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        return JasperExportManager.exportReportToPdf(jasperPrint);

    }

    public byte[] users_rapports() throws FileNotFoundException, JRException {
        List<User> usersList = userService.user_rapport();
        JasperReport jasperReport = JasperCompileManager
                .compileReport(getClass().getResourceAsStream("/users_sans_rapports.jrxml"));
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(usersList);
        Map<String, Object> parameters = new HashMap<>();
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    public byte[] users_sans_rapports() throws FileNotFoundException, JRException {
        List<User> usersList = userService.user_sans_rapport();
        JasperReport jasperReport = JasperCompileManager
                .compileReport(getClass().getResourceAsStream("/users_sans_rapports.jrxml"));
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(usersList);
        Map<String, Object> parameters = new HashMap<>();
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    public byte[] liste_users() throws FileNotFoundException, JRException {
        List<User> usersList = userService.findAll();
        JasperReport jasperReport = JasperCompileManager
                .compileReport(getClass().getResourceAsStream("/liste_users.jrxml"));
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(usersList);
        Map<String, Object> parameters = new HashMap<>();
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    public Manifestation getById(Long aLong) {
        return manifestationDao.getById(aLong);
    }

    @Autowired
    private ManifestationDao manifestationDao;
    @Autowired
    private DoneesProService doneesProService;
    @Autowired
    private UserService userService;
    @Autowired
    private SoutienService soutienService;
    @Autowired
    private MissionStageService missionStageService;
    @Autowired
    private CadreService cadreService;
    @Autowired
    private NouveauMontantService nouveauMontantService;
    @Autowired
    private EtablissementService etablissementService;
}
