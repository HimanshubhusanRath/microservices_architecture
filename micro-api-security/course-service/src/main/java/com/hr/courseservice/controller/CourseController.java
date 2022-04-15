package com.hr.courseservice.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hr.courseservice.dto.CourseDTO;

@RestController
@RequestMapping("/course")
public class CourseController {



	@GetMapping("/get-all")
	public List<CourseDTO> getAllStudents()
	{
		return Stream.of(
				new CourseDTO("ST01", "Java", 4, 10000.00),
				new CourseDTO("ST02", "C++", 3, 1000.00)
			).collect(Collectors.toList());	
	}

}
