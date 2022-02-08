package com.airline.locationservice.controller.location;


import java.util.Optional;

import com.airline.locationservice.persistence.model.location.Region;
import com.airline.locationservice.persistence.repository.location.RegionsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;

/**
 * REST controller for /location/region.
 */
@RestController
@RequestMapping( "/location/region" )
// @AutoConfigureRestDocs( outputDir = "target/snippets" )
public class RegionController
{
    @SuppressWarnings( "PMD.BeanMembersShouldSerialize" )
    private final RegionsRepository repository;

    RegionController( final RegionsRepository repository )
    {
        this.repository = repository;
    }

    // ----- Create -----
    // ----- Retrieve -----
    // ----- Update -----
    // ----- Delete -----


    /**
     * Get a paged list of all Regions.
     */
    @GetMapping( "" )
    public Page<Region> all( @PageableDefault( page = 0, size = 20 ) final Pageable pageable )
    {
        // Get list of airport codes from the DB, potentially an empty list.
        return repository.findAll( pageable );
    }





    // Single item

    /**
     * Retrieve a single region by it's ID.
     *
     * @param id unique id of the desired region.
     * @return The region with Http 200, or Http 404 if it is not found.
     */
    @GetMapping( "/{id}" )
    @SuppressWarnings( "PMD.ShortVariable" )  // id is a common variable name
    public ResponseEntity<Region> one( @PathVariable final Integer id )
    {
        final Optional<Region> region = repository.findById( id );

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
     * Retrieve a paged list of {@code Region} withing a specific continent.
     *
     * @param continent the continent code for which the {@code Region}s are requested.
     * @param paging paged results configuration.
     * @return the desired page of results.
     */
    @GetMapping( "/byContinent/{continent}" )
    public ResponseEntity<Page<Region>> regionByContinent( @PathVariable final String continent, final Pageable paging )
    {
        final Page<Region> region = repository.findByContinent( continent, paging );

        return ResponseEntity.ok( region );
    }

    /**
     * Retrieve a paged list of {@code Region} withing a specific country.
     *
     * @param country the country, by ISO country code for which the {@code Region}s are requested.
     * @param paging paged results configuration.
     * @return the desired page of results.
     */
    @GetMapping( "/byCountry/{country}" )
    public ResponseEntity<Page<Region>> regionByCountry( @PathVariable final String country, final Pageable paging )
    {
        final Page<Region> region = repository.findByCountry( country, paging );

        return ResponseEntity.ok( region );
    }

}
