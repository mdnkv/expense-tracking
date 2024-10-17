import {Component, inject, OnInit} from '@angular/core';
import {IncomeExpenseWidget} from "../../models/dashboard.models";
import {DashboardService} from "../../services/dashboard.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-dashboard-view',
  standalone: true,
  imports: [],
  templateUrl: './dashboard-view.component.html',
  styleUrl: './dashboard-view.component.css'
})
export class DashboardViewComponent implements OnInit{

  isError: boolean = false
  incomeExpense: IncomeExpenseWidget | undefined

  dashboardService: DashboardService = inject(DashboardService)

  ngOnInit() {
    // get user id
    const userIdString = localStorage.getItem("userId") as string
    const userId = Number.parseInt(userIdString)

    // call dashboard service
    this.dashboardService.getIncomeExpenseWidget(userId).subscribe({
      next: result => this.incomeExpense = result,
      error: (err: HttpErrorResponse) => {
        console.log(err)
        this.isError = true
      }
    })
  }

}
