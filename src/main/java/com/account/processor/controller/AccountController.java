package com.account.processor.controller;

import com.account.processor.dto.AccountDto;
import com.account.processor.exception.AccountNotExistException;
import com.account.processor.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/app/v1/account")
public class AccountController {
    public static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @GetMapping(path = "/{accountId}")
    public ResponseEntity<ResourceResponse<AccountDto>> fetchAccountInfo(@PathVariable("accountId") Long accountId) {
        LOGGER.info("Fetch account request initiated with accountId:", accountId);
        AccountDto accountById = null;
        try {
            accountById = accountService.getAccountById(accountId);
        } catch (AccountNotExistException e) {
            LOGGER.error("Fetch account info faild due to :", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResourceResponse<>(Arrays.asList(e.getMessage())));
        }
        return ResponseEntity.ok().body(new ResourceResponse<>(accountById));

    }

    @PostMapping
    public ResponseEntity<ResourceResponse<AccountDto>> saveAccount(@Valid @RequestBody final AccountDto accountDto, BindingResult bindingResult) {
        LOGGER.info("Save account request initiated ", accountDto);
        AccountDto savedAccountDto = null;
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            List<String> messages = new ArrayList<>();
            errors.forEach(error -> messages.add(error.getField() + ":" + error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(new ResourceResponse<>(messages));
        }
        try {
            savedAccountDto = accountService.saveAccount(accountDto);
        } catch (Exception e) {
            LOGGER.error("Account save faild due to :", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResourceResponse<>(Arrays.asList(e.getMessage())));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResourceResponse<>(savedAccountDto));

    }


}
