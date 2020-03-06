package com.airline.locationservice.controller;

import static org.assertj.core.api.Assertions.*;

import static org.mockito.BDDMockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
// import java.util.Arrays;
import java.util.Collections;
// import java.util.List;
import java.util.Optional;
// import java.util.stream.Collectors;

import com.airline.locationservice.model.Country;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockHttpServletResponse;
// import org.springframework.http.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import lombok.extern.log4j.Log4j2;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.airline.locationservice.repository.CountryRepository;

// @ExtendWith(MockitoExtension.class)
@SpringBootTest
@Log4j2
public class CountryControllerTest
{
    // @Autowired
    private MockMvc   mvc;

    // mock repository
    @Mock
    CountryRepository repository;

    @InjectMocks
    CountryController    service;
    // private AirportCodeRes

    @BeforeEach
    public void setup()
    {
        mvc = MockMvcBuilders.standaloneSetup(service)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                // .setControllerAdvice(new SuperHeroExceptionHandler())
                // .addFilters(new SuperHeroFilter())
                .build();
    }


    // @Test
    // public void getSingleKnownCountryId()
    //     throws Exception
    // {
    //     // 302797 - is the DB key for Country 'Guyana'
    //     // final IATAAirportCode airportCode = new IATAAirportCode( "ATL" );
    //     // assertThat( airportCode ).isNotNull();

    //     // when( repository.findById("ATL")).thenReturn( Optional.of( new AirportCodeIata( airportCode.getAirportCode() )));
    //     given( repository.findById(302797)).willReturn( Optional.of( new Country( )));

    //     final MockHttpServletResponse response = mvc.perform(
    //         get("/location/country/{id}", 302797 )
    //             .accept( MediaType.APPLICATION_JSON ))
    //         .andExpect(status().isOk() )
    //         // .andExpect( jsonPath("$.code", equalTo( "GY")))
    //         .andReturn().getResponse();

    //     // assert the response contains the airport code
    //     // assertThat(response)
    //     //     .isNotNull()
    //     //     .has()

    //     // assertThat(response).ok();
    //     // LocationControllerIata controller = new LocationControllerIata( mockRepository );
    // }


    

    @Test
    public void getSingleNotKnownCountryId()
        throws Exception
    {
        given( repository.findById(999999)).willReturn( Optional.ofNullable( null ));

        final MockHttpServletResponse response = mvc.perform(
            get("/location/country/{id}", 999999 )
                .accept( MediaType.APPLICATION_JSON ))
            .andExpect(status().isNotFound() )
            // .andExpect( jsonPath("$.code", equalTo( "GY")))
            .andReturn().getResponse();
    }

    @Test
    public void getSingleKnownCountryId()
        throws Exception
    {
        // --- Given ---
        // 302797 - is the DB key for Country 'Guyana'
        Country country = new Country();
        country.setCode("GY");
        country.setName("Guyana");
        country.setId(302797);
        given( repository.findById(302797)).willReturn( Optional.of( country ));

        // --- When
        final MockHttpServletResponse response = mvc.perform(
            get("/location/country/{id}", 302797 )
                .accept( MediaType.APPLICATION_JSON ))
            .andExpect( status().isOk() )
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().json("{\"id\": 302797, \"code\": \"GY\", \"name\": \"Guyana\"}"))
            .andDo(MockMvcResultHandlers.print())
            .andReturn().getResponse();
            verify(repository).findById(302797);
            verify(repository, times(1)).findById(302797);

        assertThat(response.getContentAsString())
            .as( "Response contains an airport with IATA code 'GY'")
            .doesNotContainAnyWhitespaces()
            .contains("GY");
    }



    @Test
    public void getSingleKnownCountryCode()
        throws Exception
    {
        // 302797 - is the DB key for Country 'Guyana'

        given( repository.findByCode("GY")).willReturn( Optional.of( new Country( )));

        final MockHttpServletResponse response = mvc.perform(
            get("/location/country/code/{countryCode}", "GY" )
                .accept( MediaType.APPLICATION_JSON ))
            .andExpect(status().isOk() )
            // .andExpect( jsonPath("$.code", equalTo( "GY")))
            .andReturn().getResponse();

        log.error( "Content type {}", () -> response.getContentType());
        log.error( "Content {}", () -> content());
        System.out.println( "Content type " + response.getContentType());
        System.out.println( "Content " + content());
    }


    @Test
    public void getSingleNotKnownCountryCode()
        throws Exception
    {
        given( repository.findByCode("ZZ")).willReturn( Optional.ofNullable( null ));

        final MockHttpServletResponse response = mvc.perform(
            get("/location/country/code/{countryCode}", "ZZ" )
                .accept( MediaType.APPLICATION_JSON ))
            .andExpect(status().isNotFound() )
            .andReturn().getResponse();
    }




// ------------
@Test
public void findCountriesFromKnownContinentCode()
    throws Exception
{
    // 302797 - is the DB key for Country 'Guyana'

    given( repository.findByContinent("NA")).willReturn( new ArrayList<Country>() );

    final MockHttpServletResponse response = mvc.perform(
        get("/location/country/continent/{countryCode}", "NA" )
            .accept( MediaType.APPLICATION_JSON ))
        .andExpect(status().isOk() )
        // .andExpect( jsonPath("$.code", equalTo( "GY")))
        .andReturn().getResponse();

    log.error( "Content type {}", () -> response.getContentType());
    log.error( "Content {}", () -> content());
    System.out.println( "Content type " + response.getContentType());
    System.out.println( "Content " + content());
}


@Test
public void findCountriesFromInvalidContinentCode()
    throws Exception
{
    given( repository.findByContinent("ZZ")).willReturn( new ArrayList<Country>() );

    final MockHttpServletResponse response = mvc.perform(
        get("/location/country/continent/{countryCode}", "ZZ" )
            .accept( MediaType.APPLICATION_JSON ))
        .andExpect(status().isOk() )
        // .andExpect(jsonPath("$").doesNotExist())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$").isEmpty())
        .andReturn().getResponse();

    // assertTrue(response.)
}

// ------------



    @Test
    public void getAllCountriesIsEmpty()
        throws Exception
    {
        given( repository.findAll()).willReturn( Collections.emptyList());

        final MockHttpServletResponse response = mvc.perform(
            get("/location/country" ))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk() )
            .andExpect(jsonPath("$").doesNotExist())
            .andReturn().getResponse();
    }

}

 
 
 