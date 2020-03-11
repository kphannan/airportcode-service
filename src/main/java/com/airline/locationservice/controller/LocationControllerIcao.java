package com.airline.locationservice.controller;

import java.util.Optional;

import com.airline.locationservice.persistence.model.AirportCodeIcao;
import com.airline.locationservice.persistence.repository.AirportCodeIcaoRepository;
import com.airline.core.location.AirportCode;
import com.airline.core.location.ICAOAirportCode;
import com.airline.core.location.AirportCodeFactory;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/location/airport/icao")
public class LocationControllerIcao
{
    private final AirportCodeIcaoRepository repository;

    LocationControllerIcao(AirportCodeIcaoRepository repository)
    {
        this.repository = repository;
    }



    @GetMapping("")
    ResponseEntity<Page<AirportCode>> all( Pageable paging )
    {
        Page<AirportCodeIcao> result = repository.findAll( paging );

        return ResponseEntity.ok(result.map(iata -> new ICAOAirportCode(iata.getIcaoCode())));
    }


    @GetMapping("/{id}")
    ResponseEntity<AirportCode> one(@PathVariable String id)
    {
        Optional<AirportCodeIcao> iao = repository.findById(id);
        if (iao.isPresent())
        {
            return ResponseEntity.ok(AirportCodeFactory.build(iao.get().getIcaoCode()));
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }
}


