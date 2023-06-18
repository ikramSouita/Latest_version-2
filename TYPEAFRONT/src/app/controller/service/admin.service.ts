import { HttpClient, HttpEvent } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { State } from '../enums/state.service';
import { Cadre } from '../model/cadre.model';
import { documents } from '../model/documents.model';
import { DonneePro } from '../model/donnee-pro.model';
import { MailMessage } from '../model/mailmessages.model';
import { Manifestation } from '../model/manifestation.model';
import { messages } from '../model/messages.model';
import { MissionStage } from '../model/mission-stage.model';
import { NewMontant } from '../model/montants.model';
import { Soutien } from '../model/soutien.model';
import { User } from '../model/user.model';
import { Document } from '../model/document.model';
import { environment } from 'src/environments/environment';

import { Etablissement } from '../model/Etablissement.model';
import { Budget } from '../model/Budget.model';
import { Montant_par_labo } from '../model/Montant_par_labo.model';

@Injectable({
  providedIn: 'root',
})
export class AdminService {
  private baseUrl = environment.BASE_URL + '/admin';

  constructor(private httpClient: HttpClient) {}

  findUsers(): Observable<User[]> {
    return this.httpClient.get<User[]>(`${this.baseUrl + '/Users'}`);
  }
  findUsers_rap(): Observable<User[]> {
    return this.httpClient.get<User[]>(`${this.baseUrl + '/usersrap'}`);
  }
  findUsers_sans_rap(): Observable<User[]> {
    return this.httpClient.get<User[]>(`${this.baseUrl + '/users_sans_rap'}`);
  }

  findByNom(name: string): Observable<Object> {
    return this.httpClient.get(`${this.baseUrl + '/username/'} + name`);
  }

  findMissions(): Observable<MissionStage[]> {
    return this.httpClient.get<MissionStage[]>(`${this.baseUrl + '/missions'}`);
  }

  findManifestation(): Observable<Manifestation[]> {
    return this.httpClient.get<Manifestation[]>(
      `${this.baseUrl + '/missions'}`
    );
  }

  getAllMStages(): Observable<MissionStage[]> {
    return this.httpClient.get<MissionStage[]>(`${this.baseUrl + '/missions'}`);
  }

  getAllManifs(): Observable<Manifestation[]> {
    return this.httpClient.get<Manifestation[]>(
      `${this.baseUrl + '/manifestations'}`
    );
  }

  getMissionStageById(mStageId: number): Observable<MissionStage> {
    return this.httpClient.get<MissionStage>(
      `${this.baseUrl + '/getmstage/' + mStageId}`
    );
  }

  getUserById(userId: number): Observable<User> {
    return this.httpClient.get<User>(
      `${this.baseUrl + '/getuserbyid/' + userId}`
    );
  }

  getAllMessages(): Observable<messages[]> {
    return this.httpClient.get<messages[]>(`${this.baseUrl + '/messages'}`);
  }

  getUserByMstage(mStageId: number): Observable<User> {
    return this.httpClient.get<User>(
      `${this.baseUrl + '/theuser/' + mStageId}`
    );
  }

  getUserDonne(mStageId: number): Observable<DonneePro> {
    return this.httpClient.get<DonneePro>(
      `${this.baseUrl + '/userdonne/' + mStageId}`
    );
  }

  RefuseMStage(mStageId: number): Observable<number> {
    return this.httpClient.get<number>(
      `${this.baseUrl + '/refusestage/' + mStageId}`
    );
  }

  manifRefused(mStageId: number): Observable<number> {
    return this.httpClient.get<number>(
      `${this.baseUrl + '/refusemanif/' + mStageId}`
    );
  }

  mStageApproved(mStageId: number): Observable<number> {
    return this.httpClient.get<number>(
      `${this.baseUrl + '/mstage-approved/' + mStageId}`
    );
  }

  manifApproved(mStageId: number): Observable<number> {
    return this.httpClient.get<number>(
      `${this.baseUrl + '/manif-approved/' + mStageId}`
    );
  }

  AcceptMStage(mStageId: number, formData) {
    console.log("upload service function is called")
    console.log(formData)
    return this.httpClient.post<FormData>(
      `${this.baseUrl + '/acceptstage/' + mStageId}`,
       formData, {  
        reportProgress: true,  
        observe: 'events'  
      })
  }

  AcceptManif(ManifId: number, formData) {
    console.log("upload service function is called")
    console.log(formData)
    return this.httpClient.post<FormData>(
      `${this.baseUrl + '/acceptmanif/' + ManifId}`,
       formData, {  
        reportProgress: true,  
        observe: 'events'  
      })
  }

  SendMail(mssgsMail: MailMessage): Observable<MailMessage> {
    return this.httpClient.post<MailMessage>(
      `${this.baseUrl + '/sendmail'}`,
      mssgsMail
    );
  }

  getCadreByMStage(mStageId: number): Observable<Cadre> {
    return this.httpClient.get<Cadre>(
      `${this.baseUrl + '/getcadrebystage/' + mStageId}`
    );
  }

  getSoutienByMStage(mStageId: number): Observable<Soutien> {
    return this.httpClient.get<Soutien>(
      `${this.baseUrl + '/getsoutienbystage/' + mStageId}`
    );
  }

  getManifestationById(manifId: number): Observable<Manifestation> {
    return this.httpClient.get<Manifestation>(
      `${this.baseUrl + '/getmanifbyid/' + manifId}`
    );
  }

  getUserByManifId(manifId: number): Observable<User> {
    return this.httpClient.get<User>(
      `${this.baseUrl + '/getuserbymanif/' + manifId}`
    );
  }

  getUserDonneByManifId(manifId: number): Observable<DonneePro> {
    return this.httpClient.get<DonneePro>(
      `${this.baseUrl + '/getdonnebymanif/' + manifId}`
    );
  }

  getSoutienByManifId(manifId: number): Observable<Soutien> {
    return this.httpClient.get<Soutien>(
      `${this.baseUrl + '/getsoutienbymanif/' + manifId}`
    );
  }

  getLettreMission(missionId: number): Observable<any> {
    return this.httpClient.get(
      `${this.baseUrl + '/raportlettremission/' + missionId}`,
      {
        responseType: 'arraybuffer',
      }
    );
  }

  getLettreManif(manifId: number): Observable<any> {
    return this.httpClient.get(
      `${this.baseUrl + '/raportlettremanif/' + manifId}`,
      {
        responseType: 'arraybuffer',
      }
    );
  }

  ajoutNewMontantMS(mStageId: number, nvMnt: NewMontant): Observable<number> {
    return this.httpClient.post<number>(
      `${this.baseUrl + '/addnewmontantMS/' + mStageId}`,
      nvMnt,
      {
        withCredentials: true,
      }
    );
  }

  ajoutNewMontantM(missionId: number, nvMnt: NewMontant): Observable<number> {
    return this.httpClient.post<number>(
      `${this.baseUrl + '/addnewmontant/' + missionId}`,
      nvMnt,
      {
        withCredentials: true,
      }
    );
  }

  save_budget(budget: Budget): Observable<Object> {
    return this.httpClient.post(`${this.baseUrl + '/save_budget'}`, budget);
  }



  

  /*
  AcceptManif(manifId: number, params: MailMessage, formData): Observable<number> {
    let body = JSON.stringify({ 'params': params, 'document': document })
    console.log("upload service function is called")
    console.log(formData)
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    console.log("manif data ", document.file)

    return this.httpClient.post<number>(
      `${this.baseUrl + '/acceptmanif/' + manifId}`,
      body, 
    );
  }*/

  getdonnepro(id: number): Observable<DonneePro> {
    return this.httpClient.get<DonneePro>(
      `${this.baseUrl + '/getdonne/' + id}`
    );
  }

  countusers(): Observable<number> {
    return this.httpClient.get<number>(`${this.baseUrl + '/countusers'}`);
  }

  exportNvmontantmanif(id: number): Observable<Object> {
    return this.httpClient.get(
      `${this.baseUrl + '/raportNVmontantmanif/' + id}`,
      {
        withCredentials: true,
        responseType: 'arraybuffer',
      }
    );
  }

  exportNvmontantmission(id: number): Observable<Object> {
    return this.httpClient.get(
      `${this.baseUrl + '/raportNvmontantmis/' + id}`,
      {
        withCredentials: true,
        responseType: 'arraybuffer',
      }
    );
  }

  findAllStagesByState(state: State): Observable<MissionStage[]> {
    return this.httpClient.get<MissionStage[]>(
      `${this.baseUrl + '/findallmstages/' + state}`
    );
  }

  findAllManifsByState(state: State): Observable<Manifestation[]> {
    return this.httpClient.get<Manifestation[]>(
      `${this.baseUrl + '/findallmanifs/' + state}`
    );
  }

  readDocsMStage(mStageId: number): Observable<documents> {
    return this.httpClient.get<documents>(
      `${this.baseUrl + '/viewdocs/' + mStageId}`
    );
  }

  readDocsManif(manifId: number): Observable<documents> {
    return this.httpClient.get<documents>(
      `${this.baseUrl + '/viewdocsmanif/' + manifId}`
    );
  }

  getetablissement(etabId: number): Observable<Etablissement> {
    return this.httpClient.get<Etablissement>(
      `${this.baseUrl + '/getetab/' + etabId}`
    );
  }

  get_statistic_etablissement(
    etab: string,
    e2: string,
    e3: string,
    e4: string,
    e5: string,
    e6: string,
    e7: string,
    e8: string,
    e9: string,
    e10: string,
    e11: string,
    e12: string,
    e13: string,
    e14: string,
    e15: string,
    date: number
  ): Observable<string[]> {
    return this.httpClient.get<string[]>(
      `${
        this.baseUrl +
        '/statistic_graph_bar/' +
        etab +
        '/' +
        e2 +
        '/' +
        e3 +
        '/' +
        e4 +
        '/' +
        e5 +
        '/' +
        e6 +
        '/' +
        e7 +
        '/' +
        e8 +
        '/' +
        e9 +
        '/' +
        e10 +
        '/' +
        e11 +
        '/' +
        e12 +
        '/' +
        e13 +
        '/' +
        e14 +
        '/' +
        e15 +
        '/' +
        date
      }`
    );
  }

  get_statistic_graph_mois(
    mois: string,
    e2: string,
    e3: string,
    e4: string,
    e5: string,
    e6: string,
    e7: string,
    e8: string,
    e9: string,
    e10: string,
    e11: string,
    e12: string,
    date: number
  ): Observable<string[]> {
    return this.httpClient.get<string[]>(
      `${
        this.baseUrl +
        '/statistic_graph_monsuel/' +
        mois +
        '/' +
        e2 +
        '/' +
        e3 +
        '/' +
        e4 +
        '/' +
        e5 +
        '/' +
        e6 +
        '/' +
        e7 +
        '/' +
        e8 +
        '/' +
        e9 +
        '/' +
        e10 +
        '/' +
        e11 +
        '/' +
        e12 +
        '/' +
        date
      }`
    );
  }

  get_budget_annuelle_object(date: number): Observable<Budget> {
    return this.httpClient.get<Budget>(
      `${this.baseUrl + '/BudgetAnnuelle_object/' + date}`
    );
  }

  get_budget_comsommer(date: number): Observable<number> {
    return this.httpClient.get<number>(
      `${this.baseUrl + '/budget_consommer/' + date}`
    );
  }

  find_all_montant_par_labo(): Observable<Montant_par_labo[]> {
    return this.httpClient.get<Montant_par_labo[]>(
      `${this.baseUrl + '/getmontant_par_labo'}`
    );
  }
  find_montant_par_labo_par_year(year: number): Observable<Montant_par_labo[]> {
    return this.httpClient.get<Montant_par_labo[]>(
      `${this.baseUrl + '/getmontant_par_labo_par_year/' + year}`
    );
  }

  findRapport(donneId: number): Observable<any> {
    return this.httpClient.get<any>(
      `${this.baseUrl + '/viewlastdoc/' + donneId}`
    );
  }
  users_rapport(): Observable<Object> {
    return this.httpClient.get(`${this.baseUrl + '/users_rapport'}`, {
      responseType: 'arraybuffer',
    });
  }

  users_sans_rapport(): Observable<Object> {
    return this.httpClient.get(`${this.baseUrl + '/users_sans_rapport'}`, {
      responseType: 'arraybuffer',
    });
  }

  liste_users(): Observable<Object> {
    return this.httpClient.get(`${this.baseUrl + '/liste_users'}`, {
      responseType: 'arraybuffer',
    });
  }

  generateLettreManifPDF(etat: String, id: number){
    return this.httpClient.get(this.baseUrl + '/manifestation/pdf/'+ etat + '/' + id, {responseType: 'blob'})
  }

  generateLettreMStagefPDF(etat: String, id: number){
    return this.httpClient.get(this.baseUrl + '/mission-stage/pdf/'+ etat + '/' + id, {responseType: 'blob'})
  }


}

