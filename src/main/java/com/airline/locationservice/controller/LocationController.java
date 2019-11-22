package com.airline.locationservice.controller;

import java.util.List;

import com.airline.locationservice.repository.AirportCode;
import com.airline.locationservice.repository.AirportCodeRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/airport/iata")
public class LocationController
{
    private final AirportCodeRepository     repository;

    LocationController( AirportCodeRepository repository )
    {
        this.repository = repository;
    }


    @GetMapping("")
    List<AirportCode> all()
    {
        return repository.findAll();
    }

    @PostMapping("")
    AirportCode newAirportCode( @RequestBody AirportCode newAirportCode )
    {
        return repository.save( newAirportCode );
    }


    // Single item

    @GetMapping("/{id}")
    AirportCode one(@PathVariable String id )
    {
        return repository.findById(id)
                         .orElseThrow(() -> new AirportCodeNotFoundException( id ));
    }


    @PutMapping("/{id}")
    AirportCode replaceAirportCode( @RequestBody AirportCode newAirportCode, @PathVariable String id )
    {
        return repository.findById(id)
            .map( airportCode -> {
                airportCode.setIataCode(id);

                return repository.save( airportCode );
            })
            .orElseGet(() -> {
                newAirportCode.setIataCode( id );
                return repository.save( newAirportCode );
            });
    }


    @DeleteMapping("/{id}")
    void deleteAirportCode( @PathVariable String id )
    {
        repository.deleteById(id);
    }

}




