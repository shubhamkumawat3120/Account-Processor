package com.account.processor.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
public class AddressDto implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long id;
    private String street;
    private String city;
    private String province_state;
    @NotNull(message = "Country must not be null")
    private String country;
}
