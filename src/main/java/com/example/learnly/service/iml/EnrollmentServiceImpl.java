package com.example.learnly.service.iml;

import com.example.learnly.entity.course.Course;
import com.example.learnly.entity.course.Enrollment;
import com.example.learnly.entity.user.User;
import com.example.learnly.exception.ResourceNotFoundException;
import com.example.learnly.repository.EnrollmentRepository;
import com.example.learnly.service.CourseService;
import com.example.learnly.service.EnrollmentService;
import com.example.learnly.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final CourseService courseService;

    private final UserService userService;

    private final EnrollmentRepository enrollmentRepository;

    @Override
    public Enrollment enroll(Long courseId, Long studentId) {
        Course course = courseService.getById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found course with id: " + courseId));
        User user = userService.getById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found user with id: " + studentId));
        Enrollment enrollment = Enrollment.builder()
                .student(user)
                .course(course)
                .status(Enrollment.Status.ACTIVE)
                .enrollmentDate(LocalDate.now())
                .progressPercentage(0)
                .build();
        return enrollmentRepository.save(enrollment);
    }

    @Override
    public Enrollment unenroll(Long courseId, Long studentId) {
        Enrollment enrollment = enrollmentRepository.findByCourseIdAndStudentId(courseId, studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found enrollment for courseId: " + courseId + " and studentId: " + studentId));
        enrollment.setStatus(Enrollment.Status.DROPPED);
        return enrollmentRepository.save(enrollment);
    }

}
