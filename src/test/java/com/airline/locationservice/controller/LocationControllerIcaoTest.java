package com.airline.locationservice.controller;


import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import com.airline.locationservice.persistence.model.AirportCodeIcao;
import com.airline.locationservice.persistence.repository.AirportCodeIcaoRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
// import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
// import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
// import org.springframework.data.web.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;



@ExtendWith( MockitoExtension.class )
@SpringBootTest( properties = "spring.main.allow-bean-definition-overriding=true" )
@WebAppConfiguration
@AutoConfigureMockMvc
// @Log4j2
class LocationControllerIcaoTest
{
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc   mvc;

    // mock repository
    @MockBean
    AirportCodeIcaoRepository repository;

    @InjectMocks
    LocationControllerIcao    service;

    @BeforeEach
    void setup()
    {
        mvc = MockMvcBuilders.webAppContextSetup( webApplicationContext )
            // .setControllerAdvice(new SuperHeroExceptionHandler())
            // .addFilters(new SuperHeroFilter())
            .build();
    }


    @Test
    void getSingleKnownIcaoAirportCode()
        throws Exception
    {
        given( repository.findById( "KATL" ) )
            .willReturn( Optional.of( new AirportCodeIcao( "KATL" ) ) );

        mvc.perform( get( "/location/airport/icao/{id}", "KATL" )
                            .accept( MediaType.APPLICATION_JSON ) )
            .andExpect( status().isOk() )
            .andDo( MockMvcResultHandlers.print() )
            .andExpect( jsonPath( "$.icaoAirportCode", equalTo( "KATL" ) ) )  // hamcrest
            .andReturn().getResponse();
    }


    @Test
    void getSingleNotKnownIcaoAirportCode()
        throws Exception
    {
        given( repository.findById( "KMSP" ) )
            .willReturn( Optional.ofNullable( null ) );

        final MockHttpServletResponse response = mvc.perform(
            get( "/location/airport/icao/{id}", "KMSP" )
                .accept( MediaType.APPLICATION_JSON ) )
            .andExpect( status().isNotFound() )
            .andExpect( jsonPath( "$" ).doesNotExist() )  // hamcrest
            .andReturn().getResponse();
    }



    @Test
    void getAllAiportCodesIsEmpty()
        throws Exception
    {
        List<AirportCodeIcao> expected = new ArrayList<>();
        Page<AirportCodeIcao> foundPage = new PageImpl<>( expected );

        given( repository.findAll(any( Pageable.class ) ) )
            .willReturn( foundPage );

        final MockHttpServletResponse response = mvc.perform(
            get( "/location/airport/icao" ) )
            .andDo( MockMvcResultHandlers.print() )
            .andExpect( status().isOk() )
            .andExpect( jsonPath( "$.content" ).isArray())
            .andExpect( jsonPath( "$.content", hasSize( 0 ) ) )  // hamcrest
            .andReturn().getResponse();
    }



    @Test
    void getAllAiportCodesHasOneEntry()
        throws Exception
    {
        final String[] codeStringList = { "OERK" };

        // Build a list of ICAO airport code objects from the data layer
        List<AirportCodeIcao> listOfIcaoAirportCodes =
            Arrays.asList( codeStringList )
                  .stream()
                  .map( a -> new AirportCodeIcao( a ) )
                  .collect( Collectors.toList() );
        Page<AirportCodeIcao> foundPage = new PageImpl<>( listOfIcaoAirportCodes );

        given( repository.findAll( any( Pageable.class ) ) )
            .willReturn( foundPage );

        final MockHttpServletResponse response = mvc.perform(
            get( "/location/airport/icao" ) )
            .andExpect( status().isOk() )
            .andExpect( jsonPath( "$.content" ).isArray() )
            .andExpect( jsonPath( "$.content", hasSize( 1 ) ) )  // hamcrest
            .andExpect( jsonPath( "$.content[0].icaoAirportCode", equalTo( "OERK" ) ) )  // hamcrest
            .andReturn().getResponse();

            then( repository )
                .should()
                .findAll( any( Pageable.class ) );
    }

}



