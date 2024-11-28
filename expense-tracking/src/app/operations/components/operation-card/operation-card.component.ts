import {Component, EventEmitter, Input, Output} from '@angular/core';
import {OperationResponse} from "../../models/operations.models";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-operation-card',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './operation-card.component.html',
  styleUrl: './operation-card.component.css'
})
export class OperationCardComponent {

  @Input() operation: OperationResponse | undefined

  @Output() deleteClicked = new EventEmitter<number>

  onDelete(){
    this.deleteClicked.emit(this.operation!.id!)
  }

}
