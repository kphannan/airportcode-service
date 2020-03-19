package com.airline.locationservice.persistence.model;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;

public class DefaultAuditableRepository<T extends Auditable, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements AuditableRepository<T, ID> {

    private EntityManager entityManager;


    public DefaultAuditableRepository(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager= entityManager;
    }

    public DefaultAuditableRepository(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public <S extends T> S save(S entity) {
        String action = entity.getId() == null ? "INSERT" : "UPDATE";
        String email = UserContextHolder.getEmail();
        if(entity.getId() == null) {
            entity.setAddedBy(email);
            entity.setAddedOn(UserContextHolder.getTransactionTime());
        } else {
            entity.setModifiedBy(email);
            entity.setModifiedOn(UserContextHolder.getTransactionTime());
        }
        S result = super.save(entity);
        Audit audit = result.createAudit();
        audit.setActionBy(email);
        audit.setActionOn(UserContextHolder.getTransactionTime());
        audit.setAction(action);
        this.entityManager.persist(audit);
        return result;
    }

}
