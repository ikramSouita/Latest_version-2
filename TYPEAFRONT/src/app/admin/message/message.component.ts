import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { messages } from 'src/app/controller/model/messages.model';
import { AdminService } from 'src/app/controller/service/admin.service';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.css'],
})
export class MessageComponent implements OnInit {
  messages: messages[];

  constructor(private adminService: AdminService, private router: Router) {}

  ngOnInit(): void {
    this.getMessages();
  }

  private getMessages() {
    this.adminService.getAllMessages().subscribe((data) => {
      this.messages = data;
    });
  }
}
