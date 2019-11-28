package com.airline.locationservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.airline.locationservice.repository.AirportCodeIata;
import com.airline.locationservice.repository.AirportCodeIataRepository;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.airline.core.location.AirportCode;
import com.airline.core.location.IATAAirportCode;





@RestController
@RequestMapping("/airport/iata")
public class LocationControllerIata
{
    private final AirportCodeIataRepository     repository;

    LocationControllerIata( AirportCodeIataRepository repository )
    {
        this.repository = repository;
    }


    @GetMapping("")
    List<AirportCode> all()
    {
        // Get list of airport codes from the DB, potentially an empty list.
        List<AirportCodeIata> result = repository.findAll();

        if ( result.isEmpty() )
        {
            return new ArrayList<>();
        }

        // Map the results to Domain Objects rather than DB objects
        List<AirportCode> airports =
            result.stream().map( iata -> new IATAAirportCode(iata.getIataCode()) )
                           .collect( Collectors.toList() );

        return airports;
    }

    @PostMapping("")
    ResponseEntity<AirportCode> newAirportCode( @RequestBody IATAAirportCode newAirportCode )
    {
        AirportCodeIata iataDB = repository.save( new AirportCodeIata( newAirportCode.getAirportCode() ));

        // Return 201 (Created)
        return new ResponseEntity<>(new IATAAirportCode( iataDB.getIataCode() ), HttpStatus.CREATED );
    }

    // @PostMapping("/validate")
    // Boolean validateAirportCode( @RequestBody IATAAirportCode newAirportCode )
    // {
    //     System.out.println( newAirportCode );
    //     // return repository.save( newAirportCode );
    //     return true;
    // }





    // Single item

    @GetMapping("/{id}")
    ResponseEntity<AirportCode> one(@PathVariable String id )
    {
        AirportCodeIata iata = repository.findById(id)
                                         .orElseThrow(() -> new AirportCodeNotFoundException( id ));

        return new ResponseEntity<>( new IATAAirportCode( iata.getIataCode() ),
                                     HttpStatus.OK );
    }



    @PutMapping("/{id}")
    ResponseEntity<AirportCode> replaceAirportCode( @RequestBody AirportCodeIata newAirportCode, @PathVariable String id )
    {
        System.out.println( ">>=================================================>>");
        Optional<AirportCodeIata> iata = repository.findById(id);
        if ( iata == null )
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST );;

        System.out.println( "findByID()");
        if ( iata.isPresent() )
        {
            System.out.println( "isPresent()");
            return new ResponseEntity<>( new IATAAirportCode( iata.get().getIataCode()), HttpStatus.OK );
        }

        // Map the response to a Domain Object
        System.out.println( "save()");
        return new ResponseEntity<>( new IATAAirportCode( repository.save( newAirportCode ).getIataCode()),
                                     HttpStatus.CREATED );
    }


    @DeleteMapping("/{id}")
    ResponseEntity<Boolean> deleteAirportCode( @PathVariable String id )
    {
        Optional<AirportCodeIata> iata = repository.findById(id);
        if ( iata.isPresent() )
        {
            // HTTP 204 (No Content)
            repository.deleteById(id);
            return new ResponseEntity<>(Boolean.TRUE, HttpStatus.NO_CONTENT);
        }
        else
        {
            return new ResponseEntity<>( Boolean.FALSE, HttpStatus.NOT_FOUND);
        }
    }

}
