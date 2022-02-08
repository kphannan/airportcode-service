package com.airline.locationservice.controller.airport;


import java.util.Locale;
import java.util.Optional;

import com.airline.locationservice.persistence.model.airport.Airport;
import com.airline.locationservice.persistence.repository.airport.AirportRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// import java.util.List;
// import java.util.Collections;
// import java.util.stream.Collectors;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.domain.Sort;
// import org.springframework.http.HttpStatus;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * {@code Airport} REST controller.
 */
@RestController
@RequestMapping( "/location/airport" )
@Log4j2
public class AirportController
{
  @SuppressWarnings( "PMD.BeanMembersShouldSerialize" )
  private final AirportRepository repository;

    /**
     * Instantiate a REST controller.
     *
     * @param repository persistent store of {@code Airport} records.
     */
    AirportController( final AirportRepository repository )
    {
        this.repository = repository;
    }


    // ----- Create -----

    // @PostMapping("")
    // @ResponseStatus( HttpStatus.CREATED )
    // // ResponseEntity<Airport> newAirportCode(@RequestBody Airport newAirport) {
    // Airport newAirportCode(@RequestBody Airport newAirport) {
    //     Airport airport = repository.save(newAirport);

    //     // Return 201 (Created)
    //     // return new ResponseEntity.created(airport).build();
    //     return airport;
    // }


    // ----- Retrieve -----

    /**
     * Retrieve a {@code Airport} records, a page at a time.
     *
     * @param paging configuration of a page, and the retrieved page.
     * @return the current page.
     */

    // @GetMapping(path="", params = { "page", "size" })
    @GetMapping( path = "" )
    public ResponseEntity<Page<Airport>> all( final Pageable paging )
    {
        final Page<Airport> result = repository.findAll( paging );

        // if (result.isEmpty()) {
        //     return ResponseEntity.ok(result);
        // }

        // Map the results to Domain Objects rather than DB objects
        // List<Airport> airports = result.stream().map(iata -> new ICAOAirportCode(iata.getIcaoCode()))
        //         .collect(Collectors.toList());

        // return ResponseEntity.ok(airports);
        return ResponseEntity.ok( result );
    }


    /**
     * Search for {@code Airport} records that contain any of the query parameters.
     *
     * @param iataCode optional IATA code to search on.
     * @param icaoCode optional ICAO code to search on.
     * @param ident optional identifier which may be the ICAO code to search on.
     * @param name optional airport name string.
     * @param paging current {@code Page} specification.
     * @return the target page with {@code Airport} records if any match the criteria.
     */
    @GetMapping( path = "/search" )
    public ResponseEntity<Page<Airport>> advancedQuery(  @RequestParam( name = "iataCode", required = false )
                                                         final String iataCode
                                                       , @RequestParam( name = "icaoCode", required = false )
                                                         final String icaoCode
                                                       , @RequestParam( name = "ident", required = false )
                                                         final String ident
                                                       , @RequestParam( name = "name", required = false )
                                                         final String name
                                                       , final Pageable paging )
    {
        final Page<Airport> result = repository.advancedQuery(  null == iataCode
                                                                    ? "" : iataCode.toUpperCase( Locale.US )
                                                              , null == icaoCode
                                                                    ? "" : icaoCode.toUpperCase( Locale.US )
                                                              , null == ident
                                                                    ? "" : ident.toUpperCase( Locale.US )
                                                              , null == name
                                                                    ? "" : name.toUpperCase( Locale.US )
                                                              , paging );

        return ResponseEntity.ok( result );
    }


    /**
     * Retrieve a single {@code Airport} from it's unique id.
     *
     * @param id unique Airport identifier.
     * @return OK, HTTP 200 if found, or HTTP NOT_FOUND 404.
     */
    @GetMapping( "/{id}" )
    @SuppressWarnings( "PMD.ShortVariable" )  // id is a common variable name
    public ResponseEntity<Optional<Airport>> one( @PathVariable final Long id )
    {
        final Optional<Airport> airport = repository.findById( id );
        if ( airport.isPresent() )
        {
            return ResponseEntity.ok( airport );
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    // ----- Update -----

    // @PutMapping("/{id}")
    // public ResponseEntity<AirportCode> replaceAirportCode( @RequestBody ICAOAirportCode newAirportCode,
    //         @PathVariable String id ) {
    //     Optional<Airport> icao = repository.findById(id);
    //     if ( icao.isPresent() ) {
    //         return new ResponseEntity<>(new ICAOAirportCode( icao.get().getIcaoCode()), HttpStatus.OK );
    //     }

    //     // Map the response to a Domain Object
    //     return new ResponseEntity<>(
    //             new ICAOAirportCode(
    //                     repository.save( new Airport( newAirportCode.getAirportCode() ) ).getIcaoCode() ),
    //             HttpStatus.CREATED);
    // }


    // ----- Delete -----

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Boolean> deleteAirportCode( @PathVariable String id ) {
    //     Optional<AirportCodeIcao> icao = repository.findById( id );
    //     if ( icao.isPresent() ) {
    //         // HTTP 204 (No Content)
    //         repository.deleteById(id);
    //         return new ResponseEntity<>( Boolean.TRUE, HttpStatus.NO_CONTENT );
    //     } else {
    //         return new ResponseEntity<>( Boolean.FALSE, HttpStatus.NOT_FOUND );
    //     }
    // }

}
