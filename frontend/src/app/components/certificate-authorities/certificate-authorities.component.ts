import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/model/User';

@Component({
  selector: 'app-certificate-authorities',
  templateUrl: './certificate-authorities.component.html',
  styleUrls: ['./certificate-authorities.component.css']
})
export class CertificateAuthoritiesComponent implements OnInit {
  certificateAuthorities!: any[]
  user = new User('', 1)

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.http.get<any>('api/user/loggedInUser/').subscribe(
      response => {
        this.user = response
        if(this.user.role == "ROLE_ADMIN"){
          this.getAllCertificateAuthorities()
        }
        if(this.user.role == "ROLE_CLIENT"){
          this.getUsersCertificateAuthorities()
        }
      }
    );
  }

  getAllCertificateAuthorities(){
    this.http.get<any>('api/certificate/getAllCertificateAuthorities/').subscribe(
      response => {
        this.certificateAuthorities = response
        console.log(this.certificateAuthorities)
      }
    );
  }

  getUsersCertificateAuthorities(){
    this.http.get<any>('api/certificate/getUserCertificateAuthorities/').subscribe(
      response => {
        this.certificateAuthorities = response
        console.log(this.certificateAuthorities)
      }
    );
  }
}
