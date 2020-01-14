package com.airline.locationservice.repository;

import com.airline.locationservice.model.Region;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface RegionsRepository extends JpaRepository<Region, Integer> {
// public interface RegionsRepository extends PagingAndSortingRepository<Region, Integer> {
        // public interface AirportCodeRepository extends CrudRepository<AirportCode, String> {

    // @Query( "from AirportCode airportCode where airportCode.code = :airport" )
    // Stream<AirportCode> searchAirportCode( String airport );
    // AirportCode findById( String id );
}

