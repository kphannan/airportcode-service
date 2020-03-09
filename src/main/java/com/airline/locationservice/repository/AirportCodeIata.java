package com.airline.locationservice.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table( name = "AIRPORTS" )
@Data
public class AirportCodeIata
{
    // @Id
    // @GeneratedValue( strategy = GenerationType.AUTO )
    // private final Long      id;

    @Id
    @Column( name = "IATA_CODE" )
    private final String iataCode;

    // @Override
    // public String toString()
    // {
    //     // return "{IATA airport code: '" + iataCode + "'}";
    //     return iataCode;
    // }

    protected AirportCodeIata()
        // throws IllegalArgumentException
    {
        // throw new IllegalArgumentException( "Airport code is required." );
        iataCode = "zzz";
    }

    public AirportCodeIata( final String code )
    {
        iataCode = code;
    }
}

