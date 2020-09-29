import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AlertService } from '../../services/alert.service';
import { UserAPIService } from '../../services/user-data.service';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
import { ISecurityQuestion } from 'src/app/dto/SecurityQuestion';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  questions: ISecurityQuestion[] = [];
  registerForm: FormGroup;
  loading = false;
  submitted = false;
  ownerForm = false;
  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private userDataService: UserAPIService,
    private alertService: AlertService
  ) {
    // redirect to home if already logged in
    if (this.userDataService.currUser) {
      this.router.navigate(['/']);
    }
  }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      fullname: [null, Validators.required],
      username: [null, Validators.compose([
        Validators.required,
        Validators.email,
        Validators.minLength(6),
        Validators.maxLength(30)])],
      /*remail: [null, Validators.compose([
        Validators.required,
        Validators.email
      ])],*/
      password: [null, Validators.compose([
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(30)])],
      //rpassword: [null, Validators.compose([Validators.required])],
      usertype: 3,
      salonname: null,
      phone: null,
      phoneN: new FormControl(null, [Validators.required]),
      security_question: [null, Validators.required],
      security_answer: [null, Validators.compose([
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(30)
      ])]
    });
    this.userDataService.getAllSecurityQuestion().subscribe((data)=>{
      this.questions = data;
    });
  }

  // convenience getter for easy access to form fields
  get f() { return this.registerForm.controls; }

  setUsertype(ut: number) {
    this.f.usertype.setValue(ut);
    this.ownerForm = ut==2;
    if(this.ownerForm){
      this.registerForm.controls.salonname.setValidators([Validators.required]);
    }else{
      this.registerForm.controls.salonname.setValidators(null);
    }
    console.log("set usertype = " + this.f.usertype.value);
  }

  onSubmit() {

    console.log("start post user ~!");
    this.submitted = true;
    //console.log(this.registerForm.controls.phoneN.value['number']);
    // stop here if form is invalid
    if (this.registerForm.invalid) {
      console.log("signup form not valid");
      for (const name in this.registerForm.controls) {
        if (this.registerForm.controls[name].invalid) {
          console.log(name);
        }
      }
      return;
    }

    this.loading = true;
   
    //fix phone number from complex array
    this.registerForm.controls.phone.setValue(
      this.registerForm.controls.phoneN.value['number']);

    this.userDataService.registerUser(this.registerForm.value)
      .pipe(first())
      .subscribe(
        data => {
          if(data.update>0){
            this.alertService.success('Registration successful', true);
            this.router.navigate(['/dashboard']);
            console.log("Registration successful....");
          }
          else{
            this.alertService.error("Registration failed, Server error .");
            this.loading = false;
            console.log(data);
          }
        },
        error => {
          this.alertService.error("Unable to connect to server");
          this.loading = false;
          console.log("Registration unsuccessful!");
        });
  }
}
