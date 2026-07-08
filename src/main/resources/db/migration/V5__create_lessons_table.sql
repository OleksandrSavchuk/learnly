CREATE TABLE lessons
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title       VARCHAR(255),
    description TEXT,
    course_id   BIGINT    NOT NULL,

    created_at  TIMESTAMP NOT NULL,
    updated_at  TIMESTAMP,

    CONSTRAINT fk_lessons_course
        FOREIGN KEY (course_id)
            REFERENCES courses (id)
            ON DELETE CASCADE
);