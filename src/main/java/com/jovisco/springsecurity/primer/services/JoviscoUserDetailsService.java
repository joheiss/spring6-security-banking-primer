package com.jovisco.springsecurity.primer.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jovisco.springsecurity.primer.entities.Customer;
import com.jovisco.springsecurity.primer.repositories.CustomerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class JoviscoUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    // @PreAuthorize("#username == authentication.principal.username")
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.trace("load username: " + username);
        Customer customer = customerRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User details not found for the user: " + username));
        List<GrantedAuthority> authorities = customer.getAuthorities()
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName())).collect(Collectors.toList());
        return new User(customer.getEmail(), customer.getPwd(), authorities);
        // List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(customer.getRole()));

    }
}
