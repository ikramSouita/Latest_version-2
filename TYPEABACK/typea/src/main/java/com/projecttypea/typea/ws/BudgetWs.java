package com.projecttypea.typea.ws;

import com.projecttypea.typea.bean.Budget;
import com.projecttypea.typea.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")

public class BudgetWs {
    @GetMapping("/user/findall")
    public List<Budget> findAll() {
        return budgetService.findAll();
    }

    @GetMapping("/user/count")
    public long count() {
        return budgetService.count();
    }

    @GetMapping("/admin/budget_consommer/{date}")
    public Long budget_consommer(@PathVariable int date) {
        return budgetService.budget_consommer(date);
    }

    @PostMapping("/admin/save_budget")
    public int save(@RequestBody Budget budget) {
        return budgetService.save(budget);
    }

    @GetMapping("/admin/BudgetAnnuelle_object/{date}")
    public Budget findByDate(@PathVariable int date) {
        return budgetService.findByDate(date);
    }

    @Autowired
    BudgetService budgetService;

}
