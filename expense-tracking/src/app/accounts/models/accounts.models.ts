export interface Account {
  id?: number
  userId: number
  name: string
  type: string
}

export interface AccountType {
  value: string
  name: string
}

export const AccountTypes: AccountType[] = [
  {value: 'CASH', name: 'Cash'},
  {value: 'CREDIT_CARD', name: 'Credit Card'},
  {value: 'BANK_ACCOUNT', name: 'Bank account'}
]
