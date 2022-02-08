package com.airline.locationservice.controller.location;

import java.util.List;
import java.util.Optional;

import com.airline.locationservice.persistence.model.location.Country;
import com.airline.locationservice.persistence.repository.location.CountryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * REST controller for the country reference list.
 */
@RestController
@RequestMapping( "/location/country" )
public class CountryController
{
    @SuppressWarnings( "PMD.BeanMembersShouldSerialize" )
    private final CountryRepository repository;

    /**
     * Intantiate the Country REST controller.
     *
     * @param repository persistent store of {@code Country} records.
     */
    CountryController( final CountryRepository repository )
    {
        this.repository = repository;
    }



    // ----- Create -----
    // ----- Retrieve -----

    /**
     * Returns a {@code Page} of {@code Country} items meeting the paging
     *     restriction provided in the Pageable object.
     *
     * @param pageable the {@code Page} requested.
     * @return the requested {@code Page}.
     */
    @GetMapping( "" )
    public Page<Country> all( final Pageable pageable )
    {
        // Get list of airport codes from the DB, potentially an empty list.
        return repository.findAll( pageable );
    }


    // Single item

    /**
     * Retrieves a single {@code Country} based on its unique ID.
     *
     * @param id the unique identifier of the {@code Country} item.
     * @return HTTP 200, OK if found with the {@code Country} in the body;
     *         HTTP 404, NOT_FOUND otherwise.
     */
    @GetMapping( "/{id}" )
    @SuppressWarnings( "PMD.ShortVariable" )  // id is a common variable name
    public ResponseEntity<Country> findCountryById( @PathVariable final Integer id )
    {
        final Optional<Country> region = repository.findById( id );

        if ( region.isPresent() )
        {
            return ResponseEntity.ok( region.get() );
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves a single {@code Country} based on its ISO letter code.
     *
     * @param countryCode the unique letter code of the {@code Country} item.
     * @return HTTP 200, OK if found with the {@code Country} in the body;
     *         HTTP 404, NOT_FOUND otherwise.
     */
    @GetMapping( "/code/{countryCode}" )
    public ResponseEntity<Country> findCountryByCode( @PathVariable final String countryCode )
    {
        final Optional<Country> region = repository.findByCode( countryCode );

        if ( region.isPresent() )
        {
            return ResponseEntity.ok( region.get() );
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieve a list of {@code Country}s within a specified continent.
     *
     * @param continentCode the letter code of the target contentient
     * @return HTTP 200, OK if found with the {@code Country} list in the body;
     *         HTTP 404, NOT_FOUND otherwise.
     */
    @GetMapping( "/continent/{continentCode}" )
    public ResponseEntity<List<Country>> findCountriesByContinent( @PathVariable final String continentCode )
    {
        final List<Country> region = repository.findByContinent( continentCode );

        return ResponseEntity.ok( region );
    }


    // ----- Update -----
    // ----- Delete -----




}
