package com.projecttypea.typea.dao;

import com.projecttypea.typea.bean.MailMessages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailMessageDao extends JpaRepository<MailMessages, Long> {

}
