package com.account.processor.service;

import com.account.processor.dto.AccountDto;
import com.account.processor.exception.AccountNotExistException;
import com.account.processor.exception.InvalidAddressException;

public interface AccountService {

    AccountDto saveAccount(AccountDto accountDto) throws InvalidAddressException;

    AccountDto getAccountById(Long accountId) throws AccountNotExistException;
}
