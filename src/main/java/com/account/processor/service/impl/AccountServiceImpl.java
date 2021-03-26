package com.account.processor.service.impl;

import com.account.processor.dto.AccountDto;
import com.account.processor.entity.AccountEntity;
import com.account.processor.entity.AddressEntity;
import com.account.processor.exception.AccountNotExistException;
import com.account.processor.exception.InvalidAddressException;
import com.account.processor.repository.AccountDao;
import com.account.processor.repository.CountryDao;
import com.account.processor.service.AccountService;
import com.account.processor.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    public static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private CountryDao countryDao;

    @Autowired
    private AddressService addressService;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public AccountDto saveAccount(AccountDto accountDto) throws InvalidAddressException {
        Optional<AccountEntity> optionalAccountEntity = accountDao.findByEmailAddress(accountDto.getEmailAddress());
        AccountEntity accountEntity = null;
        if (optionalAccountEntity.isPresent()) {
            accountEntity = optionalAccountEntity.get();
        } else {
            accountEntity = AccountEntity.builder()
                    .firstName(accountDto.getFirstName())
                    .lastName(accountDto.getLastName())
                    .emailAddress(accountDto.getEmailAddress())
                    .build();
        }

        if (!this.isCountryValid(accountDto.getAddress().getCountry())) {
            LOGGER.error("invalid country code");
            throw new InvalidAddressException("Invalid address");
        }

        accountDao.save(accountEntity);
        AddressEntity addressEntity = addressService.saveAddress(accountDto, accountEntity);
        accountDto.setId(accountEntity.getId());
        accountDto.getAddress().setId(addressEntity.getId());
        LOGGER.info("Account has been saved", accountDto);
        return accountDto;
    }

    @Override
    public AccountDto getAccountById(Long accountId) throws AccountNotExistException {
        Optional<AccountEntity> optionalAccountEntity = accountDao.findById(accountId);
        if (!optionalAccountEntity.isPresent()) {
            LOGGER.error("account does not existing with id:", accountId);
            throw new AccountNotExistException("Account does not exist.");
        }
        return optionalAccountEntity.get().toAccountDto();
    }

    private boolean isCountryValid(String countryName) {
        return countryDao.existsByCountryName(countryName);
    }
}
