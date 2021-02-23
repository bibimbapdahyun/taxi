-- DROP SCHEMA IF EXISTS taxi;
-- CREATE DATABASE IF NOT EXISTS taxi DEFAULT CHARACTER SET utf8;

USE taxi;

DROP TABLE IF EXISTS trip_car CASCADE;
DROP TABLE IF EXISTS trip CASCADE;
DROP TABLE IF EXISTS car CASCADE;
DROP TABLE IF EXISTS account CASCADE;
DROP TABLE IF EXISTS state CASCADE;
DROP TABLE IF EXISTS role CASCADE;
DROP TABLE IF EXISTS trip_state CASCADE;
DROP TABLE IF EXISTS car_type CASCADE;
DROP TABLE IF EXISTS gender CASCADE;



CREATE TABLE IF NOT EXISTS gender (
id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(32) NOT NULL UNIQUE
);

INSERT INTO gender (id, name) VALUES (DEFAULT, 'male'),(DEFAULT, 'female'), (DEFAULT, 'other');

CREATE TABLE IF NOT EXISTS role (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_role VARCHAR(10) UNIQUE NOT NULL
);

INSERT INTO role (id, user_role) VALUES (DEFAULT, 'admin');
INSERT INTO role (id, user_role) VALUES (DEFAULT, 'manager');
INSERT INTO role (id, user_role) VALUES (DEFAULT, 'driver');
INSERT INTO role (id, user_role) VALUES (DEFAULT, 'user');

CREATE TABLE IF NOT EXISTS car_type(
	id INT PRIMARY KEY AUTO_INCREMENT,
    type VARCHAR(32) NOT NULL UNIQUE,
    start_price DECIMAL NOT NULL,
    price_per_km DECIMAL NOT NULL
);

INSERT INTO car_type (id, type, start_price, price_per_km) VALUES (1, 'business', 50, 9);
INSERT INTO car_type (id, type, start_price, price_per_km) VALUES (2, 'econom', 25, 8);

CREATE TABLE IF NOT EXISTS state(
	id INT PRIMARY KEY AUTO_INCREMENT,
    state VARCHAR(32) NOT NULL UNIQUE DEFAULT 1
);

INSERT INTO state (id, state) VALUES (1, 'inactive');
INSERT INTO state (id, state) VALUES (2, 'intrip');
INSERT INTO state (id, state) VALUES (3, 'waiting');

CREATE TABLE IF NOT EXISTS trip_state(
	id INT PRIMARY KEY AUTO_INCREMENT,
    state VARCHAR(32) NOT NULL UNIQUE
);

INSERT INTO trip_state (id, state) VALUES (1, 'finished');
INSERT INTO trip_state (id, state) VALUES (2, 'intrip');
INSERT INTO trip_state (id, state) VALUES (3, 'canceled');

CREATE TABLE IF NOT EXISTS account (
    id INT PRIMARY KEY AUTO_INCREMENT,
    login VARCHAR(12) NOT NULL UNIQUE,
    password VARCHAR(1024) NOT NULL,
    role_id INT NOT NULL,
    mail VARCHAR(64) UNIQUE,
    name VARCHAR(1024),
    surname VARCHAR(1024),
    gender_id INT NOT NULL,
    CONSTRAINT `fk_account_role_id` FOREIGN KEY (`role_id`)
        REFERENCES `role` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT `fk_account_gender_id` FOREIGN KEY (`gender_id`)
        REFERENCES `gender` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE        
);

INSERT INTO account (id, login, password, role_id, mail, name, surname, gender_id) VALUES (1, '380666658673', 'DD59133853039C962704801CCECEDBC79EE7EE9B10C06DFD61F427CA9AD7996A955B0DAC29E2FE402E1F6BE6F8BA85F17642421AB5657C20CBB83301D1AA5798', 1, null, 'Admin', 'Admin', 1);
INSERT INTO account (id, login, password, role_id, mail, name, surname,gender_id) VALUES (2, '380666658653', '2F3ABD3E52EACD696DBC96D4737B31D3AADB2877F28ABF2B5F091AC5D2AAFA2DB38FA4AF83F4BEB0657F8B9D3CDDFB3DA18C48CF3FC8210ABAB9515C21525D14', 2, null, 'Manager', 'Manager', 2);
INSERT INTO account (id, login, password, role_id, mail, name, surname,gender_id) VALUES (3, '381000000001', '40393B09D7598C46D25B06A237F7ACFC7F1E8432271233379667661BAF4BB5D65FB42A710ABE088953501F21CCAD5FC315F8E9176F155856108FFACBE6D150BB', 3, null, 'Driver1', 'Deriver1',1);
INSERT INTO account (id, login, password, role_id, mail, name, surname,gender_id) VALUES (4, '381000000002', '61DB40B57055A548329C65A94F7482BD62A64A5695122F6DBB25D093F92DB557B01B70F6771FD8E01BFD57D6B4A0FFEFD35B4EC1684E79705A5C06A169100800', 3, null, 'Driver2', 'Deriver2',2);
INSERT INTO account (id, login, password, role_id, mail, name, surname,gender_id) VALUES (5, '381000000003', '7A3B636EB632076AEA169E1B3F33BEEB2CE0E69A40D2A0730BC8F3A1C4A7F492C3FBDD8BC929F298602C89665B70EA862AFCB07BD75F5A1F75E49B3B5883079B', 3, null, 'Driver3', 'Deriver3',2);
INSERT INTO account (id, login, password, role_id, mail, name, surname,gender_id) VALUES (6, '381000000004', '526730FB08851B6585B1BA09016BCC65E7D21ADABCE8691654D615C2E289E76FF391D8FBA9C2463A3D071D83C9B4F7A5C4BA7579E7868EF52FF4ADD259BB7BA7', 3, null, 'Driver4', 'Deriver4',2);
INSERT INTO account (id, login, password, role_id, mail, name, surname,gender_id) VALUES (7, '381000000005', 'FF73C72064AC462DAB25D24EF5E7E8D460E3793C2EC6E699809EDA5C815310FE395BFE9D45479B16CBA8105AA5FA90C5A112734F66FDB5078C2712A960351E85', 3, null, 'Driver5', 'Deriver5',2);
INSERT INTO account (id, login, password, role_id, mail, name, surname,gender_id) VALUES (8, '380000000001', 'B9861D40757914AA43B428EF1E51170C024146015691ED6288C6CB114AE19F183D3FFA810D41B7E8601BECBB933F06E5FBB1A617D5F8FF6AD53C49A619713D7C', 4, null, 'User', 'User',2);
INSERT INTO account (id, login, password, role_id, mail, name, surname,gender_id) VALUES (9, '380000000002', 'DBD00D24DF218535E95007C00DF517BEA4C9F967D8F747A761C46091B416C31F6826124F9D6C947A12C011EA50C5B0BEE8DB5A4B4C8782EED292DAE4BD5517A2', 4, null, null, null, 3);

CREATE TABLE IF NOT EXISTS car (
    id INT PRIMARY KEY AUTO_INCREMENT,
    car_number VARCHAR(10) NOT NULL,
    mark VARCHAR(32) NOT NULL,
    places INT NOT NULL,
    account_id INT NOT NULL,
    state_id INT NOT NULL DEFAULT 1,
    car_type_id INT NOT NULL,
    CONSTRAINT `fk_car_account_id` FOREIGN KEY (account_id)
        REFERENCES account (id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_car_state_id` FOREIGN KEY (state_id)
        REFERENCES state (id)
        ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT `fk_car_car_type_id` FOREIGN KEY (car_type_id)
        REFERENCES car_type (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO car (id, car_number, mark, places, account_id, state_id, car_type_id) VALUES (DEFAULT, 'AX3232DB', 'Dewo Lanos', 6, 3, 3, 2);
INSERT INTO car (id, car_number, mark, places, account_id, state_id, car_type_id) VALUES (DEFAULT, 'AC6666DB', 'Shkoda Octavia', 5, 4,3, 2);
INSERT INTO car (id, car_number, mark, places, account_id, state_id, car_type_id) VALUES (DEFAULT, 'AX1525AC', 'Reno TATA', 3, 5, 3, 1);
INSERT INTO car (id, car_number, mark, places, account_id, state_id, car_type_id) VALUES (DEFAULT, 'BB3396DB', 'Opel LALA', 4, 6, 3, 2);
INSERT INTO car (id, car_number, mark, places, account_id, state_id, car_type_id) VALUES (DEFAULT, 'SA3262AX', 'Lala TT', 6, 7, 3, 1);

CREATE TABLE IF NOT EXISTS trip(
	id INT PRIMARY KEY AUTO_INCREMENT,
    start VARCHAR(1024) NOT NULL,
    finish VARCHAR(1024) NOT NULL,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	price DECIMAL NOT NULL,
    account_id INT,
    people INT NOT NULL,
    car_type_id INT NOT NULL,
    state_id INT NOT NULL DEFAULT 2,
    CONSTRAINT `fk_trip_account_id` FOREIGN KEY (account_id)
        REFERENCES account (id)
        ON DELETE SET NULL ON UPDATE CASCADE,
	CONSTRAINT `fk_trip_state_id` FOREIGN KEY (state_id)
        REFERENCES trip_state (id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_car_type_id` FOREIGN KEY (car_type_id)
        REFERENCES car_type (id)
        ON DELETE RESTRICT ON UPDATE CASCADE
);

INSERT INTO trip (id, start, finish, create_date, price, account_id, people,car_type_id, state_id) VALUES 	
(DEFAULT, 'beberevo, 16', 'htz, 25', DEFAULT, 425, 5, 3, 2, DEFAULT), 
(DEFAULT, 'asdsf, 142', 'adfgd, 42', DEFAULT, 125, 5, 6,1, DEFAULT),
(DEFAULT, 'bgfg, 6', 'dghhtb', DEFAULT, 75, 5, 5,1, DEFAULT),
(DEFAULT, 'bbfbfbf, 23A', 'sdagrhr, 32', DEFAULT, 225, 5, 5,2, DEFAULT),
(DEFAULT, 'ghfg, 5', 'sdvvbhmj, 5', DEFAULT, 100, 5, 6,1, DEFAULT),
(DEFAULT, 'ghsdadfg, 3', 'ssadf, 74', DEFAULT, 125, 5, 6,2, DEFAULT),
(DEFAULT, 'ghfg, 3', 'sdvv, 11', DEFAULT, 130, 5, 5,2, DEFAULT),
(DEFAULT, 'ghfg, 5', 'sdvvbhmj, 23', DEFAULT, 100, 5, 4,1, DEFAULT),
(DEFAULT, 'rarra, 13', 'sadvfry, 6', DEFAULT, 200, 5, 4,2, DEFAULT);

CREATE TABLE IF NOT EXISTS trip_car(
	car_id INT NOT NULL,
    trip_id INT NOT NULL,
    PRIMARY KEY(car_id, trip_id),
	CONSTRAINT `fk_trip_car_car` FOREIGN KEY (car_id)
        REFERENCES car (id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_trip_car_trip` FOREIGN KEY (trip_id)
        REFERENCES trip (id)
        ON DELETE RESTRICT ON UPDATE CASCADE
);

-- INSERT INTO trip_car(car_id, trip_id) VALUES 
-- (1,1),
-- (1,2),
-- (2,3),
-- (3,5),
-- (4,6),
-- (3,4),
-- (5,7),
-- (6,8),
-- (3,9);

