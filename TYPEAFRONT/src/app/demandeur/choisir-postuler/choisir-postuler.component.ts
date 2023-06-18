import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-choisir-postuler',
  templateUrl: './choisir-postuler.component.html',
  styleUrls: ['./choisir-postuler.component.css'],
})
export class ChoisirPostulerComponent implements OnInit {
  isLogged: boolean = Boolean(localStorage.getItem('isLogged'));
  constructor() {}

  ngOnInit(): void {}
}
