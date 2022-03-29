import { HttpClient } from '@angular/common/http';
import { isNull } from '@angular/compiler/src/output/output_ast';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/model/User';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {
  public isLoggedIn = true;
  public isAdmin = true;
  public user!: User;
  // public username = ""
  // public password = ""

  constructor(private loginService: LoginService, private http: HttpClient) { }
  
  ngOnInit(): void {
    this.http.get<any>('api/user/loggedInUser/').subscribe(
      response => {
        if(response == null){
          this.isLoggedIn = false, this.isAdmin = false;
          return
        }
        this.user = response;
        if(this.user.role == "ROLE_ADMIN") this.isAdmin = true;
        //console.log(response)
      }
    );
  }

  yo(){
    // this.loginService.getLoggedUser().subscribe(data => {
    //   console.log(data)
    // })
    // this.http.get("http://localhost:8080/auth/loggedUser").subscribe(data => {
    //   console.log(data)
    // })
    //console.log(this.loginService.getLoggedUser())
    //console.log(this.loggedInUser$)
    this.http.get<any>('api/user/getAll/').subscribe(
      response => {
        console.log(response)
      }
    );

    
  }
}
