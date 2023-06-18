package com.projecttypea.typea.service;

import com.projecttypea.typea.bean.Budget;
import com.projecttypea.typea.dao.BudgetDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class BudgetService {
    public Long budget_consommer(int date) {
        return budgetDao.budget_consommer(date);
    }

    public Budget findByDate(int date) {
        return budgetDao.findByDate(date);

    }
    public  double budget_annuelle(int date){
       double montant =budgetDao.findByDate(date).getmontant();
        return  montant;
    }

    public List<Budget> findAll() {
        return budgetDao.findAll();
    }

    public long count() {
        return budgetDao.count();
    }

    public int save(Budget budget) {
        Budget currbudget = findByDate(LocalDate.now().getYear());
        if (currbudget == null) {
            if (budget.getDate() != LocalDate.now().getYear()) {
                return -1;
            } else {
                budgetDao.save(budget);
                return 1;
            }
        }else {

            currbudget.setmontant(budget.getmontant());
            budgetDao.save(currbudget);

            return -2;
        }
    }
    @Transactional
    public void delete(Budget entity) {
        budgetDao.delete(entity);
    }

    public int update_budget(Budget budget){
        budgetDao.save(budget);
        return 1;

    }

    @Autowired
    BudgetDao budgetDao;

}
