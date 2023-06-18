import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { User } from 'src/app/controller/model/user.model';
import { AdminService } from 'src/app/controller/service/admin.service';
import Swal from 'sweetalert2';

export interface PeriodicElement {
  name: string;
  position: number;
  weight: number;
  symbol: string;
}

const ELEMENT_DATA: PeriodicElement[] = [
  { position: 1, name: 'Hydrogen', weight: 1.0079, symbol: 'H' },
  { position: 2, name: 'Helium', weight: 4.0026, symbol: 'He' },
  { position: 3, name: 'Lithium', weight: 6.941, symbol: 'Li' },
  { position: 4, name: 'Beryllium', weight: 9.0122, symbol: 'Be' },
  { position: 5, name: 'Boron', weight: 10.811, symbol: 'B' },
  { position: 6, name: 'Carbon', weight: 12.0107, symbol: 'C' },
  { position: 7, name: 'Nitrogen', weight: 14.0067, symbol: 'N' },
  { position: 8, name: 'Oxygen', weight: 15.9994, symbol: 'O' },
  { position: 9, name: 'Fluorine', weight: 18.9984, symbol: 'F' },
  { position: 10, name: 'Neon', weight: 20.1797, symbol: 'Ne' },
];
@Component({
  selector: 'app-demandeurs',
  templateUrl: './demandeurs.component.html',
  styleUrls: ['./demandeurs.component.css'],
})
export class DemandeursComponent implements OnInit {
  file: any;
  fileUrl: any;
  users: User[];
  users_rap: User[];
  users_sans_rap: User[];

  constructor(private adminService: AdminService, private router: Router) {}

  displayedColumns: string[] = ['position', 'name', 'weight', 'symbol'];
  dataSource = new MatTableDataSource(ELEMENT_DATA);

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  ngOnInit(): void {
    this.getUsers();
    this.getUsers_rap();
    this.getUsers_sans_rap();
  }

  private getUsers() {
    this.adminService.findUsers().subscribe((data) => {
      this.users = data;
    });
  }
  private getUsers_rap() {
    this.adminService.findUsers_rap().subscribe((data) => {
      this.users_rap = data;
    });
  }
  private getUsers_sans_rap() {
    this.adminService.findUsers_sans_rap().subscribe((data) => {
      this.users_sans_rap = data;
    });
  }
  viewUserDetails(id: number) {
    this.router.navigate(['/admin/detail-demandeur', id]);
  }

  liste_demandeur() {
    this.adminService.liste_users().subscribe((data: string) => {
      this.file = new Blob([data], { type: 'application/pdf' });
      this.fileUrl = URL.createObjectURL(this.file);
      window.open(this.fileUrl);
    });
  }

  liste_demandeur_rapporst() {
    this.adminService.users_rapport().subscribe((data: string) => {
      this.file = new Blob([data], { type: 'application/pdf' });
      this.fileUrl = URL.createObjectURL(this.file);
      window.open(this.fileUrl);
    });
  }

  liste_demandeur_sans_rapporst() {
    this.adminService.users_sans_rapport().subscribe((data: string) => {
      this.file = new Blob([data], { type: 'application/pdf' });
      this.fileUrl = URL.createObjectURL(this.file);
      window.open(this.fileUrl);
    });
  }
}
