INSERT INTO role(id, name)
VALUES (gen_random_uuid(), 'USER'),
       (gen_random_uuid(), 'ADMIN');

INSERT INTO account(name, last_name, email, password, phone_number)
VALUES ('Kernius', 'Sur', 'kernius.survila@gmail.com', '$2a$10$vADVzmQpknYQ7P1tUTNxv', '123456789');