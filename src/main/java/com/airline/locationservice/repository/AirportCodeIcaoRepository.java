package com.airline.locationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// import java.util.Optional;



// import org.springframework.data.repository.PagingAndSortingRepository;
// import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// @RepositoryRestResource(collectionResourceRel = "airport", path = "airport")
// public interface AirportCodeIcaoRepository extends PagingAndSortingRepository<AirportCodeIcao, String> {


@Repository
// @NoRepositoryBean
public interface AirportCodeIcaoRepository extends JpaRepository<AirportCodeIcao, String> {
// public interface AirportCodeRepository extends CrudRepository<AirportIcaoCode, String> {

    // @Query( "from AirportCode airportCode where airportCode.code = :airport" )
    // Stream<AirportCodeIcao> searchAirportCode( String airport );
    // AirportCodeIcao findById( String id );
}

