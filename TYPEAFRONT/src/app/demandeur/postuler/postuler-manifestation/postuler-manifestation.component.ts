import {Component, ViewChild, ElementRef, OnInit} from '@angular/core';
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';
import { Manifestation } from 'src/app/controller/model/manifestation.model';
import { Soutien } from 'src/app/controller/model/soutien.model';
import { UserService } from 'src/app/controller/service/user.service';
import Swal from 'sweetalert2';
import { documents } from '../../../controller/model/documents.model';
import * as html2pdf from 'html2pdf.js';


@Component({
  selector: 'app-postuler-manifestation',
  templateUrl: './postuler-manifestation.component.html',

  styleUrls: ['./postuler-manifestation.component.css'],
})
export class PostulerManifestationComponent implements OnInit {
  selectedFile = {} as HTMLInputElement;
  selectedFileA = {} as HTMLInputElement;
  selectedFileB = {} as HTMLInputElement;
  selectedFileC = {} as HTMLInputElement;
  selectedFileD = {} as HTMLInputElement;
  selectedFileE = {} as HTMLInputElement;
  selectedFileF = {} as HTMLInputElement;

  file: any;
  fileUrl: any;
  manif: Manifestation = new Manifestation();
  soutien: Soutien = new Soutien();
  documents: documents = new documents();
  id: number;
  erreur: string;
  title = 'htmltopdf';
  @ViewChild('pdfTable', {static: false})
  pdfTable!: ElementRef;

  constructor(private userService: UserService) {
  }

  ngOnInit(): void {
  }

  onSubmit() {
    if (
      this.soutien.isBenfTypeA === 'oui' &&
      (this.documents.fileF === undefined ||
        this.soutien.montantderniersoutien === undefined ||
        this.soutien.datederniersoutien === undefined)
    ) {
      Swal.fire(
        'Ajout de manifestation',
        'Rapport ne peut pas être vide si vous avez déjà bénéficier du soutien type A',
        'error'
      );
    } else {
      if (
        this.documents.filecin !== undefined ||
        this.documents.fileA !== undefined ||
        this.documents.fileB !== undefined ||
        this.documents.fileC !== undefined ||
        this.documents.fileD !== undefined ||
        this.documents.fileE !== undefined
      ) {
        let newDateDebut = new Date(this.manif.dateDebut);
        let newDateFin = new Date(this.manif.dateFin);
        let newDateDepart = new Date(this.manif.dateDepart);
        let newDateRetour = new Date(this.manif.dateRetour);
        if (
          newDateDebut.getTime() > newDateFin.getTime() ||
          newDateDepart.getTime() > newDateRetour.getTime()
        ) {
          Swal.fire(
            'Ajout de manifestation',
            'Veuillez vérifier les dates saisies (début/fin/départ/retour)',
            'error'
          );
        } else {
          this.userService
            .addAllManif(this.manif, this.soutien)
            .subscribe((x: any) => {
              if (x == '-1') {
                Swal.fire(
                  'Ajout de manifestation',
                  'Un ou plusieurs champs sont invalides',
                  'error'
                );
              } else if (x == '-2') {
                Swal.fire(
                  'Ajout de manifestation',
                  'Vous ne pouvez pas postuler sans remplir vos données professionnelles',
                  'error'
                );
              } else {
                this.id = x;
                this.userService
                  .addFilesManif(x, this.documents)
                  .subscribe((data) => {
                    (<HTMLInputElement>(
                      document.getElementById('impbtnM')
                    )).disabled = false;
                    Swal.fire(
                      'Ajout de manifestation',
                      'Demande Enregistrée',
                      'success'
                    );
                  });
                this.id = x;
              }
            });
        }
      } else {
        Swal.fire(
          'Ajout de manifestation',
          'Veuillez remplire tous les fichiers demandés',
          'error'
        );
      }
    }
  }

  onFileSelected(event: Event) {
    let selectedFile = (<HTMLInputElement>event.target).files![0];
    this.documents.filecin = selectedFile;
    document.getElementById('cin').textContent =
      selectedFile.name.toUpperCase();
    document.getElementById('cin').style.color = 'red';
  }

  onFileSelectedA(event: Event) {
    let selectedFileA = (<HTMLInputElement>event.target).files![0];
    this.documents.fileA = selectedFileA;
    document.getElementById('doc1').textContent =
      selectedFileA.name.toUpperCase();
    document.getElementById('doc1').style.color = 'red';
  }

  onFileSelectedB(event: Event) {
    let selectedFileB = (<HTMLInputElement>event.target).files![0];
    this.documents.fileB = selectedFileB;
    document.getElementById('doc2').textContent =
      selectedFileB.name.toUpperCase();
    document.getElementById('doc2').style.color = 'red';
  }

  onFileSelectedC(event: Event) {
    let selectedFileC = (<HTMLInputElement>event.target).files![0];
    this.documents.fileC = selectedFileC;
    document.getElementById('doc3').textContent =
      selectedFileC.name.toUpperCase();
    document.getElementById('doc3').style.color = 'red';
  }

  onFileSelectedD(event: Event) {
    let selectedFileD = (<HTMLInputElement>event.target).files![0];
    this.documents.fileD = selectedFileD;
    document.getElementById('doc4').textContent =
      selectedFileD.name.toUpperCase();
    document.getElementById('doc4').style.color = 'red';
  }

  onFileSelectedE(event: Event) {
    let selectedFileE = (<HTMLInputElement>event.target).files![0];
    this.documents.fileE = selectedFileE;
    document.getElementById('doc5').textContent =
      selectedFileE.name.toUpperCase();
    document.getElementById('doc5').style.color = 'red';
  }

  onFileSelectedF(event: Event) {
    let selectedFileF = (<HTMLInputElement>event.target).files![0];
    this.documents.fileF = selectedFileF;
    document.getElementById('doc6').textContent =
      selectedFileF.name.toUpperCase();
    document.getElementById('doc6').style.color = 'red';
  }

  onSubmitt() {
    this.userService.generateReport(this.id).subscribe((data: string) => {
      this.file = new Blob([data], {type: 'application/pdf'});
      this.fileUrl = URL.createObjectURL(this.file);
      window.open(this.fileUrl);
    });
  }

  show2() {
    document.getElementById('div1').style.display = 'block';
  }

  show1() {
    document.getElementById('div1').style.display = 'none';
  }

  public downloadAsPDF() {
    const pdfTable = this.pdfTable.nativeElement;
    const options = {
      filename: 'manif.pdf',
      margin: [-30, -60, -10, 20], // Réduction de la marge droite
      image: {type: 'jpeg', quality: 1},
      html2canvas: {scale: 2},
      jsPDF: {unit: 'mm', format: 'a4', orientation: 'portrait'}
    };

    html2pdf()
      .from(pdfTable)
      .set(options)
      .save();
  }
}
