package com.airline.locationservice.controller;




public class AirportCodeNotFoundException extends RuntimeException
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    AirportCodeNotFoundException(String id)
    {
        super( "Could not find airport code ''" + id  + "'" );
    }
}
