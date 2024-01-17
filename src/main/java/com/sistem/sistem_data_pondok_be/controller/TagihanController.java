package com.sistem.sistem_data_pondok_be.controller;

import com.sistem.sistem_data_pondok_be.exception.CommonResponse;
import com.sistem.sistem_data_pondok_be.exception.ResponseHelper;
import com.sistem.sistem_data_pondok_be.model.Akun;
import com.sistem.sistem_data_pondok_be.model.Tagihan;
import com.sistem.sistem_data_pondok_be.service.TagihanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("api/tagihan")
@CrossOrigin(origins = "http://localhost:3000")
public class TagihanController {
    @Autowired
    private TagihanService tagihanService;

    private static final String JWT_PREFIX = "jwt ";

    @PostMapping("/add")
    public CommonResponse<Tagihan> add(@RequestBody Tagihan tagihan , HttpServletRequest requests){
        String jwtToken = requests.getHeader("auth-tgh").substring(JWT_PREFIX.length());
        return ResponseHelper.ok( tagihanService.addTagihan(tagihan ,jwtToken));
    }
    @GetMapping("/{id}")
    public CommonResponse <Tagihan> get(@PathVariable("id") Long id){
        return ResponseHelper.ok( tagihanService.get(id));
    }
    @GetMapping("/all")
    public CommonResponse<List<Tagihan>> getAll( HttpServletRequest requests){
        String jwtToken = requests.getHeader("auth-tgh").substring(JWT_PREFIX.length());
        return ResponseHelper.ok( tagihanService.getAll(jwtToken));
    }
    @PutMapping("/{id}")
    public CommonResponse<Tagihan> put(@PathVariable("id") Long id , @RequestBody Tagihan tagihan){
        return ResponseHelper.ok( tagihanService.edit(id, tagihan));
    }
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id")  Long id) {
        return ResponseHelper.ok( tagihanService.delete(id));
    }
    @GetMapping
    public CommonResponse<List<Tagihan>> get( HttpServletRequest requests){
        String jwtToken = requests.getHeader("auth-tgh").substring(JWT_PREFIX.length());
        return ResponseHelper.ok(tagihanService.getTagihan(jwtToken));
    }
    @GetMapping("/history")
    public CommonResponse<List<Tagihan>> getByStatus( HttpServletRequest requests){
        String jwtToken = requests.getHeader("auth-tgh").substring(JWT_PREFIX.length());
        return ResponseHelper.ok(tagihanService.getTagihanByStatus(jwtToken));
    }
    @GetMapping("/santri/{userId}/bulan/{bulan}")
    public List<Tagihan> getTagihanBySantriAndMonth(@PathVariable String userId, @PathVariable int bulan) {
        return tagihanService.findBySantriIdAndMonth(userId, bulan);
    }
    @GetMapping("/santri/{userId}/jenis_tagihan/{tagihan}")
    public List<Tagihan> getTagihanBySantriAndTagihan(@PathVariable String userId, @PathVariable String tagihan) {
        return tagihanService.findBySantriIdAndTagihan(userId, tagihan);
    }
    @GetMapping("/santri/{userId}/jenis_tagihan/{tagihan}/status/{status}")
    public List<Tagihan> getTagihanBySantriAndTagihanAndStatus(@PathVariable String userId, @PathVariable String tagihan , @PathVariable String status) {
        return tagihanService.findBySantriIdAndTagihanAndStatus(userId, tagihan , status);
    }
    @GetMapping("/bulan/{bulan}")
    public List<Tagihan> getTagihanByMonth(@PathVariable int bulan , HttpServletRequest requests) {
        String jwtToken = requests.getHeader("auth-tgh").substring(JWT_PREFIX.length());
        return tagihanService.findByMonth(jwtToken,bulan);
    }
    @GetMapping("/jenis_tagihan/{tagihan}/status/{status}")
    public List<Tagihan> getTagihanByTagihan(@PathVariable String tagihan , @PathVariable String status , HttpServletRequest requests) {
        String jwtToken = requests.getHeader("auth-tgh").substring(JWT_PREFIX.length());
        return tagihanService.findByTagihanAndStatus(jwtToken,tagihan , status);
    }
}
