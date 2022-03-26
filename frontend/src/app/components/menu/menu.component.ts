import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {
  public username = ""
  public password = ""

  constructor(private loginService: LoginService, private http: HttpClient) { }

  ngOnInit(): void {}

  yo(){
    // this.loginService.getLoggedUser().subscribe(data => {
    //   console.log(data)
    // })
    this.http.get("http://localhost:8080/auth/loggedUser").subscribe(data => {
      console.log(data)
    })
  }

  login(){
    //this.loginService.login(this.username, this.password)
  }
}
