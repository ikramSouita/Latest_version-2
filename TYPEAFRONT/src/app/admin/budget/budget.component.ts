import {Component, LOCALE_ID, OnInit} from '@angular/core';
import {AdminService} from "../../controller/service/admin.service";
import {Budget} from "../../controller/model/Budget.model";
import Swal from "sweetalert2";
import {Router} from "@angular/router";

@Component({
  selector: 'app-budget',
  templateUrl: './budget.component.html',
  styleUrls: ['./budget.component.css']
})
export class BudgetComponent implements OnInit {


  constructor( private  adminservice : AdminService ,private  router : Router) { }
bud : Budget = new Budget();
budg: Budget = new Budget();
date_actuelle : Date = new Date();
  ngOnInit(): void {
    this.adminservice.get_budget_annuelle_object(this.date_actuelle.getFullYear()).subscribe((elem)=>{
      this.budg=elem;
    })

  }

  enregistrer() {

    this.adminservice.save_budget(this.bud).subscribe((elem)=>{
      if(elem == -1){

        Swal.fire(
          'Ajout de Budget',
          'vérifiez la date entrée',
          'warning'
        )
      }
      else {
        if(elem == 1){
        Swal.fire(
          'Ajout de Budget',
          'Votre budget est bien enregistré',
          'success'
        ).then((result) => {
          if (result.isConfirmed) {
            this.router.navigate(['/dashboard']);
          }
        });
        }else{
          Swal.fire(
            'Ajout de Budget',
            'Votre budget est mis à jour',
            'success'
          ).then((result) => {
            if (result.isConfirmed) {
              this.router.navigate(['/dashboard']);
            }
          });

        }

      }
    });

  }

}
