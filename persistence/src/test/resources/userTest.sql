TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;

create sequence if not exists users_userid_seq;

INSERT INTO users(userId, username, email, password, enabled) values (2, 'fulano','fulano@gmail.com', 'password', true);
INSERT INTO users(userId, username, email, password, enabled) values (3, 'jose','jose@gmail.com', 'jose123', true);
INSERT INTO users(userId, username, email, password, enabled) values (4, 'julian','julian@gmail.com', '4uli1233', true);