package com.account.processor.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.account.processor.dto.AccountDto;
import com.account.processor.dto.AddressDto;
import com.account.processor.exception.InvalidAddressException;
import com.account.processor.service.impl.AccountServiceImpl;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AccountControllerTest {

    @InjectMocks
    private AccountController accountController;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private AccountServiceImpl accountServiceImpl;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void testContextLoad() {
        Assertions.assertNotNull(accountController, "Context must not be null");
    }

    @Test
    public void testSaveAccountIntegrationTest() {
        HttpHeaders headers = new HttpHeaders();  
        headers.setContentType(MediaType.APPLICATION_JSON);
        String sampleJson = "{\n    \"firstName\": \"John\",\n    \"lastName\": \"Doe\",\n    \"emailAddress\": \"john.doe@gmail.com\",\n    \"address\": {\n        \"street\": \"1234 Given Lane\",\n        \"city\": \"newyork2\",\n        \"province_state\": \"ON\",\n        \"country\": \"United State\"\n    }\n}";

        HttpEntity<String> entity = new HttpEntity<>(sampleJson, headers);
        ResponseEntity<Object> response = restTemplate.exchange("http://localhost:8080/app/v1/account", HttpMethod.POST, entity, Object.class);
        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());

    }

    @Test
    public void testSaveAccount() throws InvalidAddressException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

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
        AccountDto savedAccountDto = AccountDto.builder()
                .id(1L)
                .firstName("shubham")
                .lastName("kumawat")
                .emailAddress("shubham@gmail.com")
                .address(AddressDto.builder()
                        .id(1L)
                        .city("jaipur")
                        .street("lalpura")
                        .province_state("NO")
                        .country("Canada")
                        .build())
                .build();
        Mockito.when(accountServiceImpl.saveAccount(Mockito.nullable(AccountDto.class))).thenReturn(savedAccountDto);
        ResponseEntity<ResourceResponse<AccountDto>> responseEntity = accountController.saveAccount(accountDto, bindingResult);
        Assertions.assertEquals(responseEntity.getStatusCodeValue(), 201, "Internal server error");
        Assertions.assertEquals(responseEntity.getBody(), new ResourceResponse<>(savedAccountDto), "Invalid result");
        Assertions.assertNull(responseEntity.getBody().getErrorMessages(), "Invalid request");

    }


}
