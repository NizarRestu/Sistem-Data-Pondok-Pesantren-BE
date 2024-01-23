package com.sistem.sistem_data_pondok_be.service;

import com.sistem.sistem_data_pondok_be.exception.BadRequestException;
import com.sistem.sistem_data_pondok_be.exception.NotFoundException;
import com.sistem.sistem_data_pondok_be.model.Akun;
import com.sistem.sistem_data_pondok_be.model.Tagihan;
import com.sistem.sistem_data_pondok_be.model.Transaksi;
import com.sistem.sistem_data_pondok_be.repository.AkunRepository;
import com.sistem.sistem_data_pondok_be.repository.TagihanRepository;
import com.sistem.sistem_data_pondok_be.repository.TransaksiRepository;
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
    private TransaksiRepository transaksiRepository;
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AkunRepository akunRepository;


    public Tagihan addTagihan(Tagihan tagihan , String jwtToken) {
        Akun user = akunRepository.findById(tagihan.getId_santri()).orElseThrow(() -> new NotFoundException("Email Not Found"));
        tagihan.setStatus("Belum");
        tagihan.setTransaksi_id(null);
        tagihan.setId_santri(user.getId());
        tagihan.setNama(user.getNama());
        return tagihanRepository.save(tagihan);
    }

    public Tagihan get(Long id) {
        return tagihanRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
    }

    public List<Tagihan> getAll(String jwtToken) {
        Claims claims = jwtUtils.decodeJwt(jwtToken);
        String email = claims.getSubject();
        Akun user = akunRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email Not Found"));
        if (user.getRole().equals("Pengurus")){
        return tagihanRepository.findAll();
        }
        throw new BadRequestException("API ini hanya bisa di akses oleh pengurus");
    }
    public List<Tagihan> getHistory(String jwtToken) {
        Claims claims = jwtUtils.decodeJwt(jwtToken);
        String email = claims.getSubject();
        Akun user = akunRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email Not Found"));
        if (user.getRole().equals("Pengurus")){
        return tagihanRepository.findByHistory();
        }
        throw new BadRequestException("API ini hanya bisa di akses oleh pengurus");
    }

    public Tagihan edit(Long id, Tagihan tagihan) {
        Tagihan update = tagihanRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
        update.setJenis_tagihan(tagihan.getJenis_tagihan());
        update.setNama_tagihan(tagihan.getNama_tagihan());
        update.setTotal_tagihan(tagihan.getTotal_tagihan());
        return tagihanRepository.save(update);
    }
    public Tagihan lolos(Long id) {
        Tagihan update = tagihanRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
       update.setStatus("Selesai");
       Long idTransaksi = update.getTransaksi_id();
        Transaksi transaksi = transaksiRepository.findById(idTransaksi).orElseThrow(() -> new NotFoundException("Id Not Found"));
        transaksi.setStatus("Berhasil");
        transaksiRepository.save(transaksi);
        return tagihanRepository.save(update);
    }
    public Tagihan gagal(Long id) {
        Tagihan update = tagihanRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
       update.setStatus("Belum");
       Long idTransaksi = update.getTransaksi_id();
        Transaksi transaksi = transaksiRepository.findById(idTransaksi).orElseThrow(() -> new NotFoundException("Id Not Found"));
        transaksi.setStatus("Gagal");
        transaksiRepository.save(transaksi);
        return tagihanRepository.save(update);
    }
    public Tagihan hapusId(Long id) {
        Tagihan update = tagihanRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
        update.setId_santri(null);
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
    public List<Tagihan> getTagihanByStatus(String jwtToken) {
        Claims claims = jwtUtils.decodeJwt(jwtToken);
        String email = claims.getSubject();
        Akun user = akunRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email Not Found"));
        return tagihanRepository.findByStatus(String.valueOf(user.getId()));
    }

    public List<Tagihan> getTagihanByStatusProses(String jwtToken) {
        Claims claims = jwtUtils.decodeJwt(jwtToken);
        String email = claims.getSubject();
        Akun user = akunRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email Not Found"));
        if (user.getRole().equals("Pengurus")){
        return tagihanRepository.findByStatusProses();
        }
        throw new BadRequestException("API ini hanya bisa di akses oleh pengurus");
    }
    public List<Tagihan> getTagihanByStatusBelum(String jwtToken) {
        Claims claims = jwtUtils.decodeJwt(jwtToken);
        String email = claims.getSubject();
        Akun user = akunRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email Not Found"));
        if (user.getRole().equals("Pengurus")){
        return tagihanRepository.findByStatusBelum();
        }
        throw new BadRequestException("API ini hanya bisa di akses oleh pengurus");
    }
    public List<Tagihan> findBySantriIdAndMonth(String userId, int bulan) {
        return tagihanRepository.findBySantriIdAndMonth(userId,bulan);
    }
    public List<Tagihan> findBySantriIdAndTagihan(String userId, String tagihan) {
        return tagihanRepository.findBySantriIdAndJenisTagihan(userId,tagihan);
    }
    public List<Tagihan> findBySantriIdAndTagihanAndStatus(String userId, String tagihan , String status) {
        return tagihanRepository.findBySantriIdAndJenisTagihanAndStatus(userId,tagihan,status);
    }
    public List<Tagihan> findByMonth(String jwtToken, int bulan) {
        Claims claims = jwtUtils.decodeJwt(jwtToken);
        String email = claims.getSubject();
        Akun user = akunRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email Not Found"));
        if (user.getRole().equals("Pengurus")){
        return tagihanRepository.findByMonth(bulan);
        }
        throw new BadRequestException("API ini hanya bisa di akses oleh pengurus");
    }
    public List<Tagihan> findByTagihanAndStatus(String jwtToken, String tagihan , String status) {
        Claims claims = jwtUtils.decodeJwt(jwtToken);
        String email = claims.getSubject();
        Akun user = akunRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email Not Found"));
        if (user.getRole().equals("Pengurus")){
        return tagihanRepository.findByJenisTagihanAndStatus(tagihan ,status);
        }
        throw new BadRequestException("API ini hanya bisa di akses oleh pengurus");
    }
}
