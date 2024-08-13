package com.techextensor.studentmanagement.repositories;
import com.techextensor.studentmanagement.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudentRepository extends JpaRepository<Student, Long> , JpaSpecificationExecutor<Student> {
}
