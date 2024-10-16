import {Component, inject} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {Router, RouterLink} from "@angular/router";
import {AuthService} from "../../services/auth.service";
import {LoginRequest} from "../../models/auth.models";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-login-view',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './login-view.component.html',
  styleUrl: './login-view.component.css'
})
export class LoginViewComponent {

  isFormLoading: boolean = false
  isError: boolean = false

  formBuilder: FormBuilder = inject(FormBuilder)
  form: FormGroup = this.formBuilder.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required]]
  })

  authService: AuthService = inject(AuthService)
  router: Router = inject(Router)

  onFormSubmit(){
    this.isFormLoading = true
    this.isError = false

    const payload: LoginRequest = {
      email: this.form.get('email')?.value,
      password: this.form.get('password')?.value,
    }

    // execute request
    this.authService.login(payload).subscribe({
      next: (result) => {
        // save data
        localStorage.setItem("userId", result.id.toString())
        localStorage.setItem("token", result.token)

        // set is authenticated
        this.authService.authenticated.next(true)

        // output result
        console.log(result)

        // go to operations view
        this.router.navigateByUrl("/operations")

      },
      error: (err: HttpErrorResponse) => {
        console.log(err)
        this.isError = true
        this.isFormLoading = false
      }
    })

  }

}
