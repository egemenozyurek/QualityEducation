CREATE DATABASE edutrack;
USE edutrack;
 
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    role VARCHAR(20) NOT NULL
);
 
select * from users;
 
CREATE TABLE students (
    user_id INT PRIMARY KEY,
    student_no VARCHAR(20) UNIQUE,
    grade VARCHAR(10),
    points INT DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
 
CREATE TABLE teachers (
    user_id INT PRIMARY KEY,
    department VARCHAR(50),
    subject VARCHAR(100),
    FOREIGN KEY (user_id) REFERENCES users(id)
);
 
select * from users;
CREATE TABLE schedule (
    id INT PRIMARY KEY AUTO_INCREMENT,
    teacher_id INT,
    subject VARCHAR(100),
    topic VARCHAR(200),
    day_of_week INT, -- 1: Pazartesi, 2: Salı...
    start_time TIME,
    end_time TIME,
    classroom VARCHAR(20),
    difficulty INT DEFAULT 0,
    attended BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (teacher_id) REFERENCES users(id)
);
 
CREATE TABLE quiz (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    teacher_id INT,
    total_points DECIMAL(5,2),
    created_at DATE,
    FOREIGN KEY (teacher_id) REFERENCES users(id) 
);
 
CREATE TABLE questions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    quiz_id INT NOT NULL,
    question_text TEXT NOT NULL,
    option_a VARCHAR(200),
    option_b VARCHAR(200),
    option_c VARCHAR(200),
    option_d VARCHAR(200),
    correct_answer CHAR(1), -- 'A', 'B', 'C', 'D'
    points INT,
    FOREIGN KEY (quiz_id) REFERENCES quiz(id) 
);
 
CREATE TABLE exam_attempts (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    quiz_id INT NOT NULL,
    score DECIMAL(5,2),
    completed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (quiz_id) REFERENCES quiz(id) ON DELETE CASCADE
);
 
CREATE TABLE attendance (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT,
    schedule_id INT,
    date DATE,
    status VARCHAR(10), -- 'present', 'absent', 'late'
    FOREIGN KEY (student_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (schedule_id) REFERENCES schedule(id) ON DELETE CASCADE
);
 
CREATE TABLE adaptive_rules (
    id INT PRIMARY KEY AUTO_INCREMENT,
    rule_name VARCHAR(100) NOT NULL,
    condition_type VARCHAR(50),
    condition_value VARCHAR(500),
    action_type VARCHAR(50),
    priority INT DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE
);