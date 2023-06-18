import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { State } from 'src/app/controller/enums/state.service';
import { Cadre } from 'src/app/controller/model/cadre.model';
import { DonneePro } from 'src/app/controller/model/donnee-pro.model';
import { Manifestation } from 'src/app/controller/model/manifestation.model';
import { MissionStage } from 'src/app/controller/model/mission-stage.model';
import { Soutien } from 'src/app/controller/model/soutien.model';
import { User } from 'src/app/controller/model/user.model';
import { AdminService } from 'src/app/controller/service/admin.service';
import { UserService } from 'src/app/controller/service/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-historique',
  templateUrl: './historique.component.html',
  styleUrls: ['./historique.component.css'],
})
export class HistoriqueComponent implements OnInit {
  file: any;
  fileUrl: any;
  manifestations: Manifestation[];
  missionStages: MissionStage[];
  mStage: MissionStage = new MissionStage();
  manif: Manifestation = new Manifestation();
  user: User = new User();
  donnePro: DonneePro = new DonneePro();
  cadre: Cadre = new Cadre();
  soutien: Soutien = new Soutien();
  isManif: boolean = false;
  isRefused: string;
  canCanceled: boolean;

  constructor(
    private userService: UserService,
    private adminService: AdminService,
    private  router : Router
  ) {}

  ngOnInit(): void {
    this.getManifs();
    this.getMStages();
  }

  private getMStages() {
    this.userService.getMyMissionStages().subscribe((data) => {
      this.missionStages = data;
    });
  }

  private getManifs() {
    this.userService.getMyManifestations().subscribe((data) => {
      this.manifestations = data;
    });
  }

  changeList() {
    if (this.isManif) {
      this.isManif = false;
      this.getMStages();
      document.getElementById('changebtn').textContent = '=> Manifestations <=';
    } else {
      this.isManif = true;
      this.getManifs();
      document.getElementById('changebtn').textContent = '=> MissionStages <=';
    }
  }

  manifestationBtn() {
    this.isManif = true;
    this.getManifs();
  }

  missionStageBtn() {
    this.isManif = false;
    this.getMStages();
  }

  viewDetailsMStage(id: number) {
    this.adminService.getMissionStageById(id).subscribe((stage) => {
      this.mStage = stage;
    });
    this.adminService.getUserByMstage(id).subscribe((userdata) => {
      this.user = userdata;
    });
    this.adminService.getUserDonne(id).subscribe((donnedata) => {
      this.donnePro = donnedata;
    });
    this.adminService.getCadreByMStage(id).subscribe((cadredata) => {
      this.cadre = cadredata;
    });
    this.adminService.getSoutienByMStage(id).subscribe((soutiendata) => {
      this.soutien = soutiendata;
    });
    this.imprimerMStage(id);
  }
  viewDetailsManif(id: number) {
    this.adminService.getManifestationById(id).subscribe((manifdonne) => {
      this.manif = manifdonne;
    });
    this.adminService.getUserByManifId(id).subscribe((userdata) => {
      this.user = userdata;
    });
    this.adminService.getUserDonneByManifId(id).subscribe((donnedata) => {
      this.donnePro = donnedata;
    });
    this.adminService.getSoutienByManifId(id).subscribe((soutiendata) => {
      this.soutien = soutiendata;
    });
    this.imprimerManif(id);
  }

  imprimerManif(id: number) {
    this.userService.generateReport(id).subscribe((data: string) => {
      this.file = new Blob([data], { type: 'application/pdf' });
      this.fileUrl = URL.createObjectURL(this.file);
      window.open(this.fileUrl);
    });
  }

  imprimerMStage(id: number) {
    this.userService.exportReportMission(id).subscribe((data: string) => {
      this.file = new Blob([data], { type: 'application/pdf' });
      this.fileUrl = URL.createObjectURL(this.file);
      window.open(this.fileUrl);
    });
  }

  deleteMStage(id: number) {
    Swal.fire({
      title: 'Annulation',
      text: 'Voulez-vous vraiment anuuler cette demande !?',
      icon: 'warning',
      confirmButtonText: 'Oui',
      showCancelButton: true,
      cancelButtonText: 'Non',
    }).then((result) => {
      if (result.isConfirmed) {
        // annulation de la demande
        this.userService.deleteMStage(id).subscribe((res) =>{
          if(res==0){
            Swal.fire(
              'Annulation',
              'Votre demande de mission ou stage est annulée avec succès',
              'success'
            ).then((result) => {
              if (result.isConfirmed) {
                this.router.navigate(['/historique']);
                this.ngOnInit()
              }
            });
          } else{
            Swal.fire(
              'Annulation',
              'une erreur est rencontrée lors de l\'annulée, merci de réssayer une autre fois !',
              'error'
            )
          }
      })
      } else {
        this.router.navigate(['/historique']);
      }
    });

  }

  deleteManif(id: number) {
    Swal.fire({
      title: 'Annulation',
      text: 'Voulez-vous vraiment anuuler cette demande !?',
      icon: 'warning',
      confirmButtonText: 'Oui',
      showCancelButton: true,
      cancelButtonText: 'Non',
    }).then((result) => {
      if (result.isConfirmed) {
        // annulation de la demande
        this.userService.deleteManif(id).subscribe((res) =>{
          if(res==0){
            Swal.fire(
              'Annulation',
              'Votre demande de manifestation est annulée avec succès',
              'success'
            ).then((result) => {
              if (result.isConfirmed) {
                this.router.navigate(['/historique']);
                this.ngOnInit()
              }
            });
          } else{
            Swal.fire(
              'Annulation',
              'une erreur est rencontrée lors de l\'annulée, merci de réssayer une autre fois !',
              'error'
            )
          }
      })
      } else {
        this.router.navigate(['/historique']);
      }
    });

  }


}
