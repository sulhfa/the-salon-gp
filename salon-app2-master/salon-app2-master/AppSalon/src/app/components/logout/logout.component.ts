import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { UserAPIService } from '../../services/user-data.service';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userDataService: UserAPIService) {
    // redirect to home if already logged in
   
  }

  ngOnInit() {
     if (this.userDataService.currUser) {
      this.userDataService.logout();
      this.router.navigate(['/']);
    }
  }

}
