package com.airline.locationservice.controller.airport;


import com.airline.core.location.AirportCode;
import com.airline.core.location.AirportCodeFactory;
import com.airline.core.location.IATAAirportCode;
import com.airline.locationservice.persistence.model.airport.AirportCodeIata;
import com.airline.locationservice.persistence.repository.airport.AirportCodeIataRepository;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;






@RestController
@RequestMapping( "/location/airport/iata" )
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
        Page<AirportCodeIata> result = repository.findAll( paging );

        return ResponseEntity.ok( result.map( iata -> new IATAAirportCode( iata.getIataCode() ) ) );
    }

     @GetMapping( "/{id}" )
    public ResponseEntity<AirportCode> one( @PathVariable String id )
    {
        Optional<AirportCodeIata> iata = repository.findById( id );

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteAirportCode( @PathVariable String id )
    {
        Optional<AirportCodeIata> iata = repository.findById( id );
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
