package com.hr.microservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.hr.microservice.dto.StudentDto;
import com.hr.microservice.entity.Student;
import com.hr.microservice.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private StudentService studentService;
	
	@PostMapping("/add")
	public ResponseEntity<Student> addStudent(@RequestBody @Valid StudentDto studentDto)
	{
		return new ResponseEntity<Student>(studentService.addStudent(studentDto), HttpStatus.CREATED);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Student>> getAllStudents()
	{
		return ResponseEntity.ok(studentService.getAllStudents());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable final Integer id) throws Exception
	{
		return ResponseEntity.ok(studentService.getStudentById(id));
	}
}
