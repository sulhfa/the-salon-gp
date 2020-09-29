import { Component, OnInit } from '@angular/core';
import { IService } from 'src/app/dto/Service';
import { UserAPIService } from 'src/app/services/user-data.service';
import { Router } from '@angular/router';
import { AlertService } from 'src/app/services/alert.service';
import { FontAwesomeModule, FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { faStar, faCheckCircle } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-services',
  templateUrl: './services.component.html',
  styleUrls: ['./services.component.css']
})

export class ServicesComponent implements OnInit {
  service: IService[] = [];
  filteredServcie: IService[] = [];
  _listFilter = '';
  pageTitle = 'Services';
  errorMessage = '';
  loading = false;

  get listFilter(): string {
    return this._listFilter;
  }
  set listFilter(value: string) {
    this._listFilter = value;
    this.filteredServcie = this.listFilter ? this.performFilter(this.listFilter) : this.service;
  }

  constructor(private userDataService: UserAPIService,
    private router: Router,
    private alertService: AlertService,
    private library: FaIconLibrary) {
    // redirect to home if not owner
    if (!this.isOwner() && !this.isAdmin()) {
      this.router.navigate(['/']);
    }
    library.addIcons(faStar, faCheckCircle);
  }

  private performFilter(filterBy: string): IService[] {
    filterBy = filterBy.toLocaleLowerCase();
    return this.service
      .filter((serivce: IService) => serivce.title.toLocaleLowerCase().indexOf(filterBy) !== -1)
      ;
  }

  ngOnInit() {
    if (this.isOwner()) {
      this.pageTitle = this.userDataService.currUser.salonname + ' Services';
    } else {
      this.pageTitle = "Manage Services";
    }
    this.UpdateList();
  }

  isOwner(): boolean {
    return this.userDataService.isOwner();
  }
  isAdmin(): boolean {
    return this.userDataService.isAdmin();
  }

  private UpdateList() {
    if (this.isOwner()) {
      this.userDataService.getAllServicesByOwner(this.userDataService.currUser).subscribe((data) => {
        this.service = data;
        this.filteredServcie = this.service;
      });
    } else if (this.isAdmin()) {

      this.userDataService.getAllManageServices().subscribe((data) => {
        this.service = data;
        this.filteredServcie = this.service;
      });
    }
  }

  onAdd() {
    this.router.navigateByUrl('/service', { state: { service: null } });
  }

  onEdit(srv: IService) {
    //console.log(srv);
    this.router.navigateByUrl('/service', { state: { service: srv } });
  }

  onDelete(srv: IService) {
    if (window.confirm('Are sure you want to delete this service ?')) {
      this.loading = true;
      this.userDataService.delService(srv).subscribe(
        data => {
          if (data.update != -1) {
            this.alertService.success('Delete successful', true);
            this.UpdateList();
          }
          else {
            this.alertService.error("Cannot Delete due to order related.");
          }
          this.loading = false;
        },
        error => {
          this.alertService.error("Unable to connect to server");
          this.loading = false;
          console.log("Delete unsuccessful!");
        });
    }
  }

  changeStatus(serivce: IService) {
    this.loading = true;
    this.userDataService.changeServiceStatus(serivce).subscribe(
      data => {
        if (data.update > 0) {
          this.alertService.success('Update successful', true);
          console.log("Update successful....");
          this.UpdateList();
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

  getStarCounter(n: number) {
    return Array(n).fill(0).map((x, i) => i);
  }
}
