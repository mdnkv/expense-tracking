package dev.mednikov.expensetracking.currencies.models;

import dev.mednikov.expensetracking.users.models.User;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(
        name = "currencies_currency",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "currency_code"})}
)
public class Currency {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "currency_name", nullable = false)
    private String name;

    @Column(name = "currency_code", nullable = false)
    private String code;

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Currency currency)) return false;

        return user.equals(currency.user) && code.equals(currency.code);
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + code.hashCode();
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
