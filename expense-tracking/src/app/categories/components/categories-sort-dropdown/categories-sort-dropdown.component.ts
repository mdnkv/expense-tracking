import {Component, EventEmitter, Output} from '@angular/core';

@Component({
  selector: 'app-categories-sort-dropdown',
  standalone: true,
  imports: [],
  templateUrl: './categories-sort-dropdown.component.html',
  styleUrl: './categories-sort-dropdown.component.css'
})
export class CategoriesSortDropdownComponent {

  @Output() onSortSelection = new EventEmitter<string>

  isActive: boolean = false

  selectSortOrder (order: string){
    this.onSortSelection.emit(order)
    this.isActive = false
  }

}
