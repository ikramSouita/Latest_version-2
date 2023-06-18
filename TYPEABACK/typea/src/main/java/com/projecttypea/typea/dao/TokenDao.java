package com.projecttypea.typea.dao;

import com.projecttypea.typea.bean.Token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenDao extends JpaRepository<Token, Long> {

}
