package com.airline.locationservice;

import com.airline.locationservice.repository.AirportCode;
import com.airline.locationservice.repository.AirportCodeRepository;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.cache.annotation.EnableCaching;
// import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
// import org.springframework.cloud.openfeign.EnableFeignClients;
// import org.springframework.context.annotation.Bean;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
// import org.springframework.web.filter.CorsFilter;
// import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
// import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
// @EnableCaching
// @EnableZuulProxy
// @EnableFeignClients
public class LocationServiceApplication
{
    // private static final Logger log = LoggerFactory.getLogger(LocationServiceApplication.class);

	public static void main( String[] args )
	{
        log.info( "Starting the app" );
		SpringApplication.run( LocationServiceApplication.class, args );
    }
    

    @Bean
    public CommandLineRunner demo( AirportCodeRepository repository)
    // public CommandLineRunner demo( AirportCodeRepository repository )
    {
        return (args) -> {
            log.info( "----- Load the DB -----" );
            repository.save( new AirportCode( "ATL" ));
            repository.save( new AirportCode( "MSP" ));
            repository.save( new AirportCode( "NRT" ));


            log.info( "Fetch items from DB");
            for (AirportCode airport : repository.findAll() )
            {
                log.info( airport.toString() );
            }
            log.info( "----- Done -----");
        };
    }



    /**
     * This is the cors filter.
     *
     * @return
     */
    // @Bean
	// public FilterRegistrationBean corsFilter() 
	// {
    //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //     CorsConfiguration config = new CorsConfiguration();
    //     config.setAllowCredentials(true);
    //     config.addAllowedOrigin("*");
    //     config.addAllowedHeader("*");
    //     config.addAllowedMethod("*");
    //     source.registerCorsConfiguration("/**", config);
    //     FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
    //     bean.setOrder(0);
    //     return bean;
    // }

}

