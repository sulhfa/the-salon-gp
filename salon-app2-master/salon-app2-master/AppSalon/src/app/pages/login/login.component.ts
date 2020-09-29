import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AlertService } from '../../services/alert.service';
import { UserAPIService } from '../../services/user-data.service';
import { IUser } from '../../dto/User';
import { first } from 'rxjs/operators';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['../signup/signup.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  loading = false;
  submitted = false;
  returnUrl: string;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private alertService: AlertService,
    private userDataService: UserAPIService) {
    // redirect to home if already logged in
    if (this.userDataService.currUser) {
      this.router.navigate(['/']);
    }
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: [null, Validators.compose([
        Validators.required,
        Validators.email,
        Validators.minLength(6),
        Validators.maxLength(30)])],
      password: [null, Validators.compose([
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(30)])],
    });

    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }
  // convenience getter for easy access to form fields
  get f() { return this.loginForm.controls; }

  onSubmit() {
      this.submitted = true;

      // stop here if form is invalid
      if (this.loginForm.invalid) {
          return;
      }
      this.loading = true;
      this.userDataService.login(this.loginForm.value)
          .pipe(first())
          .subscribe(
              data => {
                  //console.log(data);
                  if(data.user_id>0 && data.status != 0){
                    this.alertService.success('Login successful', true);
                    this.router.navigate([this.returnUrl]);
                  }
                  else if(data.user_id>0 && data.status == 0){
                    this.alertService.error("Account not active, wait until it activiated.");
                  }
                  else if(data.user_id==-1){
                    this.alertService.error("Login failed (Wrong input).");
                  }
                  else if(data.user_id==-2){
                    this.alertService.error("Login failed (Wrong password/Username).");
                  }
                  else if(data.user_id==-3){
                    this.alertService.error("User data not found).");
                  }
                  this.loading = false;
              },
              error => {
                  this.alertService.error("Server connection failed!.");
                  this.loading = false;
              });
  }

}
