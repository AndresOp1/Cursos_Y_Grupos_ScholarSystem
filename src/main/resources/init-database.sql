CREATE SCHEMA IF NOT EXISTS courses_groups;

CREATE TABLE IF NOT EXISTS courses_groups.roles (
    role_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS courses_groups.users (
    user_id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    full_name VARCHAR(200) NOT NULL,
    role BIGINT NOT NULL REFERENCES courses_groups.roles (role_id)
);

CREATE TABLE IF NOT EXISTS courses_groups.courses (
    code BIGSERIAL PRIMARY KEY,
    name VARCHAR(200) UNIQUE NOT NULL,
    credits INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS courses_groups.groups (
    group_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    teacher_id BIGINT REFERENCES courses_groups.users (user_id),
    course_code BIGINT REFERENCES courses_groups.courses (code)
);

CREATE TABLE courses_groups.inscriptions (
    group_id BIGINT REFERENCES courses_groups.groups (group_id),
    student_id BIGINT REFERENCES courses_groups.users (user_id),
    PRIMARY KEY (group_id, student_id)
)

CREATE TABLE IF NOT EXISTS courses_groups.schedules (
    schedule_id BIGSERIAL PRIMARY KEY,
    day VARCHAR(50) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    group_id BIGINT NOT NULL UNIQUE REFERENCES courses_groups.groups (group_id)
);