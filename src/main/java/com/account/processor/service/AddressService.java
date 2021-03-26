package com.account.processor.service;

import com.account.processor.dto.AccountDto;
import com.account.processor.entity.AccountEntity;
import com.account.processor.entity.AddressEntity;

public interface AddressService {

    boolean isAddressExist(String countryName, AccountEntity accountEntity);

    AddressEntity getAddress(String countryName, AccountEntity accountEntity);

    AddressEntity saveAddress(AccountDto accountDto, AccountEntity accountEntity);
}
