package com.wccgroup.distancecalculator.security;

import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wccgroup.distancecalculator.model.AuthUser;
import com.wccgroup.distancecalculator.repository.AuthUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {

    private static final String ROLE_PREFIX = "ROLE_";

    private final AuthUserRepository authUserRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        AuthUser authUser = authUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return AuthUserPrincipal.builder()
                .username(username)
                .password(authUser.getPassword())
                .authorities(Set.of(new SimpleGrantedAuthority(ROLE_PREFIX + authUser.getRole().name())))
                .build();
    }
}
