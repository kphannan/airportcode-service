package com.airline.locationservice.persistence.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "AIRPORTS" )
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Airport
{
    /**
     * T Internal OurAirports integer identifier for the airport. This will
     * stay persistent, even if the airport code changes.
     */
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Long id;

    /**
     * The text identifier used in the OurAirports URL.
     * This will be the ICAO code if available. Otherwise, it will be a 
     * local airport code (if no conflict), or if nothing else is available,
     * an internally-generated code starting with the ISO2 country code,
     * followed by a dash and a four-digit number.
     */
    @NotNull
    private String ident;

    /**
     * The type of the airport. Allowed values are "closed_airport",
     * "heliport", "large_airport", "medium_airport", "seaplane_base",
     * and "small_airport". See the map legend for a definition of each type.
     */
    @NotNull
     private String type;

    /**
     * The official airport name, including "Airport", "Airstrip", etc.
     */
    @NotNull
    private String name;

    /**
     * The airport latitude in decimal degrees (positive for north).
     */
    @Column( name = "LATITUDE_DEG" )
    @NotNull
    private BigDecimal lattitude;
    /**
     * The airport longitude in decimal degrees (positive for east).
     */
    @Column( name = "LONGITUDE_DEG" )
    @NotNull
    private BigDecimal longitude;

    /**
     * The airport elevation MSL in feet (not metres).
     */
    @Column( name = "ELEVATION_FT" )
    private int elevation;

    /**
     * The code for the continent where the airport is (primarily) located.
     * Allowed values are "AF" (Africa), "AN" (Antarctica), "AS" (Asia),
     * "EU" (Europe), "NA" (North America), "OC" (Oceania),
     * or "SA" (South America).
     */
    @NotNull
    private String continent;

    /**
     * The two-character ISO 3166:1-alpha2 code for the country where the
     * airport is (primarily) located. A handful of unofficial, non-ISO codes
     * are also in use, such as "XK" for Kosovo. Points to the code column
     * in countries.csv.
     */
    @NotNull
    private String isoCountry;

    /**
     * 'An alphanumeric code for the high-level administrative subdivision
     * of a country where the airport is primarily located (e.g. province,
     * governorate), prefixed by the ISO2 country code and a hyphen.
     * OurAirports uses ISO 3166:2 codes whenever possible, preferring higher
     * administrative levels, but also includes some custom codes. See the
     * documentation for regions.csv.'
     */
    @NotNull
    private String isoRegion;

    /**
     * The primary municipality that the airport serves (when available).
     * Note that this is not necessarily the municipality where the airport
     * is physically located.
     */
    @NotNull
    private String municipality;

    /**
     * "yes" if the airport currently has scheduled airline service;
     * "no" otherwise.
     */
    @NotNull
    private String scheduledService;   // boolean...

    /**
     * The code that an aviation GPS database (such as Jeppesen's or Garmin's)
     * would normally use for the airport. This will always be the ICAO code
     * if one exists. Note that, unlike the ident column, this is not
     * guaranteed to be globally unique.
     */
    @NotNull
    private String gpsCode;

    /**
     * The three-letter IATA code for the airport (if it has one).
     */
    @Column( name = "IATA_CODE" )
    private String iataCode;

    /**
     * The local country code for the airport, if different from the gps_code
     * and iata_code fields (used mainly for US airports).
     */
    private String localCode;

    /**
     * URL of the airport''s official home page on the web, if one exists.
     */
    private String homeLink;            // URI

    /**
     * URL of the airport''s page on Wikipedia, if one exists.
     */
    private String wikipediaLink;       // URI

    /**
     * Extra keywords/phrases to assist with search, comma-separated.
     * May include former names for the airport, alternate codes, names in
     * other languages, nearby tourist destinations, etc.
     */
    private String keywords;





    // ----- Auditable -----
    // @CreatedBy
    // User creator;
     
    // @CreatedDate
    // Date createdAt;
     
    // @LastModifiedDate
    // Date modifiedAt;
    
    // @LastModifiedBy
    // User modifier;
}

