CREATE SCHEMA telegram;

CREATE TABLE telegram.clients
(
    "chatId" bigint NOT NULL PRIMARY KEY,
    active boolean NOT NULL,
    "firstName" text,
    "lastName" text,
    "userName" text NOT NULL,
    title text,
    type text NOT NULL
)