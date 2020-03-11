package com.airline.locationservice.persistence.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.airline.locationservice.persistence.model.AirportCodeIcao;

// import org.springframework.data.repository.PagingAndSortingRepository;

@Repository
// @NoRepositoryBean
public interface AirportCodeIcaoRepository extends JpaRepository<AirportCodeIcao, String>
{
    // @Query( "from AirportCode airportCode where airportCode.code = :airport" )
    // Stream<AirportCodeIcao> searchAirportCode( String airport );
    // AirportCodeIcao findById( String id );


    @Query( "from #{#entityName} a where a.icaoCode = :icaoCode" )
    Optional<AirportCodeIcao> findById( @Param("icaoCode") String icaoCode );

    @Query( "FROM #{#entityName} a WHERE a.icaoCode IS NOT NULL AND a.icaoCode != '' AND a.icaoCode != '0'")
    // @Query( "FROM #{#entityName} a WHERE a.icaoCode IS NOT NULL AND a.icaoCode != '' AND a.icaoCode != '0' AND iata_code NOT LIKE '%-'  AND iata_code NOT LIKE '%2'  AND iata_code NOT LIKE '%4'  AND iata_code NOT LIKE '%7'")
    // @Query( "FROM #{#entityName} a WHERE a.icaoCode #{MATCHES '[A-Z]{3}'}")
    Page<AirportCodeIcao> findAll( Pageable paging );
}

