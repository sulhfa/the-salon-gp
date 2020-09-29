import { Component, OnInit, Inject } from '@angular/core';
import { IOrder } from 'src/app/dto/Order';
import { UserAPIService } from 'src/app/services/user-data.service';
import { Router, ActivatedRoute } from '@angular/router';
import { AlertService } from 'src/app/services/alert.service';
import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

export interface DialogUserData {
  phone: string;
  fullname: string;
  title: string;
}
export interface DialogRateData {
  comment: string;
  rate: number;
}

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {
  orders: IOrder[] = [];
  pageTitle = 'Manage Orders';
  errorMessage = '';
  user: any = {};
  serv: any = {};
  revi:any = {};
  loading = false;
  id: number;
  private sub: any;

  constructor(private userDataService: UserAPIService,
    private router: Router,
    private route: ActivatedRoute,
    private alertService: AlertService,
    private library: FaIconLibrary,
    public dialog: MatDialog) {
    // redirect to home if not owner
    if (!this.userDataService.isLogin()) {
      this.router.navigate(['/']);
    }
  }


  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      this.id = +params['id'];
    });

    if (!this.id) {
      if (!this.userDataService.isLogin()) {
        this.router.navigate(['/']);
      }
    } else if (this.isClient()) {
      if (this.id == 1) {
        this.pageTitle = 'Manage Orders';
      } else if (this.id == 2) {
        this.pageTitle = 'Orders History';
      } else {
        this.router.navigate(['/']); //invalid direction
      }
    } else if (this.isOwner()) {
      if (this.id == 3) {
        this.pageTitle = 'Order List';
      } else if (this.id == 4) {
        this.pageTitle = 'Order History';
      } else {
        this.router.navigate(['/']); //invalid direction
      }
    }
    this.UpdateList();
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

  isOwner(): boolean {
    return this.userDataService.isOwner();
  }
  isAdmin(): boolean {
    return this.userDataService.isAdmin();
  }
  isClient(): boolean {
    return this.userDataService.isClient();
  }
  private UpdateList() {
    if (this.isClient()) {
      if (this.id == 1) {
        this.userDataService.getAllActiveOrderByCustomers(this.userDataService.currUser).subscribe((data) => {
          this.orders = data;
        });
      } else if (this.id == 2) {
        this.userDataService.getAllInactiveOrderByCustomers(this.userDataService.currUser).subscribe((data) => {
          this.orders = data;
        });
      }
    }
    else if (this.isOwner()) {
      if (this.id == 3) {
        this.userDataService.getAllActiveOrderByOnwer(this.userDataService.currUser).subscribe((data) => {
          this.orders = data;
        });
      } else if (this.id == 4) {
        this.userDataService.getAllIInactiveOrderByOnwer(this.userDataService.currUser).subscribe((data) => {
          this.orders = data;
        });
      }
    }
    else if (this.isAdmin()) {
      if (this.id == 5) {
        this.userDataService.getAllReportedOrder().subscribe((data) => {
          this.orders = data;
        });
      }
    }
  }

  viewOnwerInfo(id: number) {
    this.loading = true;
    this.serv = { serviceId: id };

    //console.log(order);
    this.userDataService.getUserInfoBySerivce(this.serv).subscribe(
      data => {
        if (data.user_id > 0) {
          //window.alert("Onwer Phone : "+ data.phone );
          this.dialog.open(DialogContent, {
            data: {
              title: "Owner Contact",
              phone: data.phone,
              fullname: data.fullname,
            }
          });
        }
        else {
          this.alertService.error("Update failed, Backend error .");
        }
        this.loading = false;
      },
      error => {
        this.alertService.error("Unable to connect to server");
        this.loading = false;
      });
  }

  viewInfo(custId: number) {
    this.loading = true;
    this.user = { user_id: custId };

    //console.log(order);
    this.userDataService.getUserInfo(this.user).subscribe(
      data => {
        if (data.user_id > 0) {
          //window.alert("Customer Phone : "+ data.phone );
          this.dialog.open(DialogContent, {
            data: {
              title: "Client Contact",
              phone: data.phone,
              fullname: data.fullname,
            }
          });
        }
        else {
          this.alertService.error("Update failed, Backend error .");
        }
        this.loading = false;
      },
      error => {
        this.alertService.error("Unable to connect to server");
        this.loading = false;
      });
  }

  srate: string= "3";
  comments: string;

  onComplete(order: IOrder, newstatus: number) {
    const dialogRef = this.dialog.open(DialogRate, {
      data: {
        name: this.userDataService.currUser.fullname,
        comments: this.comments,
        srate: this.srate,
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log(result);

      if (result) {
        //commit result;
        this.revi = {
          custId : order.user_id,
          serviceId : order.orderdetail[0].serviceId,
          orderId: order.id,
          srate: result.srate,
          comments: result.comments
        }
        console.log(this.revi)
        this.userDataService.addReivew(this.revi).subscribe(
          data => {
            if (data.update > 0) {
              this.alertService.success('Update successful', true);
              this.changeStatus(order,newstatus);
            }
            else {
              this.alertService.error("Update failed, Backend error .");
            }
            this.loading = false;
          },
          error => {
            this.alertService.error("Unable to connect to server");
            this.loading = false;
          });
      }
    });
  }

  changeStatus(order: IOrder, newstatus: number) {
    this.loading = true;
    order.status = newstatus;
    //console.log(order);
    this.userDataService.updateOrderStatus(order).subscribe(
      data => {
        if (data.update > 0) {
          this.alertService.success('Update successful', true);
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
      });

  }

  getStatus(status: number) {
    switch (status) {
      case 1:
        return "Pending";
      case 2:
        return "Accept By Owner";
      case 3:
        return "Rejected By Client";
      case 4:
        return "Rejected By Owner";
      case 5:
        return "Cancel By Admin";
      case 6:
        return "Completed";
      case 7:
        return "Report By Client";
      case 8:
        return "Report By Owner";
      case 9:
        return "Removed By Admin";
    }
  }

  getStarCounter(n: number) {
    return Array(n).fill(0).map((x, i) => i);
  }

}

@Component({
  selector: 'dialog-content',
  templateUrl: 'dialog-content.html',
})
export class DialogContent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: DialogUserData) { }
}

@Component({
  selector: 'dialog-rate',
  templateUrl: 'dialog-rate.html'
})
export class DialogRate {
  constructor(
    public dialogRef: MatDialogRef<DialogRate>,
    @Inject(MAT_DIALOG_DATA) public data: DialogRateData) { }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
