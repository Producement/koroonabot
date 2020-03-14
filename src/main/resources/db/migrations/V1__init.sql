CREATE TABLE incoming_webhook
(
    id                SERIAL       NOT NULL PRIMARY KEY,
    channel           VARCHAR(255) NOT NULL,
    channel_id        VARCHAR(255) NOT NULL,
    configuration_url VARCHAR(255) NOT NULL,
    url               VARCHAR(255) NOT NULL
);

CREATE TABLE bot
(
    id           SERIAL       NOT NULL PRIMARY KEY,
    user_id      VARCHAR(255) NOT NULL,
    access_token VARCHAR(255) NOT NULL
);

CREATE TABLE team
(
    team_id             VARCHAR(255)                NOT NULL PRIMARY KEY,
    team_name           VARCHAR(255)                NOT NULL,
    incoming_webhook_id INTEGER REFERENCES incoming_webhook (id),
    bot_id              INTEGER REFERENCES bot (id) NOT NULL
);
