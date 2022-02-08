package com.airline.locationservice.persistence.repository.airport;


import java.util.Optional;

import com.airline.locationservice.persistence.model.airport.AirportCodeIata;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Paged repository of IATA {@code AirportCode}s.
 */
@Repository
// @NoRepositoryBean
public interface AirportCodeIataRepository extends PagingAndSortingRepository<AirportCodeIata, String>
{
    /**
     * Lookup a IATA airport reference code by its code.
     *
     * @param iataCode the code to locate.
     * @return the record if found.
     */
    @Override
    @Query( "from #{#entityName} a where a.iataCode = :iataCode" )
    Optional<AirportCodeIata> findById( @Param( "iataCode" ) String iataCode );

    /**
     * Lookup IATA airport code by a partial, full id.  If no ID is specified, the
     * complete list is returned page by page.
     *
     * @param paging the {@code Page} criteria.
     * @return the located page, whose body contains the found records.
     */
    @Override
    @Query(  "FROM #{#entityName} a WHERE a.iataCode IS NOT NULL AND a.iataCode != ''"
           + " AND a.iataCode != '0'"
           + " AND a.iataCode NOT LIKE '%-'  AND a.iataCode NOT LIKE '%2'"
           + " AND a.iataCode NOT LIKE '%4'  AND a.iataCode NOT LIKE '%7'" )
    // @Query( "FROM #{#entityName} a WHERE a.iataCode #{MATCHES '[A-Z]{3}'}")
    Page<AirportCodeIata> findAll( Pageable paging );
}

