package com.projecttypea.typea.dao;

import com.projecttypea.typea.bean.NouveauMontant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface NouveauMontantDao extends JpaRepository<NouveauMontant, Long> {

    NouveauMontant findByManifestationId(Long id);
    NouveauMontant findByMissionstageId(Long id);
    @Query(value = "SELECT SUM(nv.newMontant) FROM NouveauMontant  nv,Etablissement e,DoneesPro d, User  u WHERE  nv.user.id = u.id AND d.user.id = u.id AND d.etablissement.id = e.id  AND e.nom like :etab AND nv.etat=1  AND  nv.year = :date")
    String montant_par_etab(@Param("etab") String etab ,@Param("date") int date);

    @Query(value = "SELECT SUM(nv.newMontant) FROM NouveauMontant  nv, User  u WHERE  nv.user.id = u.id  AND nv.month like :month AND nv.etat=1 AND nv.year = :date ")
    String montant_par_mois(@Param("month") String month ,@Param("date") int date );

    @Query(value = "SELECT SUM(nv.newMontant) FROM NouveauMontant  nv,DoneesPro d, User  u WHERE  nv.user.id = u.id AND d.user.id = u.id  AND d.labo like  :labo AND nv.etat=1 AND  nv.year = :date")
   String montant_par_labo(@Param("labo") String labo ,@Param("date") int date);



}

