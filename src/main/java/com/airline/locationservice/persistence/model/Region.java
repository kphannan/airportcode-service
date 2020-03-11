package com.airline.locationservice.persistence.model;

import java.net.URI;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * Each row represents a high-level administrative subdivision of a country.
 * The iso_region column in airports.csv links to the code column in 
 * this dataset.
 */
@Entity
@Table( name = "regions" )
@Data
public class Region
{
    /**
     * Internal OurAirports integer identifier for the region.
     * This will stay persistent, even if the region code changes.
     */
    @Id
    // @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "id" )
    @NotNull
    private Integer id;

    /**
     * local_code prefixed with the country code to make 
     * a globally-unique identifier.
     */
    @Column( name = "code" )
    @NotNull
    private String code;

    /**
     * The local code for the administrative subdivision. Whenever possible,
     * these are official ISO 3166:2, at the highest level available, but in 
     * some cases OurAirports has to use unofficial codes. There is also a
     * pseudo code "U-A" for each country, which means that the airport has 
     * not yet been assigned to a region (or perhaps can't be, as in the case
     * of a deep-sea oil platform).
     */
    @Column( name = "local_code" )
    @NotNull
    private String localCode;

    /**
     * The common English-language name for the administrative subdivision.
     * In some cases, the name in local languages will appear in the keywords 
     * field assist search.
     */
    @Column( name = "name" )
    @NotNull
    private String name;

    /**
     * The two-character ISO 3166:1-alpha2 code for the country containing the
     * administrative subdivision. A handful of unofficial, non-ISO codes are
     * also in use, such as "XK" for Kosovo.
     */
    @Column( name = "iso_country" )
    @NotNull
    private String country; // ! Create domain object for the country code

    /**
     * A code for the continent to which the region belongs. See the continent 
     * field in airports.csv for a list of codes.
     */
    @Column( name = "continent" )
    @NotNull
    private String continent;   // ! Create domain object for contient code

    /**
     * A link to the Wikipedia article describing the subdivision.
     */
    @Column( name = "wikipedia_link" )
    @Convert(converter = UriConverter.class)
    private URI  wikipediaLink;

    /**
     * A comma-separated list of keywords to assist with search.
     * May include former names for the region, and/or the region name 
     * in other languages.
     */
    @Column( name = "keywords" )
    private String keywords;
}

