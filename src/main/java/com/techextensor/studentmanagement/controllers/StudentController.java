package com.techextensor.studentmanagement.controllers;

import com.techextensor.studentmanagement.DTOs.StudentDTO;
import com.techextensor.studentmanagement.services.StudentService;
import com.techextensor.studentmanagement.specifications.FilterCriteria;
import com.techextensor.studentmanagement.utils.ResponseObject;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * This API returns List
     *
     * @return List<StudentDTO>
     */
    @PostMapping("all")
    public ResponseEntity<ResponseObject<List<StudentDTO>>> getAllStudents(@RequestBody(required = false) List<FilterCriteria> filters) {
        List<StudentDTO> students = studentService.getAllStudents(filters);
        return ResponseObject.success(students, "Students fetched successfully");
    }

    /**
     * This API returns Pageable response
     *
     * @return Page<StudentDTO>
     */
    @PostMapping("/page")
    public ResponseEntity<ResponseObject<Page<StudentDTO>>> getAllStudents(Pageable pageable, @RequestBody(required = false) List<FilterCriteria> filters) {
        Page<StudentDTO> students = studentService.getAllStudents(pageable, filters);
        return ResponseObject.success(students, "Students fetched successfully");
    }

    /**
     * This API returns Single record by ID if found
     * else throws app Exception if not found
     *
     * @return StudentDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject<StudentDTO>> getStudentById(@PathVariable Long id) {
        StudentDTO student = studentService.getStudentById(id);
        return ResponseObject.success(student, "Student fetched successfully");
    }


    /**
     * This API returns new record if created
     * else throws app Exception if any error / validation error
     *
     * @return StudentDTO
     */
    @PostMapping
    public ResponseEntity<ResponseObject<StudentDTO>> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        StudentDTO createdStudent = studentService.createStudent(studentDTO);
        return ResponseObject.created(createdStudent, "Student created successfully");
    }

    /**
     * This API returns updated record if created
     * else throws app Exception if any error / validation error
     *
     * @return StudentDTO
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject<StudentDTO>> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentDTO studentDTO) {
        StudentDTO updatedStudent = studentService.updateStudent(id, studentDTO);
        return ResponseObject.updated(updatedStudent, "Student updated successfully");
    }

    /**
     * This API returns success if record deleted
     * else throws app Exception if any error / validation error
     *
     * @return StudentDTO
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject<Object>> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseObject.success("Student Deleted successfully");
    }
}
