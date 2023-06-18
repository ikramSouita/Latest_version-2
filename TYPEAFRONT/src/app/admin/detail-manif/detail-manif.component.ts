import {Component, ViewChild, ElementRef, OnInit} from '@angular/core';
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';
import * as html2pdf from 'html2pdf.js';

import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { State } from 'src/app/controller/enums/state.service';
import { documents } from 'src/app/controller/model/documents.model';
import { DonneePro } from 'src/app/controller/model/donnee-pro.model';
import { Manifestation } from 'src/app/controller/model/manifestation.model';
import { NewMontant } from 'src/app/controller/model/montants.model';
import { Soutien } from 'src/app/controller/model/soutien.model';
import { User } from 'src/app/controller/model/user.model';
import { AdminService } from 'src/app/controller/service/admin.service';
import Swal from 'sweetalert2';
import { MailFormComponent } from '../mail-form/mail-form.component';

@Component({
  selector: 'app-detail-manif',
  templateUrl: './detail-manif.component.html',
  styleUrls: ['./detail-manif.component.css'],
})
export class DetailManifComponent implements OnInit {
  id: number;
  file: any;
  fileUrl: any;
  manif: Manifestation;
  user: User;
  donnePro: DonneePro;
  soutien: Soutien;
  newMont: NewMontant;
  documents: documents;
  sum: number;
  notgood: boolean = false;
  ismStage: boolean = false;
  @ViewChild('pdfTable', {static: false})
  pdfTable!: ElementRef;

  constructor(
    private route: ActivatedRoute,
    private adminService: AdminService,
    private router: Router,
    public dialog: MatDialog
  ) {
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.manif = new Manifestation();
    this.user = new User();
    this.soutien = new Soutien();
    this.newMont = new NewMontant();
    this.documents = new documents();

    this.adminService.getManifestationById(this.id).subscribe((manifdonne) => {
      this.manif = manifdonne;
    });
    this.adminService.getUserByManifId(this.id).subscribe((userdata) => {
      this.user = userdata;
    });
    this.adminService.getUserDonneByManifId(this.id).subscribe((donnedata) => {
      this.donnePro = donnedata;
    });
    this.adminService.getSoutienByManifId(this.id).subscribe((soutiendata) => {
      this.soutien = soutiendata;
    });
    if (
      this.manif.state == State.APPROVED ||
      this.manif.state == State.REFUSED
    ) {
      (<HTMLInputElement>document.getElementById('btna')).disabled = true;
      (<HTMLInputElement>document.getElementById('btnR')).disabled = true;
    }
    this.adminService.readDocsManif(this.id).subscribe((datadocs) => {
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
        this.adminService.manifRefused(this.id).subscribe(
          (data) => {
            if (data == 1) {
              Swal.fire('Refuser', 'La demande a été refusée ', 'success').then(
                (res) => {
                  // envoi d'émail
                  if (res.isConfirmed) {
                    this.dialog.open(MailFormComponent, {
                      data: {
                        id: this.id,
                        email: this.user.email,
                        demandeur: this.user.nom + ' ' + this.user.prenom,
                        evenement: this.manif.titreManifestation,
                        montant: this.soutien.montant,
                        type: this.ismStage,
                        status: "refus",
                        typeBtn: "SendLetter"
                      },
                    });
                  }
                  this.router.navigate(['/demandes-Manif']);
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
        this.router.navigate(['/demandes-Manif']);
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
        this.adminService.manifApproved(this.id).subscribe(
          (data) => {
            if (data === 1) {
              Swal.fire('Acceptation', 'La demande a été acceptée ', 'success').then(
                (res) => {
                  // envoi d'émail
                  if (res.isConfirmed) {
                    this.dialog.open(MailFormComponent, {
                      data: {
                        id: this.id,
                        email: this.user.email,
                        demandeur: this.user.nom + ' ' + this.user.prenom,
                        evenement: this.manif.titreManifestation,
                        montant: this.soutien.montant,
                        type: this.ismStage,
                        status: "accept",
                        typeBtn: "SendLetter"
                      },
                    });
                  }
                  this.router.navigate(['/demandes-Manif']);
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
        this.router.navigate(['/demandes-Manif']);
      }
    });
  }

  sendMail() {
    this.dialog.open(MailFormComponent, {
      data: {
        email: this.user.email,
        demandeur: this.user.nom + ' ' + this.user.prenom,
        evenement: this.manif.titreManifestation,
        montant: this.soutien.montant,
        type: this.ismStage, //false
        id: this.id,
        typeBtn: "sendMail"
      },
    });
  }

  onSave() {
    this.testsum();
    this.adminService
      .ajoutNewMontantM(this.id, this.newMont)
      .subscribe((data: number) => {
        if (data == 1) {
          Swal.fire(
            'Montants Sauvegardés',
            'Montants sauvegardés avec succès',
            'success'
          );
        } else {
          Swal.fire(
            'Montants Sauvegardés',
            'Montants sont déjà sauvegardés',
            'error'
          );
        }
      });
  }

  onclick() {
    this.adminService
      .exportNvmontantmanif(this.id)
      .subscribe((data: string) => {
        this.file = new Blob([data], {type: 'application/pdf'});
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

  generateLettreManifPDF_Refus() {
    this.adminService.generateLettreManifPDF('refus', this.id).subscribe((data) => {
      let downloadURL = window.URL.createObjectURL(data)
      let link = document.createElement('a')
      link.href = downloadURL
      link.download = "lettre_refus_" + this.user.nom + "_" + this.user.prenom + ".pdf"
      link.click()
    })
  }

  generateLettreManifPDF_Accept() {
    this.adminService.generateLettreManifPDF('acceptation', this.id).subscribe((data) => {
      let downloadURL = window.URL.createObjectURL(data)
      let link = document.createElement('a')
      link.href = downloadURL
      link.download = "lettre_acceptation_" + this.user.nom + "_" + this.user.prenom + ".pdf"
      link.click()
    })
  }

  uploadLettre() {

  }

  public downloadAsPDF() {
    const pdfTable = this.pdfTable.nativeElement;

    html2pdf().from(pdfTable).save('facture.pdf');
    html2canvas(pdfTable).then(canvas => {
      const imgData = canvas.toDataURL('image/png');
      const pdf = new jsPDF();
      const imgProps = pdf.getImageProperties(imgData);
      const pdfWidth = pdf.internal.pageSize.getWidth() * 0.5;
      const pdfHeight = (imgProps.height * pdfWidth) / imgProps.width;
      pdf.addImage(imgData, 'PNG', -30, 0, pdfWidth, pdfHeight);
      pdf.save('facture.pdf');
    });
  }
}
