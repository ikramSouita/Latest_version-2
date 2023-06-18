package com.projecttypea.typea.bean;

import java.time.LocalDate;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.projecttypea.typea.security.enums.DemandesState;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Manifestation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty(message = "Titre de manifestation peut pas etre vite ")
    private String titreManifestation;

    @NotEmpty(message = "Titre de participation peut pas etre vite ")
    private String titreParticipation;

    @NotEmpty(message = "Pays peut pas etre vite ")
    private String pays;

    @NotEmpty(message = "Ville peut pas etre vite ")
    private String ville;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateDebut;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFin;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateDepart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateRetour;

    /* @NotEmpty(message = "Nature de participation peut pas etre vite ") */
    private String natureParticiaton;

    private DemandesState state;

    private String demandeType;

    @JsonBackReference(value = "manifeStation")
    @ManyToOne
    private User user;

    @JsonManagedReference(value = "newMontantM")
    @OneToOne(mappedBy = "manifestation", cascade = CascadeType.ALL)
    private NouveauMontant nvMnt;

    @JsonManagedReference(value = "soutienM")
    @OneToOne(mappedBy = "manifestation", cascade = CascadeType.ALL)
    private Soutien soutien;

    @JsonManagedReference(value = "Mdocuments")
    @OneToMany(mappedBy = "manifestation", cascade = CascadeType.ALL)
    private List<Documents> documents;

    public long getId() {
        return id;
    }

    public String getTitreParticipation() {
        return titreParticipation;
    }

    public void setTitreParticipation(String titreParticipation) {
        this.titreParticipation = titreParticipation;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitreManifestation() {
        return titreManifestation;
    }

    public void setTitreManifestation(String titreManifestation) {
        this.titreManifestation = titreManifestation;
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

    public String getNatureParticiaton() {
        return natureParticiaton;
    }

    public void setNatureParticiaton(String natureParticiaton) {
        this.natureParticiaton = natureParticiaton;
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

    public String getDemandeType() {
        return demandeType;
    }

    public void setDemandeType(String demandeType) {
        this.demandeType = demandeType;
    }

    public NouveauMontant getNvMnt() {
        return nvMnt;
    }

    public void setNvMnt(NouveauMontant nvMnt) {
        this.nvMnt = nvMnt;
    }

    public Manifestation() {
    }

}
