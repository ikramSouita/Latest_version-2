package com.projecttypea.typea.service;

import com.projecttypea.typea.bean.Etablissement;
import com.projecttypea.typea.dao.EtablissementDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class EtablissementService {
@Autowired
    private EtablissementDao etablissementDao;
@GetMapping("/admin/getetab/{aLong}")
    public Etablissement getById(Long aLong) {
        return etablissementDao.getById(aLong);
    }

    public Etablissement findByNom(String nom) {
        return etablissementDao.findByNom(nom);
    }

    public int save(String nom , String nomdirecteur,String gender) {
    Etablissement etab = new Etablissement();
    etab.setNom(nom);
    etab.setDirecteur(nomdirecteur);
    etab.setGender(gender);
        etablissementDao.save(etab);
        return 1 ;
    }
}
