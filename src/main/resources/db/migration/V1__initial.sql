create table if not exists users_user (
    id bigint primary key,
    email varchar(512) not null unique,
    password_hash varchar(512) not null,
    first_name varchar(512) not null,
    last_name varchar(512) not null,
    is_superuser boolean not null default false
);

create table if not exists authentication_token (
    id bigserial primary key,
    token varchar(255) not null unique,
    user_id bigint not null,
    expires_at timestamp not null,
    constraint
        fk_authentication_token_user
        foreign key (user_id)
            references users_user(id)
            on delete cascade
);

create type ACCOUNTS_ACCOUNT_TYPE as enum('CASH', 'CREDIT_CARD', 'BANK_ACCOUNT');

create table if not exists accounts_account (
    id bigint primary key,
    account_name varchar(512) not null,
    account_type ACCOUNTS_ACCOUNT_TYPE not null,
    user_id bigint not null,
    constraint
        fk_accounts_user
        foreign key (user_id)
            references users_user(id)
            on delete cascade
);

create table if not exists categories_category (
    id bigint primary key,
    user_id bigint not null,
    category_name varchar(512) not null,
    constraint
        fk_categories_user
        foreign key (user_id)
            references users_user(id)
            on delete cascade
);

create table if not exists currencies_currency (
    id bigint primary key,
    user_id bigint not null,
    currency_name varchar(255) not null,
    currency_code varchar(3) not null,
    unique (user_id, currency_code),
    constraint
        fk_currencies_user
        foreign key (user_id)
            references users_user(id)
            on delete cascade
);

create type OPERATIONS_OPERATION_TYPE as enum('EXPENSE', 'INCOME');

create table if not exists operations_operation (
    id bigint primary key,
    user_id bigint not null,
    account_id bigint not null,
    currency_id bigint not null,
    category_id bigint,
    operation_type OPERATIONS_OPERATION_TYPE not null,
    operation_description text not null,
    operation_amount decimal(12,2) not null default 0.0,
    operation_date date not null,
    constraint
        fk_operations_user
        foreign key (user_id)
            references users_user(id)
            on delete cascade,
    constraint
        fk_operations_currency
        foreign key (currency_id)
            references currencies_currency(id)
            on delete cascade,
    constraint
        fk_operations_account
        foreign key (account_id)
            references accounts_account(id)
            on delete cascade,
    constraint
        fk_operations_category
        foreign key (category_id)
            references categories_category(id)
            on delete set null
);