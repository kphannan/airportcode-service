package com.airline.locationservice.persistence.repository;

// import java.util.List;
import java.util.Optional;


import com.airline.locationservice.persistence.model.AirportCodeIata;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

// public interface AirportCodeRepository extends PagingAndSortingRepository<AirportCode, String> {

@Repository
// @NoRepositoryBean
public interface AirportCodeIataRepository extends PagingAndSortingRepository<AirportCodeIata, String>
{
    @Query( "from #{#entityName} a where a.iataCode = :iataCode" )
    Optional<AirportCodeIata> findById( @Param("iataCode") String iataCode );

    @Query( "FROM #{#entityName} a WHERE a.iataCode IS NOT NULL AND a.iataCode != '' AND a.iataCode != '0' AND a.iataCode NOT LIKE '%-'  AND a.iataCode NOT LIKE '%2'  AND a.iataCode NOT LIKE '%4'  AND a.iataCode NOT LIKE '%7'")
    // @Query( "FROM #{#entityName} a WHERE a.iataCode #{MATCHES '[A-Z]{3}'}")
    Page<AirportCodeIata> findAll( Pageable paging );
}

