package dev.mednikov.expensetracking.users.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "users_user")
public class User {

    @Id
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "is_superuser", nullable = false)
    private boolean superuser;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean isSuperuser() {
        return superuser;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSuperuser(boolean superuser) {
        this.superuser = superuser;
    }

    public static final class UserBuilder {
        private Long id;
        private String email;
        private String password;
        private String firstName;
        private String lastName;
        private boolean superuser;

        public UserBuilder() {
        }

        public UserBuilder(User other) {
            this.id = other.id;
            this.email = other.email;
            this.password = other.password;
            this.firstName = other.firstName;
            this.lastName = other.lastName;
            this.superuser = other.superuser;
        }

        public UserBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder withSuperuser(boolean superuser) {
            this.superuser = superuser;
            return this;
        }

        public User build() {
            User user = new User();
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.id = this.id;
            user.password = this.password;
            user.superuser = this.superuser;
            return user;
        }
    }
}
