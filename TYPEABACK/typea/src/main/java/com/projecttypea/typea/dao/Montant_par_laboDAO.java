package com.projecttypea.typea.dao;

import com.projecttypea.typea.bean.Montant_par_labo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Montant_par_laboDAO extends JpaRepository<Montant_par_labo,Long> {
    Montant_par_labo findByLabo(String labo);
    List<Montant_par_labo> findByYear(int year);
}
