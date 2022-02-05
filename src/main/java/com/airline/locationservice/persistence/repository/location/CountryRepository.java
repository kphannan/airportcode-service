package com.airline.locationservice.persistence.repository.location;

import java.util.Optional;
import java.util.List;

import com.airline.locationservice.persistence.model.location.Country;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;




@Repository
public interface CountryRepository extends JpaRepository<Country, Integer>
{
    Page<Country>     findAll( Pageable paging );

    Optional<Country> findByCode( final String countryCode );

    List<Country>     findByContinent( final String continent );
}

