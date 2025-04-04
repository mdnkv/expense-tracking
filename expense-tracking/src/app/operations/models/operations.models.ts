import {Account} from "../../accounts/models/accounts.models";
import {Category} from "../../categories/models/categories.models";

export interface Operation {
  id?: number
  userId: number
  accountId: number
  categoryId?: number
  description: string
  currency: string
  amount: number
  operationType: string
  date: Date
  category?: Category
  account?: Account
}
