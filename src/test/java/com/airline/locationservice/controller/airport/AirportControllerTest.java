package com.airline.locationservice.controller.airport;

import static org.assertj.core.api.Assertions.*;
import static com.airline.PageableAssert.*;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.math.BigDecimal;
import java.util.ArrayList;
// import java.util.Arrays;
import java.util.Collections;
// import java.util.List;
import java.util.Optional;
// import java.util.stream.Collectors;

import com.airline.locationservice.controller.airport.AirportController;
import com.airline.locationservice.persistence.model.airport.Airport;
// import static com.airline.PageableAssert.hasPageNumber;
// import static com.airline.PageableAssert.hasPageSize;
// import static com.airline.PageableAssert.hasSort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
// import org.junit.platform.runner.JUnitPlatform;
// import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import lombok.extern.log4j.Log4j2;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.airline.locationservice.persistence.repository.airport.AirportRepository;

// @ExtendWith(MockitoExtension.class)
// @SpringBootTest
// @WebAppConfiguration
@ExtendWith( MockitoExtension.class )
// @RunWith( JUnitPlatform.class )
@Log4j2
public class AirportControllerTest
{
    // @Autowired
    private MockMvc   mvc;

    // mock repository
    @Mock
    AirportRepository repository;

    @InjectMocks
    AirportController    service;

    @Mock
    Page<Airport>        page;      // Mock interface

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


    // ----- Tests -----

    @Test
    public void getSingleNotKnownAirportById()
        throws Exception
    {
        given( repository.findById( Long.valueOf( 999999 ) ) )
            .willReturn( Optional.ofNullable( null ) );

        final MockHttpServletResponse response =
            mvc.perform( get("/location/airport/{id}", 999999 )
                            .accept( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isNotFound() )
                .andReturn().getResponse();

        verify( repository, times( 1 ) )
            .findById( Long.valueOf( 999999 ) );
    }

    @Test
    public void getSingleKnownAirportById()
        throws Exception
    {
        // --- Given ---
        Airport airport =
        Airport.builder()
            .id( Long.valueOf( 1234 ) )  // Long
            .ident( "abcd" )             // String
            .type( "type" )              // String
            .name( "name" )              // String
            .lattitude( BigDecimal.valueOf( 12.234 ) )         // BigDecimal
            .longitude( BigDecimal.valueOf( 21.543 ) )         // BigDecimal
            .elevation( 1000 )           // int
            .continent( "NA" )           // String
            .isoCountry( "US" )          // String
            .isoRegion( "US-GA" )        // String
            .municipality( "ATL" )       // String
            .scheduledService( "yes" )   // String - truthy
            .gpsCode( "ATL" )            // String
            // .iataCode()                 // String
            // .localCode()                // String
            // .homeLink()                 // String
            // .wikipediaLink()            // String
            // .keywords()                 // String
            .build();

        given( repository.findById( Long.valueOf( 1234 ) ) )
            .willReturn( Optional.of( airport ) );

        // --- When
        final MockHttpServletResponse response =
            mvc.perform( get( "/location/airport/{id}", 1234 )
                            .accept( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON_VALUE ) )
                .andExpect( content().json( "{\"id\": 1234, \"name\": \"name\", \"ident\": \"abcd\"}" ) )
                .andDo( MockMvcResultHandlers.print() )
                .andReturn().getResponse();

        verify( repository, times( 1 ) )
            .findById( Long.valueOf( 1234 ) );

        // assertThat(response.getContentAsString())
        //     .as( "Response contains an airport with IATA code 'GY'")
        //     .doesNotContainAnyWhitespaces()
        //     .contains("{\"id\": 1234, \"name\": \"name\", \"ident\": \"abcd\"}");
    }



    @Test
    void evaluatesPageableParameter() throws Exception
    {
        mvc.perform( get( "/location/airport/" )
                        .param( "page", "5" )
                        .param( "size", "10" )
                        .param( "sort", "id,desc" )    // <-- no space after comma!
                        .param( "sort", "name,asc" ) ) // <-- no space after comma!
            .andExpect( status().isOk() );

        ArgumentCaptor<Pageable> pageableCaptor =
            ArgumentCaptor.forClass( Pageable.class );
        verify( repository ).findAll( pageableCaptor.capture() );
        PageRequest pageable = (PageRequest) pageableCaptor.getValue();

        assertThat( pageable ).hasPageNumber( 5 );
        assertThat( pageable ).hasPageSize( 10 );
        assertThat( pageable ).hasSort( "name", Sort.Direction.ASC );
        assertThat( pageable ).hasSort( "id", Sort.Direction.DESC );
    }


	@Test
	void setsUpperPageLimit() throws Exception {

		mvc.perform( get( "/location/airport/" )
				        .param("size", "10000" ) )
            .andExpect( status().isOk() );
		ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass( Pageable.class );

        verify( repository ).findAll( pageableCaptor.capture() );

        PageRequest pageable = (PageRequest)pageableCaptor.getValue();
		assertThat( pageable ).hasPageSize( 2000 );
	}

	@Test
	void evaluatesPageableDefault() throws Exception {

		mvc.perform( get("/location/airport/") )
            .andExpect( status().isOk() );

		ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass( Pageable.class );
		verify(repository).findAll( pageableCaptor.capture() );
		PageRequest pageable = (PageRequest)pageableCaptor.getValue();

		assertThat( pageable ).hasPageNumber( 0 );
		assertThat( pageable ).hasPageSize( 20 );
		// assertThat(pageable).hasSort("name", Sort.Direction.DESC);
		// assertThat(pageable).hasSort("id", Sort.Direction.ASC);
	}


    // @Test
    // void evaluatesSortParameter() throws Exception
    // {
    //     mvc.perform(get("/")
    //         .param("sort", "id,desc")   // <-- no space after comma!!!
    //         .param("sort", "name,asc")) // <-- no space after comma!!!
    //         .andExpect(status().isOk());

    //     ArgumentCaptor<Sort> sortCaptor = ArgumentCaptor.forClass(Sort.class);
    //     verify(repository).findAll(sortCaptor.capture());
    //     Sort sort = sortCaptor.getValue();

    //     assertThat(sort).hasSort("name", Sort.Direction.ASC);
    //     assertThat(sort).hasSort("id", Sort.Direction.DESC);
    // }



    // ------------
    // @Test
    // public void findCountriesFromKnownContinentCode()
    //     throws Exception
    // {
    //     // 302797 - is the DB key for Country 'Guyana'

    //     given( repository.findByContinent("NA")).willReturn( new ArrayList<Country>() );

    //     final MockHttpServletResponse response = mvc.perform(
    //         get("/location/country/continent/{countryCode}", "NA" )
    //             .accept( MediaType.APPLICATION_JSON ))
    //         .andExpect(status().isOk() )
    //         // .andExpect( jsonPath("$.code", equalTo( "GY")))
    //         .andReturn().getResponse();

    //     log.error( "Content type {}", () -> response.getContentType());
    //     log.error( "Content {}", () -> content());
    //     System.out.println( "Content type " + response.getContentType());
    //     System.out.println( "Content " + content());
    // }


    // @Test
    // public void findCountriesFromInvalidContinentCode()
    //     throws Exception
    // {
    //     given( repository.findByContinent("ZZ")).willReturn( new ArrayList<Country>() );

    //     final MockHttpServletResponse response = mvc.perform(
    //         get("/location/country/continent/{countryCode}", "ZZ" )
    //             .accept( MediaType.APPLICATION_JSON ))
    //         .andExpect(status().isOk() )
    //         // .andExpect(jsonPath("$").doesNotExist())
    //         .andExpect(jsonPath("$").isArray())
    //         .andExpect(jsonPath("$").isEmpty())
    //         .andReturn().getResponse();

    //     // assertTrue(response.)
    // }

    // ------------



    // @Test
    // public void getAllCountriesIsEmpty()
    //     throws Exception
    // {

	// 	mvc.perform( get( "/location/airport/" ) )
    //         .andExpect( status().isOk() );

    //     // given( repository.findAll( any( Pageable.class ) ) )
    //     //     .willReturn( page );

    //     // final MockHttpServletResponse response =
    //     //     mvc.perform( get( "/location/airport" ) )
    //     //         .andDo( MockMvcResultHandlers.print() )
    //     //         .andExpect( status().isOk() )
    //     //         .andExpect( jsonPath( "$" ).doesNotExist() )
    //     //         .andReturn().getResponse();
    // }

}



