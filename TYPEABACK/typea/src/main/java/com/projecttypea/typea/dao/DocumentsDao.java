package com.projecttypea.typea.dao;

import java.util.List;

import com.projecttypea.typea.bean.Documents;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentsDao extends JpaRepository<Documents, Long> {

    Documents findByName(String name);

    List<Documents> findAllBydonneproId(Long donneId);
}
