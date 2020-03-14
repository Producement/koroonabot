CREATE TABLE message
(
    id      SERIAL       NOT NULL PRIMARY KEY,
    message VARCHAR(255) NOT NULL,
    time    TIMESTAMP    NOT NULL DEFAULT NOW()

);
