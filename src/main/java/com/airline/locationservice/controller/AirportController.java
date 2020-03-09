package com.airline.locationservice.controller;

import java.util.List;
import java.util.Optional;
import java.util.Collections;
// import java.util.stream.Collectors;

import com.airline.locationservice.persistence.model.Airport;
import com.airline.locationservice.persistence.repository.AirportRepository;
// import com.airline.core.location.AirportCode;
// import com.airline.core.location.ICAOAirportCode;
// import com.airline.core.location.AirportCodeFactory;

// import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
// import org.springframework.http.HttpStatus;

@RestController
// @RequestMapping("/location/airport")
@RequestMapping("/")
public class AirportController {

    private final AirportRepository repository;

    AirportController(AirportRepository repository) {
            this.repository = repository;
    }

    @GetMapping("")
    ResponseEntity<List<Airport>> all() {
        List<Airport> result = repository.findAll();

        if (result.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        // Map the results to Domain Objects rather than DB objects
        // List<Airport> airports = result.stream().map(iata -> new ICAOAirportCode(iata.getIcaoCode()))
        //         .collect(Collectors.toList());

        // return ResponseEntity.ok(airports);
        return ResponseEntity.ok(result);
    }

    // @PostMapping("")
    // ResponseEntity<AirportCode> newAirportCode(@RequestBody ICAOAirportCode newAirportCode) {
    //     Airport icaoDB = repository.save(new Airport(newAirportCode.getAirportCode()));

    //     // Return 201 (Created)
    //     return new ResponseEntity<>(new ICAOAirportCode(icaoDB.getIcaoCode()), HttpStatus.CREATED);
    // }

    @GetMapping("/{id}")
    ResponseEntity<Optional<Airport>> one(@PathVariable Long id) {
        Optional<Airport> airport = repository.findById(id);
            if (airport.isPresent())
            return ResponseEntity.ok(airport);
        else
            return ResponseEntity.notFound().build();
    }

    // @PutMapping("/{id}")
    // ResponseEntity<AirportCode> replaceAirportCode(@RequestBody ICAOAirportCode newAirportCode,
    //         @PathVariable String id) {
    //     Optional<Airport> icao = repository.findById(id);
    //     if (icao.isPresent()) {
    //         return new ResponseEntity<>(new ICAOAirportCode(icao.get().getIcaoCode()), HttpStatus.OK);
    //     }

    //     // Map the response to a Domain Object
    //     return new ResponseEntity<>(
    //             new ICAOAirportCode(
    //                     repository.save(new Airport(newAirportCode.getAirportCode())).getIcaoCode()),
    //             HttpStatus.CREATED);
    // }

    // @DeleteMapping("/{id}")
    // ResponseEntity<Boolean> deleteAirportCode(@PathVariable String id) {
    //     Optional<AirportCodeIcao> icao = repository.findById(id);
    //     if (icao.isPresent()) {
    //         // HTTP 204 (No Content)
    //         repository.deleteById(id);
    //         return new ResponseEntity<>(Boolean.TRUE, HttpStatus.NO_CONTENT);
    //     } else {
    //         return new ResponseEntity<>(Boolean.FALSE, HttpStatus.NOT_FOUND);
    //     }
    // }

}
