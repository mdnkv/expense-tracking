package dev.mednikov.expensetracking.users.services;

import dev.mednikov.expensetracking.users.models.User;

public interface TokenService {

    String getToken (User user);

}
