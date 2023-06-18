import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DonneePro } from 'src/app/controller/model/donnee-pro.model';
import { AllusersService } from 'src/app/controller/service/allusers.service';
import { UserService } from 'src/app/controller/service/user.service';
import { AdminService } from 'src/app/controller/service/admin.service';
import Swal from 'sweetalert2';
import { Etablissement } from '../../../controller/model/Etablissement.model';
import { Document } from 'src/app/controller/model/document.model';

@Component({
  selector: 'app-information-sur-demandeur',
  templateUrl: './information-sur-demandeur.component.html',
  styleUrls: ['./information-sur-demandeur.component.css'],
})
export class InformationSurDemandeurComponent implements OnInit {
  selectedFile = {} as HTMLInputElement;

  donne: DonneePro = new DonneePro();
  donneData: DonneePro = new DonneePro();
  etabData: Etablissement = new Etablissement();
  etab: Etablissement = new Etablissement();
  document: Document = new Document();
  constructor(
    private userService: UserService,
    private router: Router,
    private adminService: AdminService
  ) {}

  ngOnInit(): void {
    this.userService.getThisUserId().subscribe((theid: number) => {
      this.adminService.getdonnepro(theid).subscribe((incoming) => {
        this.donneData = incoming;
        this.adminService
          .getetablissement(incoming.etablissement.id)
          .subscribe((inc) => {
            this.etabData = inc;
          });
      });
    });
  }

  onFileSelected(event: Event) {
    let selectedFile = (<HTMLInputElement>event.target).files![0];
    this.document.file = selectedFile;
    document.getElementById('doc').textContent =
      selectedFile.name.toUpperCase();
    document.getElementById('doc').style.color = 'red';
  }

  onSubmit() {
    this.donne.etablissement = this.etab;
    this.userService.saveDonnesPro(this.donne).subscribe((data: any) => {
      if (this.donneData != null) {
        this.userService
          .addDonneProFile(this.donneData.id, this.document)
          .subscribe(() => {});
      }
      if (data == -1) {
        Swal.fire(
          'Ajout données professionnelles',
          'Vos données professionnelles ont été ajoutées',
          'success'
        ).then((result) => {
          if (result.isConfirmed) {
            this.router.navigate(['/choisir-postuler']);
          }
        });
      } else if (data == 1) {
        this.userService
          .updateDonnesPro(this.donne)
          .subscribe((updated: any) => {
            if (updated == 1) {
              Swal.fire(
                'Mise à jour données professionnelles',
                'Vos données professionnelles ont été mises à jour',
                'success'
              ).then((result) => {
                if (result.isConfirmed) {
                  this.router.navigate(['/choisir-postuler']);
                }
              });
            } else {
              Swal.fire(
                'Ajout données professionnelles',
                'Vos données professionnelles n\'ont été pas ajoutées',
                'error'
              );
            }
          });
      } else {
        Swal.fire(
          'Ajout données professionnelles',
          'Veuillez vérfier que un ou plusieurs champs ne sont pas vides',
          'error'
        );
      }
    });
  }
}
