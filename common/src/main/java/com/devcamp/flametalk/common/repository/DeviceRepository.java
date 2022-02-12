package com.devcamp.flametalk.common.repository;

import com.devcamp.flametalk.common.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Device 엔티티의 저장소입니다.
 */
public interface DeviceRepository extends JpaRepository<Device, Long> {

}