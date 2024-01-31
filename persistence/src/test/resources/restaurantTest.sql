TRUNCATE TABLE restaurant RESTART IDENTITY AND COMMIT NO CHECK;

create sequence if not exists restaurant_restid_seq;

INSERT INTO restaurant (restid, email, password, phone_number, full_name, address, price, rating, cuisine, neighborhood, message, reservation, capacity, about, cant_reviews, image) VALUES (2, 'obrador@gmail.com', '$2a$10$yPgR6w6ie4FnVHE64EHQzOKBbB5i1ayDBolXKLKTI.xu5P8WImvCy', '1156637288', 'Obrador', 'Charlone 202', 1200, 0, 'Autoctono', 'Villa Crespo', 'Nuevo', false, 100, 'Hola, este es mi restaurante.', 0, null);
INSERT INTO restaurant (restid, email, password, phone_number, full_name, address, price, rating, cuisine, neighborhood, message, reservation, capacity, about, cant_reviews, image) VALUES (3, 'elebar@gmail.com', '$2a$10$jtHMoxI/yBaJHtYn123S5ODf5Ml7jlMx/nefysJIPOjhiMo0kQrem', '1145679890', 'EleBar', 'Nicaragua 5001', 850, 4, 'Japones', 'Palermo', 'Nuevo', false, 45, 'Hola, este es mi restaurante.', 2, null);
INSERT INTO restaurant (restid, email, password, phone_number, full_name, address, price, rating, cuisine, neighborhood, message, reservation, capacity, about, cant_reviews, image) VALUES (4, 'ejemplo2@gmail.com', 'pass2', '1145679830', 'Ejemplo2', 'Nicaragua 2222', 850, 5, 'Parrilla', 'Palermo', 'Nuevo', false, 45, 'Hola, este es mi restaurante.', 2, null);
INSERT INTO restaurant (restid, email, password, phone_number, full_name, address, price, rating, cuisine, neighborhood, message, reservation, capacity, about, cant_reviews, image) VALUES (5, 'ejemplo3@gmail.com', 'pass3', '1145679800', 'Ejemplo3', 'Nicaragua 3333', 850, 5, 'Argentino', 'Palermo', 'Nuevo', false, 45, 'Hola, este es mi restaurante.', 2, null);

TRUNCATE TABLE reviews RESTART IDENTITY AND COMMIT NO CHECK;

create sequence if not exists reviews_id_seq;

TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;

create sequence if not exists users_userid_seq;

INSERT INTO users(userId, username, email, password, enabled) values (1, 'fulano','fulano@gmail.com', 'password', true);
