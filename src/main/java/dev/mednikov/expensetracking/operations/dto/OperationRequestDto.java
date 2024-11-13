package dev.mednikov.expensetracking.operations.dto;

import dev.mednikov.expensetracking.operations.models.OperationType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OperationRequestDto(
        Long id,
        @NotNull Long userId,
        @NotNull Long accountId,
        Long categoryId,
        @NotNull @NotBlank String description,
        @NotNull @NotBlank String currency,
        @NotNull BigDecimal amount,
        @NotNull OperationType type,
        @NotNull LocalDate operationDate
) {
    public static final class OperationRequestDtoBuilder {
        private Long id;
        private Long userId;
        private Long accountId;
        private Long categoryId;
        private String description;
        private String currency;
        private BigDecimal amount;
        private OperationType type;
        private LocalDate operationDate;

        public OperationRequestDtoBuilder() {
        }

        public OperationRequestDtoBuilder(OperationRequestDto other) {
            this.id = other.id();
            this.userId = other.userId();
            this.accountId = other.accountId();
            this.categoryId = other.categoryId();
            this.description = other.description();
            this.currency = other.currency();
            this.amount = other.amount();
            this.type = other.type();
            this.operationDate = other.operationDate();
        }

        public OperationRequestDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public OperationRequestDtoBuilder withUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public OperationRequestDtoBuilder withAccountId(Long accountId) {
            this.accountId = accountId;
            return this;
        }

        public OperationRequestDtoBuilder withCategoryId(Long categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public OperationRequestDtoBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public OperationRequestDtoBuilder withCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public OperationRequestDtoBuilder withAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public OperationRequestDtoBuilder withType(OperationType type) {
            this.type = type;
            return this;
        }

        public OperationRequestDtoBuilder withOperationDate(LocalDate operationDate) {
            this.operationDate = operationDate;
            return this;
        }

        public OperationRequestDto build() {
            return new OperationRequestDto(id, userId, accountId, categoryId, description, currency, amount, type, operationDate);
        }
    }
}
