package com.freenow.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.freenow.dataaccessobject.UserRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService
{

    private UserRepository userRepository;


    public JpaUserDetailsService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException
    {
        return userRepository.findByUsername(userName).orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));
    }
}
