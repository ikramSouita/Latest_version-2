import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/controller/model/user.model';
import { AllusersService } from 'src/app/controller/service/allusers.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  isLogged: boolean = false;
  erreur: string;

  user: User = new User();

  constructor(
    private allusersService: AllusersService,
    private router: Router
  ) {}
  ngOnInit(): void {}

  onSubmit() {
    this.allusersService.loginUser(this.user).subscribe((x: any) => {
      if (x == 1) {
        this.router.navigate(['/choisir-postuler']);
        this.isLogged = true;
        console.log(this.user.email);
        this.setWithExpiry('key');
        localStorage.setItem('isLogged', `${this.isLogged}`);
      } else if (x == -3 || x == -1) {
        this.erreur = 'Email ou mot de passe est invalide';
      } else if (x == -4) {
        this.erreur =
          'Veuillez confirmer votre compte en cliquant sur le lien dans votre email';
      } else {
        this.erreur = 'Email ou mot de passe est incorrect';
      }
    });
  }

  setWithExpiry(key) {
    const now = new Date();
    const item = {
      value: key,
      expiry: now.getTime() + 3550000,
    };
    localStorage.setItem(key, JSON.stringify(item));
  }
}
