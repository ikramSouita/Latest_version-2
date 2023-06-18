import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { InformationSurDemandeurComponent } from './demandeur/postuler/information-sur-demandeur/information-sur-demandeur.component';
import { LoginComponent } from './demandeur/postuler/login/login.component';
import { HomeComponent } from './demandeur/home/home.component';
import { FooterComponent } from './demandeur/footer/footer.component';
import { PostulerManifestationComponent } from './demandeur/postuler/postuler-manifestation/postuler-manifestation.component';
import { ChoisirPostulerComponent } from './demandeur/choisir-postuler/choisir-postuler.component';
import { ContactComponent } from './demandeur/contact/contact.component';
import { PostulerMissionComponent } from './demandeur/postuler/postuler-mission/postuler-mission.component';
import { HeaderComponent } from './demandeur/header/header.component';
import { RegisterComponent } from './demandeur/postuler/register/register.component';
import { HistoriqueComponent } from './demandeur/historique/historique.component';
import { AdminComponent } from './admin/admin.component';
import { AuthentificationComponent } from './admin/authentification/authentification.component';
import { DashboardComponent } from './admin/dashboard/dashboard.component';
import { DemandeursComponent } from './admin/demandeurs/demandeurs.component';

import { MessageComponent } from './admin/message/message.component';
import { DemandesComponent } from './admin/demandes/demandes.component';
import { DemandeurComponent } from './demandeur/demandeur.component';
import { DetailDemandeComponent } from './admin/detail-demande/detail-demande.component';
import { DetailDemandeurComponent } from './admin/detail-demandeur/detail-demandeur.component';
import { DemandeManifComponent } from './admin/demande-manif/demande-manif.component';
import { DetailManifComponent } from './admin/detail-manif/detail-manif.component';
import { AuthGuard } from './controller/service/authGuard/auth-guard.service';
import { AuthGuardUser } from './controller/service/authGuard/auth-guarduser.service';
import {BudgetComponent} from "./admin/budget/budget.component";

const routes: Routes = [
  {
    path: '',
    component: DemandeurComponent,
    children: [
      { path: 'header', component: HeaderComponent },
      { path: 'register', component: RegisterComponent },
      { path: 'login', component: LoginComponent },
      { path: 'home', component: HomeComponent },
      { path: '', component: HomeComponent },
      { path: 'footer', component: FooterComponent },
      { path: 'contact', component: ContactComponent },
      {
        path: 'postuler-manif',
        component: PostulerManifestationComponent,
        canActivate: [AuthGuardUser],
      },
      {
        path: 'postuler-mission',
        component: PostulerMissionComponent,
        canActivate: [AuthGuardUser],
      },
      { path: 'choisir-postuler', component: ChoisirPostulerComponent },
      {
        path: 'information-dem',
        component: InformationSurDemandeurComponent,
        canActivate: [AuthGuardUser],
      },
      {
        path: 'historique',
        component: HistoriqueComponent,
        canActivate: [AuthGuardUser],
      },
    ],
  },
  { path: 'admin', component: AuthentificationComponent },

  {
    path: 'admin',
    component: AdminComponent,

    children: [
      {
        path: 'dashboard',
        component: DashboardComponent,
        canActivate: [AuthGuard],
      },

      {
        path: 'demandeurs',
        component: DemandeursComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'demandes-Stage',
        component: DemandesComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'budget',
        component: BudgetComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'message',
        component: MessageComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'demandes-Manif',
        component: DemandeManifComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'detail-demandeur/:id',
        component: DetailDemandeurComponent,
        canActivate: [AuthGuard],
      },
    ],
  },
  {
    path: 'detail-demande-Stage/:id',
    component: DetailDemandeComponent,
    canActivate: [AuthGuard],
  },

  {
    path: 'detail-demande-Manif/:id',
    component: DetailManifComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'historique',
    component: HistoriqueComponent,
    canActivate: [AuthGuardUser],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
