package com.projecttypea.typea.dao;

import com.projecttypea.typea.bean.Messages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagesDao extends JpaRepository<Messages, Long> {

}