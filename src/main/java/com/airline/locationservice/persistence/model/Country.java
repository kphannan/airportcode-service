package com.airline.locationservice.persistence.model;

import java.net.URI;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table( name = "countries" )
@Data
public class Country
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

    @Column( name = "name" )
    @NotNull
    private String name;

    @Column( name = "continent" )
    @NotNull
    private String continent;

    @Column( name = "wikipedia_link" )
    @Convert(converter = UriConverter.class)
    private URI  wikipediaLink;

    @Column( name = "keywords" )
    private String keywords;

}

