CREATE TABLE users (
   id SERIAL PRIMARY KEY,
   username VARCHAR(32),
   email VARCHAR(32),
   password VARCHAR(32),
);

CREATE TABLE device_type (
    id SERIAL PRIMARY KEY,
    name VARCHAR(32) UNIQUE NOT NULL
);

CREATE TABLE room (
   id SERIAL PRIMARY KEY,
   name VARCHAR(32) UNIQUE NOT NULL
);

CREATE TABLE device (
    id SERIAL PRIMARY KEY,
    name VARCHAR (32) NOT NULL,
    type_id INTEGER REFERENCES device_type(id),
    room_id INTEGER REFERENCES room(id),
    user_id INTEGER REFERENCES users(id)
);

CREATE TABLE notification (
    id SERIAL PRIMARY KEY,
    device_id INTEGER REFERENCES device(id),
    user_id INTEGER REFERENCES users(id),
    time TIMESTAMP,
    text VARCHAR(256)
);

CREATE TABLE statistics (
   id SERIAL PRIMARY KEY,
   device_id INTEGER REFERENCES device(id),
   time TIMESTAMP NOT NULL,
   water_meter REAL,
   electricity_meter REAL
);

CREATE TABLE state_type (
    id SERIAL PRIMARY KEY,
    name VARCHAR(32) UNIQUE NOT NULL,
    description VARCHAR(128) UNIQUE NOT NULL
);

CREATE TABLE state (
    id SERIAL PRIMARY KEY,
    device_id INTEGER REFERENCES device(id),
    state_type_id INTEGER REFERENCES state_type(id),
    value varchar(32)
);

CREATE TABLE action_type (
    id SERIAL PRIMARY KEY,
    state_type_id INTEGER REFERENCES state_type(id),
    description VARCHAR(128),
    parameter_mode BOOLEAN
);

CREATE TABLE action (
    id SERIAL PRIMARY KEY,
    action_type_id INTEGER REFERENCES action_type(id),
    device_type_id INTEGER REFERENCES device_type(id)
);

CREATE TABLE condition (
    id SERIAL PRIMARY KEY,
    description VARCHAR(128)
);

CREATE TABLE script (
    id SERIAL PRIMARY KEY,
    device_id INTEGER REFERENCES device(id),
    condition_id INTEGER REFERENCES condition(id),
    action_id INTEGER REFERENCES action(id),
    condition_value VARCHAR(32),
    action_value VARCHAR(32),
    status INTEGER
);

