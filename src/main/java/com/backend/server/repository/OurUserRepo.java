package com.backend.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.server.entity.OurUsers;

import java.util.Optional;

public interface OurUserRepo extends JpaRepository<OurUsers, Integer> {
    Optional<OurUsers> findByEmail(String email);
}
