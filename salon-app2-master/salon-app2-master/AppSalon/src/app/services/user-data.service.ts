import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { IUser, INewPassword } from '../dto/User';
import { ISecurityQuestion } from '../dto/SecurityQuestion';
import { map, catchError, tap } from 'rxjs/operators';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { IUpdate } from '../dto/update';
import { IService } from '../dto/Service';
import { IOrder, IOrderDetials } from '../dto/Order';
import { IReview } from '../dto/Review';


@Injectable({
  providedIn: 'root'
})
export class UserAPIService {
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'text/plain'
    })
  };
  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<IUser>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  private currentUserSubject: BehaviorSubject<IUser>;
  public currentUser: Observable<IUser>;

  public get currUser(): IUser {
    return this.currentUserSubject.value;
  }

  public updateLocalUser(sr:IUser){
    this.currentUserSubject.next({...this.currentUserSubject.value, 
    fullname: sr.fullname, 
    location: sr.location,
    address: sr.address,
    phone: sr.phone});
  }

  public get userType(): number {
    return this.currUser.usertype;
  }

  public UPLOAD_DIR: string = "../../..//assets/upload/";
  public API_URL : string = "http://localhost:8084/RestSalon/web/";

  public login(user: IUser): Observable<IUser> {
    return this.http.post<IUser>(this.API_URL + "accounts/login", user)
      .pipe(map(user => {
        if (user && user.user_id > 0) {
          localStorage.setItem('currentUser', JSON.stringify(user));
          //console.log(user);
          this.currentUserSubject.next(user);
        }
        return user;
      }));
  }

  public logout() {
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }

  public getUserInfo(user: IUser): Observable<IUser> {
    return this.http.post<(IUser)>(this.API_URL + "accounts/info", user);
  }

  public getUserInfoBySerivce(serv: IOrderDetials): Observable<IUser> {
    return this.http.post<(IUser)>(this.API_URL + "accounts/byservice", serv);
  }

  public changeStatus(user: IUser): Observable<IUpdate> {
    return this.http.post<(IUpdate)>(this.API_URL + "accounts/status", user);
  }

  public changeServiceStatus(service: IService): Observable<IUpdate> {
    return this.http.post<(IUpdate)>(this.API_URL + "services/status", service);
  }

  public changePassword(newPass: INewPassword) {
    return this.http.put<(IUpdate)>(this.API_URL + "accounts", newPass);
  }

  public registerUser(user: IUser): Observable<IUpdate> {
    return this.http.post<(IUpdate)>(this.API_URL + "accounts/add", user);
  }

  public updateUser(user: IUser): Observable<IUpdate> {
    return this.http.post<(IUpdate)>(this.API_URL + "accounts/update", user);
  }

  public getAllUsers() : Observable<IUser[]> {
    if (!this.currUser ||
      this.currUser.usertype != 1) {
      return null;
    }
    return this.http.get<IUser[]>(this.API_URL + "accounts/all")
      .pipe(catchError(this.handleError));
  }

  public getAllSecurityQuestion(): Observable<ISecurityQuestion[]> {
    return this.http.get<ISecurityQuestion[]>(this.API_URL + "helper/secQuestions")
      .pipe(catchError(this.handleError));
  }

  public getAllServices(): Observable<IService[]> {
    return this.http.get<IService[]>(this.API_URL + "services")
      .pipe(catchError(this.handleError));
  }

  public getAllManageServices(): Observable<IService[]> {
    return this.http.get<IService[]>(this.API_URL + "services/manage")
    .pipe(catchError(this.handleError));
  }
  public getAllServicesByOwner(user: IUser): Observable<IService[]> {
    return this.http.post<IService[]>(this.API_URL + "services/byowner", user)
    .pipe(catchError(this.handleError));
  }
  public delService(service: IService): Observable<IUpdate> {
    return this.http.post<(IUpdate)>(this.API_URL + "services/del", service)
    .pipe(catchError(this.handleError));
  }
  public addService(service: IService): Observable<IUpdate> {
    return this.http.post<(IUpdate)>(this.API_URL + "services/add", service)
    .pipe(catchError(this.handleError));
  }
  public updateService(service: IService): Observable<IUpdate> {
    return this.http.post<(IUpdate)>(this.API_URL + "services/update", service)
    .pipe(catchError(this.handleError));
  }

  public getAllActiveOrderByCustomers(user: IUser): Observable<IOrder[]> {
    return this.http.post<IOrder[]>(this.API_URL + "orders/all", user)
    .pipe(catchError(this.handleError));
  }
  public getAllInactiveOrderByCustomers(user: IUser): Observable<IOrder[]> {
    return this.http.post<IOrder[]>(this.API_URL + "orders/history", user)
    .pipe(catchError(this.handleError));
  }
  public addOrder(order: IOrder): Observable<IUpdate> {
    return this.http.post<(IUpdate)>(this.API_URL + "orders/new", order)
    .pipe(catchError(this.handleError));
  }
  public updateOrderStatus(order: IOrder): Observable<IUpdate> {
    return this.http.post<(IUpdate)>(this.API_URL + "orders/status", order)
    .pipe(catchError(this.handleError));
  }

  public addReivew(review: IReview): Observable<IUpdate> {
    return this.http.post<(IUpdate)>(this.API_URL + "orders/review", review)
    .pipe(catchError(this.handleError));
  }

  public getAllActiveOrderByOnwer(user: IUser): Observable<IOrder[]> {
    return this.http.post<IOrder[]>(this.API_URL + "orders/activeowner", user)
    .pipe(catchError(this.handleError));
  }

  public getAllIInactiveOrderByOnwer(user: IUser): Observable<IOrder[]> {
    return this.http.post<IOrder[]>(this.API_URL + "orders/inactiveowner", user)
    .pipe(catchError(this.handleError));
  }

  public getAllReportedOrder(): Observable<IOrder[]> {
    return this.http.get<IOrder[]>(this.API_URL + "orders/reported")
    .pipe(catchError(this.handleError));
  }

  private handleError(err: HttpErrorResponse) {
    let errorMessage = '';
    if (err.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      errorMessage = `An error occurred: ${err.error.message}`;
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      errorMessage = `Server returned code: ${err.status}, error message is: ${err.message}`;
    }
    console.error(errorMessage);
    return throwError(errorMessage);
  }

  public isLogin(): boolean {
    return this.currUser != null;
  }
  public isAdmin(): boolean {
    return (this.currUser
      && this.currUser.usertype == 1);
  }
  public isOwner(): boolean {
    return (this.currUser
      && this.currUser.usertype == 2);
  }
  public isClient(): boolean {
    return (this.currUser
      && this.currUser.usertype == 3);
  }
  public getUserType(): Number{
    return this.currUser ? this.currUser.usertype  :-1;
  }

  public uploadImage(image: File): Observable<Response> {
    const formData = new FormData();
    formData.append('image', image);
    return this.http.post<(any)>('upload/images', formData);
  }
}
