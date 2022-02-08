package com.airline.locationservice.persistence.repository.location;

import java.util.List;
import java.util.Optional;

import com.airline.locationservice.persistence.model.location.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



/**
 * CRUD operations on the {@code Country} repository.
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, Integer>
{
    /**
     * Retrieve all {@code Country} records by {@code Page}.
     *
     * @param paging the {@code Page} criteria.
     * @return the paged result of {@code Country}s.
     */
    @Override
    Page<Country>     findAll( Pageable paging );

    /**
     * Find a single {@code Country} by its id code.
     *
     * @param id the unique id code.
     * @return the {@code Country} if found.
     */
    Optional<Country> findByCode( final String countryCode );

    /**
     * Retrive all the countries on a given contintent.
     * @param continent the continent code.
     * @param paging the {@code Page} criteria.
     * @return the paged result of {@code Country}s.
     */
    List<Country>     findByContinent( final String continent );
}

