package com.airline.locationservice.controller.airport;

import com.airline.core.location.AirportCode;
import com.airline.core.location.ICAOAirportCode;
import com.airline.core.location.AirportCodeFactory;
import com.airline.locationservice.persistence.model.airport.AirportCodeIcao;
import com.airline.locationservice.persistence.repository.airport.AirportCodeIcaoRepository;

import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping( "/location/airport/icao" )
public class LocationControllerIcao
{
    private final AirportCodeIcaoRepository repository;

    LocationControllerIcao( AirportCodeIcaoRepository repository )
    {
        this.repository = repository;
    }



    // ----- Create -----

    @PostMapping( "" )
    public ResponseEntity<AirportCode> newAirportCode( @RequestBody ICAOAirportCode newAirportCode )
    {
        AirportCodeIcao icaoDB = repository.save( new AirportCodeIcao( newAirportCode.getAirportCode() ) );

        // Return 201 (Created)
        return ResponseEntity.status( HttpStatus.CREATED ).body( new ICAOAirportCode( icaoDB.getIcaoCode() ) );
    }


    // ----- Retrieve -----

    @GetMapping( "" )
    public ResponseEntity<Page<AirportCode>> all( Pageable paging )
    {
        Page<AirportCodeIcao> result = repository.findAll( paging );

        return ResponseEntity.ok( result
                                    .map( icao -> new ICAOAirportCode( icao.getIcaoCode() ) )
                                );
    }


    @GetMapping( "/{id}" )
    public ResponseEntity<AirportCode> one( @PathVariable String id )
    {
        Optional<AirportCodeIcao> icao = repository.findById( id );
        if ( icao.isPresent() )
        {
            return ResponseEntity.ok( AirportCodeFactory.build( icao.get().getIcaoCode() ) );
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }



    // ----- Update -----

    // ----- Delete -----




}


