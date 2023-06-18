import {Component, ViewChild, ElementRef, OnInit} from '@angular/core';
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';
import * as html2pdf from 'html2pdf.js';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { State } from 'src/app/controller/enums/state.service';
import { Cadre } from 'src/app/controller/model/cadre.model';
import { documents } from 'src/app/controller/model/documents.model';
import { DonneePro } from 'src/app/controller/model/donnee-pro.model';
import { MissionStage } from 'src/app/controller/model/mission-stage.model';
import { NewMontant } from 'src/app/controller/model/montants.model';
import { Soutien } from 'src/app/controller/model/soutien.model';
import { User } from 'src/app/controller/model/user.model';
import { AdminService } from 'src/app/controller/service/admin.service';
import { AllusersService } from 'src/app/controller/service/allusers.service';
import Swal from 'sweetalert2';
import { MailFormComponent } from '../mail-form/mail-form.component';

@Component({
  selector: 'app-detail-demande',
  templateUrl: './detail-demande.component.html',
  styleUrls: ['./detail-demande.component.css'],
})
export class DetailDemandeComponent implements OnInit {
  id: number;
  mStage: MissionStage;
  user: User;
  donnePro: DonneePro;
  sum: number;
  notgood: boolean = false;
  cadre: Cadre;
  soutien: Soutien;
  newMont: NewMontant;
  documents: documents;
  ismStage: boolean = true;
  path: string;
  file: any;
  fileUrl: any;
  @ViewChild('pdfTable', {static: false})
  pdfTable!: ElementRef;


  constructor(
    private route: ActivatedRoute,
    private adminService: AdminService,
    public dialog: MatDialog,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.mStage = new MissionStage();
    this.user = new User();
    this.cadre = new Cadre();
    this.soutien = new Soutien();
    this.newMont = new NewMontant();
    this.documents = new documents();
    this.adminService.getMissionStageById(this.id).subscribe((stage) => {
      this.mStage = stage;
    });
    this.adminService.getUserByMstage(this.id).subscribe((userdata) => {
      this.user = userdata;
    });
    this.adminService.getUserDonne(this.id).subscribe((donnedata) => {
      this.donnePro = donnedata;
    });
    this.adminService.getCadreByMStage(this.id).subscribe((cadredata) => {
      this.cadre = cadredata;
    });
    this.adminService.getSoutienByMStage(this.id).subscribe((soutiendata) => {
      this.soutien = soutiendata;
    });
    if (
      this.mStage.state == State.APPROVED ||
      this.mStage.state == State.REFUSED
    ) {
      (<HTMLInputElement>document.getElementById('btna')).disabled = true;
      (<HTMLInputElement>document.getElementById('btnR')).disabled = true;
    }
    this.adminService.readDocsMStage(this.id).subscribe((datadocs) => {
      this.documents = datadocs;
      if (this.documents.filecin === undefined) {
        (<HTMLInputElement>document.getElementById('cinbtn')).disabled = true;
      }
      if (this.documents.fileA === undefined) {
        (<HTMLInputElement>document.getElementById('document1btn')).disabled =
          true;
      }
      if (this.documents.fileB === undefined) {
        (<HTMLInputElement>document.getElementById('document2btn')).disabled =
          true;
      }
      if (this.documents.fileC === undefined) {
        (<HTMLInputElement>document.getElementById('document3btn')).disabled =
          true;
      }
      if (this.documents.fileD === undefined) {
        (<HTMLInputElement>document.getElementById('document4btn')).disabled =
          true;
      }
      if (this.documents.fileE === undefined) {
        (<HTMLInputElement>document.getElementById('document5btn')).disabled =
          true;
      }
      if (this.documents.fileF === undefined) {
        (<HTMLInputElement>document.getElementById('document6btn')).disabled =
          true;
      }
    });
  }

  refuseMStage() {
    Swal.fire({
      title: 'Refuser',
      text: 'Etes vous sur de rejeter cette demande !?',
      icon: 'warning',
      confirmButtonText: 'Oui',
      showCancelButton: true,
      cancelButtonText: 'Non',
    }).then((result) => {
      if (result.isConfirmed) {
        // changé le status de la demande | REFUSED
        this.adminService.RefuseMStage(this.id).subscribe(
          (data) => {
            if(data == 1){
              Swal.fire('Refuser', 'La demande a été refusée ', 'success').then(
                (res) => {
                  // envoi d'émail
                  if (res.isConfirmed){
                    this.dialog.open(MailFormComponent, {
                    data: {
                      id: this.id,
                      email: this.user.email,
                      demandeur: this.user.nom + ' ' + this.user.prenom,
                      evenement: this.mStage.objetMission,
                      montant: this.soutien.montant,
                      type: this.ismStage,
                      status: "refus",
                      typeBtn: "SendLetter"
                    },
                  });
                  }
                  this.router.navigate(['/demandes-Stage']);
                }
              );
            } else {
              Swal.fire(
                'Refuser',
                'La demande est déjà traitée',
                'warning'
              );
            }
          });

      } else {
        this.router.navigate(['/demandes-Stage']);
      }
    });
  }

  acceptMStage() {
    Swal.fire({
      title: 'Acceptation',
      text: 'Voulez-vous vraiment accepter cette demande !?',
      icon: 'warning',
      confirmButtonText: 'Oui',
      showCancelButton: true,
      cancelButtonText: 'Non',
    }).then((result) => {
      if (result.isConfirmed) {
        // changé le status de la demande | APPROVED
        this.adminService.mStageApproved(this.id).subscribe(
          (data) => {
            if(data === 1){
              Swal.fire('Acceptation', 'La demande a été acceptée ', 'success').then(
                (res) => {
                  // envoi d'émail
                  if (res.isConfirmed){
                    this.dialog.open(MailFormComponent, {
                      data: {
                        id: this.id,
                        email: this.user.email,
                        demandeur: this.user.nom + ' ' + this.user.prenom,
                        evenement: this.mStage.objetMission,
                        montant: this.soutien.montant,
                        type: this.ismStage,
                        status: "accept",
                        typeBtn: "SendLetter"
                      },
                    });
                  }
                  this.router.navigate(['/demandes-Stage']);
                }
              );
            } else {
              Swal.fire(
                'Acceptation',
                'Cette demande est déjà traitée',
                'error'
              );
            }
          });

      } else {
        this.router.navigate(['/demandes-Stage']);
      }
    });
    this.dialog.open(MailFormComponent, {
      data: {
        id: this.id,
        email: this.user.email,
        demandeur: this.user.nom + ' ' + this.user.prenom,
        evenement: this.mStage.objetMission,
        montant: this.soutien.montant,
        type: this.ismStage,
        status: "accept"
      },
    });
  }

  sendMail() {
    this.dialog.open(MailFormComponent, {
      data: {
        id: this.id,
        email: this.user.email,
        demandeur: this.user.nom + ' ' + this.user.prenom,
        evenement: this.mStage.objetMission,
        montant: this.soutien.montant,
        type: this.ismStage,
        typeBtn: "sendMail"
      },
    });
  }

  onSave() {
    this.adminService
      .ajoutNewMontantMS(this.id, this.newMont)
      .subscribe((data) => {
        if (data == 1) {
          Swal.fire(
            'Montants Sauvegardés',
            'Montants sauvegardés avec succès',
            'success'
          );
        } else {
          Swal.fire(
            'Montants Sauvegardés',
            'Montats sont déjà sauvegardés',
            'error'
          );
        }
      });
  }

  onclick() {
    this.adminService
      .exportNvmontantmission(this.id)
      .subscribe((data: string) => {
        this.file = new Blob([data], { type: 'application/pdf' });
        this.fileUrl = URL.createObjectURL(this.file);
        window.open(this.fileUrl);
      });
  }

  openFile1() {
    window.open(this.documents.filecin);
  }
  openFile2() {
    window.open(this.documents.fileA);
  }
  openFile3() {
    window.open(this.documents.fileB);
  }
  openFile4() {
    window.open(this.documents.fileC);
  }
  openFile5() {
    window.open(this.documents.fileD);
  }
  openFile6() {
    window.open(this.documents.fileE);
  }
  openFile7() {
    window.open(this.documents.fileF);
  }


  testsum() {
    let sum =
      this.newMont.newmTitre +
      this.newMont.newmHebergement +
      this.newMont.newmFraisInscription +
      this.newMont.newautreMontant;
    if (sum > 15000) {
      this.notgood = true;
    } else {
      this.notgood = false;
    }
  }

  generateLettreMStagePDF_Refus(){
    this.adminService.generateLettreMStagefPDF('refus',this.id).subscribe((data) => {
      let downloadURL = window.URL.createObjectURL(data)
      let link = document.createElement('a')
      link.href = downloadURL
      link.download = "lettre_refus_" + this.user.nom + "_" + this.user.prenom + ".pdf"
      link.click()
    })
  }

  generateLettreMStagePDF_Accept(){
    this.adminService.generateLettreMStagefPDF('acceptation',this.id).subscribe((data) => {
      let downloadURL = window.URL.createObjectURL(data)
      let link = document.createElement('a')
      link.href = downloadURL
      link.download = "lettre_acceptation_" + this.user.nom + "_" + this.user.prenom + ".pdf"
      link.click()
    })
  }
  public downloadAsPDF() {
    const pdfTable = this.pdfTable.nativeElement;

    html2pdf().from(pdfTable).save('facture.pdf');
    html2canvas(pdfTable).then(canvas => {
      const imgData = canvas.toDataURL('image/png');
      const pdf = new jsPDF();
      const imgProps = pdf.getImageProperties(imgData);
      const pdfWidth = pdf.internal.pageSize.getWidth() * 0.1;
      const pdfHeight = (imgProps.height * pdfWidth) / imgProps.width;
      pdf.addImage(imgData, 'PNG', -30, 0, pdfWidth, pdfHeight);
      pdf.save('facture.pdf');
    });
  }
}
