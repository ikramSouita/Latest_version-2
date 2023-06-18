package com.projecttypea.typea.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.projecttypea.typea.bean.MailMessages;
import com.projecttypea.typea.bean.Token;
import com.projecttypea.typea.bean.User;
import com.projecttypea.typea.dao.TokenDao;
import com.projecttypea.typea.dao.UserDao;
import com.projecttypea.typea.security.config.PasswordConfig;
import com.projecttypea.typea.security.enums.UserRoles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    @Autowired
    PasswordConfig encoder;

    @Autowired
    JavaMailSender emailSender;

    @Autowired
    TokenDao tokenDao;

    public User findByNom(String name) {
        return userDao.findByNom(name);
    }

    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public int deleteByEmail(String email) {
        return userDao.deleteByEmail(email);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public int addUser(User utilisateur) {
        try {
            if (userDao.findByEmail(utilisateur.getEmail()) != null) {
                return -1;
            } else if (!utilisateur.getEmail().matches("[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@(uca.ma|uca.ac.ma)")) {
                return -2;
            } else {
                utilisateur.setPassword(encoder.passwordEncoder().encode(utilisateur.getPassword()));
                utilisateur.setUserRole(UserRoles.USER);
                utilisateur.setEnable(false);
                Token token = new Token(UUID.randomUUID().toString());
                token.setUser(utilisateur);
                tokenDao.save(token);
                utilisateur.setToken(token);
                userDao.save(utilisateur);
                sendConfirmationMail(utilisateur.getToken());
                return 1;
            }
        } catch (NullPointerException e) {
            return -3;
        }
    }

    public long count() {
        return userDao.count();
    }

    public int loginUser(User user, HttpSession session) {
        try {
            String mail = user.getEmail();
            String pass = user.getPassword();
            User dbUser = findByEmail(mail);
            Boolean isUser = encoder.passwordEncoder().matches(pass, dbUser.getPassword());
            if (mail == "" || pass == "") {
                return -1;
            } else if (dbUser == null || !isUser) {
                return -2;
            } else if (!dbUser.isEnable()) {
                return -4;
            } else {
                session.setMaxInactiveInterval(3600);
                session.setAttribute("session", user.getEmail());
                return 1;
            }
        } catch (NullPointerException e) {
            return -3;
        }
    }

    public int updateUser(Long id, User user) {
        User currentUser = userDao.getById(id);
        if (currentUser == null) {
            return -1;
        } else {
            currentUser.setNom(user.getNom());
            currentUser.setEmail(user.getEmail());
            currentUser.setPrenom(user.getPrenom());
            currentUser.setPassword(user.getPassword());
            currentUser.setTelephone(user.getTelephone());
            userDao.save(currentUser);
            return 1;
        }
    }

    public User getById(Long id) {
        return userDao.getById(id);
    }

    public int sendConfirmationMail(Token token) {
        SimpleMailMessage mssg = new SimpleMailMessage();
        mssg.setFrom("spring.email.from@gmail.com");
        mssg.setTo(token.getUser().getEmail());
        mssg.setText("http://type-a.uca.ma/api/user/registrationConfirm?token=" + token.getTheToken());
        mssg.setSubject("Confirmer votre registration");

        emailSender.send(mssg);
        return 1;
    }

    public boolean isAdmin(String mail) {
        User dbUser = findByEmail(mail);
        if (dbUser.getUserRole() == UserRoles.ADMIN) {
            return true;
        } else {
            return false;
        }
    }

    public int checkUserToken(String token,
            HttpServletResponse httpServletResponse) throws IOException {
        if (token != null) {

            User currentUser = userDao.findByTokenTheToken(token);
            currentUser.setEnable(true);
            userDao.save(currentUser);

            httpServletResponse.sendRedirect("http://type-a.uca.ma/login");
            return 1;
        } else {
            return -1;
        }
    }

    public Long getCurrentUserId(HttpSession session) {
        User cUser = findByEmail((String) session.getAttribute("session"));
        return cUser.getId();
    }

    public List<User> user_rapport() {
        List<User> userrap = new ArrayList<>();
        findAll().forEach(user -> {
            if (!user.getDonne().getFile().isEmpty()) {
                userrap.add(user);
            }

        });
        return userrap;

    }

    public List<User> user_sans_rapport() {
        List<User> userrap = new ArrayList<>();
        findAll().forEach(user -> {
            if (user.getDonne().getFile().isEmpty()) {
                userrap.add(user);
            }

        });
        return userrap;

    }
}
