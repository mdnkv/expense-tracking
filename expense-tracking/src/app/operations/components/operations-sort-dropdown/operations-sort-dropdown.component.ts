import {Component, EventEmitter, Output} from '@angular/core';

@Component({
  selector: 'app-operations-sort-dropdown',
  standalone: true,
  imports: [],
  templateUrl: './operations-sort-dropdown.component.html',
  styleUrl: './operations-sort-dropdown.component.css'
})
export class OperationsSortDropdownComponent {

  isActive: boolean = false

  @Output() onSortSelection = new EventEmitter<string>

  selectSortOrder(order: string){
    this.onSortSelection.emit(order)
    this.isActive = false
  }

}
