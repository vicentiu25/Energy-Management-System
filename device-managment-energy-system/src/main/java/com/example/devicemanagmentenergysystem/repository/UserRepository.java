package com.example.devicemanagmentenergysystem.repository;

import com.example.devicemanagmentenergysystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
