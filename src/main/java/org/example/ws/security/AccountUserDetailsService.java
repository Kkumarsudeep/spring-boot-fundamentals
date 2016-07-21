package org.example.ws.security;

import org.example.ws.model.Account;
import org.example.ws.model.Role;
import org.example.ws.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Service
public class AccountUserDetailsService implements UserDetailsService {

    /**
     * The Logger for this Class.
     */
    private static final Logger logger = LoggerFactory.getLogger(AccountUserDetailsService.class);

    /**
     * The AccountService business service.
     */
    @Autowired
    private transient AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        logger.info("> loadUserByUsername {}", username);

        final Account account = accountService.findByUsername(username);
        if (account == null) {
            // Not found...
            throw new UsernameNotFoundException("Invalid credentials.");
        }

        final Set<Role> roles = account.getRoles();
        if (roles == null || roles.isEmpty()) {
            // No Roles assigned to Account...
            throw new UsernameNotFoundException("Invalid credentials.");
        }

        final Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        for (final Role role : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getCode()));
        }

        final User userDetails = new User(account.getUsername(), account.getPassword(), account.isEnabled(),
                !account.isExpired(), !account.isCredentialsexpired(), !account.isLocked(), grantedAuthorities);

        logger.info("< loadUserByUsername {}", username);
        return userDetails;
    }

}
