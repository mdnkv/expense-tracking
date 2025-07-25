import {Component, inject, OnInit} from '@angular/core';
import {HttpErrorResponse} from "@angular/common/http";

import {IncomeExpenseWidget} from "../../models/dashboard.models";
import {DashboardService} from "../../services/dashboard.service";
import {UserService} from "../../../users/services/user.service";
import {User} from "../../../users/models/users.models";

@Component({
    selector: 'app-dashboard-view',
    imports: [],
    templateUrl: './dashboard-view.component.html',
    styleUrl: './dashboard-view.component.css'
})
export class DashboardViewComponent implements OnInit{

  isError: boolean = false
  incomeExpense: IncomeExpenseWidget | undefined
  user: User | undefined

  dashboardService: DashboardService = inject(DashboardService)
  userService: UserService = inject(UserService)

  ngOnInit() {
    // get user id
    const userIdString = localStorage.getItem("UserId") as string
    const userId = Number.parseInt(userIdString)

    // call dashboard service
    this.dashboardService.getIncomeExpenseWidget(userId).subscribe({
      next: result => this.incomeExpense = result,
      error: (err: HttpErrorResponse) => {
        console.log(err)
        this.isError = true
      }
    })

    this.userService.getUserById(userId).subscribe({
      next: result => this.user = result,
      error: (err: HttpErrorResponse) => console.log(err)
    })
  }

}
