package com.airline.locationservice.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "AIRPORTS" )
@Data
@NoArgsConstructor
public class AirportCodeIcao
{
    @Id
    @Column( name = "ICAO_CODE" )
    private String icaoCode;

    // protected AirportCodeIcao() {}

    public AirportCodeIcao( String code )
    {
        icaoCode = code;
    }
}

