import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {
  verificationCode = ''
  newPassword = ''
  success = false
  failure = false
  email = ''

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
  }

  changePassword(){
    this.http.post<boolean>('api/user/forgotPassword', {forgotPasswordVerificationCode: this.verificationCode, newPassword: this.newPassword}).subscribe(
      response => {
        this.success = response
        this.failure = !response
      }
    );
  }

  sendVerificationCode(){
    this.http.get('api/user/sendForgotPasswordVerificationCode/' + this.email).subscribe(
      response => {
       
      }
    );
  }
}
