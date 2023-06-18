import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PrimeNGConfig } from 'primeng/api';
import { ContactComponent } from '../contact/contact.component';
import { AllusersService } from '../../controller/service/allusers.service';
import { User } from '../../controller/model/user.model';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  menu: any;
  isLogged: boolean;

  constructor(private router: Router, private allusers: AllusersService) {
    router.events.subscribe((val) => {
      this.isLogged = Boolean(localStorage.getItem('isLogged'));
    });
  }

  user: User = new User();

  ngOnInit() {}

  home() {
    this.router.navigateByUrl('/contact');
  }

  logoutUser() {
    this.allusers.logoutUser().subscribe((data) => {
      this.router.navigate(['/login']);
      localStorage.removeItem('isLogged');
    });
  }

  goToLogin() {
    this.router.navigate(['/login']);
  }


  onLogin():boolean{
    this.allusers.loginUser(this.user).subscribe((x: any) => {
      if(x===1){

      }else{

      }
    })
    return true;
  }
}
