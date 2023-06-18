import {
  HttpClient,
  HttpEvent,
  HttpHandler,
  HttpRequest,
} from '@angular/common/http';
import { Component, Injectable, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable()
export class InterceptorComponent implements OnInit {
  constructor(http: HttpClient, private router: Router) {}

  ngOnInit(): void {}

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    if (this.getWithExpiry('key') != null) {
      this.setWithExpiry('key');
      return next.handle(request);
    } else {
      localStorage.removeItem('isLogged');
      this.router.navigate(['/login']);
      return next.handle(request);
    }
  }

  getWithExpiry(key) {
    const itemStr = localStorage.getItem(key);
    if (!itemStr) {
      return null;
    }
    const item = JSON.parse(itemStr);
    const now = new Date();
    // compare the expiry time of the item with the current time
    if (now.getTime() > item.expiry) {
      localStorage.removeItem(key);
      if (localStorage.getItem('isAdmin')) {
        localStorage.removeItem('isAdmin');
      }
      return null;
    }
    return item.value;
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
