import { State } from '../enums/state.service';
import { Soutien } from './soutien.model';

export class Manifestation {
  id: number;
  titreManifestation: string;
  titreParticipation: string;
  pays: string;
  ville: string;
  dateDebut: string;
  dateFin: string;
  dateDepart: string;
  dateRetour: string;
  natureParticiaton: string;
  state: State;
  private soutien: Soutien;
  public get getsoutien(): Soutien {
    return this.soutien;
  }
  public set setsoutien(value: Soutien) {
    this.soutien = value;
  }
}
