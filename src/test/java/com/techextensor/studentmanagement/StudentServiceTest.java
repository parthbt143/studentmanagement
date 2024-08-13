package com.techextensor.studentmanagement;

import com.techextensor.studentmanagement.DTOs.StudentDTO;
import com.techextensor.studentmanagement.entities.Student;
import com.techextensor.studentmanagement.repositories.StudentRepository;
import com.techextensor.studentmanagement.services.StudentService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testCreateStudent() {
        // Arrange
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("John");
        studentDTO.setSurname("Doe");
        studentDTO.setAge(20);

        Student student = new Student(studentDTO);
        student.setId(1L); // Simulate a persisted student with an ID

        // Configure mock to return the student with an ID when save is called
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        // Act
        StudentDTO result = studentService.createStudent(studentDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getName());
        assertEquals("Doe", result.getSurname());
        assertEquals(20, result.getAge());

        // Verify save method was called once
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    public void whenNameIsNull_thenThrowConstraintViolationException() {
        // Arrange
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName(null); // Name is null
        studentDTO.setSurname("Doe");
        studentDTO.setAge(20);

        // Act & Assert
        ConstraintViolationException thrown = assertThrows(
                ConstraintViolationException.class,
                () -> studentService.createStudent(studentDTO),
                "Expected createStudent() to throw, but it didn't"
        );

        // Assert
        String expectedMessage = "Name is mandatory";
        assert (thrown.getMessage().contains(expectedMessage));
    }
}
