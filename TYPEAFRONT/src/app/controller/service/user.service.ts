import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cadre } from '../model/cadre.model';
import { Document } from '../model/document.model';
import { documents } from '../model/documents.model';
import { DonneePro } from '../model/donnee-pro.model';
import { Manifestation } from '../model/manifestation.model';
import { messages } from '../model/messages.model';
import { MissionStage } from '../model/mission-stage.model';
import { Soutien } from '../model/soutien.model';
import { User } from '../model/user.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private _baseUrl = environment.BASE_URL + '/user';

  constructor(private httpClient: HttpClient) {}

  getByMail(email: string): Observable<Object> {
    return this.httpClient.get(`${this._baseUrl + '/login/' + email}`);
  }

  addManif(manif: Manifestation): Observable<Object> {
    return this.httpClient.post(
      `${this._baseUrl + '/addmanifestation'}`,
      manif
    );
  }

  addMissionStage(mStage: MissionStage): Observable<Object> {
    return this.httpClient.post(`${this._baseUrl + '/addmission'}`, mStage);
  }

  saveDonnesPro(donne: DonneePro): Observable<Object> {
    return this.httpClient.post(`${this._baseUrl + '/adddonéespro'}`, donne, {
      withCredentials: true,
    });
  }

  addDonneProFile(donneId: number, doc: Document): Observable<Object> {
    const formData = new FormData();
    if (doc.file !== undefined) {
      formData.append('fichier', doc.file, doc.file.name);
    }
    return this.httpClient.post<Object>(
      `${this._baseUrl + '/add_documentD/' + donneId}`,
      formData
    );
  }

  updateDonnesPro(donne: DonneePro): Observable<Object> {
    return this.httpClient.put(`${this._baseUrl + '/updatedonnepro'}`, donne, {
      withCredentials: true,
    });
  }

  addSoutien(
    soutien: Soutien,
    isManif: Boolean,
    manifId: number,
    missionId: number
  ): Observable<Object> {
    if (isManif) {
      return this.httpClient.post(
        `${this._baseUrl + '/addsoutienmanif/' + manifId}`,
        soutien
      );
    } else {
      return this.httpClient.post(
        `${this._baseUrl + '/addsoutienmission/' + missionId}`,
        soutien
      );
    }
  }

  addCadre(cadre: Cadre, missionId: number): Observable<Object> {
    return this.httpClient.post(
      `${this._baseUrl + '/addcadre/' + missionId}`,
      cadre
    );
  }

  addDocuments(
    file: File,
    isManif: Boolean,
    manifId: number,
    missionId: number
  ): Observable<Object> {
    if (isManif) {
      return this.httpClient.post(
        `${this._baseUrl + '/add_document/manifestation/' + manifId}`,
        file
      );
    } else {
      return this.httpClient.post(
        `${this._baseUrl + '/add_document/missionstage/' + missionId}`,
        file
      );
    }
  }

  getMyMissionStages(): Observable<MissionStage[]> {
    return this.httpClient.get<MissionStage[]>(
      `${this._baseUrl + '/getmStage'}`,
      { withCredentials: true }
    );
  }

  getMyManifestations(): Observable<Manifestation[]> {
    return this.httpClient.get<Manifestation[]>(
      `${this._baseUrl + '/getmanifestations'}`,
      { withCredentials: true }
    );
  }

  addFiles(mStageId: number, documents: documents): Observable<Object> {
    const formData = new FormData();
    if (documents.filecin !== undefined) {
      formData.append('filecin', documents.filecin, documents.filecin.name);
    }
    if (documents.fileA !== undefined) {
      formData.append('fileA', documents.fileA, documents.fileA.name);
    }
    if (documents.fileB !== undefined) {
      formData.append('fileB', documents.fileB, documents.fileB.name);
    }
    if (documents.fileC !== undefined) {
      formData.append('fileC', documents.fileC, documents.fileC.name);
    }
    if (documents.fileD !== undefined) {
      formData.append('fileD', documents.fileD, documents.fileD.name);
    }
    if (documents.fileE !== undefined) {
      formData.append('fileE', documents.fileE, documents.fileE.name);
    }
    if (documents.fileF !== undefined) {
      formData.append('fileF', documents.fileF, documents.fileF.name);
    }
    return this.httpClient.post<Object>(
      `${this._baseUrl + '/add_documentMST/' + mStageId}`,
      formData
    );
  }

  addFilesManif(manifId: number, documents: documents): Observable<Object> {
    const formData = new FormData();
    if (documents.filecin !== undefined) {
      formData.append('filecin', documents.filecin, documents.filecin.name);
    }
    if (documents.fileA !== undefined) {
      formData.append('fileA', documents.fileA, documents.fileA.name);
    }
    if (documents.fileB !== undefined) {
      formData.append('fileB', documents.fileB, documents.fileB.name);
    }
    if (documents.fileC !== undefined) {
      formData.append('fileC', documents.fileC, documents.fileC.name);
    }
    if (documents.fileD !== undefined) {
      formData.append('fileD', documents.fileD, documents.fileD.name);
    }
    if (documents.fileE !== undefined) {
      formData.append('fileE', documents.fileE, documents.fileE.name);
    }
    if (documents.fileF !== undefined) {
      formData.append('fileF', documents.fileF, documents.fileF.name);
    }
    return this.httpClient.post(
      `${this._baseUrl + '/add_documentM/' + manifId}`,
      formData
    );
  }

  addAll(
    mStage: MissionStage,
    cadre: Cadre,
    soutien: Soutien
  ): Observable<Object> {
    mStage.setcadre = cadre;
    mStage.setsoutien = soutien;

    return this.httpClient.post(
      `${this._baseUrl + '/missionstageadd'}`,
      mStage,
      { withCredentials: true }
    );
  }

  addAllManif(manif: Manifestation, soutien: Soutien): Observable<Object> {
    manif.setsoutien = soutien;
    return this.httpClient.post(
      `${this._baseUrl + '/manifestationadd'}`,
      manif,
      { withCredentials: true }
    );
  }
  generateReport(id: number): Observable<any> {
    return this.httpClient.get(`${this._baseUrl + '/raport/' + id}`, {
      withCredentials: true,
      responseType: 'arraybuffer',
    });
  }
  exportReportMission(id: number): Observable<any> {
    return this.httpClient.get(`${this._baseUrl + '/raportmission/' + id}`, {
      withCredentials: true,
      responseType: 'arraybuffer',
    });
  }

  getThisUserId(): Observable<number> {
    return this.httpClient.get<number>(`${this._baseUrl + '/getthisuserid'}`, {
      withCredentials: true,
    });
  }

  deleteMStage(id: number): Observable<any> {
    return this.httpClient.delete(`${this._baseUrl + '/cancelmission/' + id}`, {
      withCredentials: true,
    });
  }

  deleteManif(id: number): Observable<any> {
    return this.httpClient.delete(`${this._baseUrl + '/cancelmanif/' + id}`, {
      withCredentials: true,
    });
  }
}
