export interface SignupRequest {
  email: string
  firstName: string
  lastName: string
  password: string
}

export interface SignupResponse {
  id: number
}


export interface User {
  id: number
  firstName: string
  lastName: string
}

export interface ChangePasswordRequest {
  id: number
  password: string
}
