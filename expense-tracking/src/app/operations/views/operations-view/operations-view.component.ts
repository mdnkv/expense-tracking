import {Component, inject, OnInit} from '@angular/core';
import {HttpErrorResponse} from "@angular/common/http";
import {RouterLink} from "@angular/router";
import {OperationRequest, OperationResponse} from "../../models/operations.models";
import {OperationService} from "../../services/operation.service";
import {AddOperationComponent} from "../../components/add-operation/add-operation.component";
import {
  OperationsSortDropdownComponent
} from "../../components/operations-sort-dropdown/operations-sort-dropdown.component";
import {OperationsListComponent} from "../../components/operations-list/operations-list.component";
import Swal from "sweetalert2";

@Component({
    selector: 'app-operations-view',
    imports: [RouterLink, AddOperationComponent, OperationsSortDropdownComponent, OperationsListComponent],
    templateUrl: './operations-view.component.html',
    styleUrl: './operations-view.component.css'
})
export class OperationsViewComponent implements OnInit{

  operationService: OperationService = inject(OperationService)
  operationsList: OperationResponse[] = []

  ngOnInit() {
    // get user id
    const userIdString = localStorage.getItem("userId") as string
    const userId = Number.parseInt(userIdString)

    // get operations for user
    this.operationService.getAllOperationsForUser(userId).subscribe({
      next: result => {
        this.operationsList = result
      },
      error: (err: HttpErrorResponse) => {
        console.log(err)
      }
    })
  }

  createOperation(operation: OperationRequest){
    this.operationService.createOperation(operation).subscribe({
      next: result => {
        // add operation to operationsList
        this.operationsList.push(result)
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


  deleteOperation(id: number){
    this.operationService.deleteOperation(id).subscribe({
      next: result => {
        this.operationsList = this.operationsList.filter(e => e.id != id)
      },
      error: (err: HttpErrorResponse) =>{
        console.log(err)
        Swal.fire({
          icon: 'error',
          text: 'Something went wrong. Please try again later'
        })
      }
    })
  }

  sortOperations(order: string){
    switch (order){
      case 'amount-asc':
        this.operationsList.sort((op1, op2) => {
          return op1.amount - op2.amount
        })
        break
      case 'amount-desc':
        this.operationsList.sort((op1, op2) => {
          return op2.amount - op1.amount
        })
        break
      case 'date-asc':
        this.operationsList.sort((op1, op2) => {
          if (op1.operationDate > op2.operationDate){
            return 1
          } else if (op1.operationDate < op2.operationDate){
            return -1
          } else {
            return 0
          }
        })
        break
      case 'date-desc':
        this.operationsList.sort((op1, op2) => {
          if (op1.operationDate < op2.operationDate){
            return 1
          } else if (op1.operationDate > op2.operationDate){
            return -1
          } else {
            return 0
          }
        })
        break
    }
  }

}
