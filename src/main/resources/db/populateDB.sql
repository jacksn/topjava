DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (id, user_id, description, calories, datetime) VALUES
  (100100, 100000, 'Завтрак', 500,  '2015-05-30 10:00:00'),
  (100101, 100000, 'Обед',    1000, '2015-05-30 13:00:00'),
  (100102, 100000, 'Ужин',    500,  '2015-05-30 20:00:00'),
  (100103, 100000, 'Завтрак', 1000, '2015-05-31 10:00:00'),
  (100104, 100000, 'Обед',    500,  '2015-05-31 13:00:00'),
  (100105, 100000, 'Ужин',    510,  '2015-05-31 20:00:00'),
  (100106, 100001, 'Завтрак админа', 1000, '2015-05-30 10:00:00'),
  (100107, 100001, 'Обед админа',    500,  '2015-05-30 13:00:00'),
  (100108, 100001, 'Ужин админа',    510,  '2015-05-30 20:00:00');
