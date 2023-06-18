package com.projecttypea.typea.dao;

import java.util.List;

import com.projecttypea.typea.bean.MissionStage;
import com.projecttypea.typea.security.enums.DemandesState;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MissionStageDao extends JpaRepository<MissionStage, Long> {

    List<MissionStage> findAllByUserEmail(String email);

    List<MissionStage> findAllByState(DemandesState state);

}
