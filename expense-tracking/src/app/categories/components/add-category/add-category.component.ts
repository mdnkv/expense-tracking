import {Component, EventEmitter, inject, Output} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {Category} from "../../models/categories.models";
import Swal from 'sweetalert2';

@Component({
    selector: 'app-add-category',
    imports: [ReactiveFormsModule],
    templateUrl: './add-category.component.html',
    styleUrl: './add-category.component.css'
})
export class AddCategoryComponent {

  @Output() onCreateCategory = new EventEmitter<Category>()

  formBuilder: FormBuilder = inject(FormBuilder)
  categoryCreateForm: FormGroup = this.formBuilder.group({
    name: ['', [Validators.required]]
  })

  isActive: boolean = false

  formSubmit(){
    // get user id
    const userId = localStorage.getItem("UserId") as string

    // create payload
    const payload: Category = {
      name: this.categoryCreateForm.get('name')?.value,
      userId
    }

    // emit event
    this.onCreateCategory.emit(payload)

    // close modal and clean up the form
    this.categoryCreateForm.reset()
    this.isActive = false
  }

  closeModal(){
    if (this.categoryCreateForm.touched){
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
          this.categoryCreateForm.reset()
          this.isActive = false
        }
      })
    } else {
      // close modal
      this.isActive = false
    }
  }

}
