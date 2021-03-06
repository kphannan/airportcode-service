package com.airline.locationservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

// import com.airline.core.location.AirportCode;
// import com.airline.core.location.AirportCodeIata;

// import java.util.Optional;



// import org.springframework.data.repository.PagingAndSortingRepository;
// import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// @RepositoryRestResource(collectionResourceRel = "airport", path = "airport")
// public interface AirportCodeRepository extends PagingAndSortingRepository<AirportCode, String> {


@Repository
// @NoRepositoryBean
public interface AirportCodeIataRepository extends JpaRepository<AirportCodeIata, String> {
// public interface AirportCodeRepository extends CrudRepository<AirportCode, String> {

    // Stream<AirportCode> searchAirportCode( String airport );
    // @Query( "from AirportCode airportCode where airportCode.iataCode = :iataCode" )
    // AirportCodeIata findById( @Param("iataCode") String airport );


    @Query( "FROM #{#entityName} a WHERE a.iataCode IS NOT NULL AND a.iataCode != '' AND a.iataCode != '0' AND iata_code NOT LIKE '%-'  AND iata_code NOT LIKE '%2'  AND iata_code NOT LIKE '%4'  AND iata_code NOT LIKE '%7'")
    // @Query( "FROM #{#entityName} a WHERE a.iataCode #{MATCHES '[A-Z]{3}'}")
    List<AirportCodeIata> findAll();
}

