package com.sistem.sistem_data_pondok_be.service;

import com.sistem.sistem_data_pondok_be.DTO.LoginRequest;
import com.sistem.sistem_data_pondok_be.exception.BadRequestException;
import com.sistem.sistem_data_pondok_be.exception.NotFoundException;
import com.sistem.sistem_data_pondok_be.model.Akun;
import com.sistem.sistem_data_pondok_be.repository.AkunRepository;
import com.sistem.sistem_data_pondok_be.security.JwtUtils;
import io.jsonwebtoken.Claims;
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
        Akun user = akunRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new RuntimeException("Username not found"));
        if (encoder.matches(loginRequest.getPassword(), user.getPassword())) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateToken(authentication);
            user.setLast_login(new Date());
            akunRepository.save(user);
            Map<Object, Object> response = new HashMap<>();
            response.put("data", user);
            response.put("token", jwt);
            return response;
        }
        throw new NotFoundException("Password not found");
    }

    public Akun addSantri(Akun user , String jwtToken) {
        Claims claims = jwtUtils.decodeJwt(jwtToken);
        String email = claims.getSubject();
        Akun akun = akunRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email Not Found"));
        if (akun.getRole().equals("Pengurus")){
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("Santri");
        return akunRepository.save(user);
        }
        throw new BadRequestException("API ini hanya bisa di akses oleh pengurus");
    }

    public Akun addPengurus(Akun user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("Pengurus");
        return akunRepository.save(user);
    }

    public Akun get(Long id , String jwtToken) {
        Claims claims = jwtUtils.decodeJwt(jwtToken);
        String email = claims.getSubject();
        Akun user = akunRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email Not Found"));
        if (user.getRole().equals("Pengurus")){
        return akunRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
        }
        throw new BadRequestException("API ini hanya bisa di akses oleh pengurus");
    }

    public List<Akun> getAll(String jwtToken) {
        Claims claims = jwtUtils.decodeJwt(jwtToken);
        String email = claims.getSubject();
        Akun user = akunRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email Not Found"));
        if (user.getRole().equals("Pengurus")){
        return akunRepository.findAll();
        }
        throw new BadRequestException("API ini hanya bisa di akses oleh pengurus");
    }
    public List<Akun> getSantri(String jwtToken) {
        Claims claims = jwtUtils.decodeJwt(jwtToken);
        String email = claims.getSubject();
        Akun user = akunRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email Not Found"));
        if (user.getRole().equals("Pengurus")){
        return akunRepository.findBySantri();
        }
        throw new BadRequestException("API ini hanya bisa di akses oleh pengurus");
    }

    public Akun edit(Long id, Akun user , String jwtToken) {
        Claims claims = jwtUtils.decodeJwt(jwtToken);
        String email = claims.getSubject();
        Akun akun = akunRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email Not Found"));
        if (akun.equals("Pengurus")){
        Akun update = akunRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
        update.setPassword(user.getPassword());
        update.setUsername(user.getUsername());
        return akunRepository.save(update);
        }
        throw new BadRequestException("API ini hanya bisa di akses oleh pengurus");
    }
    public Map<String, Boolean> delete(Long id , String jwtToken) {
        Claims claims = jwtUtils.decodeJwt(jwtToken);
        String email = claims.getSubject();
        Akun user = akunRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email Not Found"));
        if (user.getRole().equals("Pengurus")){

        try {
            akunRepository.deleteById(id);
            Map<String, Boolean> res = new HashMap<>();
            res.put("Deleted", Boolean.TRUE);
            return res;
        } catch (Exception e) {
            return null;
        }
        }
        throw new BadRequestException("API ini hanya bisa di akses oleh pengurus");
    }
}
