select * from user;

select * from user where first_name like ?;

insert into user (first_name, last_name, sex, email, date_of_birth) values ("Макс", "Васильчук", "male", "maximkaa@gmail.com", '2003-12-01');

select * from user_tax;

insert into user_tax (id_payment, user_id, tax_id, value, tax_amount, payment_date) 
values (321456, 4, 5, 1241.414, 310.3535, '2021-01-15');


select 	u.id, u.first_name, u.last_name, u.sex, u.email, u.date_of_birth, sum(tax_amount) as total_tax_amount
from user_tax ut 
left join user u
on user_id = id
group by ut.user_id
order by total_tax_amount;






