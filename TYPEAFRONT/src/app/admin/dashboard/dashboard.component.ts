import {Component, OnInit} from '@angular/core';
import {Chart} from 'node_modules/chart.js';
import {AdminService} from "../../controller/service/admin.service";
import {Montant_par_labo} from "../../controller/model/Montant_par_labo.model";



@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  constructor(private adminservice: AdminService) {
  }

  liste_montant_number: number [] = [];
  nbrusers: number;
  date: number;
  number: number;
  montant_par_lab: Montant_par_labo [];
  date_actuelle: Date = new Date();
  budget_annuel: number;
  budget_consommer: number;
  budget_rest: number;
  list_montant_mois: number[] = [];
  mylineChart : Chart;
  mylineChartt : Chart;
  ngOnInit() {
    this.update(this.date_actuelle.getFullYear());

  }

  graphmois(montant_mois: number []) {
    if(this.mylineChart){
      this.mylineChart.destroy();
    }

    this.mylineChart= new Chart('lineChart', {
      type: 'line',
      data: {
        labels: ["January", "February", "March", "April", "May", "June", "July", " August", "September", "October", "November", " December"],
        datasets: [{
          label: "Les dépenses mensuelles (DH) ",
          data: montant_mois,

          borderWidth: 2
        },

        ]
      },
      options: {
        responsive: true
      }
    });
  }


  graphbar(numeros: number[]) {
    if(this.mylineChartt){
      this.mylineChartt.destroy();
    }

    this.mylineChartt=new Chart('myChart', {

      type: 'bar',
      data: {

        labels: ["ENSA-M", "FSSM", "ENCG-M", "FMP-M", "FLSHM", "FSTG", "FSJES", "ENS-M", "FP Safi", "ENSA Safi", "EST Safi", "EST Essaouira", "FSJESK", "FLAM", "ESTK"],
        datasets: [
          {

            label: 'Les montants par établissement (DH) ',
            data: numeros,
            backgroundColor: [
              'rgba(255, 99, 132, 0.2)',
              'rgba(54, 162, 235, 0.2)',
              'rgba(255, 206, 86, 0.2)',
              'rgba(75, 192, 192, 0.2)',
              'rgba(153, 102, 255, 0.2)',
              'rgba(255, 159, 64, 0.2)',
            ],
            borderColor: [
              'rgba(255, 99, 132, 1)',
              'rgba(54, 162, 235, 1)',
              'rgba(255, 206, 86, 1)',
              'rgba(75, 192, 192, 1)',
              'rgba(153, 102, 255, 1)',
              'rgba(255, 159, 64, 1)',
            ],
            borderWidth: 1,
          },
        ],
      },
      options: {
        scales: {
          y: {
            beginAtZero: true,
          },
        },
      },
    });
  }


  update(datee: number) {

//MONTANTS PAR LABORATOIRE

    this.adminservice.find_montant_par_labo_par_year(datee).subscribe((elem) =>
    {
      this.montant_par_lab = elem;
    });
// MONATNTS PAR ETABLISSMENT
    this.adminservice.get_statistic_etablissement("ENSA-M", "FSSM", "ENCG-M", "FMP-M", "FLSHM", "FSTG", "FSJES", "ENS-M", "FP Safi", "ENSA Safi", "EST Safi", "EST Essaouira", "FSJESK", "FLAM", "ESTK", datee).subscribe((Liste_montants_string) => {
      this.liste_montant_number = [];
      Liste_montants_string.forEach(elem => {
        if (elem != null) {
          this.number = +elem;
          this.liste_montant_number.push(this.number);
        } else {
          this.number = 0;
          this.liste_montant_number.push(this.number);

        }

      })

      this.graphbar(this.liste_montant_number);
    })

//MONTANT PAR MOIS
    this.adminservice.get_statistic_graph_mois("JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER", datee).subscribe((Liste_montants_mois_string) => {
      this.list_montant_mois = [];
      Liste_montants_mois_string.forEach(elem => {
        if (elem != null) {
          this.number = +elem;
          this.list_montant_mois.push(this.number);
        } else {
          this.number = 0;
          this.list_montant_mois.push(this.number);
        }

      })
      this.graphmois(this.list_montant_mois);
    })

// NBR DE USERS
    this.adminservice.countusers().subscribe((elem) => {
      this.nbrusers = elem;
    })
//budget
    this.adminservice.get_budget_annuelle_object(datee).subscribe((elem) => {
      if(elem==null)
      {
        this.budget_annuel = 0;
      }
      else {
        this.budget_annuel = elem.montant;
      }

      this.adminservice.get_budget_comsommer(datee).subscribe((element) => {
        if(elem==null)
        {
          this.budget_consommer = 0;
        }
        else {
          this.budget_consommer = element;
        }



      })

    })

  }
}
