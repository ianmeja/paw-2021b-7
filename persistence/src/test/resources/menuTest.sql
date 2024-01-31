TRUNCATE TABLE restaurant RESTART IDENTITY AND COMMIT NO CHECK;

create sequence if not exists restaurant_restid_seq;

INSERT INTO restaurant (restid, email, password, phone_number, full_name, address, price, rating, cuisine, neighborhood, message, reservation, capacity, about, cant_reviews, image) VALUES (1, 'obrador@gmail.com', '$2a$10$yPgR6w6ie4FnVHE64EHQzOKBbB5i1ayDBolXKLKTI.xu5P8WImvCy', '1156637288', 'Obrador', 'Charlone 202', 1200, 0, 'Autoctono', 'Villa Crespo', 'Nuevo', false, 100, 'Hola, este es mi restaurante.', 0, null);

TRUNCATE TABLE menu RESTART IDENTITY AND COMMIT NO CHECK;

create sequence if not exists menu_dish_id_seq;

INSERT INTO menu (dish_id, category, description, dish,price,rest_id) VALUES (2,'MAIN','Con tuco','Fideos',500,1);