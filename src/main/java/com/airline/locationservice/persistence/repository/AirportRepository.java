package com.airline.locationservice.persistence.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.airline.locationservice.persistence.model.Airport;

@Repository
public interface AirportRepository extends PagingAndSortingRepository<Airport, Long>
{
    @Query( "from #{#entityName} airportCode where airportCode.id = :id" )
    Optional<Airport> findById( @Param( "id" ) Long id) ;

	Page<Airport> findAll( Pageable paging );
}

