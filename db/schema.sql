CREATE TABLE tasks
(
    id   SERIAL PRIMARY KEY,
    description TEXT,
    created TIMESTAMP,
    done bool,
    user_id int not null references users(id)
);

insert into tasks (id, description, created, done, user_id) values (1, 'Сходить в зал', localtimestamp(1), false, 1);
insert into tasks (id, description, created, done, user_id) values (2, 'Выучить джаву', localtimestamp(1), false, 1);
insert into tasks (id, description, created, done, user_id) values (3, 'Сделать работу', localtimestamp(1), false, 1);

insert into tasks (id, description, created, done, user_id) values (4, 'Выучить Jquery', localtimestamp(1), false, 2);
insert into tasks (id, description, created, done, user_id) values (5, 'Выучить Javascript', localtimestamp(1), false, 2);
insert into tasks (id, description, created, done, user_id) values (6, 'Заполнить ежедневник', localtimestamp(1), false, 2);

CREATE TABLE users
(
    id   SERIAL PRIMARY KEY,
    username TEXT,
    email TEXT,
    password TEXT
);

insert into users (id, username, email, password) values (1, 'Parfiry', 'parfiry@ya.ru', 'parf');
insert into users (id, username, email, password) values (2, 'Vsevolod', '1234@ya.ru', '12345');

