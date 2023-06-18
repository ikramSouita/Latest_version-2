import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/controller/model/user.model';
import { AllusersService } from 'src/app/controller/service/allusers.service';

@Component({
  selector: 'app-authentification',
  templateUrl: './authentification.component.html',
  styleUrls: ['./authentification.component.css'],
})
export class AuthentificationComponent implements OnInit {
  isLogged: boolean = false;
  isAdmin: boolean = false;
  user: User = new User();
  erreur: string;



  constructor(
    private alluserService: AllusersService,
    private router: Router
  ) {}

  ngOnInit(): void {}

  onSubmit() {
    this.alluserService.loginUser(this.user).subscribe((x: any) => {
      if (x == 1) {
        this.alluserService
          .isAdmin(this.user.email)
          .subscribe((data: boolean) => {
            this.isAdmin = data;
            if (this.isAdmin == true) {
              this.isLogged = true;
              this.setWithExpiry('key');
              localStorage.setItem('isLogged', `${this.isLogged}`);
              localStorage.setItem('isAdmin', `${this.isAdmin}`);
              this.router.navigate(['/admin/dashboard']);
            } else {
              this.erreur =
                'Vous n\'êtes pas un admin, veuillez se connecter sur l\'interface utilisateur';
            }
          });
      } else if (x == -3 || x == -1) {
        this.erreur = 'Email ou mot de passe est invalide';
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
