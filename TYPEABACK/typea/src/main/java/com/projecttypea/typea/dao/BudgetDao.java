package com.projecttypea.typea.dao;

import com.projecttypea.typea.bean.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BudgetDao extends JpaRepository<Budget,Long> {
    Budget findByDate(int date);

    @Query(value = "SELECT SUM(nv.newMontant) FROM NouveauMontant  nv , Budget  b WHERE  nv.budget.id = b.id AND b.date= :date AND  nv.etat=1")
    Long budget_consommer(@Param("date") int  date);

}
