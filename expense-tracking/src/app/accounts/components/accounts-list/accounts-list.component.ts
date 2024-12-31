import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Account} from "../../models/accounts.models";
import {RouterLink} from "@angular/router";
import Swal from "sweetalert2";
import {EmptyPlaceholderComponent} from "../../../core/components/empty-placeholder/empty-placeholder.component";

@Component({
    selector: 'app-accounts-list',
    imports: [RouterLink, EmptyPlaceholderComponent],
    templateUrl: './accounts-list.component.html',
    styleUrl: './accounts-list.component.css'
})
export class AccountsListComponent {

  @Input() accounts: Account[] = []
  @Output() onRemoveClicked = new EventEmitter<number>()

  removeAccount(id: number){
    // ask user to confirmation
    Swal.fire({
      icon: 'warning',
      text: 'Do you want to remove this account? This action cannot be undone',
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
