package com.sparta.spartaboard.repository;

import com.sparta.spartaboard.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
