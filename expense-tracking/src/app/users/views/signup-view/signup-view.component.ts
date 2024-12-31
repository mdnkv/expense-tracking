import {Component, inject} from '@angular/core';
import {Router, RouterLink} from "@angular/router";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {SignupRequest} from "../../models/users.models";
import {UserService} from "../../services/user.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
    selector: 'app-signup-view',
    imports: [RouterLink, ReactiveFormsModule],
    templateUrl: './signup-view.component.html',
    styleUrl: './signup-view.component.css'
})
export class SignupViewComponent {

  isFormLoading: boolean = false
  isError: boolean = false
  isShowPassword: boolean = false
  isAlreadyExist: boolean = false

  formBuilder: FormBuilder = inject(FormBuilder)
  form: FormGroup = this.formBuilder.group({
    email: ['', [Validators.required, Validators.email]],
    firstName: ['', [Validators.required]],
    lastName: ['', [Validators.required]],
    password: ['', [Validators.required, Validators.minLength(8)]],
  })

  userService: UserService = inject(UserService)
  router: Router = inject(Router)

  onFormSubmit(){
    this.isFormLoading = true
    this.isError = false

    const payload: SignupRequest = {
      email: this.form.get('email')?.value,
      password: this.form.get('password')?.value,
      firstName: this.form.get('firstName')?.value,
      lastName: this.form.get('lastName')?.value,
    }

    this.userService.signup(payload).subscribe({
      next: (result) => {
        console.log(result)
        this.router.navigateByUrl('/auth/login')
      },
      error: (err: HttpErrorResponse) => {
        // check error type
        if (err.status == 400) {
          // user email is already registered
          this.form.reset()
          this.isAlreadyExist = true
        } else {
          // any other generic error
          this.isError = true
        }
        this.isFormLoading = false
      }
    })

  }

}
