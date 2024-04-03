package com.example.monitoringcommunication.repository;

import com.example.monitoringcommunication.model.DeviceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DeviceDataRepository extends JpaRepository<DeviceData, Long> {
    @Query("SELECT devData FROM DeviceData devData WHERE devData.timestamp = :timestamp and devData.idDevice.idDevice = :idDevice")
    DeviceData findDeviceData(@Param("timestamp") LocalDateTime timestamp, @Param("idDevice") Long idDevice);

    @Query("SELECT devData FROM DeviceData devData WHERE devData.idDevice.userId = :idUser AND devData.timestamp >= :dateMin AND  devData.timestamp <=:dateMax")
    List<DeviceData> findDeviceDataByUser(@Param("idUser") Long idUser, @Param("dateMin") LocalDateTime dateMin, @Param("dateMax") LocalDateTime dateMax);
}
