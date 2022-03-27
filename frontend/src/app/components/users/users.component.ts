import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  users!: any[]

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.http.get<any>('api/user/getAll/').subscribe(
      response => {
        this.users = response
        console.log(response)
      }
    );
  }

}
