package dev.mednikov.expensetracking.users.events;

import dev.mednikov.expensetracking.users.models.User;
import org.springframework.context.ApplicationEvent;

public final class NewUserCreatedEvent extends ApplicationEvent {

    private final User user;

    public NewUserCreatedEvent(Object source, User user) {
        super(source);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
