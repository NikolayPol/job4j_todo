CREATE TABLE tasks
(
    id   SERIAL PRIMARY KEY,
    description TEXT,
    created TIMESTAMP,
    done bool
);

insert into tasks (id, description, created, done) values (1, 'Заказать пиццу', localtimestamp(1), false);
insert into tasks (id, description, created, done) values (2, 'Выучить джаву', localtimestamp(1), false);
insert into tasks (id, description, created, done) values (3, 'Устроиться на работу', localtimestamp(1), false);

