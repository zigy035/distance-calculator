INSERT INTO auth_user (id, name, username, password, role, create_date) VALUES
(1, 'Administrator', 'admin', '$2a$10$/7t/Cfpji13XEfwXmeLALeRkDMrsz1kPd49aRWtWvSoWJFlm6mwF.', 'ADMIN', NOW()),
(2, 'Regular user', 'user', '$2a$10$W4i5MBBZTz7h5GiniEcmyuTkLy1E1ceY2R3uSf/fTThHrNMQavJ7e', 'USER', NOW());

INSERT INTO postcode_coordinate (id, postcode, latitude, longitude) VALUES
(1, 'AB10 1XG', 57.144156, -2.114864),
(2, 'AB21 0AL', 57.263206, -2.158990),
(3, 'BN91 9AA', 0.000000, 0.000000);
