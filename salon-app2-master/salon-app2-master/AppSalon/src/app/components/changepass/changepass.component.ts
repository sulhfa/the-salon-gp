import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AlertService } from '../../services/alert.service';
import { UserAPIService } from '../../services/user-data.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';

@Component({
  selector: 'app-changepass',
  templateUrl: './changepass.component.html',
  styleUrls: ['./changepass.component.css']
})
export class ChangepassComponent implements OnInit {

  registerForm: FormGroup;
  loading = false;
  submitted = false;
  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private userDataService: UserAPIService,
    private alertService: AlertService
  ) {
    // redirect to home if already logged in
    if (!this.userDataService.currUser) {
      this.router.navigate(['/']);
    }
  }
  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      user_id:this.userDataService.currUser.user_id,
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

   // convenience getter for easy access to form fields
   get f() { return this.registerForm.controls; }

   onSubmit() {
     this.submitted = true;
 
     // stop here if form is invalid
     if (this.registerForm.invalid) {
       return;
     }
 
     this.loading = true;
     this.userDataService.changePassword(this.registerForm.value)
       .pipe(first())
       .subscribe(
         data => {
           this.alertService.success('Update successful', true);
           this.router.navigate(['/']);
         },
         error => {
           this.alertService.error(error);
           this.loading = false;
         });
   }
}
