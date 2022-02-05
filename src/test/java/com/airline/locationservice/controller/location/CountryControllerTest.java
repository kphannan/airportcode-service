package com.airline.locationservice.controller.location;

import static org.hamcrest.Matchers.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.anyInt;
// import static org.mockito.BDDMockito.anyString;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.airline.locationservice.controller.location.CountryController;
import com.airline.locationservice.persistence.model.location.Country;

import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import lombok.extern.log4j.Log4j2;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.airline.locationservice.persistence.repository.location.CountryRepository;


@ExtendWith(MockitoExtension.class)
// @SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
// @WebAppConfiguration
// @AutoConfigureMockMvc
@Log4j2
public class CountryControllerTest
{
    @Autowired
    private MockMvc      mvc;

    @Mock
    CountryRepository    repository;    // mock repository

    @InjectMocks
    CountryController    service;       // service under test



   // ----- Before -----
   @BeforeEach
   public void setup()
   {
       mvc = MockMvcBuilders.standaloneSetup( service )
               .setCustomArgumentResolvers( new PageableHandlerMethodArgumentResolver() )
               // .setControllerAdvice(new SuperHeroExceptionHandler())
               // .addFilters(new SuperHeroFilter())
               .build();
   }



    @Test
    public void getSingleNotKnownCountryId()
        throws Exception
    {
        given( repository.findById(999999))
            .willReturn( Optional.ofNullable( null ));

        mvc.perform( get("/location/country/{id}", 999999 )
                        .accept( MediaType.APPLICATION_JSON ))
            .andExpect( status().isNotFound() )
            .andExpect(jsonPath("$").doesNotExist());

        then(repository)
            .should()
            .findById(999999);
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

        given( repository.findById(302797))
            .willReturn( Optional.of( country ));

        // --- When
        mvc.perform( get("/location/country/{id}", 302797 )
                        .accept( MediaType.APPLICATION_JSON ))
            // .andDo(MockMvcResultHandlers.print())
            .andExpect( status().isOk() )
            .andExpect( content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect( jsonPath("$.code", equalTo( "GY" )))
            .andExpect( jsonPath("$.name", equalTo( "Guyana" )));

        then(repository)
            .should()
            .findById(anyInt());
    }



    @Test
    public void getSingleKnownCountryCode()
        throws Exception
    {
        // 302797 - is the DB key for Country 'Guyana'
        Country country = new Country();
        country.setCode("GY");
        country.setName("Guyana");
        country.setId(302797);

        given( repository.findByCode("GY"))
            .willReturn( Optional.of(country));

        final MockHttpServletResponse response = mvc.perform(
            get("/location/country/code/{countryCode}", "GY" )
                .accept( MediaType.APPLICATION_JSON ))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk() )
            .andExpect( jsonPath("$.code", equalTo( "GY")))
            .andReturn().getResponse();

        // log.error( "Content type {}", () -> response.getContentType());
        // log.error( "Content {}", () -> content());
        // System.out.println( "Content type " + response.getContentType());
        // System.out.println( "Content " + content());

        then(repository)
            .should()
            .findByCode("GY");
    }


    @Test
    public void getSingleNotKnownCountryCode()
        throws Exception
    {
        given( repository.findByCode("ZZ"))
            .willReturn( Optional.ofNullable( null ));

        mvc.perform( get("/location/country/code/{countryCode}", "ZZ" )
                        .accept( MediaType.APPLICATION_JSON ))
            .andExpect(status().isNotFound() );

        then(repository)
            .should()
            .findByCode("ZZ");
    }




    // ------------
    @Test
    public void findCountriesFromKnownContinentCode()
        throws Exception
    {
        given( repository.findByContinent("NA"))
            .willReturn( new ArrayList<Country>() );

        final MockHttpServletResponse response = mvc.perform(
            get("/location/country/continent/{countryCode}", "NA" )
                .accept( MediaType.APPLICATION_JSON ))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk() )
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(0)))
            .andReturn().getResponse();

        // log.error( "Content type {}", () -> response.getContentType());
        // log.error( "Content {}", () -> content());
        // System.out.println( "Content type " + response.getContentType());
        // System.out.println( "Content " + content());

        then(repository)
            .should()
            .findByContinent("NA");
    }


    @Test
    public void findCountriesFromInvalidContinentCode()
        throws Exception
    {
        given( repository.findByContinent("ZZ"))
            .willReturn( new ArrayList<Country>() );

        mvc.perform( get("/location/country/continent/{countryCode}", "ZZ" )
                    .accept( MediaType.APPLICATION_JSON ))
            .andExpect(status().isOk() )
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        then(repository)
            .should()
            .findByContinent("ZZ");
    }

    // ------------



    @Test
    public void getAllCountriesIsEmpty()
        throws Exception
    {
        List<Country> expected = new ArrayList<>();
        Page<Country> foundPage = new PageImpl<>(expected);

        given( repository.findAll(any(Pageable.class)))
            .willReturn( foundPage );

        mvc.perform( get("/location/country" ))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk() )
            .andExpect(jsonPath("$.content").isArray())
            .andExpect(jsonPath("$.content", hasSize(0)));

        then(repository)
            .should()
            .findAll(any(Pageable.class));
    }

}



