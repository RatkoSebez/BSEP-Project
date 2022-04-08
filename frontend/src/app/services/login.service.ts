import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../model/User';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient) { }

  // login(username: string, password: string){
  //   var postData = {
  //     username: username,
  //     password: password
  //   }
  //   this.http.post<any>("http://localhost:8080/login", postData).toPromise().then(data => {
  //     console.log(data);
  //   });
  // }

  getLoggedInUser(){
    return this.http.get<User>('api/user/loggedInUser/');
  }
}
