package com.sistem.sistem_data_pondok_be.controller;


import com.sistem.sistem_data_pondok_be.DTO.LoginRequest;
import com.sistem.sistem_data_pondok_be.exception.CommonResponse;
import com.sistem.sistem_data_pondok_be.exception.ResponseHelper;
import com.sistem.sistem_data_pondok_be.model.Akun;
import com.sistem.sistem_data_pondok_be.service.AkunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("api/akun")
@CrossOrigin(origins = "http://localhost:3000")
public class AkunController {

    @Autowired
    private AkunService akunService;

    private static final String JWT_PREFIX = "jwt ";


    @PostMapping("/login")
    public CommonResponse<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        return ResponseHelper.ok( akunService.login(loginRequest));
    }
    @PostMapping("/add/pengurus")
    public CommonResponse<Akun> addPengurus(@RequestBody Akun akun){
        return ResponseHelper.ok( akunService.addPengurus(akun));
    }

    @PostMapping("/add/santri")
    public CommonResponse<Akun> addSantri(@RequestBody Akun akun, HttpServletRequest requests){
        String jwtToken = requests.getHeader("auth-tgh").substring(JWT_PREFIX.length());
        return ResponseHelper.ok( akunService.addSantri(akun ,jwtToken));
    }
    @GetMapping("/{id}")
    public CommonResponse <Akun> get(@PathVariable("id") Long id , HttpServletRequest requests){
        String jwtToken = requests.getHeader("auth-tgh").substring(JWT_PREFIX.length());
        return ResponseHelper.ok( akunService.get(id ,jwtToken ));
    }
    @GetMapping
    public CommonResponse<List<Akun>> getAll( HttpServletRequest requests){
        String jwtToken = requests.getHeader("auth-tgh").substring(JWT_PREFIX.length());
        return ResponseHelper.ok( akunService.getAll(jwtToken));
    }
    @GetMapping("/santri")
    public CommonResponse<List<Akun>> getSantri( HttpServletRequest requests){
        String jwtToken = requests.getHeader("auth-tgh").substring(JWT_PREFIX.length());
        return ResponseHelper.ok( akunService.getSantri(jwtToken));
    }
    @PutMapping("/{id}")
    public CommonResponse<Akun> put(@PathVariable("id") Long id , @RequestBody Akun akun ,  HttpServletRequest requests){
        String jwtToken = requests.getHeader("auth-tgh").substring(JWT_PREFIX.length());
        return ResponseHelper.ok( akunService.edit(id, akun , jwtToken));
    }
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id")  Long id , HttpServletRequest requests) {
        String jwtToken = requests.getHeader("auth-tgh").substring(JWT_PREFIX.length());
        return ResponseHelper.ok( akunService.delete(id, jwtToken));
    }
}
