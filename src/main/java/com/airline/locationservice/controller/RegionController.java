package com.airline.locationservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.airline.locationservice.persistence.model.Region;
// import com.airline.locationservice.repository.AirportCodeIata;
// import com.airline.locationservice.repository.AirportCodeIataRepository;
import com.airline.locationservice.persistence.repository.RegionsRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
@RequestMapping( "/location/region" )
public class RegionController
{
    private final RegionsRepository repository;

    RegionController( RegionsRepository repository )
    {
        this.repository = repository;
    }


    // @PageableDefault(size = 10, direction = Sort.Direction.DESC, sort = "someField")

    @GetMapping( "" )
    public Page<Region> all( @PageableDefault( page = 0, size = 20 ) Pageable pageable )
    {
        // Get list of airport codes from the DB, potentially an empty list.
        Page<Region> result = repository.findAll( pageable );

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

    @GetMapping( "/{id}" )
    public ResponseEntity<Region> one( @PathVariable Integer id )
    {
        Optional<Region> region = repository.findById( id );
                                        //  .orElseThrow(() -> new AirportCodeNotFoundException( "999" ));
        if ( region.isPresent() )
            return ResponseEntity.ok( region.get() );
        else
            return ResponseEntity.notFound().build();
        // return new ResponseEntity<>( new IATAAirportCode( iata.getIataCode() ),
        //                              HttpStatus.OK );
    }



    @GetMapping("/byContinent/{continent}")
    public ResponseEntity<Page<Region>> regionByContinent( @PathVariable String continent, Pageable paging )
    {
        Page<Region> region = repository.findByContinent( continent, paging );

        return ResponseEntity.ok( region );
    }

    @GetMapping("/byCountry/{country}")
    public ResponseEntity<Page<Region>> regionByCountry( @PathVariable String country, Pageable paging )
    {
        Page<Region> region = repository.findByCountry( country, paging);

        return ResponseEntity.ok(region);
    }

    // ! Add 'like' searches for name

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
