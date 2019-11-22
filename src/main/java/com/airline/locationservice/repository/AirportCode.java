package com.airline.locationservice.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table( name = "IATA_AIRPORTCODE" )
@Data
public class AirportCode
{
    // @Id
    // @GeneratedValue( strategy = GenerationType.AUTO )
    // private final Long      id;

    @Id
    @Column( name = "IATA_CODE" )
    private String iataCode;

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

