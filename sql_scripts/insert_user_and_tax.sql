select * from user;

insert into user(first_name, last_name, email, sex, date_of_birth)
values
("Катерина", "Кондратюк", "catrinnn@gmail.com", "female", '2003-10-23'),
("Анна", "Мамчур", "anna_mamchur@gmail.com", "female", '1993-12-30'),
("Саша", "Каплун", "sashenka@gmail.com", "female", '1994-04-27'),
("Марк", "Смоляк", "markooooo@gmail.com", "male", '1994-06-27'),
("Світлана", "Кондрацька", "kondrtskiiaa@gmail.com", "female", '1996-05-06'),
("Світлана", "Морозюк", "svetka_ua@gmail.com", "female", '1997-07-23'),
("Віктор", "Чернятинський", "vitiok_cherry@gmail.com", "male", '1998-04-15'),
("Олександр", "Васильчук", "sanok@gmail.com", "male", '1999-11-16'),
("Анастасія", "Садовська", "nasstasia33@gmail.com", "female", '2000-02-18'),
("Богдан", "Карасійчук", "bodichkakar@gmail.com", "male", '2000-04-17'),
("Назар", "Галькович", "nazar_galka@gmail.com", "male", '2000-07-13');


select * from tax_type;

insert into tax_type(id, tax_name, tax_multiplier)
values
(1, "Податок на дохід з основного та додаткового місць роботи", 0.49),
(2, "Податок на подарунки", 0.15),
(3, "Податок на авторські винагороди", 0.3),
(4, "Податок на продаж майна", 0.2),
(5, "Податок на перекази з-за кордону", 0.25),
(6, "Податок пільг на дітей", 0.1),
(7, "Податок на матеріальну допомогу", 0.05);

select * from user_tax;

insert into user_tax (id_payment, user_id, tax_id, value, tax_amount, payment_date) 
values 
(23546, 1, 1, 6500.0, 3185.0, '2022-02-04'),
(67854, 1, 7, 3412.0, 170.6, '2022-02-08'),
(12342, 1, 3, 120.0, 36.0, '2022-05-18'),
(12341, 2, 1, 12349.43, 6051.22, '2022-07-15'),
(43213, 3, 2, 30000.0, 4500.0, '2022-09-27');