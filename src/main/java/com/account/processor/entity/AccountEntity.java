package com.account.processor.entity;

import com.account.processor.dto.AccountDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "account")
public class AccountEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email_address")
    private String emailAddress;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AddressEntity> addressEntitySet;

    public static List<AccountDto> toAccountDtos(List<AccountEntity> accountEntities) {
        return accountEntities.stream()
                .map(AccountEntity::toAccountDto)
                .collect(Collectors.toList());
    }

    public AccountDto toAccountDto() {
        return AccountDto.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .emailAddress(emailAddress)
                .addressDtoList(addressEntitySet == null ? null : AddressEntity.toAddressDtos(addressEntitySet))
                .build();
    }


}
