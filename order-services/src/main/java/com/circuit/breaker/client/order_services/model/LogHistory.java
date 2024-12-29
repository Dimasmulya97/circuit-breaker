package com.circuit.breaker.client.order_services.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "log_history")
public class LogHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String servicesName;
    private String endpoint;
    private String errorMessage;
    @Column(name = "timestamp", nullable = false, updatable = false)
    private Timestamp timestamp;

    @PrePersist
    public void prePersist() {
        if (this.timestamp == null) {
            this.timestamp = new Timestamp(System.currentTimeMillis());
        }
    }
}
