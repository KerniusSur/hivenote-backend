CREATE TABLE IF NOT EXISTS role
(
    id   SERIAL PRIMARY KEY,
    name text NOT NULL
);

CREATE TABLE IF NOT EXISTS account
(
    id                                   SERIAL PRIMARY KEY,
    name                                 text,
    last_name                            text,
    email                                text UNIQUE NOT NULL,
    password                             text,
    phone_number                         text,
    is_active                            boolean DEFAULT FALSE,
    is_email_confirmed                   boolean DEFAULT FALSE,
    last_login                           timestamp,
    password_reset_token                 text,
    password_reset_token_expiration_date timestamp
);

CREATE TABLE IF NOT EXISTS account_role
(
    id         SERIAL PRIMARY KEY,
    account_id integer NOT NULL,
    role_id    integer NOT NULL,
    deleted    boolean DEFAULT FALSE,

    FOREIGN KEY (account_id) REFERENCES account (id),
    FOREIGN KEY (role_id) REFERENCES role (id)
);


CREATE TABLE IF NOT EXISTS validation
(
    id                SERIAL PRIMARY KEY,
    type              TEXT    NOT NULL,
    validation_value  TEXT    NOT NULL,
    value_to_validate TEXT    NOT NULL,
    used              BOOLEAN NOT NULL DEFAULT FALSE,
    created_at        TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    expires_at        TIMESTAMP
);