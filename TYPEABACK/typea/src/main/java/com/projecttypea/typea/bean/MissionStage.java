package com.projecttypea.typea.bean;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
//import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
//import javax.persistence.OneToMany;
//import javax.persistence.OneToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.projecttypea.typea.security.enums.DemandesState;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class MissionStage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty(message = "Objetmission peut pas etre vide")
    private String objetMission;

    @NotEmpty(message = "pays peut pas etre vide")
    private String pays;

    @NotEmpty(message = "ville peut pas etre vide")
    private String ville;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateDebut;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFin;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateDepart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateRetour;

    private DemandesState state;

    @JsonBackReference(value = "missionStage")
    @ManyToOne
    private User user;

    @JsonManagedReference(value = "soutienMS")
    @OneToOne(mappedBy = "missionstage", cascade = CascadeType.ALL)
    private Soutien soutien;

    @JsonManagedReference(value = "newMontantMS")
    @OneToOne(mappedBy = "missionstage", cascade = CascadeType.ALL)
    private NouveauMontant nvMnt;

    @JsonManagedReference(value = "cadre")
    @OneToOne(mappedBy = "missionstage", cascade = CascadeType.ALL)
    private Cadre cadre;

    @JsonManagedReference(value = "Sdocuments")
    @OneToMany(mappedBy = "missionstage", cascade = CascadeType.ALL)
    private List<Documents> documents;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getObjetMission() {
        return objetMission;
    }

    public void setObjetMission(String objetMission) {
        this.objetMission = objetMission;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public LocalDate getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(LocalDate dateDepart) {
        this.dateDepart = dateDepart;
    }

    public LocalDate getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(LocalDate dateRetour) {
        this.dateRetour = dateRetour;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Soutien getSoutien() {
        return soutien;
    }

    public void setSoutien(Soutien soutien) {
        this.soutien = soutien;
    }

    public Cadre getCadre() {
        return cadre;
    }

    public void setCadre(Cadre cadre) {
        this.cadre = cadre;
    }

    public List<Documents> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Documents> documents) {
        this.documents = documents;
    }

    public DemandesState getState() {
        return state;
    }

    public void setState(DemandesState state) {
        this.state = state;
    }

    public NouveauMontant getNvMnt() {
        return nvMnt;
    }

    public void setNvMnt(NouveauMontant nvMnt) {
        this.nvMnt = nvMnt;
    }

    public MissionStage() {
    }
}
