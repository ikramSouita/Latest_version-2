package com.projecttypea.typea.ws;

import com.projecttypea.typea.service.formulaire;

import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api")

public class FORMULAIREWS {

        @GetMapping("/user/raport/{id}")
        public ResponseEntity<byte[]> generateReport(@PathVariable long id) throws FileNotFoundException, JRException {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "text/plain");
                byte[] path = formulairee.exportReport(id);
                return ResponseEntity
                                .status(HttpStatus.OK)
                                .headers(headers)
                                .body(path);
        }

        @GetMapping("/admin/raportlettremission/{id}")
        public ResponseEntity<byte[]> exportLettremission(@PathVariable long id)
                        throws FileNotFoundException, JRException {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "text/plain");
                byte[] path = formulairee.exportLettremission(id);
                return ResponseEntity
                                .status(HttpStatus.OK)
                                .headers(headers)
                                .body(path);
        }

        @GetMapping("/user/raportmission/{id}")
        public ResponseEntity<byte[]> exportReportMission(@PathVariable long id)
                        throws FileNotFoundException, JRException {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "text/plain");
                byte[] path = formulairee.exportReportMission(id);
                return ResponseEntity
                                .status(HttpStatus.OK)
                                .headers(headers)
                                .body(path);
        }

        @GetMapping("/admin/raportlettremanif/{id}")
        public ResponseEntity<byte[]> exportLettremanif(@PathVariable long id)
                        throws FileNotFoundException, JRException {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "text/plain");
                byte[] path = formulairee.exportLettremanif(id);
                return ResponseEntity
                                .status(HttpStatus.OK)
                                .headers(headers)
                                .body(path);
        }

        @GetMapping("/admin/raportNVmontantmanif/{id}")
        public ResponseEntity<byte[]> exportNvmontantmanif(@PathVariable long id)
                        throws FileNotFoundException, JRException {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "text/plain");
                byte[] path = formulairee.exportNvmontantmanif(id);
                return ResponseEntity
                                .status(HttpStatus.OK)
                                .headers(headers)
                                .body(path);
        }

        @GetMapping("/admin/raportNvmontantmis/{id}")
        public ResponseEntity<byte[]> exportNvmontantmission(@PathVariable long id)
                        throws FileNotFoundException, JRException {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "text/plain");
                byte[] path = formulairee.exportNvmontantmission(id);
                return ResponseEntity
                                .status(HttpStatus.OK)
                                .headers(headers)
                                .body(path);
        }

        @GetMapping("/admin/users_rapport")
        public ResponseEntity<byte[]> users_rapports()
                        throws FileNotFoundException, JRException {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "text/plain");
                byte[] path = formulairee.users_rapports();
                return ResponseEntity
                                .status(HttpStatus.OK)
                                .headers(headers)
                                .body(path);
        }

        @GetMapping("/admin/users_sans_rapport")
        public ResponseEntity<byte[]> users_sans_rapports()
                        throws FileNotFoundException, JRException {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "text/plain");
                byte[] path = formulairee.users_sans_rapports();
                return ResponseEntity
                                .status(HttpStatus.OK)
                                .headers(headers)
                                .body(path);
        }

        @GetMapping("/admin/liste_users")
        public ResponseEntity<byte[]> liste_users()
                        throws FileNotFoundException, JRException {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "text/plain");
                byte[] path = formulairee.liste_users();
                return ResponseEntity
                                .status(HttpStatus.OK)
                                .headers(headers)
                                .body(path);
        }

        @Autowired
        private formulaire formulairee;

}
