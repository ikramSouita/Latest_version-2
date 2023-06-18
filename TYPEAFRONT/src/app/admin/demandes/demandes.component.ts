import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { State } from 'src/app/controller/enums/state.service';
import { MissionStage } from 'src/app/controller/model/mission-stage.model';
import { AdminService } from 'src/app/controller/service/admin.service';
import { User } from '../../controller/model/user.model';

@Component({
  selector: 'app-demandes',
  templateUrl: './demandes.component.html',
  styleUrls: ['./demandes.component.css'],
})
export class DemandesComponent implements OnInit {
  mStages: MissionStage[];
  mStagesA: MissionStage[];
  mStagesR: MissionStage[];
  mStagesC: MissionStage[];
  user: User;

  constructor(private adminService: AdminService, private router: Router) {}

  ngOnInit(): void {
    this.getMStages();
    this.getMStagesA();
    this.getMStagesR();
    this.getMStagesC();
  }

  private getMStages() {
    this.adminService.findAllStagesByState(State.IDLE).subscribe((data) => {
      this.mStages = data;
    });
  }

  private getMStagesA() {
    this.adminService.findAllStagesByState(State.APPROVED).subscribe((data) => {
      this.mStagesA = data;
    });
  }
  private getMStagesR() {
    this.adminService.findAllStagesByState(State.REFUSED).subscribe((data) => {
      this.mStagesR = data;
    });
  }

  private getMStagesC() {
    this.adminService.findAllStagesByState(State.ANNULE).subscribe((data) => {
      this.mStagesC = data;
    });
  }

  viewDetails(id: number) {
    this.router.navigate(['/detail-demande-Stage', id]);
  }

  accepted() {}

  refused(id: number) {
    this.adminService.RefuseMStage(id).subscribe((data) => {});
  }
}
