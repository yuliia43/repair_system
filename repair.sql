CREATE DATABASE repair_agency;

USE repair_agency;

CREATE TABLE Users(
user_id int AUTO_INCREMENT NOT NULL,
first_name nvarchar(30) NOT NULL,
last_name nvarchar(40) NOT NULL,
`role` nvarchar(10) NOT NULL,
email nvarchar(50) NOT NULL,
`password` nvarchar(100) NOT NULL,
salt nvarchar(100) NOT NULL,

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
feedback nvarchar(200),

PRIMARY KEY(feedback_id),
FOREIGN KEY(master_id) REFERENCES Users(user_id)
ON DELETE CASCADE
ON UPDATE CASCADE,
INDEX(master_id)
);
