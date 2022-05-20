import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {
  currentPassword = ''
  newPassword = ''
  success = false
  failure = false

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
  }

  changePassword(){
    this.http.post<boolean>('api/user/changePassword', {currentPassword: this.currentPassword, newPassword: this.newPassword}).subscribe(
      response => {
        this.success = response
        this.failure = !response
        // console.log('response: ' + response)
        // console.log('success: ' + this.success)
        // console.log('failure: ' + this.failure)
      }
    );
  }
}
