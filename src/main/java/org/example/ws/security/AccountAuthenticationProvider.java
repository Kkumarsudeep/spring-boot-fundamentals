package org.example.ws.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AccountAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(AccountAuthenticationProvider.class);

    @Autowired
    private transient AccountUserDetailsService userDetailsService;

    @Autowired
    private transient PasswordEncoder passwordEncoder;

    @Override
    protected void additionalAuthenticationChecks(final UserDetails userDetails,
                                                  final UsernamePasswordAuthenticationToken token) throws AuthenticationException {
        logger.info("> additionalAuthenticationChecks");

        if (token.getCredentials() == null || userDetails.getPassword() == null) {
            logger.info("< additionalAuthenticationChecks");
            throw new BadCredentialsException("Credentials may not be null.");
        }

        if (!passwordEncoder.matches((String) token.getCredentials(), userDetails.getPassword())) {
            logger.info("< additionalAuthenticationChecks");
            throw new BadCredentialsException("Invalid credentials.");
        }

//        RequestContext.setUsername(userDetails.getUsername());

        logger.info("< additionalAuthenticationChecks");
    }

    @Override
    protected UserDetails retrieveUser(final String username, final UsernamePasswordAuthenticationToken token)
            throws AuthenticationException {
        logger.info("> retrieveUser");

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        logger.info("< retrieveUser");
        return userDetails;
    }

}