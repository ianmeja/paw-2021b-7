TRUNCATE TABLE restaurant RESTART IDENTITY AND COMMIT NO CHECK;

create sequence if not exists restaurant_restid_seq;

INSERT INTO restaurant (restid, email, password, phone_number, full_name, address, price, rating, cuisine, neighborhood, message, reservation, capacity, about, cant_reviews, image) VALUES (1, 'obrador@gmail.com', '$2a$10$yPgR6w6ie4FnVHE64EHQzOKBbB5i1ayDBolXKLKTI.xu5P8WImvCy', '1156637288', 'Obrador', 'Charlone 202', 1200, 0, 'Autoctono', 'Villa Crespo', 'Nuevo', false, 100, 'Hola, este es mi restaurante.', 0, null);

TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;

create sequence if not exists users_userid_seq;

INSERT INTO users(userId, username, email, password, enabled) values (1, 'fulano','fulano@gmail.com', 'password', true);

TRUNCATE TABLE bookings RESTART IDENTITY AND COMMIT NO CHECK;

create sequence if not exists bookings_booking_id_seq;

INSERT INTO bookings (booking_id, confirmed, date, diners, time, user_id, rest_id) VALUES (2, false, '2021-11-10', 3, '22:00', 1, 1);
