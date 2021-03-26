package com.account.processor.repository;

import com.account.processor.entity.AccountEntity;
import com.account.processor.entity.AddressEntity;
import com.account.processor.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressDao extends JpaRepository<AddressEntity, Long> {

    @Override
    Optional<AddressEntity> findById(Long aLong);

    Optional<List<AddressEntity>> findByAccount(AccountEntity accountEntity);

    Optional<AddressEntity> findByCountryAndAccount(CountryEntity countryEntity, AccountEntity accountEntity);

}
