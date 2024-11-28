import {Component, EventEmitter, Input, Output} from '@angular/core';
import {RouterLink} from "@angular/router";

import {EmptyPlaceholderComponent} from "../../../core/components/empty-placeholder/empty-placeholder.component";
import {OperationResponse} from "../../models/operations.models";
import {OperationCardComponent} from "../operation-card/operation-card.component";

import Swal from "sweetalert2";

@Component({
  selector: 'app-operations-list',
  standalone: true,
  imports: [RouterLink, EmptyPlaceholderComponent, OperationCardComponent],
  templateUrl: './operations-list.component.html',
  styleUrl: './operations-list.component.css'
})
export class OperationsListComponent {

  @Input() operations: OperationResponse[] = []
  @Output() onRemoveClicked = new EventEmitter<number>()

  removeOperation(id: number){
    // ask user to confirmation
    Swal.fire({
      icon: 'warning',
      text: 'Do you want to remove this operation? This action cannot be undone',
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
