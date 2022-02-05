package com.airline.locationservice.controller.airport;

// import static org.hamcrest.MatcherAssert.assertThat;
// import static org.hamcrest.Matchers.both;
// import static org.hamcrest.Matchers.containsString;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.mockito.Mockito.when;

import static org.hamcrest.Matchers.*;

import static org.assertj.core.api.Assertions.*;

// import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
// import static org.mockito.BDDMockito.*;
// import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.Arrays;
// import java.util.Collections;
import java.util.List;
// import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.data.web.JsonPath;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
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
import org.springframework.http.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

// import liquibase.Liquibase;
import lombok.extern.log4j.Log4j2;

import org.mockito.InjectMocks;
import org.mockito.Mock;
// import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.airline.locationservice.controller.airport.LocationControllerIata;
import com.airline.locationservice.persistence.model.airport.AirportCodeIata;
import com.airline.locationservice.persistence.repository.airport.AirportCodeIataRepository;
// import com.airline.core.location.AirportCode;
// import com.airline.core.location.IATAAirportCode;


import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.test.context.ContextConfiguration;




@ExtendWith( MockitoExtension.class )
// @SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
@WebAppConfiguration

// @WebMvcTest(controllers = LocationControllerIata.class)
// @EnableJpaRepositories(basePackages="com.airline.locationservice",
// entityManagerFactoryRef="emf")
// @EnableJpaRepositories(basePackages="com.airline.locationservice")
// @EnableSpringDataWebSupport


// @AutoConfigureMockMvc
// @ContextConfiguration(classes = {AirportCodeIataRepository.class, LocationControllerIata.class})
// @WebMvcTest
@Log4j2
public class LocationControllerIataTest {
    // @Autowired
    // private WebApplicationContext webApplicationContext;

    // @Autowired
    private MockMvc mvc;

    // mock repository
    @Mock
    AirportCodeIataRepository repository;

    @InjectMocks
    LocationControllerIata service;
    // private AirportCodeRes

    // @BeforeAll
    // public static void switchOffLiquibase() {
    //     System.setProperty(Liquibase.SHOULD_RUN_SYSTEM_PROPERTY, "false");
    // }

    // @BeforeEach
    // public void setup()
    // {
    //     mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
    //         // .setControllerAdvice(new SuperHeroExceptionHandler())
    //         // .addFilters(new SuperHeroFilter())
    //         .build();
    // }


    // ----- Before -----
    @BeforeEach
    public void setup()
    {
        // mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        //         // .setControllerAdvice(new SuperHeroExceptionHandler())
        //         // .addFilters(new SuperHeroFilter())
        //         .build();
        mvc = MockMvcBuilders.standaloneSetup( service )
                .setCustomArgumentResolvers( new PageableHandlerMethodArgumentResolver() )
                // .setControllerAdvice(new SuperHeroExceptionHandler())
                // .addFilters(new SuperHeroFilter())
                .build();
    }


    @Test
    public void getSingleKnownIataAirportCode()
        throws Exception
    {
        // final IATAAirportCode airportCode = new IATAAirportCode( "ATL" );
        // assertThat( airportCode ).isNotNull();

        given( repository.findById( "ATL" ) )
            .willReturn( Optional.of( new AirportCodeIata( "ATL" ) ) );

        final MockHttpServletResponse response =
            mvc.perform( get( "/location/airport/iata/{id}", "ATL" )
                            .accept( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() )
                // .andDo(MockMvcResultHandlers.print())
                .andExpect( jsonPath( "$.iataAirportCode", equalTo( "ATL" ) ) )  // hamcrest
                .andReturn().getResponse();

        log.error( "response: '" + response.getContentAsString() + "'");

        // assert the response contains the airport code
        // assertThat(response)
        //     .isNotNull()
        //     .has()

        // assertThat(response).ok();
        // LocationControllerIata controller = new LocationControllerIata( mockRepository );
    }


    @Test
    public void getSingleNotKnownIataAirportCode()
        throws Exception
    {
        given( repository.findById("MSP"))
            .willReturn( Optional.ofNullable( null ));

        final MockHttpServletResponse response =
            mvc.perform( get("/location/airport/iata/{id}", "MSP" )
                            .accept( MediaType.APPLICATION_JSON ))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound() )
                // .andExpect(status().isOk() )
                // .andExpect(jsonPath("$.content").isArray())
                // .andExpect(jsonPath("$.content", hasSize(1)))  // hamcrest
                // .andExpect(jsonPath("$.content", hasItem("NRT")))  // hamcrest
                .andExpect(jsonPath("$").doesNotExist())  // hamcrest
                .andReturn().getResponse();
    }



    @Test
    public void getAllAiportCodesIsEmpty()
        throws Exception
    {
        List<AirportCodeIata> expected = new ArrayList<>();
        Page<AirportCodeIata> foundPage = new PageImpl<>( expected) ;

        given( repository.findAll( any( Pageable.class ) ) )
            .willReturn( foundPage );

        final MockHttpServletResponse response = mvc.perform(
            get("/location/airport/iata" ))
            .andDo(MockMvcResultHandlers.print())
            .andExpect( status().isOk() )
            .andExpect( jsonPath( "$.content" ).isArray() )
            .andExpect( jsonPath( "$.content", hasSize( 0 ) ) )  // hamcrest
            .andReturn().getResponse();

        verify( repository, times( 1 ) ).findAll( any( Pageable.class ) );   // Mockito
    }



    @Test
    public void getAllAiportCodesHasOneEntry()
        throws Exception
    {
        final String[] codeStringList = { "NRT" };

        // Build a list of IATA airport code objects from the data layer
        List<AirportCodeIata> listOfIataAirportCodes =
            Arrays.asList( codeStringList )
                  .stream()
                  .map( a -> new AirportCodeIata( a ) )
                  .collect( Collectors.toList() );
        Page<AirportCodeIata> foundPage = new PageImpl<>( listOfIataAirportCodes );

        given( repository.findAll( any( Pageable.class ) ) )
            .willReturn( foundPage );

        final MockHttpServletResponse response =
            mvc.perform( get( "/location/airport/iata" ) )
                // .andDo(MockMvcResultHandlers.print())
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$.content" ).isArray() )
                .andExpect( jsonPath( "$.content", hasSize( 1 ) ) )  // hamcrest
                .andExpect( jsonPath( "$.content[0].iataAirportCode", equalTo( "NRT" ) ) )  // hamcrest
                .andReturn().getResponse();
    }


    @Test
    public void getAllAiportCodesHasFiveEntries()
        throws Exception
    {
        final String[] codeStringList = { "NRT", "ATL", "MSP", "ORD", "LAX" };

        // Build a list of IATA airport code objects from the data layer
        List<AirportCodeIata> listOfIataAirportCodes =
            Arrays.asList( codeStringList )
                  .stream()
                  .map( a -> new AirportCodeIata( a ))
                  .collect(Collectors.toList());
        Page<AirportCodeIata> foundPage = new PageImpl<>(listOfIataAirportCodes);

        log.error( "list: {}", () -> listOfIataAirportCodes  );
        log.error( "page before {}", () -> foundPage );
        log.error( "page before {}", () -> foundPage.toList() );

        given( repository.findAll(any(Pageable.class)))
            .willReturn( foundPage );

        final MockHttpServletResponse response = mvc.perform(
            get("/location/airport/iata" ))
            // .andDo(MockMvcResultHandlers.print())
            // .andExpect( content(). )
            // .andExpect(content().json("[{iataAirportCode: \"NRT\"}]"))
            // .andExpect( jsonPath("$.iataAirportCode", equalTo( "ATL")))
            .andExpect( status().isOk() )
            .andExpect( jsonPath("$.content" ).isArray() )
            .andExpect( jsonPath("$.content", hasSize( 5 ) ) )  // hamcrest
            // .andExpect(jsonPath("$.content", hasItem("{\"iataAirportCode\":\"NRT\"}")))  // hamcrest
            // .andExpect(jsonPath("$.content", hasItem("LAX")))  // hamcrest
            // .andExpect(jsonPath("$.content", hasItem("ORD")))  // hamcrest
            // .andExpect(jsonPath("$.content", hasItem("MSP")))  // hamcrest
            // .andExpect(jsonPath("$.content", hasItem("ATL")))  // hamcrest
            // .andExpect(jsonPath("$.content", hasItem("YYZ")))  // hamcrest
            .andExpect( jsonPath( "$.content[*].iataAirportCode", containsInAnyOrder( "NRT", "ATL", "MSP", "ORD", "LAX" ) ) )  // hamcrest
            .andReturn().getResponse();

        log.error( "response: '" + response.getContentAsString() + "'");
    }


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



