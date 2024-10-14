import {Component, inject, OnInit} from '@angular/core';
import {RouterLink} from "@angular/router";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {User} from "../../models/users.models";
import {UserService} from "../../services/user.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-update-user-view',
  standalone: true,
  imports: [RouterLink, ReactiveFormsModule],
  templateUrl: './update-user-view.component.html',
  styleUrl: './update-user-view.component.css'
})
export class UpdateUserViewComponent implements OnInit{

  isFormLoading: boolean = false
  isError: boolean = false
  isSuccess: boolean = false

  user: User | undefined

  formBuilder: FormBuilder = inject(FormBuilder)
  form: FormGroup = this.formBuilder.group({
    firstName: ['', [Validators.required]],
    lastName: ['', [Validators.required]]
  })

  userService: UserService = inject(UserService)

  ngOnInit() {
    const userId = localStorage.getItem("userId") as string
    const id = Number.parseInt(userId)
    this.userService.getUserById(id).subscribe({
      next: result => {
        this.user = result
        this.form.get('firstName')?.setValue(this.user.firstName)
        this.form.get('lastName')?.setValue(this.user.lastName)
      },
      error: (err: HttpErrorResponse) => {
        console.log(err)
      }
    })
  }

  onFormSubmit(){
    this.isFormLoading = true
    this.isError = false
    this.isSuccess = false

    // create payload
    const payload: User = {
      ...this.user!,
      firstName: this.form.get('firstName')?.value,
      lastName: this.form.get('lastName')?.value
    }

    // execute request
    this.userService.updateUser(payload).subscribe({
      next: result => {
        this.user = result
        this.isSuccess = true
        this.isFormLoading = false
      },
      error: (err: HttpErrorResponse) =>{
        console.log(err)
        this.isError = true
        this.isFormLoading = false
      }
    })


  }

}
