import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-passwordless',
  templateUrl: './passwordless.component.html',
  styleUrls: ['./passwordless.component.css']
})
export class PasswordlessComponent implements OnInit {
  email = ''

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
  }

  sendPasswordlessLink(){
    this.http.get('api/user/sendPasswordlessLink/' + this.email).subscribe(
      response => {
       
      }
    );
  }

}
