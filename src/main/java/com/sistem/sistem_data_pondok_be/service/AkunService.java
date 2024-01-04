package com.sistem.sistem_data_pondok_be.service;

import com.sistem.sistem_data_pondok_be.DTO.LoginRequest;
import com.sistem.sistem_data_pondok_be.exception.NotFoundException;
import com.sistem.sistem_data_pondok_be.model.Akun;
import com.sistem.sistem_data_pondok_be.repository.AkunRepository;
import com.sistem.sistem_data_pondok_be.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AkunService {
    @Autowired
    AkunRepository akunRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AuthenticationManager authenticationManager;


    public Map<Object, Object> login(LoginRequest loginRequest) {
        Akun user = akunRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new RuntimeException("Username not found"));
        if (encoder.matches(loginRequest.getPassword(), user.getPassword())) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getPassword(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateToken(authentication);
            user.setLast_login(new Date());
            akunRepository.save(user);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedLastLogin = sdf.format(user.getLast_login());
            Map<Object, Object> response = new HashMap<>();
            response.put("data", user);
            response.put("token", jwt);
            response.put("last_login", formattedLastLogin);
            return response;
        }
        throw new NotFoundException("Password not found");
    }

    public Akun addSantri(Akun user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("Santri");
        return akunRepository.save(user);
    }

    public Akun addPengurus(Akun user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("Pengurus");
        return akunRepository.save(user);
    }

    public Akun get(Long id) {
        return akunRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
    }

    public List<Akun> getAll() {
        return akunRepository.findAll();
    }

    public Akun edit(Long id, Akun user) {
        Akun update = akunRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
        update.setPassword(user.getPassword());
        update.setUsername(user.getUsername());
        return akunRepository.save(user);
    }
    public Map<String, Boolean> delete(Long id) {
        try {
            akunRepository.deleteById(id);
            Map<String, Boolean> res = new HashMap<>();
            res.put("Deleted", Boolean.TRUE);
            return res;
        } catch (Exception e) {
            return null;
        }
    }
}
