package com.airline.locationservice.controller;

import java.util.List;

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
    List<AirportCodeIcao> all()
    {
        return repository.findAll();
    }

    @PostMapping("")
    AirportCodeIcao newAirportCode( @RequestBody AirportCodeIcao newAirportCode )
    {
        return repository.save( newAirportCode );
    }


    // Single item

    @GetMapping("/{id}")
    AirportCodeIcao one(@PathVariable String id )
    {
        return repository.findById(id)
                         .orElseThrow(() -> new AirportCodeNotFoundException( id ));
    }


    @PutMapping("/{id}")
    AirportCodeIcao replaceAirportCode( @RequestBody AirportCodeIcao newAirportCode, @PathVariable String id )
    {
        return repository.findById(id)
            .map( airportCode -> {
                airportCode.setIcaoCode(id);

                return repository.save( airportCode );
            })
            .orElseGet(() -> {
                newAirportCode.setIcaoCode( id );
                return repository.save( newAirportCode );
            });
    }


    @DeleteMapping("/{id}")
    void deleteAirportCode( @PathVariable String id )
    {
        repository.deleteById(id);
    }

}




