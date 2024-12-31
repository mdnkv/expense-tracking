import {Component, EventEmitter, Input, Output} from '@angular/core';
import {RouterLink} from "@angular/router";

import {EmptyPlaceholderComponent} from "../../../core/components/empty-placeholder/empty-placeholder.component";
import {Category} from "../../models/categories.models";

import Swal from "sweetalert2";

@Component({
    selector: 'app-categories-list',
    imports: [RouterLink, EmptyPlaceholderComponent],
    templateUrl: './categories-list.component.html',
    styleUrl: './categories-list.component.css'
})
export class CategoriesListComponent {

  @Input() categories: Category[] = []
  @Output() onRemoveClicked = new EventEmitter<number>()

  removeCategory(id: number){
    // ask user to confirmation
    Swal.fire({
      icon: 'warning',
      text: 'Do you want to remove this category? This action cannot be undone',
      showCancelButton: true,
      showConfirmButton: true,
      cancelButtonText: 'No, keep it',
      confirmButtonText: 'Yes, remove it'
    }).then(result => {
      if (result.isConfirmed){
        this.onRemoveClicked.emit(id)
      }
    })
  }
}
