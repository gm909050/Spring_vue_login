package com.example.demo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.UserVo;

public interface DbSpringBootDao extends JpaRepository<UserVo, Integer> {
	Optional<UserVo> findByEmail(String email);
}
