import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserAPIService } from '../../services/user-data.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {

  constructor(
    private router: Router,
    private userDataService: UserAPIService) {
  }

  Logout(){
    this.router.navigate(['logout']);
  }
  isAdmin(): boolean {
    return  (this.userDataService.currUser 
      && this.userDataService.currUser.usertype==1);
  }
  isOwner():boolean{
    return  (this.userDataService.currUser 
      && this.userDataService.currUser.usertype==2);
  }
  isClient():boolean{
    return  (this.userDataService.currUser 
      && this.userDataService.currUser.usertype==3);
  }

  ngOnInit() {
  }
}
