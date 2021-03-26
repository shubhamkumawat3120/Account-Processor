package com.account.processor.repository;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.account.processor.entity.CountryEntity;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RepositoryTest {

    @Autowired
    private CountryDao countryDao;

    @Test
    public void testFindByIdForCountryRepo(){
        Long id = 1l;
        Optional<CountryEntity> countryEntity = countryDao.findById(id);
        Assertions.assertEquals(countryEntity.get().getCountryCode(), "US", "Country code not equal");
    }

    @Test
    public void testFindByCountryNameCountryRepo(){
        String countryName = "Canada";
        Optional<CountryEntity> optionalCountryEntity = countryDao.findByCountryName(countryName);
        Assertions.assertEquals(optionalCountryEntity.get().getCountryName(), "United State", "Country name not equal");
    }


}
