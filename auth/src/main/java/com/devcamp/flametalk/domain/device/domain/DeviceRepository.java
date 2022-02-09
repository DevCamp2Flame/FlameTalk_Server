package com.devcamp.flametalk.domain.device.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Device 엔티티의 저장소입니다.
 */
public interface DeviceRepository extends JpaRepository<Device, Long> {

  Optional<Device> findByDeviceIdAndUserId(String deviceId, String userId);
}