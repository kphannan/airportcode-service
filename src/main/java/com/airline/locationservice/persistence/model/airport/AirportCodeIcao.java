package com.airline.locationservice.persistence.model.airport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Definition of the IATA reference table.
 */
@Entity
@Table( name = "icao_airportcode" )
@Data
@NoArgsConstructor
public class AirportCodeIcao
{
    @Id
    @Column( name = "icao_code" )
    private String icaoCode;

    /**
     * Instantiate a ICAO airport code record.
     *
     * @param icaoCode the ICAO code string.
     */
    public AirportCodeIcao( final String icaoCode )
    {
        this.icaoCode = icaoCode;
    }
}

