import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './auth.guard';
import { CertificateAuthoritiesComponent } from './components/certificate-authorities/certificate-authorities.component';
import { CertificatesComponent } from './components/certificates/certificates.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { ForgotPasswordComponent } from './components/forgot-password/forgot-password.component';
import { HomeComponent } from './components/home/home.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { PasswordlessComponent } from './components/passwordless/passwordless.component';
import { RegisterComponent } from './components/register/register.component';
import { UsersComponent } from './components/users/users.component';

const routes: Routes = [
  {path: "", component: HomeComponent},
  {path: "users", component: UsersComponent, canActivate: [AuthGuard]},
  {path: "certificates", component: CertificatesComponent},
  {path: "certificateAuthorities", component: CertificateAuthoritiesComponent},
  {path: "register", component: RegisterComponent},
  {path: "passwordless", component: PasswordlessComponent},
  {path: "changePassword", component: ChangePasswordComponent},
  {path: "forgotPassword", component: ForgotPasswordComponent},
  {path: "**", component: PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
