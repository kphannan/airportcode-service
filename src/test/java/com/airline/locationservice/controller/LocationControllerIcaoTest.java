package com.airline.locationservice.controller;

// import static org.hamcrest.MatcherAssert.assertThat;
// import static org.hamcrest.Matchers.both;
// import static org.hamcrest.Matchers.containsString;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.*;

// import static org.assertj.core.api.BDDAssertions.then;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.*;
// import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



import org.springframework.test.web.servlet.result.MockMvcResultHandlers;


// import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.airline.locationservice.repository.AirportCodeIcao;
import com.airline.locationservice.repository.AirportCodeIcaoRepository;
// import com.airline.core.location.AirportCode;
import com.airline.core.location.ICAOAirportCode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.data.web.JsonPath;
// import org.springframework.boot.test.mock.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.http.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.mockito.InjectMocks;
import org.mockito.Mock;


// @ExtendWith(MockitoExtension.class)
@SpringBootTest
public class LocationControllerIcaoTest
{
    // @Autowired
    private MockMvc   mvc;

    // mock repository
    @Mock
    AirportCodeIcaoRepository repository;

    @InjectMocks
    LocationControllerIcao    service;
    // private AirportCodeRes

    @BeforeEach
    public void setup()
    {
        mvc = MockMvcBuilders.standaloneSetup(service)
                // .setControllerAdvice(new SuperHeroExceptionHandler())
                // .addFilters(new SuperHeroFilter())
                .build();
    }


    @Test
    public void getSingleKnownIcaoAirportCode()
        throws Exception
    {
        final ICAOAirportCode airportCode = new ICAOAirportCode( "KATL" );
        assertThat( airportCode ).isNotNull();

        given( repository.findById("KATL")).willReturn( Optional.of( new AirportCodeIcao( airportCode.getAirportCode() )));

        final MockHttpServletResponse response = mvc.perform(
            get("/airport/icao/{id}", airportCode.getAirportCode() )
                .accept( MediaType.APPLICATION_JSON ))
            .andExpect(status().isOk() )
            // .andExpect( jsonPath("$.icaoAirportCode", equalTo( "KATL")))
            .andReturn().getResponse();
    }


    @Test
    public void getSingleNotKnownIcaoAirportCode()
        throws Exception
    {
        given( repository.findById("KMSP")).willReturn( Optional.ofNullable( null ));

        final MockHttpServletResponse response = mvc.perform(
            get("/airport/icao/{id}", "KMSP" )
                .accept( MediaType.APPLICATION_JSON ))
            .andExpect(status().isNotFound() )
            .andReturn().getResponse();
    }



    @Test
    public void getAllAiportCodesIsEmpty()
        throws Exception
    {
        given( repository.findAll()).willReturn( Collections.emptyList());

        final MockHttpServletResponse response = mvc.perform(
            get("/airport/icao" ))
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
        
        given( repository.findAll()).willReturn( listOfIcaoAirportCodes );

        final MockHttpServletResponse response = mvc.perform(
            get("/airport/icao" ))
            .andExpect(status().isOk() )
            .andExpect(content().json("[{icaoAirportCode: \"OERK\"}]"))
            // .andExpect( jsonPath("$.icaoAirportCode", equalTo( "KATL")))
            .andReturn().getResponse();
    }



    @Test
    public void deleteKnownAirportCode()
        throws Exception
    {
        final String targetAirport = "KMSP";
        // ICAOAirportCode airportCode = new ICAOAirportCode( "KATL" );
        // assertThat( airportCode ).isNotNull();

        given( repository.findById(targetAirport)).willReturn( Optional.of( new AirportCodeIcao( targetAirport )));

        final MockHttpServletResponse response = mvc.perform(
            delete("/airport/icao/{id}", targetAirport )
                .accept( MediaType.APPLICATION_JSON ))
            .andExpect(status().isNoContent() )
            .andReturn().getResponse();
    }

    @Test
    public void deleteAnUnknownAirportCode()
        throws Exception
    {
        final String targetAirport = "KMSP";

        given( repository.findById(targetAirport)).willReturn( Optional.ofNullable( null ));

        final MockHttpServletResponse response = mvc.perform(
            delete("/airport/icao/{id}", targetAirport )
                .accept( MediaType.APPLICATION_JSON ))
            .andExpect(status().isNotFound() )
            .andReturn().getResponse();
    }


    @Test
    public void addNewIcaoAirportCodeSYGC()
        throws Exception
    {
        final ICAOAirportCode airportCode = new ICAOAirportCode( "KATL" );
        assertThat( airportCode ).isNotNull();

        final String json = "{ \"icaoAirportCode\": \"SYGC\"}";

        AirportCodeIcao icao = new AirportCodeIcao( "SYGC" );
        ICAOAirportCode icaoAirportCode = new ICAOAirportCode("SYGC");

        given( repository.save( icao )).willReturn( icao );

        final MockHttpServletResponse response = mvc.perform(
            post("/airport/icao" )
                // .content(icaoAirportCode)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept( MediaType.APPLICATION_JSON ))
            .andExpect(status().isCreated() )
            // .andExpect( jsonPath("$.icaoAirportCode", equalTo( "KATL")))
            .andReturn().getResponse();
    }



    @Test
    public void updateExistingAirportCode()
        throws Exception
    {
        // final ICAOAirportCode airportCode = new ICAOAirportCode( "KATL" );
        // assertThat( airportCode ).isNotNull();

        final String json = "{ \"icaoAirportCode\": \"SYGC\"}";

        AirportCodeIcao icao = new AirportCodeIcao( "SYGC" );
        ICAOAirportCode icaoAirportCode = new ICAOAirportCode("SYGC");

        given( repository.save( icao ))
            .willReturn( icao );
        given( repository.findById(icao.getIcaoCode()))
            .willReturn( Optional.of( icao ));

        final MockHttpServletResponse response = mvc.perform(
            put("/airport/icao/{id}", icaoAirportCode.getAirportCode() )
                // .content(icaoAirportCode)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept( MediaType.APPLICATION_JSON ))
            .andExpect(status().isOk() )
            // .andExpect( jsonPath("$.icaoAirportCode", equalTo( "KATL")))
            .andReturn().getResponse();
    }

    @Test
    public void updateNonExistingAirportCodeIsReallyAnAdd()
        throws Exception
    {
        final String json = "{ \"icaoAirportCode\": \"SYGC\"}";

        AirportCodeIcao icao = new AirportCodeIcao( "SYGC" );
        ICAOAirportCode icaoAirportCode = new ICAOAirportCode("SYGC");

        given( repository.findById(icao.getIcaoCode()))
            .willReturn( Optional.ofNullable(null));
        given( repository.save( icao ))
            .willReturn( icao );

        final MockHttpServletResponse response = mvc.perform(
            put("/airport/icao/{id}", icaoAirportCode.getAirportCode() )
                // .content(icaoAirportCode)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept( MediaType.APPLICATION_JSON ))
            .andExpect(status().isCreated() )
            // .andExpect( jsonPath("$.icaoAirportCode", equalTo( "KATL")))
            .andReturn().getResponse();
    }


    @Test
    public void updateAirportCodeWrongKeyIsInvalid()
        throws Exception
    {
        final String json = "{ \"icaoAirportCode\": \"LAX\"}";

        AirportCodeIcao icao = new AirportCodeIcao( "SYGC" );
        ICAOAirportCode icaoAirportCode = new ICAOAirportCode("SYGC");

        given( repository.findById(icao.getIcaoCode()))
            .willReturn( Optional.ofNullable(null));
        given( repository.save( icao ))
            .willReturn( icao );

        final MockHttpServletResponse response = mvc.perform(
            put("/airport/icao/{id}", icaoAirportCode.getAirportCode() )
                // .content(icaoAirportCode)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept( MediaType.APPLICATION_JSON ))
            .andExpect(status().isBadRequest() )
            // .andExpect( jsonPath("$.icaoAirportCode", equalTo( "KATL")))
            .andReturn().getResponse();
    }



}

 
 
 