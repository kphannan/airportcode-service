package com.airline.locationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// import java.util.Optional;



// import org.springframework.data.repository.PagingAndSortingRepository;
// import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// @RepositoryRestResource(collectionResourceRel = "airport", path = "airport")
// public interface AirportCodeRepository extends PagingAndSortingRepository<AirportCode, String> {


@Repository
// @NoRepositoryBean
public interface AirportCodeRepository extends JpaRepository<AirportCode, String> {
// public interface AirportCodeRepository extends CrudRepository<AirportCode, String> {

    // @Query( "from AirportCode airportCode where airportCode.code = :airport" )
    // Stream<AirportCode> searchAirportCode( String airport );
    // AirportCode findById( String id );
}

