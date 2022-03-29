import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-certificate-authorities',
  templateUrl: './certificate-authorities.component.html',
  styleUrls: ['./certificate-authorities.component.css']
})
export class CertificateAuthoritiesComponent implements OnInit {
  certificateAuthorities!: any[]

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.http.get<any>('api/certificate/getAllCertificateAuthorities/').subscribe(
      response => {
        this.certificateAuthorities = response
      }
    );
  }

}
