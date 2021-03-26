package com.account.processor.repository;

import com.account.processor.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountDao extends JpaRepository<AccountEntity, Long> {

    @Override
    Optional<AccountEntity> findById(Long aLong);

    Optional<AccountEntity> findByEmailAddress(String emailAddress);
}
