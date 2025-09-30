import {Account} from "../../accounts/models/accounts.models";
import {Category} from "../../categories/models/categories.models";
import {Currency} from "../../currencies/models/currencies.models";

export interface Operation {
  id?: number
  userId: number
  accountId: number
  categoryId?: number
  description: string
  currencyId: number
  amount: number
  operationType: string
  date: Date
  category?: Category
  account?: Account
  currency?: Currency
}
