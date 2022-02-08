package com.airline.locationservice.controller;



/**
 * Exception denoting that an ISO airport code is not present in any reference list.
 */
public class AirportCodeNotFoundException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    @SuppressWarnings( "PMD.ShortVariable" )
    AirportCodeNotFoundException( final String id )
    {
        super( "Could not find airport code ''" + id  + "'" );
    }
}
