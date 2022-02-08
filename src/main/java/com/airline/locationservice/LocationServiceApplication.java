package com.airline.locationservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * REST service application.
 */
@Slf4j
@SpringBootApplication
// @EnableCaching
// @EnableZuulProxy
// @EnableFeignClients
// @EnableJpaRepositories("com.airline.locationservice.repository")
@EnableJpaRepositories( "com.airline.locationservice" )
public final class LocationServiceApplication
{

    /**
     * Entry point of the Location REST service.
     *
     * @param args optional commandline arguments.
     */
    public static void main( final String[] args )
    {
        log.info( "Starting the app" );
        SpringApplication.run( LocationServiceApplication.class, args );
    }


    private LocationServiceApplication()
    {
        // Prevent instantiation
    }
}

