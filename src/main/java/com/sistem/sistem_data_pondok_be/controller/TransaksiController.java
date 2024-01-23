package com.sistem.sistem_data_pondok_be.controller;


import com.sistem.sistem_data_pondok_be.exception.CommonResponse;
import com.sistem.sistem_data_pondok_be.exception.ResponseHelper;
import com.sistem.sistem_data_pondok_be.model.Tagihan;
import com.sistem.sistem_data_pondok_be.model.Transaksi;
import com.sistem.sistem_data_pondok_be.service.TransaksiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("api/transaksi")
@CrossOrigin(origins = "http://localhost:3000")
public class TransaksiController {
    @Autowired
    private TransaksiService transaksiService;

    private static final String JWT_PREFIX = "jwt ";

    @PostMapping(consumes = "multipart/form-data")
    public CommonResponse<?> add(Transaksi transaksi , @RequestPart("file") MultipartFile multipartFile , HttpServletRequest requests){
        String jwtToken = requests.getHeader("auth-tgh").substring(JWT_PREFIX.length());
        return ResponseHelper.ok( transaksiService.add(transaksi ,jwtToken, multipartFile));
    }
    @GetMapping("/{id}")
    public CommonResponse <Transaksi> get(@PathVariable("id") Long id){
        return ResponseHelper.ok( transaksiService.get(id));
    }
    @GetMapping("/all")
    public CommonResponse<List<Transaksi>> getAll( HttpServletRequest requests){
        String jwtToken = requests.getHeader("auth-tgh").substring(JWT_PREFIX.length());
        return ResponseHelper.ok( transaksiService.getAll(jwtToken));
    }

    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id")  Long id) {
        return ResponseHelper.ok( transaksiService.delete(id));
    }
    @GetMapping
    public CommonResponse<List<Transaksi>> get( HttpServletRequest requests){
        String jwtToken = requests.getHeader("auth-tgh").substring(JWT_PREFIX.length());
        return ResponseHelper.ok(transaksiService.getTransaksi(jwtToken));
    }
    @GetMapping("/history")
    public CommonResponse<List<Transaksi>> getHistory( HttpServletRequest requests){
        String jwtToken = requests.getHeader("auth-tgh").substring(JWT_PREFIX.length());
        return ResponseHelper.ok(transaksiService.getHistoryTransaksi(jwtToken));
    }
    @GetMapping("/status")
    public CommonResponse<List<Transaksi>> getStatus(String status, HttpServletRequest requests){
        String jwtToken = requests.getHeader("auth-tgh").substring(JWT_PREFIX.length());
        return ResponseHelper.ok(transaksiService.getTransaksiByStatus(jwtToken , status));
    }
    @GetMapping("/santri/{userId}/bulan/{bulan}")
    public List<Transaksi> getTagihanBySantriAndMonth(@PathVariable String userId, @PathVariable int bulan) {
        return transaksiService.findBySantriIdAndMonth(userId, bulan);
    }
    @GetMapping("/bulan/{bulan}")
    public List<Transaksi> getTagihanByMonth(@PathVariable int bulan , HttpServletRequest requests) {
        String jwtToken = requests.getHeader("auth-tgh").substring(JWT_PREFIX.length());
        return transaksiService.findByMonth(jwtToken,bulan);
    }
}
