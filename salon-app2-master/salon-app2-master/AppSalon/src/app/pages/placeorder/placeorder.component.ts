import { Component, OnInit } from '@angular/core';
import { IService } from 'src/app/dto/Service';
import { UserAPIService } from 'src/app/services/user-data.service';
import { Router } from '@angular/router';
import { AlertService } from 'src/app/services/alert.service';
import { IUser } from 'src/app/dto/User';
import { first } from 'rxjs/operators';
import { Location } from '@angular/common';
import { IOrder, IOrderDetials } from 'src/app/dto/Order';
import { FormGroup, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-placeorder',
  templateUrl: './placeorder.component.html',
  styleUrls: ['./placeorder.component.css']
})
export class PlaceorderComponent implements OnInit {
  currService: IService = null;
  currDetails: any = null;
  currOrder: any;
  currOrderDetails: IOrderDetials;
  errorMessage = '';
  orderForm: FormGroup;
  loading = false;
  submitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private userDataService: UserAPIService,
    private router: Router,
    private alertService: AlertService,
    private location: Location) {
    if (!this.userDataService.isClient()) {
      this.router.navigate(['/']);
    }
  }


  ngOnInit() {
    console.log(this.userDataService.currUser);
    this.currService = <IService>this.location.getState()["service"];
    if (this.currService) {
      this.orderForm = this.formBuilder.group({
        paymentType: ['cash'],
      });

      this.currDetails = <IUser>{ user_id: this.currService.ownerId };
      this.userDataService.getUserInfo(this.currDetails)
        .pipe(first())
        .subscribe(
          data => {
            //console.log(data);
            if (data.user_id > 0 && data.status != 0) {
              this.currDetails = data;
            }
            else {
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

  onSubmit() {
    console.log("start post user ~!");
    this.submitted = true;
    this.loading = true;
    this.currOrder={
      user_id: this.userDataService.currUser.user_id,
      totalcost: this.currService.cost,
      status: 1,
      paymentType: (this.orderForm.controls.paymentType.value==["cash"]?1:2),
      orderdetail: [{
        cost: this.currService.cost,
        serviceId: this.currService.id
      }]
    }
    console.log(this.currOrder);
    this.userDataService.addOrder(this.currOrder).subscribe(
      data => {
        if (data.update > 0) {
          this.alertService.success('Adding successful', true);
          console.log("Adding successful....");
          this.router.navigate(['/order/1']);
        }
        else {
          this.alertService.error("Adding failed, Backend error .");
        }
        this.loading = false;
      },
      error => {
        this.alertService.error("Unable to connect to server");
        this.loading = false;
        console.log("Place order unsuccessful!");
      });

  }
  get f() {
    return this.currService;
  }
  get c() {
    return this.userDataService.currUser;
  }
  get g(){
    return this.currDetails;
  }

}
