package com.airline.locationservice.controller.airport;

import java.util.Optional;

import com.airline.core.location.AirportCode;
import com.airline.core.location.AirportCodeFactory;
import com.airline.core.location.IATAAirportCode;
import com.airline.locationservice.persistence.model.airport.AirportCodeIata;
import com.airline.locationservice.persistence.repository.airport.AirportCodeIataRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;





/**
 * REST controller for the IATA airport code reference list.
 */
@RestController
@RequestMapping( "/location/airport/iata" )
public class LocationControllerIata
{
    @SuppressWarnings( "PMD.BeanMembersShouldSerialize" )
    private final AirportCodeIataRepository     repository;

    /**
     * Create a new REST controller instance.
     *
     * @param repository reference datastore containing the reference list.
     */
    LocationControllerIata( final AirportCodeIataRepository repository )
    {
        this.repository = repository;
    }

    // ----- Create -----
    /**
     * Add a new IATA code to the reference list.
     *
     * @param newAirportCode the new code to add.
     * @return HTTP OK (200) with a response body containing the added code.
     */
    @PostMapping( "" )
    public ResponseEntity<AirportCode> newAirportCode( @RequestBody final IATAAirportCode newAirportCode )
    {
        final AirportCodeIata icaoDB = repository.save( new AirportCodeIata( newAirportCode.getAirportCode() ) );

        // Return 201 (Created)
        return ResponseEntity.status( HttpStatus.CREATED ).body( new IATAAirportCode( icaoDB.getIataCode() ) );
    }

    // ----- Retrieve -----
    /**
     * Retrieve a list of IATA airport codes, a page at a time.
     *
     * @param paging configuration of a page, and the retrieved page.
     * @return the current page.
     */
    @GetMapping( "" )
    public ResponseEntity<Page<AirportCode>> all( final Pageable paging )
    {
        final Page<AirportCodeIata> result = repository.findAll( paging );

        return ResponseEntity.ok( result.map( iata -> new IATAAirportCode( iata.getIataCode() ) ) );
    }

    /**
     * Retrieve a single ISO IATA airport code if present in the reference list.
     *
     * @param id IATA code to look for.
     * @return OK, HTTP 200 if found, or HTTP NOT_FOUND 404.
     */
    @GetMapping( "/{id}" )
    @SuppressWarnings( "PMD.ShortVariable" )  // id is a common variable name
    public ResponseEntity<AirportCode> one( @PathVariable final String id )
    {
        final Optional<AirportCodeIata> iata = repository.findById( id );

        if ( iata.isPresent() )
        {
            return ResponseEntity.ok( AirportCodeFactory.build( iata.get().getIataCode() ) );
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }



    // ----- Update -----

    // @PutMapping("/{id}")
    // ResponseEntity<AirportCode> replaceAirportCode( @RequestBody IATAAirportCode newAirportCode, @PathVariable String id )
    // {
    //     if ( !id.equals( newAirportCode.getAirportCode()))
    //         return ResponseEntity.badRequest().build();

    //     Optional<AirportCodeIata> iata = repository.findById( id );
    //     if ( iata.isPresent() )
    //     {
    //         return ResponseEntity.ok( newAirportCode );
    //     }

    //     AirportCodeIata zzz = new AirportCodeIata( newAirportCode.getAirportCode() );
    //     repository.save( zzz );
    //     // Map the response to a Domain Object
    //     // System.out.println( "save()");
    //     // return new ResponseEntity<>( new IATAAirportCode( repository.save( newAirportCode ).getIataCode()),
    //     //                              HttpStatus.CREATED );
    //     return ResponseEntity.status(HttpStatus.CREATED).body( newAirportCode );
    // }


    // ----- Delete -----

    /**
     * Delete a single IATA airport code.
     *
     * @param id the ISO IATA code to remove from the reference list.
     * @return HTTP NO-CONTENT (204) when the removed from the reference,
     *      NOT-FOUND (404) otherwise.
     */
    @DeleteMapping( "/{id}" )
    @SuppressWarnings( "PMD.ShortVariable" )  // id is a common variable name
    public ResponseEntity<Boolean> deleteAirportCode( @PathVariable final String id )
    {
        final Optional<AirportCodeIata> iata = repository.findById( id );
        if ( iata.isPresent() )
        {
            repository.deleteById( id );  // throws IllegalArgumentException if ID not found...
            return ResponseEntity.noContent().build();   // HTTP 204
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

}
