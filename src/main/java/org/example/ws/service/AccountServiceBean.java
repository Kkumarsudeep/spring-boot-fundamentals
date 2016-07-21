package org.example.ws.service;

import org.example.ws.model.Account;
import org.example.ws.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceBean implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceBean.class);

    @Autowired
    private transient AccountRepository accountRepository;

    @Override
    public Account findByUsername(final String username) {
        logger.info("> findByUsername");

        final Account account = accountRepository.findByUsername(username);

        logger.info("< findByUsername");
        return account;
    }

}
