package com.example.Email.generator.repository;

import com.example.Email.generator.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {

}
