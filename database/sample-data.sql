USE library_db;

INSERT IGNORE INTO users (id,name,email,password,role) VALUES
(1,'Library Admin','admin@library.com','admin123','ADMIN'),
(2,'Akshara Student','student@library.com','student123','STUDENT');

INSERT IGNORE INTO authors (id,name,biography) VALUES
(1,'Ramez Elmasri','Database systems author'),
(2,'Abraham Silberschatz','Computer science author'),
(3,'Robert C. Martin','Software engineering author'),
(4,'James Gosling','Java creator'),
(5,'Andrew S. Tanenbaum','Operating systems author');

INSERT IGNORE INTO categories (id,name) VALUES
(1,'Computer Science'),(2,'Programming'),(3,'Database'),(4,'Operating Systems'),(5,'Software Engineering');

INSERT IGNORE INTO publishers (id,name,email,phone,address) VALUES
(1,'Pearson','info@pearson.com','1800000001','New Delhi'),
(2,'McGraw Hill','info@mheducation.com','1800000002','Mumbai'),
(3,'Prentice Hall','info@ph.com','1800000003','Bengaluru');

INSERT IGNORE INTO members (id,user_id,name,email,phone,department,course,year_of_study,status) VALUES
(1,2,'Akshara Student','student@library.com','9876543210','CSE','B.Tech',3,'ACTIVE'),
(2,NULL,'Rahul Kumar','rahul@example.com','9876500001','CSE','B.Tech',2,'ACTIVE'),
(3,NULL,'Priya Sharma','priya@example.com','9876500002','ECE','B.Tech',3,'ACTIVE'),
(4,NULL,'Arjun Reddy','arjun@example.com','9876500003','IT','B.Tech',4,'ACTIVE'),
(5,NULL,'Sneha Rao','sneha@example.com','9876500004','CSE','B.Tech',1,'ACTIVE');

INSERT IGNORE INTO books (id,isbn,title,author_id,category_id,publisher_id,publication_year,total_copies,available_copies,shelf_number,description) VALUES
(1,'9780133970777','Fundamentals of Database Systems',1,3,1,2016,5,5,'A-01','Database fundamentals and design'),
(2,'9780073523323','Operating System Concepts',2,4,2,2018,4,4,'A-02','Core operating systems concepts'),
(3,'9780132350884','Clean Code',3,5,1,2008,5,5,'B-01','Practical software craftsmanship'),
(4,'9780134685991','Effective Java',4,2,1,2018,3,3,'B-02','Best practices for Java programming'),
(5,'9780133591620','Modern Operating Systems',5,4,3,2014,4,4,'A-03','Operating systems theory');

INSERT IGNORE INTO notices (id,title,description,notice_date,priority) VALUES
(1,'Library Hours','Library will remain open from 8 AM to 8 PM during examination week.',CURRENT_DATE,'HIGH'),
(2,'New Books','New programming and database books have been added to the library.',CURRENT_DATE,'NORMAL'),
(3,'Return Reminder','Students are requested to return overdue books as soon as possible.',CURRENT_DATE,'HIGH');
