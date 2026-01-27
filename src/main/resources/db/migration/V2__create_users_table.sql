CREATE TABLE users
(
    id           BIGINT GENERATED ALWAYS AS IDENTITY,
    created_at   TIMESTAMP NOT NULL,
    updated_at   TIMESTAMP,
    email        VARCHAR   NOT NULL UNIQUE,
    password     VARCHAR   NOT NULL,
    first_name   VARCHAR   NOT NULL,
    last_name    VARCHAR   NOT NULL,
    role_id   BIGINT    NOT NULL,
    CONSTRAINT user_id PRIMARY KEY (id),
    CONSTRAINT role_id FOREIGN KEY (role_id)
        REFERENCES roles (id) ON DELETE RESTRICT
);