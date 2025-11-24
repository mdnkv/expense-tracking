package dev.mednikov.expensetracking.authentication.dto;

public class LoginResponseDto{
    private String id;
    private String token;

    public LoginResponseDto() {
    }

    public LoginResponseDto(Long id, String token) {
        this.id = id.toString();
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
