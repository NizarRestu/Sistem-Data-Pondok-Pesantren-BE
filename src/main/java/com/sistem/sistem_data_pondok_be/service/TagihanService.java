package com.sistem.sistem_data_pondok_be.service;

import com.sistem.sistem_data_pondok_be.exception.NotFoundException;
import com.sistem.sistem_data_pondok_be.model.Akun;
import com.sistem.sistem_data_pondok_be.model.Tagihan;
import com.sistem.sistem_data_pondok_be.repository.AkunRepository;
import com.sistem.sistem_data_pondok_be.repository.TagihanRepository;
import com.sistem.sistem_data_pondok_be.security.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TagihanService {

    @Autowired
    private TagihanRepository tagihanRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AkunRepository akunRepository;


    public Tagihan addTagihan(Tagihan tagihan , String jwtToken) {
        Claims claims = jwtUtils.decodeJwt(jwtToken);
        String email = claims.getSubject();
        Akun user = akunRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email Not Found"));
        tagihan.setStatus("Belum");
        tagihan.setTransaksi_id(null);
        tagihan.setId_santri(user.getId());
        return tagihanRepository.save(tagihan);
    }

    public Tagihan get(Long id) {
        return tagihanRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
    }

    public List<Tagihan> getAll() {
        return tagihanRepository.findAll();
    }

    public Tagihan edit(Long id, Tagihan tagihan) {
        Tagihan update = tagihanRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
        update.setJenis_tagihan(tagihan.getJenis_tagihan());
        update.setNama_tagihan(tagihan.getNama_tagihan());
        update.setJumlah_tagihan(tagihan.getJumlah_tagihan());
        update.setTotal_tagihan(tagihan.getTotal_tagihan());
        return tagihanRepository.save(update);
    }
    public Map<String, Boolean> delete(Long id) {
        try {
            tagihanRepository.deleteById(id);
            Map<String, Boolean> res = new HashMap<>();
            res.put("Deleted", Boolean.TRUE);
            return res;
        } catch (Exception e) {
            return null;
        }
    }
    public List<Tagihan> getTagihan(String jwtToken) {
        Claims claims = jwtUtils.decodeJwt(jwtToken);
        String email = claims.getSubject();
        Akun user = akunRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email Not Found"));
        return tagihanRepository.findBySantriId(String.valueOf(user.getId()));
    }
}
