package com.airline.locationservice.persistence.model;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Date;

import lombok.Data;


/**
 * It represents the auditable entity.
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<E extends Audit>
{
    @Column(name = "ADDED_BY")
    @CreatedBy
    private String addedBy;

    @Column(name = "ADDED_ON")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date addedOn;

    @Column(name = "MODIFIED_BY")
    @LastModifiedBy
    private String modifiedBy;

    @Column(name = "MODIFIED_ON")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date modifiedOn;

    /**
     * Returns the id of the entity.
     * @return
     */
    public abstract Long getId();

    /**
     * Create the audit record.
     * @return
     */
    public abstract E createAudit();
}
