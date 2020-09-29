import { Component, OnInit } from '@angular/core';
import { AlertService } from 'src/app/services/alert.service';
import { UserAPIService } from 'src/app/services/user-data.service';
import { Router } from '@angular/router';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { first } from 'rxjs/operators';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  profileForm: FormGroup;
  passForm: FormGroup;
  pageTitle = 'Profile Data';
  errorMessage = '';
  loading1 = false;
  loading2 = false;
  submitted1 = false;
  submitted2 = false;

  constructor(
    private formBuilder: FormBuilder,
    private userDataService: UserAPIService,
    private router: Router,
    private alertService: AlertService) {
    // redirect to signin if not login
    if (!this.userDataService.isLogin()) {
      this.router.navigate(['/']);
    }
  }
  ngOnInit() {
    this.profileForm = this.formBuilder.group({
      salonname: this.userDataService.currUser ? this.userDataService.currUser.salonname : null,
      usertype: this.userDataService.currUser ? this.userDataService.currUser.usertype : null,
      fullname: [this.userDataService.currUser ? this.userDataService.currUser.fullname : null, Validators.required],
      username: this.userDataService.currUser ? this.userDataService.currUser.username : null,
      phoneN: new FormControl(null, [Validators.required]),
      phone: this.userDataService.currUser ? this.userDataService.currUser.phone : null,
      address: [this.userDataService.currUser ? this.userDataService.currUser.address : null, Validators.required],
      location: [this.userDataService.currUser ? this.userDataService.currUser.location : null, Validators.required],
    });
    this.passForm = this.formBuilder.group({
      username: this.userDataService.currUser ? this.userDataService.currUser.username : null,
      password: [null, Validators.compose([
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(30)])],
      newpassword: [null, Validators.compose([
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(30)])],
    });
    if (this.userDataService.currUser) {
      this.profileForm.controls.phoneN.setValue(this.userDataService.currUser.phone);
      if (this.userDataService.isOwner()) {
        this.profileForm.controls.salonname.setValidators([Validators.required]);
      }
    }
  }

  isOwner(): boolean {
    return this.userDataService.isOwner();
  }

  get f2() { return this.passForm.controls; }
  get f1() { return this.profileForm.controls; }

  onPassChange() {
    console.log("start update pass user ~!");
    this.submitted2 = true;
    // stop here if form is invalid
    if (this.passForm.invalid) {
      console.log("pass form not valid");
      return;
    }

    this.loading2 = true;

    this.userDataService.changePassword(this.passForm.value)
      .pipe(first())
      .subscribe(
        data => {
          if (data.update > 0) {
            this.alertService.success('Update successful', true);
            console.log("Update Password successful....");
            this.loading2 = false;
          }
          else {
            this.alertService.error("Update Password failed.");
            this.loading2 = false;
            console.log(data);
          }
        },
        error => {
          this.alertService.error("Unable to connect to server");
          this.loading2 = false;
          console.log("Update unsuccessful!");
        });

  }

  onSubmit() {
    console.log("start post user ~!");
    this.submitted1 = true;
    // stop here if form is invalid
    if (this.profileForm.invalid) {
      console.log("profile form not valid");
      for (const name in this.profileForm.controls) {
        if (this.profileForm.controls[name].invalid) {
          console.log(name);
        }
      }
      return;
    }

    this.loading1 = true;

    //fix phone number from complex array
    this.profileForm.controls.phone.setValue(
      this.profileForm.controls.phoneN.value['number']);

    this.userDataService.updateUser(this.profileForm.value)
      .pipe(first())
      .subscribe(
        data => {
          if (data.update > 0) {
            this.alertService.success('Update successful', true);
            this.userDataService.updateLocalUser(this.profileForm.value);
            console.log("Update successful....");
          }
          else {
            this.alertService.error("Update failed, Server error .");
            console.log(data);
          }
          this.loading1 = false;
        },
        error => {
          this.alertService.error("Unable to connect to server");
          this.loading1 = false;
          console.log("Update unsuccessful!");
        });
  }
}
