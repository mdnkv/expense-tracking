create table users_user (
    id bigserial primary key,
    email varchar(512) not null unique,
    password_hash varchar(512) not null,
    first_name varchar(512) not null,
    last_name varchar(512) not null,
    is_superuser boolean not null default false
);

create type ACCOUNTS_ACCOUNT_TYPE as enum('CASH', 'CREDIT_CARD', 'BANK_ACCOUNT');

create table accounts_account (
    id bigserial primary key,
    account_name varchar(512) not null,
    account_type ACCOUNTS_ACCOUNT_TYPE not null,
    user_id bigint not null,
    constraint
        fk_accounts_user
        foreign key (user_id)
            references users_user(id)
            on delete cascade
);

create table categories_category (
    id bigserial primary key,
    user_id bigint not null,
    category_name varchar(512) not null,
    constraint
        fk_categories_user
        foreign key (user_id)
            references users_user(id)
            on delete cascade
);

create type OPERATIONS_OPERATION_TYPE as enum('EXPENSE', 'INCOME');

create table operations_operation (
    id bigserial primary key,
    user_id bigint not null,
    account_id bigint not null,
    category_id bigint,
    operation_type OPERATIONS_OPERATION_TYPE not null,
    operation_description text not null,
    operation_currency varchar(3) not null,
    operation_amount decimal(12,2) not null default 0.0,
    operation_date date not null,
    constraint
        fk_operations_user
        foreign key (user_id)
            references users_user(id)
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