import { Component, OnInit } from '@angular/core';
import { UserAPIService } from 'src/app/services/user-data.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  constructor(
    private router: Router,
    private userDataService: UserAPIService) {
  }


  isLogin(): boolean {
    return this.userDataService.isLogin();
  }
  isAdmin(): boolean {
    return this.userDataService.isAdmin();
  }
  isOwner(): boolean {
    return this.userDataService.isOwner();
  }
  isClient(): boolean {
    return this.userDataService.isClient();
  }

  ngOnInit() {

  }

}
