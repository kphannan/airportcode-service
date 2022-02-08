package com.airline.locationservice.persistence.model.airport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Definition of operations on he IATA reference table.
 */
@Entity
@Table( name = "iata_airportcode" )
@Data
@NoArgsConstructor
public class AirportCodeIata
{
    @Id
    @Column( name = "IATA_CODE" )
    // @Value("#{' matches [A-Z]{3}'}")
    private String iataCode;

    /**
     * Instantiate a IATA airport code record.
     *
     * @param iataCode the IATA code string.
     */
    public AirportCodeIata( final String iataCode )
    {
        this.iataCode = hasText( iataCode ) ? iataCode : "ZZZ";
    }


    private boolean hasText( final String value )
    {
        if ( null != value )
        {
            return !value.trim().isBlank();
        }

        return false;
    }
}

