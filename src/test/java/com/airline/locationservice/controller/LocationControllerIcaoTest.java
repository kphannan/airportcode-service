package com.airline.locationservice.controller;

// import static org.hamcrest.MatcherAssert.assertThat;
// import static org.hamcrest.Matchers.both;
// import static org.hamcrest.Matchers.containsString;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.mockito.Mockito.when;

// import static org.assertj.core.api.Assertions.*;

// import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
// import static org.mockito.BDDMockito.*;
// import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.Arrays;
// import java.util.Collections;
import java.util.List;
// import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.data.web.JsonPath;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
// import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
// import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
// import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.web.WebAppConfiguration;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.boot.test.mock.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.airline.locationservice.persistence.model.AirportCodeIcao;
import com.airline.locationservice.persistence.repository.AirportCodeIcaoRepository;
// import com.airline.core.location.AirportCode;
// import com.airline.core.location.ICAOAirportCode;




@ExtendWith(MockitoExtension.class)
@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
// @WebMvcTest(controllers = LocationControllerIcao.class)
// @EnableSpringDataWebSupport
// @ContextConfiguration(classes = {
//     LocationControllerIcao.class
// })
@WebAppConfiguration
public class LocationControllerIcaoTest
{
    @Autowired
    private WebApplicationContext webApplicationContext;
 
    private MockMvc   mvc;

    // mock repository
    @Mock
    AirportCodeIcaoRepository repository;

    @InjectMocks
    LocationControllerIcao    service;

    @BeforeEach
    public void setup()
    {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            // .setControllerAdvice(new SuperHeroExceptionHandler())
            // .addFilters(new SuperHeroFilter())
            .build();
    }


    @Test
    public void getSingleKnownIcaoAirportCode()
        throws Exception
    {
        // final ICAOAirportCode airportCode = new ICAOAirportCode( "KATL" );
        // assertThat( airportCode ).isNotNull();

        given( repository.findById("KATL"))
            .willReturn( Optional.of( new AirportCodeIcao( "KATL" )));

        final MockHttpServletResponse response =
            mvc.perform( get("/location/airport/icao/{id}", "KATL" )
                             .accept( MediaType.APPLICATION_JSON ))
                .andExpect(status().isOk() )
                // .andExpect( jsonPath("$.icaoAirportCode", equalTo( "KATL")))
                .andReturn().getResponse();
    }


    @Test
    public void getSingleNotKnownIcaoAirportCode()
        throws Exception
    {
        given( repository.findById("KMSP"))
            .willReturn( Optional.ofNullable( null ));

        final MockHttpServletResponse response = mvc.perform(
            get("/location/airport/icao/{id}", "KMSP" )
                .accept( MediaType.APPLICATION_JSON ))
            .andExpect(status().isNotFound() )
            .andReturn().getResponse();
    }



    @Test
    public void getAllAiportCodesIsEmpty()
        throws Exception
    {
        List<AirportCodeIcao> expected = new ArrayList<>();
        Page<AirportCodeIcao> foundPage = new PageImpl<>(expected);

        given( repository.findAll(any(Pageable.class)))
            .willReturn( foundPage );

        final MockHttpServletResponse response = mvc.perform(
            get("/location/airport/icao" ))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk() )
            .andExpect(content().json("[]"))
            .andReturn().getResponse();
    }



    @Test
    public void getAllAiportCodesHasOneEntry()
        throws Exception
    {
        final String[] codeStringList = { "OERK" };

        // Build a list of ICAO airport code objects from the data layer
        List<AirportCodeIcao> listOfIcaoAirportCodes =
            Arrays.asList( codeStringList )
                  .stream()
                  .map( a -> new AirportCodeIcao( a ))
                  .collect(Collectors.toList());
        Page<AirportCodeIcao> foundPage = new PageImpl<>(listOfIcaoAirportCodes);
        
        given( repository.findAll( any( Pageable.class )))
            .willReturn( foundPage );

        final MockHttpServletResponse response = mvc.perform(
            get("/location/airport/icao" ))
            .andExpect(status().isOk() )
            .andExpect(content().json("[{icaoAirportCode: \"OERK\"}]"))
            // .andExpect( jsonPath("$.icaoAirportCode", equalTo( "KATL")))
            .andReturn().getResponse();

            then(repository)
                .should()
                .findAll(any( Pageable.class ));
    }

}

 
 
 