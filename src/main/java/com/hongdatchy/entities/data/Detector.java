package com.hongdatchy.entities.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "detector")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Detector {

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "address_detector", nullable = false)
    private String addressDetector;

    @Column(name = "slot_id", nullable = false)
    private Integer slotId;

    @Column(name = "gateway_id", nullable = false)
    private Integer gatewayId;

    @Column(name = "battery_level", nullable = false)
    private String batteryLevel;

    @Column(name = "communication_level", nullable = false)
    private String communication_level;

    @Column(name = "last_time_update", nullable = false)
    private Date lastTimeUpdate;

    @Column(name = "last_time_setup", nullable = false)
    private Date lastTimeSetup;

}
