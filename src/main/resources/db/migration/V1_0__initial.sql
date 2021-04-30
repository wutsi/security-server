CREATE TABLE T_API_KEY(
    id      VARCHAR(36) NOT NULL,
    name    VARCHAR(100) NOT NULL,
    scopes  TEXT NOT NULL,

    creation_date_time TIMESTAMPTZ NOT NULL DEFAULT now(),
    expiry_date_time TIMESTAMPTZ DEFAULT NULL,

    PRIMARY KEY (id)
);
