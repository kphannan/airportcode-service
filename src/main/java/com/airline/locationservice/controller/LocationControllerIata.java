package com.airline.locationservice.controller;

import java.util.List;

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
    List<AirportCodeIata> all()
    {
        return repository.findAll();
    }

    @PostMapping("")
    AirportCodeIata newAirportCode( @RequestBody AirportCodeIata newAirportCode )
    {
        return repository.save( newAirportCode );
    }


    // Single item

    @GetMapping("/{id}")
    AirportCodeIata one(@PathVariable String id )
    {
        return repository.findById(id)
                         .orElseThrow(() -> new AirportCodeNotFoundException( id ));
    }


    @PutMapping("/{id}")
    AirportCodeIata replaceAirportCode( @RequestBody AirportCodeIata newAirportCode, @PathVariable String id )
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




