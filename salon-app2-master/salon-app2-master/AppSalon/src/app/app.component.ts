import { Component } from '@angular/core';
import { IUser } from './dto/User';
import { Router } from '@angular/router';
import { UserAPIService } from './services/user-data.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Salon App';
  currentUser: IUser;
  constructor(
    private router:Router,
    private UserDataService: UserAPIService
  ){
    this.UserDataService.currentUser.subscribe(x=>this.currentUser=x);
  }
  logout(){
    this.UserDataService.logout();
    this.router.navigate(['\login']);
  }
}
