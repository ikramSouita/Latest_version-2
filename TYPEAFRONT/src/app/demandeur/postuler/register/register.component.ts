import { Component, OnInit } from '@angular/core';
import { User } from '../../../controller/model/user.model';
import { AllusersService } from '../../../controller/service/allusers.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  erreur: string;
  user: User = new User();

  constructor(
    private allusersService: AllusersService,
    private router: Router
  ) {}

  ngOnInit(): void {}

  addUser() {
    /*     if (document.getElementById('confpass').innerHTML === this.user.password) { */
    this.allusersService.registerUser(this.user).subscribe((data: number) => {
      if (data == 1) {
        Swal.fire({
          title: 'Création de compte',
          text: 'Ce compte a été crée avec succès. Veuillez confrimer votre compte en cliquant sur le lien envoyer sur votre email',
          icon: 'success',
          confirmButtonText: 'OK',
        }).then((result) => {
          if (result.isConfirmed) {
            this.router.navigate(['/login']);
          }
        });
      } else if (data == -1) {
        this.erreur = 'Ce mail existe déjà. Veuillez saisir un autre';
      } else if (data == -2) {
        this.erreur =
          'Ce mail doit être en format universitaire (example@uca.ma)';
      } else {
        this.erreur = 'Un ou plusieurs champs sont invalides, veuillez vérifier';
      }
    });
    /*     } else {
      this.erreur =
        'les champs mot de passe et confirmation mot de passe doivent etre identique';
    } */
  }
  onSubmit() {
    this.addUser();
  }
}
