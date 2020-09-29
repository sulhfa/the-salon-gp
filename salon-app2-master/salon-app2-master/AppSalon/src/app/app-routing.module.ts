import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { SignupComponent } from './pages/signup/signup.component';
import { ChangepassComponent } from './components/changepass/changepass.component';
import { LogoutComponent } from './components/logout/logout.component';
import { ManageComponent } from './pages/manage/manage.component';
import { ExploreComponent } from './pages/explore/explore.component';
import { ServicesComponent } from './pages/services/services.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { ServiceComponent } from './pages/service/service.component';
import { DetailsComponent } from './pages/details/details.component';
import { OrdersComponent } from './pages/orders/orders.component';
import { OrderlistComponent } from './pages/orderlist/orderlist.component';
import { PlaceorderComponent } from './pages/placeorder/placeorder.component';

const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'login', component: LoginComponent },
  { path: 'logout', component: LogoutComponent },
  { path: 'changepassword', component: ChangepassComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'manage', component: ManageComponent },
  { path: 'explore', component: ExploreComponent },
  { path: 'services', component: ServicesComponent },
  { path: 'service', component: ServiceComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'details', component: DetailsComponent },
  { path: 'order/:id', component: OrdersComponent },
  { path: 'orderlist', component: OrderlistComponent },
  { path: 'placeorder', component: PlaceorderComponent },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
