import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {AccountService} from "../../services/account.service";
import {Account, AccountTypes} from "../../models/accounts.models";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-edit-account-view',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './edit-account-view.component.html',
  styleUrl: './edit-account-view.component.css'
})
export class EditAccountViewComponent implements OnInit{

  activatedRoute: ActivatedRoute = inject(ActivatedRoute)
  router: Router = inject(Router)
  formBuilder: FormBuilder = inject(FormBuilder)

  isFormLoading: boolean = false
  isSuccess: boolean = false
  isError: boolean = false
  account: Account | undefined

  accountTypes = AccountTypes

  accountUpdateForm: FormGroup = this.formBuilder.group({
    name: ['', [Validators.required]],
    type: ['', [Validators.required]]
  })
  accountService: AccountService = inject(AccountService)

  onCancel() {
    this.router.navigateByUrl('/accounts')
  }

  onFormSubmit(){
    this.isFormLoading = true
    this.isSuccess = false
    this.isError = false

    // create payload
    const payload: Account = {
      ...this.account!,
      name: this.accountUpdateForm.get('name')?.value,
      type: this.accountUpdateForm.get('type')?.value
    }

    // execute the service method
    this.accountService.updateAccount(payload).subscribe({
      next: result =>{
        this.account = result
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

  ngOnInit() {
    const pathParam = this.activatedRoute.snapshot.paramMap.get('id')
    if (pathParam != null){
      const id = Number.parseInt(pathParam)
      this.accountService.getAccountById(id).subscribe({
        next: (result) => {
          this.account = result
          this.accountUpdateForm.get('name')?.setValue(result.name)
          this.accountUpdateForm.get('type')?.setValue(result.type)
        },
        error: (err: HttpErrorResponse) => {
          console.log(err)
        }
      })
    } else {
      this.isError = true
    }
  }

}
