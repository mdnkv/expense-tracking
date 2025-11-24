export interface SignupRequest {
  email: string
  firstName: string
  lastName: string
  password: string
}

export interface SignupResponse {
  id: string
}


export interface User {
  id: string
  firstName: string
  lastName: string
}

export interface ChangePasswordRequest {
  id: string
  password: string
}
