package com.projecttypea.typea.dao;

import com.projecttypea.typea.bean.Documents;
import com.projecttypea.typea.bean.DoneesPro;
import com.projecttypea.typea.bean.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoneesProDao extends JpaRepository<DoneesPro, Long> {
    DoneesPro findByUserId(Long id);

    DoneesPro findByUser(User user);


}
