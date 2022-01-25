package com.devcamp.flametalk.device;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Device 엔티티의 저장소입니다.
 */
public interface DeviceRepository extends JpaRepository<Device, Long> {

}