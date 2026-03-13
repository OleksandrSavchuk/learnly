CREATE TABLE courses
(
    id            BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title         VARCHAR(255),
    description   TEXT,
    instructor_id BIGINT    NOT NULL,
    created_at    TIMESTAMP NOT NULL,
    updated_at    TIMESTAMP,

    CONSTRAINT fk_course_instructor
        FOREIGN KEY (instructor_id)
            REFERENCES users (id)
            ON DELETE CASCADE
);

CREATE TABLE enrollments
(
    id                  BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    course_id           BIGINT      NOT NULL,
    student_id          BIGINT      NOT NULL,
    status              VARCHAR(20) NOT NULL,
    enrollment_date     DATE        NOT NULL,
    completion_date     DATE,
    progress_percentage INT,

    created_at          TIMESTAMP   NOT NULL,
    updated_at          TIMESTAMP,

    CONSTRAINT uq_course_student UNIQUE (course_id, student_id),

    CONSTRAINT fk_enrollments_course
        FOREIGN KEY (course_id)
            REFERENCES courses (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_enrollments_student
        FOREIGN KEY (student_id)
            REFERENCES users (id)
            ON DELETE CASCADE
);