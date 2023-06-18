package com.projecttypea.typea.bean;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Messages {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Message peut pas etre vide")
    private String mssgContent;

    @NotEmpty(message = "Message peut pas etre vide")
    private String subject;

    @NotEmpty
    private String email;

    @NotEmpty(message = "Message peut pas etre vide")
    private String telephone;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDate timeSent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMssgContent() {
        return mssgContent;
    }

    public void setMssgContent(String mssgContent) {
        this.mssgContent = mssgContent;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public LocalDate getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(LocalDate timeSent) {
        this.timeSent = timeSent;
    }

    public Messages() {
    }

}
