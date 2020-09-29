import { Component, OnInit } from '@angular/core';
import { UserAPIService } from 'src/app/services/user-data.service';
import { Router } from '@angular/router';
import { IService } from 'src/app/dto/Service';
import { Location } from '@angular/common';
import { IUser } from 'src/app/dto/User';
import { first } from 'rxjs/operators';
import { AlertService } from 'src/app/services/alert.service';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class DetailsComponent implements OnInit {
  currService : IService =null;
  currDetails : any=null;
  pageTitle = '*';
  pCount = 0;
  constructor(
    private userDataService: UserAPIService,
    private router: Router,
    private alertService: AlertService,
    private location: Location) { 
      if (!this.userDataService.isClient()) {
        this.router.navigate(['/']);
      }
    }

    
  ngOnInit() {
    this.currService =<IService>this.location.getState()["service"];
    //console.log(this.currService);
    if (this.currService) {
      this.pageTitle = "Service Details";//this.currService.title;
      this.pCount = this.currService.pcount;

      this.currDetails = <IUser>{user_id:this.currService.ownerId};
      this.userDataService.getUserInfo(this.currDetails)
      .pipe(first())
      .subscribe(
          data => {
              //console.log(data);
              if(data.user_id>0 && data.status != 0){
                this.currDetails = data;
              }
              else{
                this.router.navigate(['/explore']);
                this.alertService.error("User data not found).");
              }
          },
          error => {
          });
    } else {
      this.router.navigate(['/explore']);
    }
  }
  
  get fImgList2() {
    return (<IService>this.currService).imagelist;
  }
  onOrder(){
    this.router.navigateByUrl('/placeorder', { state: { service:this.currService } });
  }
  get f(){
    return this.currService;
  }
  get g(){
    return this.currDetails;
  }

  get rootFolder(){
    return this.userDataService.UPLOAD_DIR;
  }

}
