package com.airline.locationservice.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table( name = "ICAO_AIRPORTCODE" )
@Data
public class AirportCodeIcao
{
    @Id
    @Column( name = "ICAO_CODE" )
    private String icaoCode;

    @Override
    public String toString()
    {
        // return "{ICAO airport code: '" + icaoCode + "'}";
        return icaoCode;
    }

    protected AirportCodeIcao() {}

    public AirportCodeIcao( String code )
    {
        icaoCode = code;
    }
}

