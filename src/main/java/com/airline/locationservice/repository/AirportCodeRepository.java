package com.airline.locationservice.repository;

import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

// import java.util.Optional;


@Repository
@NoRepositoryBean
public interface AirportCodeRepository extends JpaRepository<AirportCode, Long> {

    @Query( "from AirportCode airportCode where airportCode.code = :airport" )
    Stream<AirportCode> searchAirportCode( String airport );
}

