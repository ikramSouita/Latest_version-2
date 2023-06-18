package com.projecttypea.typea.ws;

import java.io.IOException;
import java.util.List;

import com.projecttypea.typea.bean.MissionStage;
import com.projecttypea.typea.bean.Token;

//import javax.transaction.Transactional;

import com.projecttypea.typea.bean.User;
import com.projecttypea.typea.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")

public class UserWS {

    @Autowired
    UserService userService;

    @GetMapping("/admin/username/{name}")
    public User findByNom(String name) {
        return userService.findByNom(name);
    }

    @PostMapping("/allusers/login")
    public int loginUser(@RequestBody User user, HttpSession session) {
        return userService.loginUser(user, session);
    }

    @GetMapping("/admin/countusers")
    public long count() {
        return userService.count();
    }

    @GetMapping("/admin/usersrap")
    public List<User> user_rapport() {
        return userService.user_rapport();
    }

    @GetMapping("/admin/users_sans_rap")
    public List<User> user_sans_rapport() {
        return userService.user_sans_rapport();
    }

    @GetMapping("/allusers/logout")
    public int logoutUser(HttpSession session) {
        if (session.getAttribute("session") != null) {
            session.removeAttribute("session");
            return 1;
        } else {
            return -1;
        }
    }

    @PutMapping("/user/updateuser/{id}")
    public int updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @GetMapping("/user/email/{email}")
    public User findByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    @GetMapping("/admin/Users")
    public List<User> findAll() {
        return userService.findAll();
    }

    @PostMapping("/allusers/register")
    public int addUser(@RequestBody User utilisateur) {
        return userService.addUser(utilisateur);
    }

    @GetMapping("/admin/getuserbyid/{id}")
    public User getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @GetMapping("/allusers/isadmin/{mail}")
    public boolean isAdmin(@PathVariable String mail) {
        return userService.isAdmin(mail);
    }

    @PostMapping("/user/sendconfmail")
    public int sendConfirmationMail(@RequestBody Token token) {
        return userService.sendConfirmationMail(token);
    }

    @GetMapping("/user/registrationConfirm")
    public int checkUserToken(@RequestParam("token") String token,
            HttpServletResponse httpServletResponse) throws IOException {
        return userService.checkUserToken(token, httpServletResponse);
    }

    @GetMapping("/user/getthisuserid")
    public Long getCurrentUserId(HttpSession session) {
        return userService.getCurrentUserId(session);
    }

}
