package com.example.usermanagmentenergysystem.repository;

import com.example.usermanagmentenergysystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT us FROM User us WHERE us.username= :username")
    User matchUser(@Param("username") String username);

    @Query("SELECT us FROM User us WHERE us.idUser= :userId")
    User matchUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(us.idUser) FROM User us WHERE us.username= :username")
    int existsUsername(@Param("username") String username);
    @Query("SELECT us.idUser FROM User us WHERE us.role=0")
    Long getAdminId();
}
