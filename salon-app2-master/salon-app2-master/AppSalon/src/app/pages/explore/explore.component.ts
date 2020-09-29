import { Component, OnInit } from '@angular/core';
import { IService, IServiceDetials } from 'src/app/dto/Service';
import { UserAPIService } from 'src/app/services/user-data.service';
import { Router } from '@angular/router';
import { AlertService } from 'src/app/services/alert.service';
import { FontAwesomeModule, FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { faStar ,faShoppingCart } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-explore',
  templateUrl: './explore.component.html',
  styleUrls: ['./explore.component.css']
})
export class ExploreComponent implements OnInit {

  service: IService[] = [];
  filteredServcie: IService[] = [];
  _listFilter = '';
  pageTitle = 'View Services';
  errorMessage = '';
  loading=false;
  get listFilter(): string {
    return this._listFilter;
  }
  set listFilter(value: string) {
    this._listFilter = value;
    this.filteredServcie = this.listFilter ? this.performFilter(this.listFilter) : this.service;
  }
///test
  constructor(private userDataService: UserAPIService,
    private router: Router, 
    private library: FaIconLibrary,
    private alertService: AlertService) {
    // redirect to home if not client
    if (!this.userDataService.isLogin()) {
      this.router.navigate(['/login']);
    }
    library.addIcons(faStar,faShoppingCart);
  }
  performFilter(filterBy: string): IService[] {
    filterBy = filterBy.toLocaleLowerCase();
    return  this.service
    .filter((serivce: IService) => (serivce.salonname.toLocaleLowerCase().indexOf(filterBy) !== -1)
    || (serivce.title.toLocaleLowerCase().indexOf(filterBy) !== -1));
  }
  ngOnInit() {
   this.userDataService.getAllServices().subscribe((data)=>{
      this.service = data;
      this.filteredServcie = this.service;
    });
  }
  getStarCounter(n: number){
    return Array(n).fill(0).map((x,i)=>i);
  }
  getImg(d: IServiceDetials[]) {
    return d[0].picturepath;
  }
  onOrder(srv:IService){
    //console.log(srv);
    this.router.navigateByUrl('/placeorder', { state: { service:srv } });
  }
  onDetail(srv:IService){
    //console.log(srv);
    this.router.navigateByUrl('/details', { state: { service:srv } });
  }

  get rootFolder(){
    return this.userDataService.UPLOAD_DIR;
  }
}
