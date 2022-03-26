import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

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

  getLoggedUser(){
     return this.http.get("http://localhost:8080/auth/loggedUser")
  }
}
