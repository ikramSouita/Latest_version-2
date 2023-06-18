import { DonneePro } from './donnee-pro.model';
import { MissionStage } from './mission-stage.model';

export class User {
  id: number;
  nom: string;
  prenom: string;
  email: string;
  password: string;
  telephone: string;
  donnePro : DonneePro;
  gender: string;
}
