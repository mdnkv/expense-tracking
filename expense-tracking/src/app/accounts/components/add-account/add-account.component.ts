import {Component, EventEmitter, inject, Output} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {Account, AccountTypes} from "../../models/accounts.models";
import Swal from "sweetalert2";

@Component({
  selector: 'app-add-account',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './add-account.component.html',
  styleUrl: './add-account.component.css'
})
export class AddAccountComponent {

  @Output() onCreateAccount = new EventEmitter<Account>()

  isShowModal: boolean = false
  accountTypes  = AccountTypes

  formBuilder: FormBuilder = inject(FormBuilder)
  accountCreateForm: FormGroup = this.formBuilder.group({
    name: ['', [Validators.required]],
    type: ['CASH', [Validators.required]]
  })

  onFormSubmit() {
    // get user id
    const userId = localStorage.getItem("userId") as string
    const id = Number.parseInt(userId)

    // create payload
    const payload: Account = {
      userId: id,
      name: this.accountCreateForm.get('name')?.value,
      type: this.accountCreateForm.get('type')?.value
    }

    this.onCreateAccount.emit(payload)
    this.isShowModal = false
    this.accountCreateForm.reset()
  }

  onCloseFormModal(){
    if (this.accountCreateForm.touched){
      // ask the user, if the user wants to close the modal and lost all data
      Swal.fire({
        title: 'Close the window',
        text: 'Do you want to close the window and lost all entered data?',
        icon: 'info',
        showConfirmButton: true,
        showCancelButton: true,
        confirmButtonText: 'Yes, close',
        cancelButtonText: 'No, keep it'
      }).then(result => {
        if (result.isConfirmed){
          this.accountCreateForm.reset()
          this.isShowModal = false
        }
      })
    } else {
      // close modal
      this.isShowModal = false
    }
  }

}
