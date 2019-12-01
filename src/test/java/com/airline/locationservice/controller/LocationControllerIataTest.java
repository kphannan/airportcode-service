package com.airline.locationservice.controller;

// import static org.hamcrest.MatcherAssert.assertThat;
// import static org.hamcrest.Matchers.both;
// import static org.hamcrest.Matchers.containsString;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.*;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.*;
// import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import java.util.Optional;

import com.airline.locationservice.repository.AirportCodeIata;
import com.airline.locationservice.repository.AirportCodeIataRepository;
import com.airline.core.location.AirportCode;
import com.airline.core.location.IATAAirportCode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.boot.test.mock.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.mockito.InjectMocks;
import org.mockito.Mock;


// @ExtendWith(MockitoExtension.class)
@SpringBootTest
public class LocationControllerIataTest
{
    // @Autowired
    private MockMvc   mvc;

    // mock repository
    @Mock
    AirportCodeIataRepository repository;

    @InjectMocks
    LocationControllerIata    service;
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
    public void getSingleKnownIataAirportCode()
        throws Exception
    {
        IATAAirportCode airportCode = new IATAAirportCode( "ATL" );
        assertThat( airportCode ).isNotNull();

        // when( repository.findById("ATL")).thenReturn( Optional.of( new AirportCodeIata( airportCode.getAirportCode() )));
        given( repository.findById("ATL")).willReturn( Optional.of( new AirportCodeIata( airportCode.getAirportCode() )));

        MockHttpServletResponse response = mvc.perform(
            get("/airport/iata/{id}", airportCode.getAirportCode() )
                .accept( MediaType.APPLICATION_JSON ))
            // .andExpect().ok()
            .andReturn().getResponse();

        // assertThat(response).ok();
        // LocationControllerIata controller = new LocationControllerIata( mockRepository );

        // when( mockRepository.findById("ATL")).thenReturn(null);
        // ResponseEntity<AirportCode> iataResp = controller.one( "ATL" );
    }
}

 
 
 