package com.hr.studentservice.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hr.studentservice.dto.StudentDTO;

@RestController
@RequestMapping("/student")
public class StudentController {

	@GetMapping("/get-all")
	public List<StudentDTO> getAllStudents()
	{
		return Stream.of(
				new StudentDTO("1", "1", "Himanshu"),
				new StudentDTO("2", "2", "Rajesh")
			).collect(Collectors.toList());	
	}
}
