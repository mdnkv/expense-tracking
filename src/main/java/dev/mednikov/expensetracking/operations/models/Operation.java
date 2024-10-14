package dev.mednikov.expensetracking.operations.models;

import dev.mednikov.expensetracking.accounts.models.Account;
import dev.mednikov.expensetracking.categories.models.Category;
import dev.mednikov.expensetracking.users.models.User;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "operations_operation")
public class Operation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Account account;

    @Column(name = "operation_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OperationType type;

    @Column(name = "operation_description", nullable = false)
    private String description;

    @Column(name = "operation_currency", nullable = false)
    private String currency;

    @Column(name = "operation_amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "operation_date", nullable = false)
    private LocalDate operationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Operation operation)) return false;
        return Objects.equals(user, operation.user) && Objects.equals(account, operation.account) && type == operation.type && Objects.equals(description, operation.description) && Objects.equals(currency, operation.currency) && Objects.equals(amount, operation.amount) && Objects.equals(operationDate, operation.operationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, account, type, description, currency, amount, operationDate);
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Category getCategory() {
        return category;
    }

    public Account getAccount() {
        return account;
    }

    public OperationType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getOperationDate() {
        return operationDate;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setOperationDate(LocalDate operationDate) {
        this.operationDate = operationDate;
    }


    public static final class OperationBuilder {
        private Long id;
        private User user;
        private Category category;
        private Account account;
        private OperationType type;
        private String description;
        private String currency;
        private BigDecimal amount;
        private LocalDate operationDate;

        public OperationBuilder() {
        }

        public OperationBuilder(Operation other) {
            this.id = other.id;
            this.user = other.user;
            this.category = other.category;
            this.account = other.account;
            this.type = other.type;
            this.description = other.description;
            this.currency = other.currency;
            this.amount = other.amount;
            this.operationDate = other.operationDate;
        }

        public OperationBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public OperationBuilder withUser(User user) {
            this.user = user;
            return this;
        }

        public OperationBuilder withCategory(Category category) {
            this.category = category;
            return this;
        }

        public OperationBuilder withAccount(Account account) {
            this.account = account;
            return this;
        }

        public OperationBuilder withType(OperationType type) {
            this.type = type;
            return this;
        }

        public OperationBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public OperationBuilder withCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public OperationBuilder withAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public OperationBuilder withOperationDate(LocalDate operationDate) {
            this.operationDate = operationDate;
            return this;
        }

        public Operation build() {
            Operation operation = new Operation();
            operation.setCategory(category);
            operation.setAccount(account);
            operation.setType(type);
            operation.setDescription(description);
            operation.setCurrency(currency);
            operation.setAmount(amount);
            operation.setOperationDate(operationDate);
            operation.id = this.id;
            operation.user = this.user;
            return operation;
        }
    }

}
