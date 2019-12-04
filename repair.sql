CREATE DATABASE repair_agency;

USE repair_agency;

CREATE TABLE Users(
user_id int AUTO_INCREMENT NOT NULL,
first_name nvarchar(30) NOT NULL,
last_name nvarchar(40) NOT NULL,
`role` nvarchar(10) NOT NULL,
email nvarchar(50) NOT NULL,
`password` nvarchar(30) NOT NULL,

PRIMARY KEY(user_id),
CONSTRAINT unique_email UNIQUE KEY(email)
);

CREATE TABLE Applications(
application_id int AUTO_INCREMENT NOT NULL,
user_id int NOT NULL,
repair_details nvarchar(200) NOT NULL,
`status` nvarchar(10) NOT NULL, 
manager_id int,
price int,
manager_details nvarchar(200),

PRIMARY KEY(application_id),
FOREIGN KEY(user_id) REFERENCES Users(user_id)
ON DELETE CASCADE
ON UPDATE CASCADE,
FOREIGN KEY(manager_id) REFERENCES Users(user_id)
ON DELETE CASCADE
ON UPDATE CASCADE,
INDEX(`status`)
);

CREATE TABLE Feedbacks(
feedback_id int AUTO_INCREMENT NOT NULL,
application_id int NOT NULL,
master_id int NOT NULL,
feedback nvarchar(200) NOT NULL,

PRIMARY KEY(feedback_id),
FOREIGN KEY(master_id) REFERENCES Users(user_id)
ON DELETE CASCADE
ON UPDATE CASCADE,
INDEX(master_id)
);


INSERT INTO Users (first_name, last_name, `role`, email, `password`)
VALUES ('Polina', 'Tikhonova', 'user', 'polya@gmail.com', 'zhora' ),
('Yulia', 'Shcherbakova', 'manager', 'yulia@gmail.com', '111111' ),
('Коля', 'Сахарок', 'master', 'kolya@gmail.com', '000000' );

INSERT INTO Applications(user_id, repair_details, `status`, manager_id, price, manager_details)
VALUES (1, 'Vacuum cleaner tube repair needed!', 'created', null, null, null),
(1, 'Змінити обшивку крісла', 'accepted', 2, 200, null),
(1, 'Змінити вікно', 'finished', 2, 100, null),
(1, 'Change the light bulb', 'in_process', 2, 50, null),
(1, 'Change', 'refused', 2, null, 'Not enough details');

INSERT INTO Feedbacks(application_id, master_id, feedback)
VALUES (3, 3, 'Thank you!'),
(4, 3, 'VERY SLOW!');
