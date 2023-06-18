package com.projecttypea.typea.service;

import java.io.IOException;
import java.util.HashMap;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import com.projecttypea.typea.bean.Documents;
import com.projecttypea.typea.bean.DoneesPro;
import com.projecttypea.typea.bean.Montant_par_labo;
import com.projecttypea.typea.bean.User;
import com.projecttypea.typea.dao.DocumentsDao;
import com.projecttypea.typea.dao.DoneesProDao;
import com.projecttypea.typea.dao.EtablissementDao;
import com.projecttypea.typea.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DoneesProService {

    @Autowired
    UserDao userDao;
    @Autowired
    UserService userService;

    @Autowired
    DoneesProDao doneesProDao;
    @Autowired
    EtablissementService etablissementService;
    @Autowired
    private EtablissementDao etablissementDao;
    @Autowired
    private NouveauMontantService nouveauMontantService;
    @Autowired
    private Montant_par_laboService montant_par_laboService;

    @Autowired
    private DocumentsDao documentsDao;

    public List<DoneesPro> findAll() {
        return doneesProDao.findAll();
    }

    public DoneesPro findByUser(User user) {
        return doneesProDao.findByUser(user);
    }

    public DoneesPro findByUserId(Long id) {
        return doneesProDao.findByUserId(id);
    }

    public int addDonesPro(DoneesPro donne, HttpSession session) {
        User currentUser = userDao.findByEmail((String) session.getAttribute("session"));
        if (currentUser.getDonne() != null) {
            return 1;
        } else if (currentUser.getDonne() == null) {
            currentUser.setDonne(donne);
            donne.setUser(currentUser);
            donne.setEtablissement(etablissementService.findByNom(donne.getEtablissement().getNom()));
            doneesProDao.save(donne);
            findlabos();
            return -1;
        } else {
            return -3;
        }
    }

    public int addDonneProFile(Long donneId, MultipartFile file) throws IOException {
        if (file != null) {
            String documentName = file.getOriginalFilename();
            Documents documents = new Documents(documentName, file.getContentType(), file.getBytes());
            DoneesPro doneesPro = doneesProDao.getById(donneId);
            documents.setDonnepro(doneesPro);
            documents.setFileName(documentName);
            documents.setName(UUID.randomUUID().toString());
            documentsDao.save(documents);

        }
        return 1;
    }

    public int savee(DoneesPro doneesPro) {
        doneesProDao.save(doneesPro);
        return 1;
    }

    public int save(DoneesPro donne, HttpSession session) {
        User currentUser = userDao.findByEmail((String) session.getAttribute("session"));
        DoneesPro savedDonne = findByUser(currentUser);
        if (donne.getCed() != null) {
            savedDonne.setCed(donne.getCed());
        }
        if (donne.getEntiteRecherche() != null) {
            savedDonne.setEntiteRecherche(donne.getEntiteRecherche());
        }
        if (donne.getGrade() != null) {
            savedDonne.setGrade(donne.getGrade());
        }
        if (donne.getNiveau() != null) {
            savedDonne.setNiveau(donne.getNiveau());
        }
        if (donne.getRespoEntite() != null) {
            savedDonne.setRespoEntite(donne.getRespoEntite());
        }
        if (donne.getLabo() != null) {
            savedDonne.setLabo(donne.getLabo());
        }
        if (donne.getFile() != null) {
            savedDonne.setFile(donne.getFile());
        }
        doneesProDao.save(savedDonne);
        donne.setUser(currentUser);
        return 1;
    }

    /*
     * public int updateDonesPro(Long id, DonéesPro donnePro) {
     * DonéesPro currentDonnes = donéesProDao.getById(id);
     * if (currentDonnes == null) {
     * return -1;
     * } else {
     * currentDonnes.setCed(donnePro.getCed());
     * currentDonnes.setEntiteRecherche(donnePro.getEntiteRecherche());
     * currentDonnes.setEtablissement(donnePro.getEtablissement());
     * currentDonnes.setGrade(donnePro.getGrade());
     * currentDonnes.setNiveau(donnePro.getNiveau());
     * currentDonnes.setRespoEntite(donnePro.getRespoEntite());
     * donéesProDao.save(currentDonnes);
     * return 1;
     * }
     * }
     */
    public void deleteById(Long id) {

        doneesProDao.deleteById(id);
    }

    public void findlabos() {
        Montant_par_labo element = new Montant_par_labo();
        findAll().forEach(donne -> {
            element.setLabo(donne.getLabo());
            element.setYear(LocalDate.now().getYear());
            if (nouveauMontantService.montant_par_labo(donne.getLabo(), LocalDate.now().getYear()) == null) {
                element.setMontant(0);
                montant_par_laboService.save(element);
            } else {
                element.setMontant(Integer
                        .parseInt(nouveauMontantService.montant_par_labo(donne.getLabo(), LocalDate.now().getYear())));
                montant_par_laboService.save(element);
            }

        });
    }

    public List<Documents> findAllDocumentsById(Long donneId) {
        return documentsDao.findAllBydonneproId(donneId);

    }

    public Map<String, String> findLastDoneeDoc(Long donneId) {
        List<Documents> alldocs = findAllDocumentsById(donneId);
        Documents lastDoc = alldocs.get(alldocs.size() - 1);
        HashMap<String, String> map = new HashMap<>();
        map.put("fichier", "http://type-a.uca.ma/api/admin/retrievedoc/" + lastDoc.getName());
        return map;
    }
}