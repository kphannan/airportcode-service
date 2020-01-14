package com.airline.locationservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.airline.locationservice.model.Country;
// import com.airline.locationservice.repository.AirportCodeIata;
// import com.airline.locationservice.repository.AirportCodeIataRepository;
import com.airline.locationservice.repository.CountryRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

// import com.airline.core.location.AirportCode;
// import com.airline.core.location.IATAAirportCode;

@RestController
@RequestMapping("/location/country")
public class CountryController {
    private final CountryRepository repository;

    CountryController( CountryRepository repository )
    {
        this.repository = repository;
    }


    @GetMapping("")
    Page<Country> all(Pageable pageable)
    {
        // Get list of airport codes from the DB, potentially an empty list.
        Page<Country> result = repository.findAll(pageable);

        return result;
    }

    // @PostMapping("")
    // ResponseEntity<AirportCode> newAirportCode( @RequestBody IATAAirportCode newAirportCode )
    // {
    //     AirportCodeIata iataDB = repository.save( new AirportCodeIata( newAirportCode.getAirportCode() ));

    //     // Return 201 (Created)
    //     return new ResponseEntity<>(new IATAAirportCode( iataDB.getIataCode() ), HttpStatus.CREATED );
    // }

    // // @PostMapping("/validate")
    // // Boolean validateAirportCode( @RequestBody IATAAirportCode newAirportCode )
    // // {
    // //     System.out.println( newAirportCode );
    // //     // return repository.save( newAirportCode );
    // //     return true;
    // // }





    // // Single item

    @GetMapping("/{id}")
    ResponseEntity<Country> findCountryById(@PathVariable Integer id )
    {
        Optional<Country> region = repository.findById(id);

        if ( region.isPresent() )
            return ResponseEntity.ok(region.get());
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/code/{countryCode}")
    ResponseEntity<Country> findCountryByCode(@PathVariable String countryCode )
    {
        Optional<Country> region = repository.findByCode(countryCode);

        if ( region.isPresent() )
            return ResponseEntity.ok(region.get());
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/continent/{continentCode}")
    ResponseEntity<List<Country>> findCountriesByContinent(@PathVariable String continentCode )
    {
        List<Country> region = repository.findByContinent(continentCode);

        return ResponseEntity.ok( region );
    }



    // @PutMapping("/{id}")
    // ResponseEntity<AirportCode> replaceAirportCode( @RequestBody AirportCodeIata newAirportCode, @PathVariable String id )
    // {
    //     Optional<AirportCodeIata> iata = repository.findById(id);
    //     if ( iata.isPresent() )
    //     {
    //         return new ResponseEntity<>( new IATAAirportCode( iata.get().getIataCode()), HttpStatus.OK );
    //     }

    //     // Map the response to a Domain Object
    //     return new ResponseEntity<>( new IATAAirportCode( repository.save( newAirportCode ).getIataCode()),
    //                                  HttpStatus.CREATED );
    // }


    // @DeleteMapping("/{id}")
    // ResponseEntity<Boolean> deleteAirportCode( @PathVariable String id )
    // {
    //     Optional<AirportCodeIata> iata = repository.findById(id);
    //     if ( iata.isPresent() )
    //     {
    //         // HTTP 204 (No Content)
    //         repository.deleteById(id);
    //         return new ResponseEntity<>(Boolean.TRUE, HttpStatus.NO_CONTENT);
    //     }
    //     else
    //     {
    //         return new ResponseEntity<>( Boolean.FALSE, HttpStatus.NOT_FOUND);
    //     }
    // }

}
