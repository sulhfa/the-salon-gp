import { Component, OnInit } from '@angular/core';
import { UserAPIService } from 'src/app/services/user-data.service';
import { Router } from '@angular/router';
import { IUser } from 'src/app/dto/User';
import { AlertService } from 'src/app/services/alert.service';

@Component({
  selector: 'app-manage',
  templateUrl: './manage.component.html',
  styleUrls: ['./manage.component.css']
})
export class ManageComponent implements OnInit {
  users: IUser[] = [];
  filteredUser: IUser[] = [];
  _listFilter = '';
  pageTitle = 'Manage Users';
  errorMessage = '';
  loading = false;

  get listFilter(): string {
    return this._listFilter;
  }
  set listFilter(value: string) {
    this._listFilter = value;
    this.filteredUser = this.listFilter ? this.performFilter(this.listFilter) : this.users;
  }

  constructor(private userDataService: UserAPIService,
    private router: Router,
    private alertService: AlertService) {
    // redirect to home if not admin
    if (!this.userDataService.currUser || this.userDataService.currUser.usertype != 1) {
      this.router.navigate(['/']);
    }
  }

  changeStatus(user: IUser) {
    this.loading = true;
    this.userDataService.changeStatus(user).subscribe(
      data => {
        if (data.update > 0) {
          this.alertService.success('Update successful', true);
          console.log("Update successful....");
        }
        else {
          this.alertService.error("Update failed, Backend error .");
        }
        this.loading = false;
      },
      error => {
        this.alertService.error("Unable to connect to server");
        this.loading = false;
        console.log("Update unsuccessful!");
      });
  }

  performFilter(filterBy: string): IUser[] {
    filterBy = filterBy.toLocaleLowerCase();
    return this.users.filter((user: IUser) =>
      user.username.toLocaleLowerCase().indexOf(filterBy) !== -1);
  }

  ngOnInit() {
    this.userDataService.getAllUsers().subscribe((data) => {
      this.users = data;
      this.filteredUser = this.users;
    });
  }

}
