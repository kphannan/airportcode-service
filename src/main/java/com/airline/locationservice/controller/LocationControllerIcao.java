package com.airline.locationservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.airline.locationservice.repository.AirportCodeIcao;
import com.airline.locationservice.repository.AirportCodeIcaoRepository;

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
import com.airline.core.location.ICAOAirportCode;

@RestController
@RequestMapping("/airport/icao")
public class LocationControllerIcao
{
    private final AirportCodeIcaoRepository repository;

    LocationControllerIcao( AirportCodeIcaoRepository repository )
    {
        this.repository = repository;
    }


    @GetMapping("")
    List<AirportCode> all()
    {
        List<AirportCodeIcao> result = repository.findAll();

        if ( result.isEmpty() )
        {
            return new ArrayList<>();
        }

        // Map the results to Domain Objects rather than DB objects
        List<AirportCode> airports =
            result.stream().map( icao -> new ICAOAirportCode(icao.getIcaoCode()) )
                           .collect( Collectors.toList() );

        return airports;
    }

    @PostMapping("")
    ResponseEntity<AirportCode> newAirportCode( @RequestBody ICAOAirportCode newAirportCode )
    {
        AirportCodeIcao icaoDB = repository.save( new AirportCodeIcao( newAirportCode.getAirportCode() ));

        // Return 201 (Created)
        return new ResponseEntity<>(new ICAOAirportCode( icaoDB.getIcaoCode() ), HttpStatus.CREATED );
    }
    // @PostMapping("/validate")
    // Boolean validateAirportCode( @RequestBody ICAOAirportCode newAirportCode )
    // {
    //     System.out.println( newAirportCode );
    //     // return repository.save( newAirportCode );
    //     return true;
    // }




    // Single item

    @GetMapping("/{id}")
    ResponseEntity<AirportCode> one(@PathVariable String id )
    {
        AirportCodeIcao icao = repository.findById(id)
                                         .orElseThrow(() -> new AirportCodeNotFoundException( id ));

        return new ResponseEntity<>( new ICAOAirportCode( icao.getIcaoCode() ),
                                     HttpStatus.OK );
    }


    @PutMapping("/{id}")
    ResponseEntity<AirportCode> replaceAirportCode( @RequestBody ICAOAirportCode newAirportCode, @PathVariable String id )
    {
        Optional<AirportCodeIcao> icao = repository.findById(id);
        if ( icao.isPresent() )
        {
            return new ResponseEntity<>( new ICAOAirportCode( icao.get().getIcaoCode()), HttpStatus.OK );
        }

        // Map the response to a Domain Object
        return new ResponseEntity<>( new ICAOAirportCode( repository.save( new AirportCodeIcao( newAirportCode.getAirportCode()) ).getIcaoCode()),
                                     HttpStatus.CREATED );
    }


    @DeleteMapping("/{id}")
    ResponseEntity<Boolean> deleteAirportCode( @PathVariable String id )
    {
        Optional<AirportCodeIcao> icao = repository.findById(id);
        if ( icao.isPresent() )
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




