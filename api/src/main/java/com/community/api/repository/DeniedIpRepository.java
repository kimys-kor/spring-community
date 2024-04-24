package com.community.api.repository;

import com.community.api.model.DeniedIp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeniedIpRepository extends JpaRepository<DeniedIp, Long> {
    DeniedIp findByIpAddress(String ipAddress);
}
