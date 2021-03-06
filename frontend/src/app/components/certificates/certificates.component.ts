import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-certificates',
  templateUrl: './certificates.component.html',
  styleUrls: ['./certificates.component.css']
})
export class CertificatesComponent implements OnInit {
  certificates!: any[]

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.http.get<any>('api/certificate/').subscribe(
      response => {
        this.certificates = response
      }
    );
  }

  isRevoked(id){
    this.http.get<any>('api/certificate/revoked/' + id).subscribe(
      response => {
        console.log(response)
      }
    );
  }
}
