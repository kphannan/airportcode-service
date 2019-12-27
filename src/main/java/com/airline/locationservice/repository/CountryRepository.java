package com.airline.locationservice.repository;

import java.util.Optional;
import java.util.List;

import com.airline.locationservice.model.Country;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

    public Optional<Country> findByCode( final String countryCode );

    public List<Country>     findByContinent( final String continent );
}

