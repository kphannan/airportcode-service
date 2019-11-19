package com.airline.locationservice.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table( name = "IATA_AIRPORT", schema = "RMA" )
@Data
public class AirportCode
{
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private final Long      id;

    @Column( name = "IATA_CODE" )
    private String iataCode;

    @Column( name = "ICAO_CODE" )
    private String icaoCode;
}

