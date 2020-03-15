package com.airline.locationservice.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
// import lombok.Value;
import lombok.NoArgsConstructor;
// import lombok.RequiredArgsConstructor;

@Entity
@Table( name = "AIRPORTS" )
@Data
// @Value
@NoArgsConstructor
// @RequiredArgsConstructor
public class AirportCodeIata
{
    @Id
    @Column( name = "IATA_CODE" )
    // @Value("#{' matches [A-Z]{3}'}")
    private String iataCode = "ZZZ";

    // ! Would like a single arg c'tor for 'iataCode' and no setter
    // protected AirportCodeIata()
    //     // throws IllegalArgumentException
    // {
    //     // throw new IllegalArgumentException( "Airport code is required." );
    //     iataCode = "zzz";
    // }

    public AirportCodeIata( final String iataCode )
    {
        this.iataCode = iataCode;
    }
}

