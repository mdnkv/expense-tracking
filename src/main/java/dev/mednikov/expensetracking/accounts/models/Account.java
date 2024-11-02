package dev.mednikov.expensetracking.accounts.models;

import dev.mednikov.expensetracking.users.models.User;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.util.Objects;

@Entity
@Table(name = "accounts_account")
public class Account implements Comparable<Account> {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "account_name", nullable = false)
    private String name;

    @Column(name = "account_type", nullable = false)
    @Enumerated
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private AccountType type;

    @Override
    public int compareTo(Account o) {
        return this.name.compareTo(o.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account account)) return false;
        return Objects.equals(user, account.user) && Objects.equals(name, account.name) && type == account.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, name, type);
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public AccountType getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public static final class AccountBuilder {
        private Long id;
        private User user;
        private String name;
        private AccountType type;

        public AccountBuilder() {
        }

        public AccountBuilder(Account other) {
            this.id = other.id;
            this.user = other.user;
            this.name = other.name;
            this.type = other.type;
        }

        public AccountBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public AccountBuilder withUser(User user) {
            this.user = user;
            return this;
        }

        public AccountBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public AccountBuilder withType(AccountType type) {
            this.type = type;
            return this;
        }

        public Account build() {
            Account account = new Account();
            account.setName(name);
            account.setType(type);
            account.id = this.id;
            account.user = this.user;
            return account;
        }
    }

}
