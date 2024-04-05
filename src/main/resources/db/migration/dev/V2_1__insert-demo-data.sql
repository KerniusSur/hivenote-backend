INSERT INTO role(id, name)
VALUES (gen_random_uuid(), 'USER'),
       (gen_random_uuid(), 'ADMIN');

INSERT INTO account(name, last_name, email, password, phone_number)
VALUES ('Kernius', 'Sur', 'kernius.survila@gmail.com', '$2a$10$XgXwTP4l7N43Q64BGA8gve/ZqadAGCNTHBTUrih3nGpBf4WRy3hay',
        '123456789');

-- Pass: password123