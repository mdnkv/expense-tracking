package dev.mednikov.expensetracking.authentication.dto;

public class LoginResponseDto{
    private Long id;
    private String token;

    public LoginResponseDto() {
    }

    public LoginResponseDto(Long id, String token) {
        this.id = id;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
