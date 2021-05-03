insert into book (id, name, description, author, type, classification,price, isbn)
values(1000, 'JAVA Programming', 'Coding', 'Ahmed', 'fiction', 'CLASS-1', 200, '13123-121-121');

insert into book(id, name, description, author, type, classification,price, isbn)
values(1001, 'C++ Programming', 'Coding', 'Junaid', 'comic', 'CLASS-1', 200, '13123-121-121');

insert into book_type 
values('fiction','0.10');

insert into book_type 
values('comic','0.0');

insert into book_classification 
values('CLASS-1','0.10');

insert into book_classification 
values('CLASS-2','0.0');

insert into book_promo_code 
values('PRO111','0.20');

insert into book_promo_code 
values('PRO444','0.10');
