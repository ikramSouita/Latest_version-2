package com.projecttypea.typea.bean;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;
import java.util.List;

@Entity
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int date;
    private double montant;

    public List<NouveauMontant> getNvmontantss() {
        return nvmontantss;
    }

    public void setNvmontantss(List<NouveauMontant> nvmontantss) {
        this.nvmontantss = nvmontantss;
    }

    @JsonManagedReference(value = "nvmontantss")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<NouveauMontant> nvmontantss;
    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public double getmontant() {
        return montant;
    }

    public void setmontant(double montant) {
        this.montant = montant;
    }

    public Budget() {
    }

}
