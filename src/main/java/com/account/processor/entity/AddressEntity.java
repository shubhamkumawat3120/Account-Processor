package com.account.processor.entity;

import com.account.processor.dto.AddressDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class AddressEntity implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "province_state")
    private String provinceState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private CountryEntity country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private AccountEntity account;

    public static List<AddressDto> toAddressDtos(List<AddressEntity> addressEntities) {
        return addressEntities.stream()
                .map(AddressEntity::toAddressDto)
                .collect(Collectors.toList());
    }

    public AddressDto toAddressDto() {
        return AddressDto.builder()
                .id(id)
                .street(street)
                .city(city)
                .province_state(provinceState)
                .country(country == null ? null : country.getCountryName())
                .build();
    }

}
