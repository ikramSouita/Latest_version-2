import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AllusersService } from 'src/app/controller/service/allusers.service';
import { parseSelectorToR3Selector } from '@angular/compiler/src/core';

@Component({
  selector: 'app-headera',
  templateUrl: './headera.component.html',
  styleUrls: ['./headera.component.css'],
})
export class HeaderaComponent implements OnInit {
  numb : number;
  constructor(
    private router: Router,
    private allusersService: AllusersService
  ) {}

  ngOnInit(): void {}


  onLogoutAdmin() {
    this.allusersService.logoutUser().subscribe((data) => {
      this.router.navigate(['/admin']);
      localStorage.removeItem('isLogged');
      localStorage.removeItem('isAdmin');
    });
  }
}
