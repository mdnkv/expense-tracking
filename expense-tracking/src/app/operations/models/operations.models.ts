import {Account} from "../../accounts/models/accounts.models";
import {Category} from "../../categories/models/categories.models";
import {Currency} from "../../currencies/models/currencies.models";

export interface Operation {
  id?: string
  userId: string
  accountId: string
  categoryId?: string
  description: string
  currencyId: string
  amount: number
  operationType: string
  date: Date
  category?: Category
  account?: Account
  currency?: Currency
}
