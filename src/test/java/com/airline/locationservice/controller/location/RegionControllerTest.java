package com.airline.locationservice.controller.location;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.airline.locationservice.persistence.model.location.Region;
import com.airline.locationservice.persistence.repository.location.RegionsRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;



@ExtendWith(MockitoExtension.class)
// @SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
// @WebAppConfiguration
// @AutoConfigureMockMvc
public class RegionControllerTest
{
    @Autowired
    private MockMvc   mvc;              // web / REST support

    @Mock
    RegionsRepository repository;       // mock repository

    @InjectMocks
    RegionController    service;        // Service under test



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



    // -----  -----
    @Test
    public void getSingleKnownRegionCode()
        throws Exception
    {
        Region region = new Region();
        region.setContinent( "NA" );
        region.setCountry( "FooLand" );
        region.setName( "Foo" );
        region.setId( 306086 );

        given( repository.findById( 306086 ) )
            .willReturn( Optional.of( region ) );

        mvc.perform( get("/location/region/{id}", 306086 )
                        .accept( MediaType.APPLICATION_JSON ))
            .andExpect(status().isOk() )
            // .andDo(MockMvcResultHandlers.print())
            .andExpect( jsonPath("$.id", equalTo( 306086 )))
            .andExpect( jsonPath("$.country", equalTo( "FooLand" )))
            .andExpect( jsonPath("$.name", equalTo( "Foo" )));

        then(repository)
            .should()
            .findById(anyInt());
    }


    // -----  -----
    @Test
    public void getSingleNotKnownRegionCode()
        throws Exception
    {
        given( repository.findById(1234))
            .willReturn( Optional.ofNullable( null ));

        mvc.perform( get("/location/region/{id}", 1234 )
                        .accept( MediaType.APPLICATION_JSON ))
            .andExpect(status().isNotFound() )
            .andReturn().getResponse();

        then(repository)
            .should()
            .findById(anyInt());
    }



    // -----  -----
    @Test
    public void getAllRegionsIsEmpty()
        throws Exception
    {
        List<Region> expected = new ArrayList<>();
        Page<Region> foundPage = new PageImpl<>(expected);

        given( repository.findAll(any(Pageable.class)))
            .willReturn( foundPage );

        mvc.perform( get("/location/region" ))
            // .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk() )
            .andExpect(jsonPath("$.content").isArray())
            .andExpect(jsonPath("$.content", hasSize(0)));

        then(repository)
            .should()
            .findAll(any(Pageable.class));
    }


    // ----- By Contient -----
    @Test
    public void getRegionByContinent()
        throws Exception
    {
        Region region = new Region();
        region.setContinent("NA");
        region.setCountry("FooLand");
        region.setName("Foo");
        region.setId( 306086 );
        List<Region> regionList = new ArrayList<>();
        regionList.add( region );
        Page<Region> foundPage = new PageImpl<>(regionList);

        given( repository.findByContinent( anyString(), any(Pageable.class)))
            .willReturn( foundPage );

        mvc.perform( get( "/location/region/byContinent/{continent}", "NA" ) )
            // .andDo(MockMvcResultHandlers.print())
            .andExpect( status().isOk() )
            .andExpect( jsonPath( "$.content" ).isArray() )
            .andExpect( jsonPath( "$.content", hasSize( 1 ) ) )  // hamcrest
            .andExpect( jsonPath( "$.content[0].id", equalTo( 306086 ) ) )
            .andExpect( jsonPath( "$.content[0].country", equalTo( "FooLand" ) ) )
            .andExpect( jsonPath( "$.content[0].name", equalTo( "Foo" ) ) );

        then(repository)
            .should()
            .findByContinent( anyString(), any( Pageable.class ) );
    }




    // ----- By Country -----
    @Test
    public void getRegionByCountry()
        throws Exception
    {
        Region region = new Region();
        region.setContinent( "NA" );
        region.setCountry( "FooLand" );
        region.setName( "Foo" );
        region.setId( 306086 );
        List<Region> regionList = new ArrayList<>();
        regionList.add( region );
        Page<Region> foundPage = new PageImpl<>( regionList );

        given( repository.findByCountry( anyString(), any( Pageable.class ) ) )
            .willReturn( foundPage );

        mvc.perform( get( "/location/region/byCountry/{country}", "CAN" ) )
            // .andDo(MockMvcResultHandlers.print())
            .andExpect( status().isOk() )
            .andExpect( jsonPath( "$.content" ).isArray() )
            .andExpect( jsonPath( "$.content", hasSize( 1 ) ) )  // hamcrest
            .andExpect( jsonPath( "$.content[0].id", equalTo( 306086 ) ) )
            .andExpect( jsonPath( "$.content[0].country", equalTo( "FooLand" ) ) )
            .andExpect( jsonPath( "$.content[0].name", equalTo( "Foo" ) ) );

        then( repository )
            .should()
            .findByCountry( anyString(), any( Pageable.class ) );
    }

}



