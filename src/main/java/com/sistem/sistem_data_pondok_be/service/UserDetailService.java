package com.sistem.sistem_data_pondok_be.service;


import com.sistem.sistem_data_pondok_be.exception.NotFoundException;
import com.sistem.sistem_data_pondok_be.model.Akun;
import com.sistem.sistem_data_pondok_be.repository.AkunRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    AkunRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        if (userRepository.existsByEmail(username)){
            Akun user = userRepository.findByEmail(username).orElseThrow(() -> new NotFoundException("Id Not Found"));
            return UserDetail.buildUser(user);
        }
        throw new NotFoundException("User Not Found with username: " + username);
    }

}
