package com.hr.micro.userservice.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hr.micro.userservice.dto.UserDTO;
import com.hr.micro.userservice.entity.User;
import com.hr.micro.userservice.repo.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public UserDTO getUser(final Integer userID)
	{
		final UserDTO userDTO = new UserDTO();
		final Optional<User> result = userRepository.findById(userID);
		
		if(result.isPresent()){
			BeanUtils.copyProperties(result.get(), userDTO);
			return userDTO;
		}
		return null;
		
	}

	public void addUser(UserDTO userDTO) {
		final User user = new User();
		BeanUtils.copyProperties(userDTO, user);
		userRepository.save(user);
	}
}
