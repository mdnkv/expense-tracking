import {Component, EventEmitter, Input, Output} from '@angular/core';
import {RouterLink} from "@angular/router";
import {CurrencyPipe} from "@angular/common";

import {Operation} from "../../models/operations.models";

@Component({
    selector: 'app-operation-card',
    imports: [RouterLink, CurrencyPipe],
    templateUrl: './operation-card.component.html',
    styleUrl: './operation-card.component.css'
})
export class OperationCardComponent {

  @Input() operation: Operation | undefined

  @Output() deleteClicked = new EventEmitter<number>

  onDelete(){
    this.deleteClicked.emit(this.operation!.id!)
  }

}
