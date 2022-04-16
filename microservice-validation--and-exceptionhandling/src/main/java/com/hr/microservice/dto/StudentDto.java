package com.hr.microservice.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {

	private Integer id;
	
	@NotNull
	private String name;
	
	@Email(message = "Invalid email")
	private String email;
	
	private String gender;
	
	@Min(18)
	@Max(40)
	private Integer age;
}
