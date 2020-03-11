package com.airline.locationservice.persistence.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import com.airline.locationservice.persistence.model.Region;



@Repository
public interface RegionsRepository extends PagingAndSortingRepository<Region, Integer>
{
    Optional<Region> findById( final Integer id );

    Optional<Region> findByCode( final String regionCode );

    Page<Region>     findByContinent( final String continent, Pageable paging );

    Page<Region>     findByCountry( final String country, Pageable paging );

    Page<Region>     findAll( Pageable paging );
}

