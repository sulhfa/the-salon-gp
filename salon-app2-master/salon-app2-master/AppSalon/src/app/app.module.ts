import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BsDropdownModule} from 'ngx-bootstrap/dropdown';
import { NgxIntlTelInputModule } from 'ngx-intl-tel-input';
import { FileUploadModule } from 'ng2-file-upload';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import {MatDialogModule} from '@angular/material/dialog';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './pages/login/login.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { SignupComponent } from './pages/signup/signup.component';
import { ChangepassComponent } from './components/changepass/changepass.component';
import { LogoutComponent } from './components/logout/logout.component';

import { AlertComponent } from './components/alert/alert.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FooterComponent } from './components/footer/footer.component';
import { ManageComponent } from './pages/manage/manage.component';
import { ServicesComponent } from './pages/services/services.component';
import { ExploreComponent } from './pages/explore/explore.component';
import { OrdersComponent,DialogContent,DialogRate } from './pages/orders/orders.component';
import { HistoryComponent } from './pages/history/history.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { HeaderComponent } from './components/header/header.component';
import { SliderComponent } from './components/slider/slider.component';
import { PreviewComponent } from './components/preview/preview.component';
import { ServiceComponent } from './pages/service/service.component';
import { DetailsComponent } from './pages/details/details.component';
import { OrderlistComponent } from './pages/orderlist/orderlist.component';
import { PlaceorderComponent } from './pages/placeorder/placeorder.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    DashboardComponent,
    SignupComponent,
    ChangepassComponent,
    LogoutComponent,
    AlertComponent,
    FooterComponent,
    ManageComponent,
    ServicesComponent,
    ExploreComponent,
    OrdersComponent,
    HistoryComponent,
    ProfileComponent,
    HeaderComponent,
    SliderComponent,
    PreviewComponent,
    ServiceComponent,
    DetailsComponent,
    OrderlistComponent,
    PlaceorderComponent,
    DialogContent,DialogRate,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    FileUploadModule,
    ReactiveFormsModule,
    BsDropdownModule.forRoot(),
    NgxIntlTelInputModule,
    BrowserAnimationsModule,
    FontAwesomeModule,
    [MatDialogModule]
  ],
  providers: [
    DatePipe,
  ],
  bootstrap: [AppComponent],
  entryComponents: [DialogContent, DialogRate]
})
export class AppModule {
  baseUrl: string = "http://localhost:8084/RestSalon/web";
}
