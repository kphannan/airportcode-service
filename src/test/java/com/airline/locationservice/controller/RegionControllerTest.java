package com.airline.locationservice.controller;

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
// import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.*;
// import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.airline.locationservice.persistence.model.Region;

import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import lombok.extern.log4j.Log4j2;

import org.mockito.InjectMocks;
// import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.airline.locationservice.persistence.repository.RegionsRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
@WebAppConfiguration
@AutoConfigureMockMvc
@Log4j2
public class RegionControllerTest
{
    @Autowired
    private MockMvc   mvc;

    // mock repository
    @MockBean
    RegionsRepository repository;

    @InjectMocks
    RegionController    service;


    @Test
    public void getSingleKnownRegionCode()
        throws Exception
    {
        Region region = new Region();
        region.setContinent("NA");
        region.setCountry("FooLand");
        region.setName("Foo");
        region.setId( 306086 );

        given( repository.findById(306086)).willReturn( Optional.of( region ));

        final MockHttpServletResponse response = mvc.perform(
            get("/location/region/{id}", 306086 )
                .accept( MediaType.APPLICATION_JSON ))
            .andExpect(status().isOk() )
            .andDo(MockMvcResultHandlers.print())
            .andExpect( jsonPath("$.id", equalTo( 306086 )))
            .andExpect( jsonPath("$.country", equalTo( "FooLand" )))
            .andExpect( jsonPath("$.name", equalTo( "Foo" )))
            .andReturn().getResponse();

            then(repository)
                .should()
                .findById(anyInt());
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
            // .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk() )
            // .andExpect(content().) //  .json("[]"))
            .andExpect(jsonPath("$").doesNotExist())
            // .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();
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

        final MockHttpServletResponse response = mvc.perform(
            get("/location/region/byContinent/{continent}", "NA" ))
            .andDo(MockMvcResultHandlers.print())
            // .andExpect(status().isOk() )

            .andExpect( jsonPath("$.content").isArray())
            .andExpect( jsonPath("$.content", hasSize(1)))  // hamcrest
            .andExpect( jsonPath("$.content[0].id", equalTo( 306086 )))
            .andExpect( jsonPath("$.content[0].country", equalTo( "FooLand" )))
            .andExpect( jsonPath("$.content[0].name", equalTo( "Foo" )))
            // .andExpect(jsonPath("$").doesNotExist())
            // .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();
    }




    // ----- By Country -----
    @Test
    public void getRegionByCountry()
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

        given( repository.findByCountry( anyString(), any(Pageable.class)))
            .willReturn( foundPage );

        final MockHttpServletResponse response = mvc.perform(
            get("/location/region/byCountry/{country}", "CAN" ))
            .andDo(MockMvcResultHandlers.print())
            // .andExpect(status().isOk() )

            .andExpect( jsonPath("$.content").isArray())
            .andExpect( jsonPath("$.content", hasSize(1)))  // hamcrest
            .andExpect( jsonPath("$.content[0].id", equalTo( 306086 )))
            .andExpect( jsonPath("$.content[0].country", equalTo( "FooLand" )))
            .andExpect( jsonPath("$.content[0].name", equalTo( "Foo" )))
            .andReturn().getResponse();
    }








}

 
 
 