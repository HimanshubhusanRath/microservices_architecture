package com.hr.micro.userservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hr.micro.userservice.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

}
