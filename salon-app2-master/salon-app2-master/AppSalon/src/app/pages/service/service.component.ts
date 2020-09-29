import { Component, OnInit } from '@angular/core';
import { UserAPIService } from 'src/app/services/user-data.service';
import { Router } from '@angular/router';
import { AlertService } from 'src/app/services/alert.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { IService } from 'src/app/dto/Service';
import { Location } from '@angular/common';
import { FileUploader } from 'ng2-file-upload';

@Component({
  selector: 'app-service',
  templateUrl: './service.component.html',
  styleUrls: ['./service.component.css']
})
export class ServiceComponent implements OnInit {
  currService : IService ;
  picForm: FormGroup;
  serviceForm: FormGroup;
  pageTitle = '*';
  pCount = 0;
  errorMessage = '';
  loading1 = false;
  loading2 = false;
  submitted1 = false;
  submitted2 = false;
  addNew = false;

  constructor(
    private formBuilder: FormBuilder,
    private userDataService: UserAPIService,
    private router: Router,
    private location: Location,
    private alertService: AlertService) {
    // redirect to home if not owner
    if (!this.userDataService.isOwner()) {
      this.router.navigate(['/']);
    }
  }
  

  public uploader: FileUploader = new FileUploader({ 
    url: this.userDataService.API_URL + 'upload/images', 
    itemAlias: 'file' 
  });

  ngOnInit() {
    this.currService =<IService>this.location.getState()["service"];
    if (this.currService) {
      this.pageTitle = "Edit " + this.currService.title;
      this.pCount = this.currService.pcount;
    } else {
      this.pageTitle = "* Add New Service";
      this.addNew = true;
    }
    //console.log(this.currService);
    this.serviceForm = this.formBuilder.group({
      id: this.addNew ? null : this.currService.id,
      ownerId: this.userDataService.currUser.user_id,
      title: [this.addNew ? null : this.currService.title, Validators.required],
      cost: [this.addNew ? null : this.currService.cost, Validators.required],
      details: [this.addNew ? null : this.currService.details, Validators.required],
    });
    //console.log(this.serviceForm.value);

    this.uploader.onAfterAddingFile = (file) => { 
      file.withCredentials = false; 
      console.log(file.file.rawFile);
     };
     this.uploader.onCompleteItem = (item: any, response: any, status: any, headers: any) => {
          console.log('FileUpload:uploaded successfully:', item, status, response);
          alert('Your file has been uploaded successfully');
     };
  }

  get fImgList() {
      return this.currService.imagelist;
  }
  processFile(imageInput: any) {
    const file: File = imageInput.files[0];
    const reader = new FileReader();

    /*reader.addEventListener('load', (event: any) => {
      //console.log(file);
      
    });

    reader.readAsDataURL(file);*/
  }

  get f2() { return this.picForm.controls; }
  get f1() { return this.serviceForm.controls; }

  onSubmit() {
    console.log("start post user ~!");
    this.submitted1 = true;
    // stop here if form is invalid
    if (this.serviceForm.invalid) {
      console.log("profile form not valid");
      for (const name in this.serviceForm.controls) {
        if (this.serviceForm.controls[name].invalid) {
          console.log(name);
        }
      }
      return;
    }

    this.loading1 = true;
    if (this.addNew) {
      this.userDataService.addService(this.serviceForm.value).subscribe(
        data => {
          if (data.update > 0) {
            this.alertService.success('Add successful', true);
          }
          else {
            this.alertService.error("Cannot Add, Backend Error");
          }
          this.loading1 = false;
        },
        error => {
          this.alertService.error("Unable to connect to server");
          this.loading1 = false;
          console.log("Added unsuccessful!");
        });
    } else {
      this.userDataService.updateService(this.serviceForm.value).subscribe(
        data => {
          if (data.update > 0) {
            this.alertService.success('Edit successful', true);
          }
          else {
            this.alertService.error("Cannot Edit, Backend Error");
          }
          this.loading1 = false;
        },
        error => {
          this.alertService.error("Unable to connect to server");
          this.loading1 = false;
          console.log("Added unsuccessful!");
        });
    }
  }

  get rootFolder(){
    return this.userDataService.UPLOAD_DIR;
  }

}
