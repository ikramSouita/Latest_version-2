package com.projecttypea.typea.dao;

import com.projecttypea.typea.bean.Etablissement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtablissementDao  extends JpaRepository<Etablissement, Long> {
    Etablissement findByNom(String nom);


}
