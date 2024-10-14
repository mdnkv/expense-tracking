import {Component, inject} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {RouterLink} from "@angular/router";
import {UserService} from "../../services/user.service";
import {ChangePasswordRequest} from "../../models/users.models";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-update-password-view',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './update-password-view.component.html',
  styleUrl: './update-password-view.component.css'
})
export class UpdatePasswordViewComponent {

  isFormLoading: boolean = false
  isSuccess: boolean = false
  isError: boolean = false

  formBuilder: FormBuilder = inject(FormBuilder)
  userService: UserService = inject(UserService)

  form: FormGroup = this.formBuilder.group({
    password: ['', [Validators.required, Validators.minLength(8)]]
  })

  onFormSubmit(){
    this.isFormLoading = true
    this.isError = false
    this.isSuccess = false

    // get user id from local storage
    const userId = localStorage.getItem("userId") as string
    const id = Number.parseInt(userId)

    // create payload
    const payload: ChangePasswordRequest = {
      password: this.form.get('password')?.value,
      id: id
    }

    // execute request
    this.userService.updatePassword(payload).subscribe({
      next: result => {
        this.isSuccess = true
        this.isFormLoading = false
        this.form.reset()
      },
      error: (err: HttpErrorResponse) => {
        console.log(err)
        this.isError = true
        this.isFormLoading = false
        this.form.reset()
      }
    })

  }

}
