package com.techextensor.studentmanagement.services;

import com.techextensor.studentmanagement.DTOs.StudentDTO;
import com.techextensor.studentmanagement.entities.Student;
import com.techextensor.studentmanagement.repositories.StudentRepository;
import com.techextensor.studentmanagement.specifications.FilterCriteria;
import com.techextensor.studentmanagement.specifications.SpecificationBuilder;
import com.techextensor.studentmanagement.utils.AppException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    private final SpecificationBuilder<Student> specificationBuilder;

    public StudentService(
            StudentRepository studentRepository,
            SpecificationBuilder<Student> specificationBuilder
    ) {
        this.studentRepository = studentRepository;
        this.specificationBuilder = specificationBuilder;
    }

    public List<StudentDTO> getAllStudents(List<FilterCriteria> filters) {
        Specification<Student> spec = specificationBuilder.build(filters, null);
        return studentRepository.findAll(spec).stream()
                .map(StudentDTO::new)
                .collect(Collectors.toList());
    }

    public Page<StudentDTO> getAllStudents(Pageable pageable, List<FilterCriteria> filters) {
        Specification<Student> spec = specificationBuilder.build(filters, pageable);
        Page<Student> studentPage = studentRepository.findAll(spec, pageable);
        return studentPage.map(StudentDTO::new);

    }

    public StudentDTO getStudentById(Long id) throws AppException {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            return new StudentDTO(optionalStudent.get());
        }
        throw new AppException("Student not found", HttpStatus.NOT_FOUND);
    }

    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student student = new Student(studentDTO);
        Student createdStudent = studentRepository.save(student);
        return new StudentDTO(createdStudent);
    }

    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) throws AppException {
        getStudentById(id); // Check if the student exists
        Student student = new Student(studentDTO, id);
        Student updatedStudent = studentRepository.save(student);
        return new StudentDTO(updatedStudent);
    }

    public void deleteStudent(Long id) throws AppException {
        getStudentById(id); // Check if the student exists
        studentRepository.deleteById(id);
    }


}
