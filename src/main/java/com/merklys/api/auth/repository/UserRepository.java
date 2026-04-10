package com.merklys.api.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.merklys.api.auth.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
