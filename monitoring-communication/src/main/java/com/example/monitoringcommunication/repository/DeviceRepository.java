package com.example.monitoringcommunication.repository;

import com.example.monitoringcommunication.model.Device;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Device dev SET dev.userId =:userId  WHERE dev.idDevice =:idDevice")
    void mapDevice(@Param("idDevice") Long idDevice, @Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE Device dev SET dev.userId =null  WHERE dev.idDevice =:idDevice")
    void mapDeleteDevice(@Param("idDevice") Long idDevice);

}
