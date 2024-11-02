import {Component, EventEmitter, Output} from '@angular/core';

@Component({
  selector: 'app-accounts-sort-dropdown',
  standalone: true,
  imports: [],
  templateUrl: './accounts-sort-dropdown.component.html',
  styleUrl: './accounts-sort-dropdown.component.css'
})
export class AccountsSortDropdownComponent {

  @Output() onSortSelection = new EventEmitter<string>

  isActive: boolean = false

  selectSortOrder (order: string){
    this.onSortSelection.emit(order)
    this.isActive = false
  }

}
