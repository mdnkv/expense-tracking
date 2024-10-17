import {Account} from "../../accounts/models/accounts.models";
import {Category} from "../../categories/models/categories.models";

export interface OperationRequest {
  id?: number
  userId: number
  accountId: number
  categoryId?: number
  description: string
  currency: string
  amount: number
  type: string
  operationDate: Date
}

export interface OperationType {
  name: string
  value: string
}

export const OperationTypes: OperationType[] = [
  {name : 'Expense', value: 'EXPENSE'},
  {name : 'Income', value: 'INCOME'}
]

export interface OperationResponse {
  id: number
  account: Account
  category: Category
  description: string
  currency: string
  amount: number
  type: string
  operationDate: Date
  monetaryAmount: string
}
