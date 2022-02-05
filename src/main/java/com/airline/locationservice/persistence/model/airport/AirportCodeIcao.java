package com.airline.locationservice.persistence.model.airport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "icao_airportcode" )
@Data
@NoArgsConstructor
public class AirportCodeIcao
{
    @Id
    @Column( name = "icao_code" )
    private String icaoCode;

    public AirportCodeIcao( final String icaoCode )
    {
        this.icaoCode = icaoCode;
    }
}

