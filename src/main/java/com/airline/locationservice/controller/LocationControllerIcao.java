package com.airline.locationservice.controller;

import java.util.List;
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

import com.delta.rm.core.location.AirportCode;
import com.delta.rm.core.location.ICAOAirportCode;

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
        List<AirportCode> airports =
            result.stream().map( icao -> new ICAOAirportCode(icao.getIcaoCode()) )
                           .collect( Collectors.toList() );

        return airports;
    }

    // @PostMapping("")
    // AirportCode newAirportCode( @RequestBody ICAOAirportCode newAirportCode )
    // {
    //     AirportCodeIcao icao = repository.save( new AirportCodeIcao( newAirportCode.getAirportCode() ));

    //     return new ICAOAirportCode( icao.getIcaoCode() );
    // }
    @PostMapping("")
    // AirportCodeIcao newAirportCode( @RequestBody AirportCodeIcao newAirportCode )
    AirportCodeIcao newAirportCode( @RequestBody ICAOAirportCode newAirportCode )
    {
        AirportCodeIcao icao = new AirportCodeIcao( newAirportCode.getAirportCode() );
        return repository.save( icao );
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
    AirportCode one(@PathVariable String id )
    {
        AirportCodeIcao icao = repository.findById(id)
                                         .orElseThrow(() -> new AirportCodeNotFoundException( id ));

        return new ICAOAirportCode( icao.getIcaoCode() );
    }


    @PutMapping("/{id}")
    AirportCode replaceAirportCode( @RequestBody ICAOAirportCode newAirportCode, @PathVariable String id )
    {
        AirportCodeIcao icao =  repository.findById(id)
            .map( airportCode -> {
                airportCode.setIcaoCode(id);

                return repository.save( airportCode );
            })
            .orElseGet(() -> {
                return repository.save( new AirportCodeIcao( id ));
            });

        // Map the response to a Domain Object
        return new ICAOAirportCode( icao.getIcaoCode() );
    }


    @DeleteMapping("/{id}")
    void deleteAirportCode( @PathVariable String id )
    {
        repository.deleteById(id);
    }

}




