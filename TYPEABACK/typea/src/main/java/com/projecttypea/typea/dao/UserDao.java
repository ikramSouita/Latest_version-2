package com.projecttypea.typea.dao;

import com.projecttypea.typea.bean.Token;
import com.projecttypea.typea.bean.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByNom(String nom);

    int deleteByEmail(String email);

    User findByEmailAndPassword(String email, String password);




    User findByTokenTheToken(String theToken);
}
