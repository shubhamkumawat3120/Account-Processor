package com.account.processor.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.account.processor.dto.AccountDto;
import com.account.processor.dto.AddressDto;
import com.account.processor.entity.AccountEntity;
import com.account.processor.entity.AddressEntity;
import com.account.processor.entity.CountryEntity;
import com.account.processor.exception.InvalidAddressException;
import com.account.processor.repository.AccountDao;
import com.account.processor.repository.CountryDao;
import com.account.processor.service.impl.AccountServiceImpl;
import com.account.processor.service.impl.AddressServiceImpl;


@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountServiceImpl accountServiceImpl;

    @Mock
    private AccountDao accountDao;

    @Mock
    private CountryDao countryDao;

    @Mock
    private AddressServiceImpl addressServiceImpl;

    @Test
    public void testSaveAccount() throws InvalidAddressException {
        AccountDto accountDto = AccountDto.builder()
                .firstName("shubham")
                .lastName("kumawat")
                .emailAddress("shubham@gmail.com")
                .address(AddressDto.builder()
                        .city("jaipur")
                        .street("lalpura")
                        .province_state("NO")
                        .country("Canada")
                        .build())
                .build();

       AccountEntity accountEntity = AccountEntity.builder()
               .id(1l)
                .firstName(accountDto.getFirstName())
                .lastName(accountDto.getLastName())
                .emailAddress(accountDto.getEmailAddress())
                .build();

       AddressEntity addressEntity = AddressEntity.builder()
               .id(1l)
                .street(accountDto.getAddress().getStreet())
                .city(accountDto.getAddress().getCity())
                .provinceState(accountDto.getAddress().getProvince_state())
                .country(CountryEntity.builder()
                        .id(1l)
                        .countryCode("CAD")
                        .countryName("Canada")
                        .build())
                .account(accountEntity)
                .build();


        Mockito.when(addressServiceImpl.saveAddress(Mockito.nullable(AccountDto.class), Mockito.nullable(AccountEntity.class))).thenReturn(addressEntity);
        Mockito.when(countryDao.existsByCountryName(Mockito.any())).thenReturn(true);
        AccountDto saveAccount = accountServiceImpl.saveAccount(accountDto);
        Assertions.assertNotNull(saveAccount, "Account must not be null");
    }

    @Test
    public void testSaveAccountException() throws InvalidAddressException {
        AccountDto accountDto = AccountDto.builder()
                .firstName("shubham")
                .lastName("kumawat")
                .emailAddress("shubham@gmail.com")
                .address(AddressDto.builder()
                        .city("jaipur")
                        .street("lalpura")
                        .province_state("NO")
                        .country("Canada")
                        .build())
                .build();
 

        Mockito.when(countryDao.existsByCountryName(Mockito.any())).thenReturn(false);
        Assertions.assertThrows(InvalidAddressException.class, ()->accountServiceImpl.saveAccount(accountDto), " Exception thrown invalid address");
    }

    @Test
    public void testSaveAccountIfUserExist() throws InvalidAddressException {
        AccountDto accountDto = AccountDto.builder()
                .firstName("shubham")
                .lastName("kumawat")
                .emailAddress("shubham@gmail.com")
                .address(AddressDto.builder()
                        .city("jaipur")
                        .street("lalpura")
                        .province_state("NO")
                        .country("Canada")
                        .build())
                .build();
        
        AccountEntity accountEntity = AccountEntity.builder()
                .id(1l)
                .firstName(accountDto.getFirstName())
                .lastName(accountDto.getLastName())
                .emailAddress(accountDto.getEmailAddress())
                .build();

        AddressEntity addressEntity = AddressEntity.builder()
                .id(1l)
                .street(accountDto.getAddress().getStreet())
                .city(accountDto.getAddress().getCity())
                .provinceState(accountDto.getAddress().getProvince_state())
                .country(CountryEntity.builder()
                        .id(1l)
                        .countryCode("CAD")
                        .countryName("Canada")
                        .build())
                .account(accountEntity)
                .build();

        Mockito.when(accountDao.findByEmailAddress(Mockito.any())).thenReturn(Optional.of(accountEntity));
        Mockito.when(addressServiceImpl.saveAddress(Mockito.nullable(AccountDto.class), Mockito.nullable(AccountEntity.class))).thenReturn(addressEntity);
        Mockito.when(countryDao.existsByCountryName(Mockito.any())).thenReturn(true);
        AccountDto saveAccount = accountServiceImpl.saveAccount(accountDto);
        Assertions.assertNotNull(saveAccount, "Account must not be null");
    }
}
