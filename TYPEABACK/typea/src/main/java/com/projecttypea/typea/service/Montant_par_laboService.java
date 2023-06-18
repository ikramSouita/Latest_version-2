package com.projecttypea.typea.service;

import com.projecttypea.typea.bean.Montant_par_labo;
import com.projecttypea.typea.dao.Montant_par_laboDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Montant_par_laboService {
    @Autowired
    private Montant_par_laboDAO montant_par_laboDAO;
    @Autowired
    private DoneesProService doneesProService;

    public Montant_par_labo findByLabo(String labo) {
        return montant_par_laboDAO.findByLabo(labo);
    }

    public List<Montant_par_labo> findByYear(int year) {
        return montant_par_laboDAO.findByYear(year);
    }

    public int save(Montant_par_labo entity) {
        Montant_par_labo currentlabo = findByLabo(entity.getLabo());
        if (currentlabo != null) {
            currentlabo.setMontant(entity.getMontant());
            montant_par_laboDAO.save(entity);
            return -1;
        } else {
            montant_par_laboDAO.save(entity);
            return 1;
        }
    }

    public List<Montant_par_labo> findAll() {

        return montant_par_laboDAO.findAll();
    }
}
