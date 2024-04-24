package com.community.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeniedIp implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "IP_ADDRESS", nullable = false)
    private String ipAddress;
}
