package com.projecttypea.typea.bean;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.Month;

@Entity
public class NouveauMontant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    private int newmTitre;
    private int newmFraisInscription;
    private int newmHebergement;
    private int newMontant;

    private int newautreMontant;
    private int etat;

private String month;

    public String getMonth() {
        return month;
    }


    @JsonBackReference("nvmontantss")
    @ManyToOne
    private  Budget budget;


    @JsonBackReference("nvmontants")
    @ManyToOne
    public  User user;


    @JsonBackReference(value = "newMontantMS")
    @OneToOne
    private MissionStage missionstage;

    @JsonBackReference(value = "newMontantM")
    @OneToOne
    private Manifestation manifestation;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public  int year ;


    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    public int getNewautreMontant() {
        return newautreMontant;
    }

    public void setNewautreMontant(int newautreMontant) {
        this.newautreMontant = newautreMontant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNewmTitre() {
        return newmTitre;
    }

    public void setNewmTitre(int newmTitre) {
        this.newmTitre = newmTitre;
    }

    public int getNewmFraisInscription() {
        return newmFraisInscription;
    }

    public void setNewmFraisInscription(int newmFraisInscription) {
        this.newmFraisInscription = newmFraisInscription;
    }

    public int getNewmHebergement() {
        return newmHebergement;
    }

    public void setNewmHebergement(int newmHebergement) {
        this.newmHebergement = newmHebergement;
    }

    public int getNewMontant() {
        return newMontant=newmTitre+newmHebergement+newmFraisInscription+newautreMontant;
    }

    public void setNewMontant(int newMontant) {
        this.newMontant = newMontant;
    }

    @JsonBackReference
    public MissionStage getMissionStage() {
        return missionstage;
    }

    @JsonBackReference
    public void setMissionStage(MissionStage mStage) {
        this.missionstage = mStage;
    }

    public Manifestation getManifestation() {
        return manifestation;
    }

    public void setManifestation(Manifestation manif) {
        this.manifestation = manif;
    }

    public NouveauMontant() {
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public MissionStage getMissionstage() {
        return missionstage;
    }

    public void setMissionstage(MissionStage missionstage) {
        this.missionstage = missionstage;
    }
}
