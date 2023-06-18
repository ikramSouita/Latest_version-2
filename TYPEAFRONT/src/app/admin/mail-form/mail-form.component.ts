import { Component, ElementRef, Inject, OnInit, ViewChild } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MailMessage } from 'src/app/controller/model/mailmessages.model';
import { AdminService } from 'src/app/controller/service/admin.service';
import Swal from 'sweetalert2';
import { DialogData } from './dialogData.service';
import { Document } from 'src/app/controller/model/document.model';

@Component({
  selector: 'app-mail-form',
  templateUrl: './mail-form.component.html',
  styleUrls: ['./mail-form.component.css'],
})
export class MailFormComponent implements OnInit {

  mssgsMail: MailMessage = new MailMessage();
  file: any;
  fileUrl: any;
  document: Document = new Document();

  formData = new FormData();
  
  // added
  @ViewChild("fileUpload", {static: false}) fileUpload: ElementRef;files  = [];  
  fileName:string;

  acceptBody_manif: string = " Bonjour; \n\n"+
      "Nous avons le plaisir de vous informer qu'un soutien de "+ this.data.montant + " dh  a été accordé au " + this.data.demandeur + 
      ", pour participer à l'évènement scientifique " + this.data.evenement + "\n\n" + 
      
      "Par ailleurs, nous vous prions de nous transmettre au plus tard 4 semaines après son retour: \n" +
      
        "\t- Une attestation de participation \n" +
        " \t- Le programme de la manifestation \n" + 
        " \t- Un rapport de votre participation. \n" +
        "\n"
      
      + "Bien à vous";

  refusBody_manif: string = "Suite à votre demande de soutien, pour participer à l'évènement scientifique " + this.data.evenement + "\n\n" +
    " j'ai le regret de porter à votre connaissance qu'aucune suite favorable n'a pu être donnée à votre " + 
    "demande."

  acceptBody_mstage: string = " Bonjour; \n\n"+
    "Nous avons le plaisir de vous informer qu'un soutien de "+ this.data.montant + " dh  a été accordé au " + this.data.demandeur + 
    ", pour effectuer un stage de recherche \n\n"  + 
    
    "Par ailleurs, nous vous prions de nous transmettre au plus tard 4 semaines après son retour: \n" +
      ""+ 
      "\t- Une attestation de stage \n" +
      " \t- Un rapport de votre stage. \n" +
      "\n"
    
    + "Bien à vous";

  refusBody_mstage: string = "Suite à votre demande de soutien, pour effectuer un stage de recherche \n\n"  +
    " j'ai le regret de porter à votre connaissance qu'aucune suite favorable n'a pu être donnée à votre " + 
    "demande."

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private adminService: AdminService,
    private dialogRef: MatDialogRef<MailFormComponent>
  ) {}

  ngOnInit(): void {
    this.mssgsMail.toEmail = this.data.email;
    this.mssgsMail.subject = "Soutien type A";
    if(this.data.typeBtn === 'SendLetter'){
      if(this.data.type){
        this.mssgsMail.body = this.data.status === "refus" ? this.refusBody_mstage : this.acceptBody_mstage;
      } else {
        this.mssgsMail.body = this.data.status === "refus" ? this.refusBody_manif : this.acceptBody_manif;
      }
      
    } else {
      this.mssgsMail.body = ""
    }
    
  }

  Valider() {
    if (this.mssgsMail.subject !== undefined && this.data.type) {
      
      this.formData.append('body', this.mssgsMail.body);
      this.formData.append('subject',this.mssgsMail.subject);
      this.formData.append('toEmail', this.mssgsMail.toEmail);
      this.adminService
        .AcceptMStage(this.data.id, this.formData)
        .subscribe((ret: any) => {
          if (ret == 1) {
            this.dialogRef.close();
            Swal.fire(
              'Email envoyé',
              'Email d\'acceptation de la demande Mission Stage a été envoyé avec succès',
              'success'
            );
          } else {
            Swal.fire(
              'Erreur',
              'Erreur : Email d\'acceptation n\'été pas envoyé',
              'error'
            );
          }
        });
    } else if (this.mssgsMail.subject !== undefined && !this.data.type) {
      this.formData.append('body', this.mssgsMail.body);
      this.formData.append('subject',this.mssgsMail.subject);
      this.formData.append('toEmail', this.mssgsMail.toEmail);
      this.adminService
        .AcceptManif(this.data.id, this.formData)
        .subscribe((ret: any) => {
          if (ret == 1) {
            this.dialogRef.close();
            Swal.fire(
              'Email envoyé',
              'Email d\'acceptation de la demande Mission Stage a été envoyé avec succès',
              'success'
            );
          } else {
            Swal.fire(
              'Erreur',
              'Erreur : Email d\'acceptation n\'été pas envoyé',
              'error'
            );
          }
        });
    } else {
      document.getElementById('sujet').textContent = 'Sujet ne peut pas être vide';
    }
  }

  onFileSelected(event: Event) {
    let selectedFile = (<HTMLInputElement>event.target).files![0];
    this.document.file = selectedFile;
    document.getElementById('inputFile').textContent =
      selectedFile.name.toUpperCase();
    document.getElementById('inputFile').style.color = 'red';
  }

  
    // added
    onClickUp() {  
      const fileUpload = this.fileUpload.nativeElement;
      fileUpload.onchange = () => {  
        for (let index = 0; index < fileUpload.files.length; index++)  
        {  
        const file = fileUpload.files[index];  
          this.fileName = file.name +" est importé"
        
        this.files.push({ data: file, inProgress: false, progress: 0});  
        }
          this.uploadFiles();
      };  
      fileUpload.click();  
  }
  private uploadFiles() {  
    this.fileUpload.nativeElement.value = '';  
    this.files.forEach(file => {  
      this.uploadFile(file);  
    });  
  }
   

  uploadFile(file) {   
    this.formData.append('file', file.data);  
    file.inProgress = true;  
  }
}
