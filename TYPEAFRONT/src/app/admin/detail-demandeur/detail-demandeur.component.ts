import {Component, ViewChild, ElementRef, OnInit ,Input} from '@angular/core';
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';
import * as html2pdf from 'html2pdf.js';import { ActivatedRoute, Params } from '@angular/router';
import { Document } from 'src/app/controller/model/document.model';
import { User } from 'src/app/controller/model/user.model';
import { AdminService } from 'src/app/controller/service/admin.service';
import { DonneePro } from '../../controller/model/donnee-pro.model';
import { UserService } from '../../controller/service/user.service';

@Component({
  selector: 'app-detail-demandeur',
  templateUrl: './detail-demandeur.component.html',
  styleUrls: ['./detail-demandeur.component.css'],
})
export class DetailDemandeurComponent implements OnInit {
  id: number;
  file: any;
  fileUrl: any;
  user: User;
  donnepro: DonneePro = new DonneePro();
  unfhichier: string;
  @ViewChild('pdfTable', {static: false})
  pdfTable!: ElementRef;
  constructor(
    private route: ActivatedRoute,
    private adminService: AdminService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      this.id = params['id'];
    });
    this.user = new User();
    this.adminService.getUserById(this.id).subscribe((data) => {
      this.user = data;
    });
    this.adminService.getdonnepro(this.id).subscribe((dat) => {
      this.donnepro = dat;
      this.adminService.findRapport(this.donnepro.id).subscribe((da) => {
        this.unfhichier = da.fichier;
        if (this.unfhichier === undefined) {
          (<HTMLInputElement>document.getElementById('rapport')).disabled =
            true;
        }
      });
    });
  }

  openFile() {
    window.open(this.unfhichier);
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
