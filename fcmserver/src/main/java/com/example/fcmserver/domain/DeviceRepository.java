package com.example.fcmserver.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findByUserId(String userId);




}
