-- ============================================
-- CMPE343 Project 2 - Group 11 Database Setup
-- Contact Management System Database
-- ============================================

CREATE DATABASE IF NOT EXISTS contactdb 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE contactdb;

-- ============================================
-- 1) CONTACTS TABLE
-- ============================================

DROP TABLE IF EXISTS contacts;

CREATE TABLE contacts (
    contact_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    middle_name VARCHAR(100) NULL,
    last_name VARCHAR(100) NOT NULL,
    nickname VARCHAR(100) NULL,
    phone_primary VARCHAR(20) NOT NULL,
    phone_secondary VARCHAR(20) NULL,
    email VARCHAR(255) NOT NULL,
    linkedin_url VARCHAR(500) NULL,
    birth_date DATE NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_first_name (first_name),
    INDEX idx_last_name (last_name),
    INDEX idx_email (email),
    INDEX idx_phone (phone_primary)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- 2) USERS TABLE
-- ============================================

DROP TABLE IF EXISTS users;

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    surname VARCHAR(100) NOT NULL,
    role ENUM('Tester', 'Junior', 'Senior', 'Manager') NOT NULL,
    employment_status ENUM('Employed', 'Fired') NOT NULL DEFAULT 'Employed',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_username (username),
    INDEX idx_role (role)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- 3) UNDO OPERATIONS TABLE
-- ============================================

DROP TABLE IF EXISTS undo_operations;

CREATE TABLE undo_operations (
    operation_id INT AUTO_INCREMENT PRIMARY KEY,
    operation_type ENUM('ADD', 'UPDATE', 'DELETE') NOT NULL,
    entity_type ENUM('CONTACT', 'USER') NOT NULL,
    entity_id INT NOT NULL,
    entity_data JSON NULL,  -- Stores the old state (for UPDATE/DELETE) or null (for ADD)
    operation_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id INT NULL,  
    INDEX idx_timestamp (operation_timestamp DESC),
    INDEX idx_entity (entity_type, entity_id),
    INDEX idx_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- 4) INSERT REQUIRED USERS
-- ============================================
-- Passwords are stored as plain text initially.
-- The application (AuthService) will automatically upgrade them to BCrypt hashes on first login.
-- Default passwords: username = password (e.g., 'tt' user has password 'tt')

INSERT INTO users (username, password_hash, name, surname, role, employment_status) VALUES
('tt', 'tt', 'Test', 'User', 'Tester', 'Employed'),
('jd', 'jd', 'Junior', 'Developer', 'Junior', 'Employed'),
('sd', 'sd', 'Senior', 'Developer', 'Senior', 'Employed'),
('man', 'man', 'Manager', 'User', 'Manager', 'Employed');

-- ============================================
-- 5) INSERT 50 CONTACTS FOR TESTING
-- ============================================

INSERT INTO contacts (first_name, middle_name, last_name, nickname, phone_primary, phone_secondary, email, linkedin_url, birth_date) VALUES
('Sarah', NULL, 'Nauman', 'srahz', '5057539581', NULL, 'sarahnauman15@gmail.com', 'linkedin.com/in/sarah-nauman-741788266', '2003-02-15'),
('Emily', 'Sharon', 'Smith', 'Emmy', '5551234567', NULL, 'emmysmith@hotmail.com', NULL, '1990-08-22'),
('Ahmet', 'Mehmet', 'Yılmaz', 'Ahmo', '5321234567', '2125551234', 'ahmet.yilmaz@email.com', 'linkedin.com/in/ahmet-yilmaz', '1990-05-20'),
('Ayşe', NULL, 'Demir', 'Ayşe', '5332345678', NULL, 'ayse.demir@email.com', 'linkedin.com/in/ayse-demir', '1992-08-15'),
('Mehmet', 'Ali', 'Kaya', 'Memo', '5343456789', '2165552345', 'mehmet.kaya@email.com', NULL, '1988-11-30'),
('Fatma', NULL, 'Şahin', 'Fatma', '5354567890', NULL, 'fatma.sahin@email.com', 'linkedin.com/in/fatma-sahin', '1995-03-10'),
('Mustafa', 'Kemal', 'Çelik', 'Musti', '5365678901', '3125553456', 'mustafa.celik@email.com', 'linkedin.com/in/mustafa-celik', '1987-07-25'),
('Zeynep', NULL, 'Arslan', 'Zeyno', '5376789012', NULL, 'zeynep.arslan@email.com', NULL, '1993-12-05'),
('Ali', 'Veli', 'Öztürk', 'Ali', '5387890123', '2325554567', 'ali.ozturk@email.com', 'linkedin.com/in/ali-ozturk', '1991-09-18'),
('Elif', NULL, 'Yıldız', 'Elif', '5398901234', NULL, 'elif.yildiz@email.com', 'linkedin.com/in/elif-yildiz', '1994-04-22'),
('Hasan', 'Hüseyin', 'Aydın', 'Hasan', '5309012345', '2425555678', 'hasan.aydin@email.com', NULL, '1989-06-14'),
('Selin', NULL, 'Kurt', 'Selin', '5310123456', NULL, 'selin.kurt@email.com', 'linkedin.com/in/selin-kurt', '1996-10-08'),
('Burak', NULL, 'Doğan', 'Burak', '5321234567', '3125556789', 'burak.dogan@email.com', 'linkedin.com/in/burak-dogan', '1990-01-12'),
('Deniz', NULL, 'Özdemir', 'Deniz', '5332345678', NULL, 'deniz.ozdemir@email.com', NULL, '1992-07-03'),
('Emre', 'Can', 'Şimşek', 'Emre', '5343456789', '2165557890', 'emre.simsek@email.com', 'linkedin.com/in/emre-simsek', '1988-11-27'),
('Gizem', NULL, 'Türk', 'Gizem', '5354567890', NULL, 'gizem.turk@email.com', 'linkedin.com/in/gizem-turk', '1995-05-19'),
('İbrahim', 'Yusuf', 'Koç', 'İbo', '5365678901', '2325558901', 'ibrahim.koc@email.com', NULL, '1987-09-11'),
('Kübra', NULL, 'Akar', 'Kübra', '5376789012', NULL, 'kubra.akar@email.com', 'linkedin.com/in/kubra-akar', '1993-02-28'),
('Onur', NULL, 'Bulut', 'Onur', '5387890123', '2425559012', 'onur.bulut@email.com', 'linkedin.com/in/onur-bulut', '1991-08-06'),
('Pınar', NULL, 'Erdoğan', 'Pınar', '5398901234', NULL, 'pinar.erdogan@email.com', NULL, '1994-12-24'),
('Serkan', 'Murat', 'Güneş', 'Serkan', '5309012345', '3125550123', 'serkan.gunes@email.com', 'linkedin.com/in/serkan-gunes', '1989-04-17'),
('Tuğba', NULL, 'Kılıç', 'Tuğba', '5310123456', NULL, 'tugba.kilic@email.com', 'linkedin.com/in/tugba-kilic', '1996-06-09'),
('Umut', NULL, 'Yavuz', 'Umut', '5321234567', '2165551234', 'umut.yavuz@email.com', NULL, '1990-10-31'),
('Yasemin', NULL, 'Çakır', 'Yasemin', '5332345678', NULL, 'yasemin.cakir@email.com', 'linkedin.com/in/yasemin-cakir', '1992-03-13'),
('Cem', 'Berk', 'Özkan', 'Cem', '5343456789', '2325552345', 'cem.ozkan@email.com', 'linkedin.com/in/cem-ozkan', '1988-07-26'),
('John', 'Michael', 'Smith', 'Johnny', '5551234567', '5559876543', 'john.smith@email.com', 'linkedin.com/in/john-smith', '1985-03-15'),
('Emily', NULL, 'Johnson', 'Em', '5552345678', NULL, 'emily.johnson@email.com', 'linkedin.com/in/emily-johnson', '1990-08-22'),
('Michael', 'James', 'Williams', 'Mike', '5553456789', '5558765432', 'michael.williams@email.com', NULL, '1987-11-05'),
('Sarah', 'Elizabeth', 'Brown', 'Sarah', '5554567890', NULL, 'sarah.brown@email.com', 'linkedin.com/in/sarah-brown', '1992-04-18'),
('David', 'Robert', 'Jones', 'Dave', '5555678901', '5557654321', 'david.jones@email.com', 'linkedin.com/in/david-jones', '1986-09-30'),
('Jessica', NULL, 'Garcia', 'Jess', '5556789012', NULL, 'jessica.garcia@email.com', NULL, '1991-12-12'),
('Christopher', 'Paul', 'Miller', 'Chris', '5557890123', '5556543210', 'christopher.miller@email.com', 'linkedin.com/in/christopher-miller', '1988-06-25'),
('Amanda', NULL, 'Davis', 'Mandy', '5558901234', NULL, 'amanda.davis@email.com', 'linkedin.com/in/amanda-davis', '1993-01-08'),
('Daniel', 'Thomas', 'Rodriguez', 'Dan', '5559012345', '5555432109', 'daniel.rodriguez@email.com', NULL, '1989-07-14'),
('Jennifer', NULL, 'Martinez', 'Jen', '5550123456', NULL, 'jennifer.martinez@email.com', 'linkedin.com/in/jennifer-martinez', '1994-10-27'),
('Matthew', NULL, 'Hernandez', 'Matt', '5551234509', '5554321098', 'matthew.hernandez@email.com', 'linkedin.com/in/matthew-hernandez', '1990-02-19'),
('Lisa', NULL, 'Lopez', 'Lisa', '5552345601', NULL, 'lisa.lopez@email.com', NULL, '1992-05-03'),
('Andrew', 'William', 'Wilson', 'Andy', '5553456702', '5553210987', 'andrew.wilson@email.com', 'linkedin.com/in/andrew-wilson', '1987-08-16'),
('Michelle', NULL, 'Anderson', 'Shelly', '5554567803', NULL, 'michelle.anderson@email.com', 'linkedin.com/in/michelle-anderson', '1995-11-29'),
('James', 'Edward', 'Thomas', 'Jim', '5555678904', '5552109876', 'james.thomas@email.com', NULL, '1986-03-12'),
('Ashley', NULL, 'Taylor', 'Ash', '5556789015', NULL, 'ashley.taylor@email.com', 'linkedin.com/in/ashley-taylor', '1991-06-24'),
('Robert', 'Charles', 'Moore', 'Rob', '5557890126', '5551098765', 'robert.moore@email.com', 'linkedin.com/in/robert-moore', '1988-09-07'),
('Stephanie', NULL, 'Jackson', 'Steph', '5558901237', NULL, 'stephanie.jackson@email.com', NULL, '1993-12-20'),
('William', 'Joseph', 'Martin', 'Will', '5559012348', '5550987654', 'william.martin@email.com', 'linkedin.com/in/william-martin', '1989-04-02'),
('Nicole', NULL, 'Lee', 'Nikki', '5550123459', NULL, 'nicole.lee@email.com', 'linkedin.com/in/nicole-lee', '1994-07-15'),
('Joseph', NULL, 'Thompson', 'Joe', '5551234560', '5559876543', 'joseph.thompson@email.com', NULL, '1990-10-28'),
('Melissa', NULL, 'White', 'Mel', '5552345671', NULL, 'melissa.white@email.com', 'linkedin.com/in/melissa-white', '1992-01-11'),
('Richard', 'Anthony', 'Harris', 'Rick', '5553456782', '5558765432', 'richard.harris@email.com', 'linkedin.com/in/richard-harris', '1987-05-24'),
('Kimberly', NULL, 'Clark', 'Kim', '5554567893', NULL, 'kimberly.clark@email.com', NULL, '1995-08-06'),
('Charles', 'Mark', 'Lewis', 'Chuck', '5555678904', '5557654321', 'charles.lewis@email.com', 'linkedin.com/in/charles-lewis', '1986-11-19');

-- ============================================
-- 6) VERIFICATION QUERIES
-- ============================================

-- Verify users
SELECT COUNT(*) AS total_users FROM users;
SELECT * FROM users;

-- Verify contacts
SELECT COUNT(*) AS total_contacts FROM contacts;

-- Verify table structures
SHOW TABLES;
DESCRIBE users;
DESCRIBE contacts;
DESCRIBE undo_operations;


