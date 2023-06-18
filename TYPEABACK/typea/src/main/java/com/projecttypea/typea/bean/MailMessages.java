package com.projecttypea.typea.bean;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.annotation.Persistent;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;

@Entity
public class MailMessages {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String toEmail;
    private String subject;
    private String body;

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public MailMessages() {
    }

}
