// package com.airline.locationservice.controller;

// import static org.hamcrest.MatcherAssert.assertThat;
// import static org.hamcrest.Matchers.both;
// import static org.hamcrest.Matchers.containsString;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.mockito.Mockito.when;

// import com.airline.locationservice.repository.AirportCodeIataRepository;
// import com.airline.core.location.AirportCode;

// import org.junit.jupiter.api.Test;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.ResponseEntity;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.beans.factory.annotation.Autowired;


// @DataJpaTest
// public class LocationControllerIataTest
// {
//     // mock repository
//     @Autowired
//     AirportCodeIataRepository mockRepository;

//     @Test
//     public void getSingleKnownIataAirportCode()
//     {

//         LocationControllerIata controller = new LocationControllerIata( mockRepository );

//         when( mockRepository.findById("ATL")).thenReturn(null);
//         ResponseEntity<AirportCode> iataResp = controller.one( "ATL" );
//     }
// }

 