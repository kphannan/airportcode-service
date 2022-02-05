package com.airline.locationservice.controller.location;

import java.util.List;
import java.util.Optional;

import com.airline.locationservice.persistence.model.location.Country;
import com.airline.locationservice.persistence.repository.location.CountryRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping( "/location/country" )
public class CountryController
{
    private final CountryRepository repository;

    CountryController( CountryRepository repository )
    {
        this.repository = repository;
    }



    // ----- Create -----
    // ----- Retrieve -----

    @GetMapping( "" )
    public Page<Country> all( Pageable pageable )
    {
        // Get list of airport codes from the DB, potentially an empty list.
        Page<Country> result = repository.findAll( pageable );

        return result;
    }


    // Single item

    @GetMapping( "/{id}" )
    public ResponseEntity<Country> findCountryById(@PathVariable Integer id )
    {
        Optional<Country> region = repository.findById(id);

        if ( region.isPresent() )
            return ResponseEntity.ok(region.get());
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping( "/code/{countryCode}" )
    public ResponseEntity<Country> findCountryByCode( @PathVariable String countryCode )
    {
        Optional<Country> region = repository.findByCode(countryCode);

        if ( region.isPresent() )
            return ResponseEntity.ok(region.get());
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping( "/continent/{continentCode}" )
    public ResponseEntity<List<Country>> findCountriesByContinent( @PathVariable String continentCode )
    {
        List<Country> region = repository.findByContinent(continentCode);

        return ResponseEntity.ok( region );
    }


    // ----- Update -----
    // ----- Delete -----




}
