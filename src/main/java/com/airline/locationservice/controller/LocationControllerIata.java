package com.airline.locationservice.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.airline.locationservice.repository.AirportCodeIata;
import com.airline.locationservice.repository.AirportCodeIataRepository;
import com.airline.locationservice.repository.AirportCodeIcao;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.delta.rm.core.location.AirportCode;
import com.delta.rm.core.location.IATAAirportCode;

// import com.delta.rm.core.location.AirportCode;
// import com.delta.rm.core.location.IATAAirportCode;

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
        List<AirportCodeIata> result = repository.findAll();
        List<AirportCode> airports =
            result.stream().map( iata -> new IATAAirportCode(iata.getIataCode()) )
                           .collect( Collectors.toList() );

        // airports.forEach( System.out::println );

        return airports;
    }

    @PostMapping("")
    AirportCodeIata newAirportCode( @RequestBody IATAAirportCode newAirportCode )
    {
        return repository.save( new AirportCodeIata( newAirportCode.getAirportCode() ));
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
    AirportCode one(@PathVariable String id )
    {
        AirportCodeIata iata = repository.findById(id)
                                         .orElseThrow(() -> new AirportCodeNotFoundException( id ));

        return new IATAAirportCode( iata.getIataCode() );
    }


    @PutMapping("/{id}")
    AirportCode replaceAirportCode( @RequestBody AirportCodeIata newAirportCode, @PathVariable String id )
    {
        AirportCodeIata iata = repository.findById(id)
            .map( airportCode -> {
                airportCode.setIataCode(id);

                return repository.save( airportCode );
            })
            .orElseGet(() -> {
                newAirportCode.setIataCode( id );
                return repository.save( newAirportCode );
            });

        // Map the response to a Domain Object
        return new IATAAirportCode( iata.getIataCode() );
    }


    @DeleteMapping("/{id}")
    void deleteAirportCode( @PathVariable String id )
    {
        repository.deleteById(id);
    }

}




