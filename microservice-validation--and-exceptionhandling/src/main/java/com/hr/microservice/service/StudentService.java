package com.hr.microservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hr.microservice.dto.StudentDto;
import com.hr.microservice.entity.Student;
import com.hr.microservice.exceptionhandler.UserNotFoundException;
import com.hr.microservice.respository.StudentRepository;

@Service
public class StudentService {

	@Autowired
	private StudentRepository repository;
	
	public Student addStudent(final StudentDto studentDto)
	{
		final Student student = new Student();
		BeanUtils.copyProperties(studentDto, student);
		return repository.save(student);
	}
	
	public List<Student> getAllStudents()
	{
		return repository.findAll();
	}

	public Student getStudentById(Integer id) throws Exception{
		final Optional<Student> result = repository.findById(id);
		if(result.isPresent())
		{
			return result.get();
		}
		else {
			throw new UserNotFoundException("no.user.found","No user is found for the given id");
		}
	}
	
}
