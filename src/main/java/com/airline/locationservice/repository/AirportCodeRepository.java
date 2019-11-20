package com.airline.locationservice.repository;

// import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.repository.NoRepositoryBean;
// import org.springframework.stereotype.Repository;

// import java.util.Optional;


// @Repository
// @NoRepositoryBean
public interface AirportCodeRepository extends JpaRepository<AirportCode, String> {
// public interface AirportCodeRepository extends CrudRepository<AirportCode, String> {

    // @Query( "from AirportCode airportCode where airportCode.code = :airport" )
    // Stream<AirportCode> searchAirportCode( String airport );
    // AirportCode findById( String id );
}

