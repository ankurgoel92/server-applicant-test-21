/**
 * CREATE Script for init of DB
 */

 -- Create drivers in user table
 
insert into user (id,date_created, deleted, password, username) values (1, now(), false, 'driver01pw', 'driver01');
insert into user (id,date_created, deleted, password, username) values (2, now(), false, 'driver02pw', 'driver02');
insert into user (id,date_created, deleted, password, username) values (3, now(), false, 'driver03pw', 'driver03');
insert into user (id,date_created, deleted, password, username) values (4, now(), false, 'driver04pw', 'driver04');
insert into user (id,date_created, deleted, password, username) values (5, now(), false, 'driver05pw', 'driver05');
insert into user (id,date_created, deleted, password, username) values (6, now(), false, 'driver06pw', 'driver06');
insert into user (id,date_created, deleted, password, username) values (7, now(), false, 'driver07pw', 'driver07');
insert into user (id,date_created, deleted, password, username) values (8, now(), false, 'driver08pw', 'driver08');

-- Create 3 admin users

insert into user (id, date_created, deleted, password, username) values (9, now(), false, 'admin01pw', 'admin01');
insert into user (id, date_created, deleted, password, username) values (10, now(), false, 'admin01pw', 'admin02');
insert into user (id, date_created, deleted, password, username) values (11, now(), false, 'admin01pw', 'admin03');

-- Create 3 roles

insert into role (id, date_created, deleted, authority) values (1, now(), false, 'ROLE_ADMIN');
insert into role (id, date_created, deleted, authority) values (2, now(), false, 'ROLE_DRIVER');

-- Create driver Role Mapping
insert into user_role (user_id, role_id) values (1, 2);
insert into user_role (user_id, role_id) values (2, 2);
insert into user_role (user_id, role_id) values (3, 2);
insert into user_role (user_id, role_id) values (4, 2);
insert into user_role (user_id, role_id) values (5, 2);
insert into user_role (user_id, role_id) values (6, 2);
insert into user_role (user_id, role_id) values (7, 2);
insert into user_role (user_id, role_id) values (8, 2);

-- Create admin Role Mapping

insert into user_role (user_id, role_id) values (9, 1);
insert into user_role (user_id, role_id) values (10, 1);
insert into user_role (user_id, role_id) values (11, 1);

-- Create 3 OFFLINE drivers

insert into driver (id, online_status) values (1, 'OFFLINE');
insert into driver (id, online_status) values (2, 'OFFLINE');
insert into driver (id, online_status) values (3, 'OFFLINE');

-- Create 3 ONLINE drivers
insert into driver (id, online_status) values (4, 'ONLINE');
insert into driver (id, online_status) values (5, 'ONLINE');
insert into driver (id, online_status) values (6, 'ONLINE');

-- Create 1 OFFLINE driver with coordinate(longitude=9.5&latitude=55.954)

insert into driver (id, coordinate, date_coordinate_updated, online_status) values (7, 'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704023000000000000404bfa1cac083127', now(), 'OFFLINE');

-- Create 1 ONLINE driver with coordinate(longitude=9.5&latitude=55.954)

insert into driver (id, coordinate, date_coordinate_updated, online_status) values (8, 'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704023000000000000404bfa1cac083127', now(), 'ONLINE');

-- Create 3 Manufacturers

insert into manufacturer(id, name, date_created, deleted) values (1,'Hyundai', now(), false);
insert into manufacturer(id, name, date_created, deleted) values (2,'BMW', now(), false);
insert into manufacturer(id, name, date_created, deleted) values (3,'AUDI', now(), false);

-- Create 3 Cars
insert into car (id,model,color,license_plate,seat_count,convertible,rating,engine_type,transmission,car_type,manufacturer_id,date_created, deleted) 
values (1, 'i10', 'Blue', 'CAR-001', 5, false, 3, 'PETROL', 'MANUAL', 'SMALL', 1, now(), false);

insert into car (id,model,color,license_plate,seat_count,convertible,rating,engine_type,transmission,car_type,manufacturer_id,date_created, deleted) 
values (2, 'X6', 'White', 'CAR-002', 8, true, 9, 'DIESEL', 'AUTOMATIC', 'SUV', 2, now(), false);

insert into car (id,model,color,license_plate,seat_count,convertible,rating,engine_type,transmission,car_type,manufacturer_id,date_created, deleted) 
values (3, 'A3', 'White', 'CAR-003', 5, false, 7, 'DIESEL', 'MANUAL', 'MEDIUM', 3, now(), false);


-- Cars With Driver Associated
insert into car (id,model,color,license_plate,seat_count,convertible,rating,engine_type,transmission,car_type,manufacturer_id,date_created, deleted,driver_id) 
values (4, 'A4', 'White', 'CAR-004', 5, false, 8, 'DIESEL', 'MANUAL', 'MEDIUM', 3, now(), false, 4);

insert into car (id,model,color,license_plate,seat_count,convertible,rating,engine_type,transmission,car_type,manufacturer_id,date_created, deleted,driver_id)
values (5, 'X6', 'White', 'CAR-005', 8, true, 9, 'DIESEL', 'AUTOMATIC', 'SUV', 2, now(), false, 5);

insert into car (id,model,color,license_plate,seat_count,convertible,rating,engine_type,transmission,car_type,manufacturer_id,date_created, deleted,driver_id) 
values (6, 'i10', 'Blue', 'CAR-006', 5, false, 3, 'PETROL', 'MANUAL', 'SMALL', 1, now(), false, 6);