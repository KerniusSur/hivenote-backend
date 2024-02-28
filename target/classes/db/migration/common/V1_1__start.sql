-- Role TABLE
CREATE TABLE IF NOT EXISTS role
(
    id   uuid PRIMARY KEY,
    name text NOT NULL
);

-- ACCOUNT TABLE
CREATE TABLE IF NOT EXISTS account
(
    id                                   uuid PRIMARY KEY,
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

-- ACCOUNT_ROLE TABLE
CREATE TABLE IF NOT EXISTS account_role
(
    id         uuid PRIMARY KEY,
    account_id uuid NOT NULL,
    role_id    uuid NOT NULL,
    deleted    boolean DEFAULT FALSE,

    FOREIGN KEY (account_id) REFERENCES account (id),
    FOREIGN KEY (role_id) REFERENCES role (id)
);

-- VALIDATION TABLE
CREATE TABLE IF NOT EXISTS validation
(
    id                uuid PRIMARY KEY,
    type              TEXT    NOT NULL,
    validation_value  TEXT    NOT NULL,
    value_to_validate TEXT    NOT NULL,
    used              BOOLEAN NOT NULL DEFAULT FALSE,
    created_at        TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    expires_at        TIMESTAMP
);


-- NOTEBOOK TABLE
CREATE TABLE notebook
(
    id          uuid PRIMARY KEY,
    name        text NOT NULL,
    account_id  uuid NOT NULL,
    is_archived boolean DEFAULT FALSE,
    is_deleted  boolean DEFAULT FALSE,

    FOREIGN KEY (account_id) REFERENCES account (id)
);

-- NOTE TABLE
CREATE TABLE note
(
    id          uuid PRIMARY KEY,
    type        text,
    title       text,
    cover_url   text,
    is_archived boolean DEFAULT FALSE,
    is_deleted  boolean DEFAULT FALSE,
    notebook_id uuid NOT NULL,

    FOREIGN KEY (notebook_id) REFERENCES notebook (id)
);

-- NOTE_ACCESS TABLE
CREATE TABLE note_access
(
    id          uuid PRIMARY KEY,
    account_id  uuid NOT NULL,
    note_id     uuid NOT NULL,
    access_type text,

    FOREIGN KEY (account_id) REFERENCES account (id),
    FOREIGN KEY (note_id) REFERENCES note (id)
);

-- COMPONENT TABLE
CREATE TABLE component
(
    id         uuid PRIMARY KEY,
    type       text  NOT NULL,
    properties jsonb NOT NULL,
    parent_id  uuid,
    note_id    uuid,

    FOREIGN KEY (parent_id) REFERENCES component (id),
    FOREIGN KEY (note_id) REFERENCES note (id)
);

-- EVENT TABLE
CREATE TABLE event
(
    id          uuid PRIMARY KEY,
    title       text,
    description text,
    event_start timestamp with time zone,
    event_end   timestamp with time zone,
    account_id  uuid,

    FOREIGN KEY (account_id) REFERENCES account (id)
);

-- COMMENT TABLE
CREATE TABLE comment
(
    id          uuid PRIMARY KEY,
    body        text,
    account_id  uuid,
    note_id     uuid,
    note_line   int,
    parent_id   uuid,
    is_resolved boolean,

    FOREIGN KEY (account_id) REFERENCES account (id),
    FOREIGN KEY (note_id) REFERENCES note (id),
    FOREIGN KEY (parent_id) REFERENCES comment (id)
);
