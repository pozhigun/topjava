DELETE
FROM user_roles;
DELETE
FROM users;
DELETE
FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (description, calories, date_time, user_id)
VALUES ('Завтрак', 500, '2020-05-30 08:00:00.000000', 100000),
       ('Обед', 1000, '2020-05-30 13:00:00.000000', 100000),
       ('Ужин', 500, '2020-05-30 20:00:00.000000', 100000),
       ('Завтрак', 1000, '2020-05-31 08:00:00.000000', 100001),
       ('Обед', 500, '2020-05-31 13:00:00.000000', 100001),
       ('Ужин', 510, '2020-05-31 20:00:00.000000', 100001);