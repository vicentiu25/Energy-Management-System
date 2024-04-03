package com.example.devicemanagmentenergysystem.repository;

import com.example.devicemanagmentenergysystem.model.Device;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    @Query("SELECT dev FROM Device dev WHERE dev.user.idUser = :userId")
    List<Device> findAllByUser(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE Device dev SET dev.user.idUser =:userId  WHERE dev.idDevice =:idDevice")
    void mapDevice(@Param("idDevice") Long idDevice, @Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE Device dev SET dev.user.idUser =null  WHERE dev.idDevice =:idDevice")
    void mapDeleteDevice(@Param("idDevice") Long idDevice);

    @Modifying
    @Transactional
    @Query("UPDATE Device dev SET dev.user.idUser = null WHERE dev.user.idUser =:idUser")
    void mapDeleteUser(@Param("idUser") Long idUser);
}
