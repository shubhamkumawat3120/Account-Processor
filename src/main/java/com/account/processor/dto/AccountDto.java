package com.account.processor.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long id;
    @NotNull(message = "FirstName must not be null")
    private String firstName;
    @NotNull(message = "LastName must not be null")
    private String lastName;
    @NotNull
    @Email(message = "Invalid email address")
    private String emailAddress;
    @NotNull(message = "Address must not be null")
    private AddressDto address;
    private List<AddressDto> addressDtoList;


}
