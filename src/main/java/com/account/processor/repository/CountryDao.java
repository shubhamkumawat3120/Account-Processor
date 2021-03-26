package com.account.processor.repository;

import com.account.processor.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryDao extends JpaRepository<CountryEntity, Long> {
    @Override
    Optional<CountryEntity> findById(Long aLong);

    Optional<CountryEntity> findByCountryName(String name);

    boolean existsByCountryName(String countryName);
}
