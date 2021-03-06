package com.airline.locationservice.model;

import java.net.URI;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table( name = "regions" )
@Data
public class Region
{
    // @Id
    // @GeneratedValue( strategy = GenerationType.AUTO )
    // private final Long      id;

    @Id
    @Column( name = "id" )
    @NotNull
    private Integer id;

    @Column( name = "code" )
    @NotNull
    private String code;

    @Column( name = "local_code" )
    @NotNull
    private String localCode;

    @Column( name = "name" )
    @NotNull
    private String name;

    @Column( name = "iso_country" )
    @NotNull
    private String iso_country;

    @Column( name = "continent" )
    @NotNull
    private String continent;

    @Column( name = "wikipedia_link" )
    @Convert(converter = UriConverter.class)
    private URI  wikipediaLink;

    @Column( name = "keywords" )
    private String keywords;

}

