package com.airline.locationservice.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
// @Table( name = "IATA_AIRPORT", schema = "RMA" )
@Data
public class AirportCode
{
    // @Id
    // @GeneratedValue( strategy = GenerationType.AUTO )
    // private final Long      id;

    @Id
    @Column( name = "IATA_CODE" )
    private String iataCode;

    // @Column( name = "ICAO_CODE" )
    // private String icaoCode;

    @Override
    public String toString()
    {
        // return "{IATA airport code: '" + iataCode + "'}";
        return iataCode;
    }

    protected AirportCode() {}

    public AirportCode( String code )
    {
        iataCode = code;
    }
}

