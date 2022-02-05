package com.airline.locationservice.persistence.repository;

import com.airline.locationservice.persistence.model.AirportCodeIcao;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportCodeIcaoRepository extends PagingAndSortingRepository<AirportCodeIcao, String>
{

    @Query( "from #{#entityName} a where a.icaoCode = :icaoCode" )
    Optional<AirportCodeIcao> findById( @Param( "icaoCode" ) String icaoCode );

    @Query( "FROM #{#entityName} a WHERE a.icaoCode IS NOT NULL AND LENGTH(a.icaoCode) = 4 AND SUBSTRING(a.icaoCode, 1,1 ) >= 'A' AND SUBSTRING(a.icaoCode, 2,1 ) >= 'A' AND SUBSTRING(a.icaoCode, 3,1 ) >= 'A' AND SUBSTRING(a.icaoCode, 4,1 ) >= 'A'")
    Page<AirportCodeIcao> findAll( Pageable paging );
}

