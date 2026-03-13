package com.example.learnly.entity.course;

import com.example.learnly.entity.BaseEntity;
import com.example.learnly.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "enrollments",
        uniqueConstraints = @UniqueConstraint(columnNames = {"course_id", "student_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false, updatable = false)
    private LocalDate enrollmentDate;

    private LocalDate completionDate;

    @Column
    private Integer progressPercentage;

    public enum Status {
        ACTIVE,
        COMPLETED,
        DROPPED
    }
}