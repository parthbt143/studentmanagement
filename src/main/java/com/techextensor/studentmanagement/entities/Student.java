package com.techextensor.studentmanagement.entities;

import com.techextensor.studentmanagement.DTOs.StudentDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tbl_student")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 1, max = 50)
    @Column(name = "student_name")
    private String name;

    @NotBlank(message = "Surname is mandatory")
    @Size(min = 1, max = 50)
    @Column(name = "student_surname")
    private String surname;

    @Min(value = 1, message = "Age must be greater than or equal to 1")
    @Column(name = "student_age")
    private int age;


    public Student(StudentDTO studentDTO) {
        this.id = studentDTO.getId();
        this.name = studentDTO.getName();
        this.surname = studentDTO.getSurname();
        this.age = studentDTO.getAge();
    }

    public Student(StudentDTO studentDTO, Long id) {
        this(studentDTO);
        this.id = id;
    }
}
