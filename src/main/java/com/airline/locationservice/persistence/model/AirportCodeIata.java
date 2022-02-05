package com.airline.locationservice.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "iata_airportcode" )
@Data
@NoArgsConstructor
public class AirportCodeIata
{
    @Id
    @Column( name = "IATA_CODE" )
    // @Value("#{' matches [A-Z]{3}'}")
    private String iataCode = "ZZZ";

    public AirportCodeIata( final String iataCode )
    {
        this.iataCode = iataCode;
    }
}

