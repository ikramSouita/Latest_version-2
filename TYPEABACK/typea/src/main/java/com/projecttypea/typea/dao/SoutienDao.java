package com.projecttypea.typea.dao;

import com.projecttypea.typea.bean.Soutien;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SoutienDao extends JpaRepository<Soutien, Long> {


}
