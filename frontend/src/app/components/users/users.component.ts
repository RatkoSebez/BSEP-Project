import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/model/User';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  users!: any[]
  ownerId!: number
  idOfCertificatePublisher!: number
  user = new User('')

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.http.get<any>('api/user/loggedInUser/').subscribe(
      response => {
        this.user = response
        console.log(this.user)
        if(this.user.role == "ROLE_ADMIN"){
          this.getAllUsers()
        }
      }
    );
  }

  getAllUsers(){
    this.http.get<any>('api/user/getAll/').subscribe(
      response => {
        this.users = response
      }
    );
  }

  createRootCa(){
    this.http.post('api/certificate/createCertificateAuthority', {idOfCertificatePublisher: null, ownerId: this.ownerId, isEndEntityCertificate: false}).subscribe(
      response => {
      }
    );
  }

  createCa(){
    this.http.post('api/certificate/createCertificateAuthority', {idOfCertificatePublisher: this.idOfCertificatePublisher, ownerId: this.ownerId, isEndEntityCertificate: false}).subscribe(
      response => {
      }
    );
  }

  createEndEntityCertificate(){
    this.http.post('api/certificate/createCertificateAuthority', {idOfCertificatePublisher: this.idOfCertificatePublisher, ownerId: this.ownerId, isEndEntityCertificate: true}).subscribe(
      response => {
      }
    );
  }
}
