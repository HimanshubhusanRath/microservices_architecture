package com.hr.microservice.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hr.microservice.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>{

}
