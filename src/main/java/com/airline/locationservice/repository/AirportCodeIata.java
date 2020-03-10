package com.airline.locationservice.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// import org.springframework.beans.factory.annotation.Value;

import lombok.Data;
// import lombok.Value;

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
    // @Value("#{' matches [A-Z]{3}'}")
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

