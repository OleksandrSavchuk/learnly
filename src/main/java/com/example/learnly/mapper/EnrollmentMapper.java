package com.example.learnly.mapper;

import com.example.learnly.dto.enrollment.EnrollmentResponseDto;
import com.example.learnly.entity.course.Enrollment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {

    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "student.id", target = "studentId")
    EnrollmentResponseDto toResponse(Enrollment enrollment);

}
