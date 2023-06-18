package com.projecttypea.typea.bean;

import java.util.List;

import javax.persistence.CascadeType;
//import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
//import javax.persistence.OneToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.projecttypea.typea.security.enums.UserRoles;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty(message = "Nom peut pas etre vide")
    private String nom;

    @NotEmpty(message = "Prenom peut pas etre vide")
    private String prenom;

    @NotEmpty(message = "Email peut pas etre vide")
    private String email;

    @NotEmpty(message = "Mot de passe peut pas etre vide")
    @Size(min = 8, message = "Mot de passe doit contenir au moin 8 caracteres")
    private String password;

    @NotEmpty(message = "Telephone peut pas etre vide")
    private String telephone;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @NotEmpty(message = "gender peut pas etre vide")
    private String gender;

    private UserRoles userRole;

    private boolean isEnable;

    @JsonManagedReference(value = "donne")
    @OneToOne(mappedBy = "user")
    private DoneesPro donne;

    @JsonManagedReference(value = "manifeStation")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Manifestation> manifEstation;

    @JsonManagedReference(value = "nvmontants")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<NouveauMontant> nvmontants;

    @JsonManagedReference(value = "missionStage")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<MissionStage> missionStage;

    @JsonManagedReference(value = "token")
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Token token;

    public DoneesPro getDonne() {
        return donne;
    }

    public void setDonne(DoneesPro donne) {
        donne = this.donne;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Manifestation> getManifEstation() {
        return manifEstation;
    }

    public void setManifEstation(List<Manifestation> manifEstation) {
        this.manifEstation = manifEstation;
    }

    public List<MissionStage> getMissionStage() {
        return missionStage;
    }

    public void setMissionStage(List<MissionStage> missionStage) {
        this.missionStage = missionStage;
    }

    public UserRoles getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRoles userRole) {
        this.userRole = userRole;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public User() {
    }

}
