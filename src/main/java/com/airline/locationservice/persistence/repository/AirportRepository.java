package com.airline.locationservice.persistence.repository;

import java.util.List;
import java.util.Optional;

// import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
// import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.airline.locationservice.persistence.model.Airport;
// import com.airline.core.location.AirportCode;
// import com.airline.core.location.AirportCodeIata;

// import java.util.Optional;



@Repository
// @NoRepositoryBean
// JpaRepository<AirportCodeIata, String> {
public interface AirportRepository extends PagingAndSortingRepository<Airport, Long>
    // public interface AirportCodeRepository extends CrudRepository<AirportCode,
    // String> {
{


    @Query("from #{#entityName} airportCode where airportCode.id = :id")
    Optional<Airport> findById(@Param("id") Long id);

	Page<Airport> findAll(Pageable paging);
}

