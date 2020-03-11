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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

// import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.airline.locationservice.model.Region;
// import com.airline.locationservice.repository.AirportCodeIata;
// import com.airline.locationservice.repository.AirportCodeIataRepository;
// import com.airline.core.location.AirportCode;
import com.airline.core.location.IATAAirportCode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.data.web.JsonPath;
// import org.springframework.boot.test.mock.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.airline.locationservice.persistence.repository.RegionsRepository;

// @ExtendWith(MockitoExtension.class)
@SpringBootTest
public class RegionControllerTest
{
    // @Autowired
    private MockMvc   mvc;

    // mock repository
    @Mock
    RegionsRepository repository;

    @InjectMocks
    RegionController    service;
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


    @Test
    public void getSingleKnownRegionCode()
        throws Exception
    {
        // 306086 - is the DB key for Region 'US-GA'
        // final IATAAirportCode airportCode = new IATAAirportCode( "ATL" );
        // assertThat( airportCode ).isNotNull();

        // when( repository.findById("ATL")).thenReturn( Optional.of( new AirportCodeIata( airportCode.getAirportCode() )));
        given( repository.findById(306086)).willReturn( Optional.of( new Region( )));

        final MockHttpServletResponse response = mvc.perform(
            get("/location/region/{id}", 306086 )
                .accept( MediaType.APPLICATION_JSON ))
            .andExpect(status().isOk() )
            // .andExpect( jsonPath("$.iataAirportCode", equalTo( "ATL")))
            .andReturn().getResponse();

        // assert the response contains the airport code
        // assertThat(response)
        //     .isNotNull()
        //     .has()

        // assertThat(response).ok();
        // LocationControllerIata controller = new LocationControllerIata( mockRepository );
    }


    @Test
    public void getSingleNotKnownRegionCode()
        throws Exception
    {
        given( repository.findById(1234)).willReturn( Optional.ofNullable( null ));

        final MockHttpServletResponse response = mvc.perform(
            get("/location/region/{id}", 1234 )
                .accept( MediaType.APPLICATION_JSON ))
            .andExpect(status().isNotFound() )
            .andReturn().getResponse();
    }



    @Test
    public void getAllRegionsIsEmpty()
        throws Exception
    {
        given( repository.findAll()).willReturn( Collections.emptyList());

        final MockHttpServletResponse response = mvc.perform(
            get("/location/region" ))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk() )
            // .andExpect(content().) //  .json("[]"))
            .andExpect(jsonPath("$").doesNotExist())
            // .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();
    }



    // @Test
    // public void getAllAiportCodesHasOneEntry()
    //     throws Exception
    // {
    //     final String[] codeStringList = { "NRT" };

    //     // Build a list of IATA airport code objects from the data layer
    //     List<AirportCodeIata> listOfIataAirportCodes =
    //         Arrays.asList( codeStringList )
    //               .stream()
    //               .map( a -> new AirportCodeIata( a ))
    //               .collect(Collectors.toList());
        
    //     given( repository.findAll()).willReturn( listOfIataAirportCodes );

    //     final MockHttpServletResponse response = mvc.perform(
    //         get("/location/region" ))
    //         .andExpect(status().isOk() )
    //         .andExpect(content().json("[{iataAirportCode: \"NRT\"}]"))
    //         // .andExpect( jsonPath("$.iataAirportCode", equalTo( "ATL")))
    //         .andReturn().getResponse();
    // }



    // @Test
    // public void deleteKnownAirportCode()
    //     throws Exception
    // {
    //     final String targetAirport = "MSP";
    //     // IATAAirportCode airportCode = new IATAAirportCode( "ATL" );
    //     // assertThat( airportCode ).isNotNull();

    //     given( repository.findById(targetAirport)).willReturn( Optional.of( new AirportCodeIata( targetAirport )));

    //     final MockHttpServletResponse response = mvc.perform(
    //         delete("/airport/iata/{id}", targetAirport )
    //             .accept( MediaType.APPLICATION_JSON ))
    //         .andExpect(status().isNoContent() )
    //         .andReturn().getResponse();
    // }

    // @Test
    // public void deleteAnUnknownAirportCode()
    //     throws Exception
    // {
    //     final String targetAirport = "MSP";

    //     given( repository.findById(targetAirport)).willReturn( Optional.ofNullable( null ));

    //     final MockHttpServletResponse response = mvc.perform(
    //         delete("/airport/iata/{id}", targetAirport )
    //             .accept( MediaType.APPLICATION_JSON ))
    //         .andExpect(status().isNotFound() )
    //         .andReturn().getResponse();
    // }


    // @Test
    // public void addNewIataAirportCodeYYZ()
    //     throws Exception
    // {
    //     final IATAAirportCode airportCode = new IATAAirportCode( "ATL" );
    //     assertThat( airportCode ).isNotNull();

    //     final String json = "{ \"iataAirportCode\": \"YYZ\"}";

    //     AirportCodeIata iata = new AirportCodeIata( "YYZ" );
    //     IATAAirportCode iataAirportCode = new IATAAirportCode("YYZ");

    //     given( repository.save( iata )).willReturn( iata );

    //     final MockHttpServletResponse response = mvc.perform(
    //         post("/airport/iata" )
    //             // .content(iataAirportCode)
    //             .content(json)
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .accept( MediaType.APPLICATION_JSON ))
    //         .andExpect(status().isCreated() )
    //         // .andExpect( jsonPath("$.iataAirportCode", equalTo( "ATL")))
    //         .andReturn().getResponse();
    // }



    // @Test
    // public void updateExistingAirportCode()
    //     throws Exception
    // {
    //     // final IATAAirportCode airportCode = new IATAAirportCode( "ATL" );
    //     // assertThat( airportCode ).isNotNull();

    //     final String json = "{ \"iataAirportCode\": \"YYZ\"}";

    //     AirportCodeIata iata = new AirportCodeIata( "YYZ" );
    //     IATAAirportCode iataAirportCode = new IATAAirportCode("YYZ");

    //     given( repository.save( iata ))
    //         .willReturn( iata );
    //     given( repository.findById(iata.getIataCode()))
    //         .willReturn( Optional.of( iata ));

    //     final MockHttpServletResponse response = mvc.perform(
    //         put("/airport/iata/{id}", iataAirportCode.getAirportCode() )
    //             // .content(iataAirportCode)
    //             .content(json)
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .accept( MediaType.APPLICATION_JSON ))
    //         .andExpect(status().isOk() )
    //         // .andExpect( jsonPath("$.iataAirportCode", equalTo( "ATL")))
    //         .andReturn().getResponse();
    // }

    // @Test
    // public void updateNonExistingAirportCodeIsReallyAnAdd()
    //     throws Exception
    // {
    //     final String json = "{ \"iataAirportCode\": \"YYZ\"}";

    //     AirportCodeIata iata = new AirportCodeIata( "YYZ" );
    //     IATAAirportCode iataAirportCode = new IATAAirportCode("YYZ");

    //     given( repository.findById(iata.getIataCode()))
    //         .willReturn( Optional.ofNullable(null));
    //     given( repository.save( iata ))
    //         .willReturn( iata );

    //     final MockHttpServletResponse response = mvc.perform(
    //         put("/airport/iata/{id}", iataAirportCode.getAirportCode() )
    //             // .content(iataAirportCode)
    //             .content(json)
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .accept( MediaType.APPLICATION_JSON ))
    //         .andExpect(status().isCreated() )
    //         // .andExpect( jsonPath("$.iataAirportCode", equalTo( "ATL")))
    //         .andReturn().getResponse();
    // }


    // @Test
    // public void updateAirportCodeWrongKeyIsInvalid()
    //     throws Exception
    // {
    //     final String json = "{ \"iataAirportCode\": \"LAX\"}";

    //     AirportCodeIata iata = new AirportCodeIata( "YYZ" );
    //     IATAAirportCode iataAirportCode = new IATAAirportCode("YYZ");

    //     given( repository.findById(iata.getIataCode()))
    //         .willReturn( Optional.ofNullable(null));
    //     given( repository.save( iata ))
    //         .willReturn( iata );

    //     final MockHttpServletResponse response = mvc.perform(
    //         put("/airport/iata/{id}", iataAirportCode.getAirportCode() )
    //             // .content(iataAirportCode)
    //             .content(json)
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .accept( MediaType.APPLICATION_JSON ))
    //         .andExpect(status().isBadRequest() )
    //         // .andExpect( jsonPath("$.iataAirportCode", equalTo( "ATL")))
    //         .andReturn().getResponse();
    // }



}

 
 
 