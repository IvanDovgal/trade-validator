CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;

CREATE TABLE holiday
(
    id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    currency    char(3),
    date        date,
    working_day boolean,
    unique (currency, date)
);

INSERT INTO holiday(currency, date, working_day)
values ('USD', '2019-04-07', false);