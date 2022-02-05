package com.airline.locationservice.controller;

// import java.util.Collections;
// import java.util.List;
import java.util.Optional;
// import java.util.stream.Collectors;

import com.airline.locationservice.persistence.model.AirportCodeIata;
import com.airline.locationservice.persistence.repository.AirportCodeIataRepository;

// import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
// import org.springframework.data.domain.PageImpl;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.http.HttpStatus;

import com.airline.core.location.AirportCode;
import com.airline.core.location.AirportCodeFactory;
import com.airline.core.location.IATAAirportCode;





@RestController
@RequestMapping( "/location/airport/iata" )
@Log4j2
public class LocationControllerIata
{
    private final AirportCodeIataRepository     repository;

    LocationControllerIata( AirportCodeIataRepository repository )
    {
        this.repository = repository;
    }

    // ----- Create -----
    @PostMapping( "" )
    public ResponseEntity<AirportCode> newAirportCode( @RequestBody IATAAirportCode newAirportCode )
    {
        AirportCodeIata icaoDB = repository.save( new AirportCodeIata( newAirportCode.getAirportCode() ) );

        // Return 201 (Created)
        return ResponseEntity.status( HttpStatus.CREATED ).body( new IATAAirportCode( icaoDB.getIataCode() ) );
    }

    // ----- Retrieve -----
    @GetMapping( "" )
    public ResponseEntity<Page<AirportCode>> all( Pageable paging )
    {
        // log.error("GET /location/airport/iata --- call repo");
        // log.error( "Paging: {}", paging );
        // log.error( "repos: {}", repository );
        // System.out.println("--GET /location/airport/iata --- call repo");
        // Get list of airport codes from the DB, potentially an empty list.
        Page<AirportCodeIata> result = repository.findAll( paging );
        // log.error( "result: {}", result.toList() );
        // System.out.println( "result: {" + result.toList() + "}" );
        // if ( result.isEmpty() )
        // {
        //     return ResponseEntity.ok(Collections.emptyList());
        // }

        // Map the results to Domain Objects rather than DB objects
        // List<AirportCode> airports =
        //     result.stream()
        //           .map( iata -> new IATAAirportCode(iata.getIataCode()) )
        //         //   .map( iata -> IATAAirportCode::new )
        //           .collect( Collectors.toList() );

        // map the result to a domain object from the DTO
        // return ResponseEntity.ok( airports );
        // return new PageImpl<AirportCode>( airports, paging, airports.size() );
        // Page<AirportCode> e = result.map(iata -> new IATAAirportCode(iata.getIataCode()) );
        // log.error("GET /location/airport/iata --- map result from repository");
        return ResponseEntity.ok( result.map( iata -> new IATAAirportCode( iata.getIataCode() ) ) );

        // return ResponseEntity.ok( result );
    }

     @GetMapping( "/{id}" )
    public ResponseEntity<AirportCode> one( @PathVariable String id )
    {
        Optional<AirportCodeIata> iata = repository.findById( id );
        // log.error( "Response to one() {} {}", () -> id, () -> iata );
        // System.out.println( "Response to one(" + id +") " + iata );
        if ( iata.isPresent() )
        {
            // log.error( "one() is present() {}", () -> iata.get() );
            return ResponseEntity.ok( AirportCodeFactory.build( iata.get().getIataCode() ) );
        }
        else
        {
            // log.error( "one() not found" );
            return ResponseEntity.notFound().build();
        }
    }



    // ----- Update -----

    // ----- Delete -----


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

    // @DeleteMapping("/{id}")
    // ResponseEntity<Boolean> deleteAirportCode( @PathVariable String id )
    // {
    //     Optional<AirportCodeIata> iata = repository.findById(id);
    //     if ( iata.isPresent() )
    //     {
    //         repository.deleteById(id);  // throws IllegalArgumentException if ID not found...
    //         return ResponseEntity.noContent().build();   // HTTP 204
    //     }
    //     else
    //     {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

}
