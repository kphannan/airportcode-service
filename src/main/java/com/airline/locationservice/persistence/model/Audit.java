package com.airline.locationservice.persistence.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Data;

/**
 * It represents the audit log.
 */
@Data
@MappedSuperclass
public abstract class Audit {
    @Column(name = "ACTION")
    private String action;
    @Column(name = "ACTION_BY")
    private String actionBy;
    @Column(name = "ACTION_ON")
    private Date actionOn;
}
