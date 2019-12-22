package com.airline.locationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
// @EnableCaching
// @EnableZuulProxy
// @EnableFeignClients
public class LocationServiceApplication
{

	public static void main( String[] args )
	{
        log.info( "Starting the app" );
		SpringApplication.run( LocationServiceApplication.class, args );
    }
    
}

