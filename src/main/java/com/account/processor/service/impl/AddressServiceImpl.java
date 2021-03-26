package com.account.processor.service.impl;

import com.account.processor.dto.AccountDto;
import com.account.processor.dto.AddressDto;
import com.account.processor.entity.AccountEntity;
import com.account.processor.entity.AddressEntity;
import com.account.processor.repository.AddressDao;
import com.account.processor.repository.CountryDao;
import com.account.processor.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    public static final Logger LOGGER = LoggerFactory.getLogger(AddressServiceImpl.class);
    @Autowired
    private AddressDao addressDao;

    @Autowired
    private CountryDao countryDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean isAddressExist(String countryName, AccountEntity accountEntity) {
        Optional<List<AddressEntity>> optionalAddressEntity = addressDao.findByAccount(accountEntity);
        if (optionalAddressEntity.isPresent()) {
            List<AddressEntity> addressEntities = optionalAddressEntity.get();
            return addressEntities.stream().anyMatch(address -> address.getCountry().getCountryName().equals(countryName));
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public AddressEntity getAddress(String countryName, AccountEntity accountEntity) {
        Optional<AddressEntity> byCountryAndAccount = addressDao.findByCountryAndAccount(countryDao.findByCountryName(countryName).get(), accountEntity);
        if (byCountryAndAccount.isPresent()) {
            return byCountryAndAccount.get();
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public AddressEntity saveAddress(AccountDto accountDto, AccountEntity accountEntity) {
        AddressEntity addressEntity = null;
        AddressDto addressDto = accountDto.getAddress();
        if (this.isAddressExist(addressDto.getCountry(), accountEntity)) {
            addressEntity = this.getAddress(accountDto.getAddress().getCountry(), accountEntity);

            if (!Objects.equals(addressEntity.getStreet(), addressDto.getStreet())) {
                addressEntity.setStreet(addressDto.getStreet());
            }
            if (!Objects.equals(addressEntity.getCity(), addressDto.getCity())) {
                addressEntity.setCity(addressDto.getCity());
            }
            if (!Objects.equals(addressEntity.getProvinceState(), addressDto.getProvince_state())) {
                addressEntity.setProvinceState(addressDto.getProvince_state());
            }

        } else {
            addressEntity = AddressEntity.builder()
                    .street(accountDto.getAddress().getStreet())
                    .city(accountDto.getAddress().getCity())
                    .provinceState(accountDto.getAddress().getProvince_state())
                    .country(countryDao.findByCountryName(accountDto.getAddress().getCountry()).get())
                    .account(accountEntity)
                    .build();

        }
        return addressDao.save(addressEntity);
    }
}
