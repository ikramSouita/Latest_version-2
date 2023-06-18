package com.projecttypea.typea.bean;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lowagie.text.Document;

@Entity
public class DoneesPro {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String niveau;

    private String grade;

    private String ced;
    private String labo;

    public String getLabo() {
        return labo;
    }

    public void setLabo(String labo) {
        this.labo = labo;
    }


    public void setEtablissement(Etablissement etablissement) {
        this.etablissement = etablissement;
    }

    @OneToOne
    private Etablissement etablissement;

    public Etablissement getEtablissement() {
        return etablissement;
    }


    private String entiteRecherche;

    private String respoEntite;

    @OneToOne
    private Manifestation manifestation;

    @OneToOne
    private MissionStage missionStage;

    @JsonBackReference(value = "donne")
    @JoinColumn(name = "user_id")
    @OneToOne
    private User user;

    @JsonManagedReference(value = "Ddocument")
    @OneToMany(mappedBy = "donnepro", cascade = CascadeType.ALL)
    private List<Documents> file;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCed() {
        return ced;
    }

    public void setCed(String ced) {
        this.ced = ced;
    }

    public String getEntiteRecherche() {
        return entiteRecherche;
    }

    public void setEntiteRecherche(String entiteRecherche) {
        this.entiteRecherche = entiteRecherche;
    }

    public String getRespoEntite() {
        return respoEntite;
    }

    public void setRespoEntite(String respoEntite) {
        this.respoEntite = respoEntite;
    }

    public DoneesPro() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Documents> getFile() {
        return file;
    }

    public void setFile(List<Documents> file) {
        this.file = file;
    }

}
