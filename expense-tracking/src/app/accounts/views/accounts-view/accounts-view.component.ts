import {Component, inject, OnInit} from '@angular/core';
import {HttpErrorResponse} from "@angular/common/http";
import {RouterLink} from "@angular/router";

import {Account} from "../../models/accounts.models";
import {AccountService} from "../../services/account.service";
import {AddAccountComponent} from "../../components/add-account/add-account.component";
import {AccountsSortDropdownComponent} from "../../components/accounts-sort-dropdown/accounts-sort-dropdown.component";
import {AccountsListComponent} from "../../components/accounts-list/accounts-list.component";

import Swal from "sweetalert2";

@Component({
    selector: 'app-accounts-view',
    imports: [RouterLink, AddAccountComponent, AccountsSortDropdownComponent, AccountsListComponent],
    templateUrl: './accounts-view.component.html',
    styleUrl: './accounts-view.component.css'
})
export class AccountsViewComponent implements OnInit{

  accountsList: Account[] = []

  accountService: AccountService = inject(AccountService)

  ngOnInit() {
    // get current user id
    const userId = localStorage.getItem("UserId") as string

    // retrieve accounts for user
    this.accountService.getAllAccountsForUser(userId).subscribe({
      next: result => {
        this.accountsList = result
      },
      error: (err: HttpErrorResponse) => {
        console.log(err)
      }
    })
  }

  createAccount(account: Account){
    // execute request
    this.accountService.createAccount(account).subscribe({
      next: result => {
        this.accountsList.push(result)
      },
      error: (err: HttpErrorResponse) => {
        Swal.fire({
          icon: 'error',
          text: 'Something went wrong. Please try again later'
        })
      }
    })
  }

  deleteAccount(id: string) {
    this.accountService.deleteAccount(id).subscribe({
      next: result => {
        this.accountsList = this.accountsList.filter(e => e.id != id)
      },
      error: (err: HttpErrorResponse) => {
        console.log(err)
        Swal.fire({
          icon: 'error',
          text: 'Something went wrong. Please try again later'
        })
      }
    })
  }

  sortCategories(order: string){
    if (order == 'name-asc'){
      this.accountsList.sort((c1, c2) => {
        return c1.name.localeCompare(c2.name)
      })
    } else {
      this.accountsList.sort((c1, c2) => {
        return c2.name.localeCompare(c1.name)
      })
    }
  }

}
