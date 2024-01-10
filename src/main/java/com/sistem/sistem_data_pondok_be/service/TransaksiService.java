package com.sistem.sistem_data_pondok_be.service;

import com.sistem.sistem_data_pondok_be.exception.BadRequestException;
import com.sistem.sistem_data_pondok_be.exception.InternalErrorException;
import com.sistem.sistem_data_pondok_be.exception.NotFoundException;
import com.sistem.sistem_data_pondok_be.model.Akun;
import com.sistem.sistem_data_pondok_be.model.Tagihan;
import com.sistem.sistem_data_pondok_be.model.Transaksi;
import com.sistem.sistem_data_pondok_be.repository.AkunRepository;
import com.sistem.sistem_data_pondok_be.repository.TagihanRepository;
import com.sistem.sistem_data_pondok_be.repository.TransaksiRepository;
import com.sistem.sistem_data_pondok_be.security.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransaksiService {
    @Autowired
    private TransaksiRepository transaksiRepository;

    @Autowired
    private AkunRepository akunRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private TagihanRepository tagihanRepository;


    private String convertToBase64Url(MultipartFile file) {
        String url = "";
        try {
            byte[] byteData = Base64.encodeBase64(file.getBytes());
            String result = new String(byteData);
            url = "data:" + file.getContentType() + ";base64," + result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return url;
        }

    }


    public Map<String, Object> add(Transaksi transaksi, String jwtToken, MultipartFile multipartFile) {
        try {
            Claims claims = jwtUtils.decodeJwt(jwtToken);
            String email = claims.getSubject();
            Akun user = akunRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email Not Found"));

            // Konversi gambar ke format base64
            transaksi.setImage(convertToBase64Url(multipartFile));
            transaksi.setStatus("Proses");
            transaksi.setTagihan_id(transaksi.getTagihan_id());
            transaksi.setId_santri(user.getId());

            // Simpan transaksi
            transaksiRepository.save(transaksi);

            // Ambil tagihan terkait
            Tagihan tagihan = tagihanRepository.findById(transaksi.getTagihan_id())
                    .orElseThrow(() -> new NotFoundException("Tagihan Not Found"));

            // Periksa apakah tagihan sudah diproses sebelumnya
            if (!"Proses".equals(tagihan.getStatus())) {
                // Jika belum diproses, lakukan pembaruan
                tagihan.setStatus("Proses");
                tagihan.setTransaksi_id(transaksi.getId());

                // Simpan perubahan tagihan
                tagihanRepository.save(tagihan);

                // Persiapkan respons
                Map<String, Object> res = new HashMap<>();
                res.put("transaksi", "Sedang Di Proses");
                res.put("nama_tagihan", tagihan.getNama_tagihan());
                res.put("total_tagihan", tagihan.getTotal_tagihan());
                res.put("jenis_tagihan", tagihan.getJenis_tagihan());
                return res;
            } else {
                // Jika sudah diproses sebelumnya, lemparkan pengecualian atau berikan respons sesuai
                throw new IllegalStateException("Tagihan sudah diproses sebelumnya.");
            }
        } catch (Exception e) {
            // Tangani kesalahan secara umum
            e.printStackTrace(); // Gantilah ini dengan penanganan kesalahan yang lebih baik
            throw new InternalErrorException("Gagal menambahkan transaksi");
        }
    }

    public List<Transaksi> getTransaksi(String jwtToken) {
        Claims claims = jwtUtils.decodeJwt(jwtToken);
        String email = claims.getSubject();
        Akun user = akunRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email Not Found"));
        return transaksiRepository.findBySantriId(String.valueOf(user.getId()));
    }
    public Map<String, Boolean> delete(Long id) {
        try {
            transaksiRepository.deleteById(id);
            Map<String, Boolean> res = new HashMap<>();
            res.put("Deleted", Boolean.TRUE);
            return res;
        } catch (Exception e) {
            return null;
        }
    }
    public Transaksi get(Long id) {
        return transaksiRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
    }
    public List<Transaksi> getAll(String jwtToken) {
        Claims claims = jwtUtils.decodeJwt(jwtToken);
        String email = claims.getSubject();
        Akun user = akunRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email Not Found"));
        if (user.getRole().equals("Pengurus")){
        return transaksiRepository.findAll();
        }
        throw new BadRequestException("API ini hanya bisa di akses oleh pengurus");
    }
    public List<Transaksi> findBySantriIdAndMonth(String jwtToken, int bulan) {
        Claims claims = jwtUtils.decodeJwt(jwtToken);
        String email = claims.getSubject();
        Akun user = akunRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email Not Found"));
        return transaksiRepository.findBySantriIdAndMonth(String.valueOf(user.getId()), bulan);
    }

    public List<Transaksi> findByMonth(String jwtToken, int bulan) {
        Claims claims = jwtUtils.decodeJwt(jwtToken);
        String email = claims.getSubject();
        Akun user = akunRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email Not Found"));
        if (user.getRole().equals("Pengurus")){
            return transaksiRepository.findByMonth(bulan);
        }
        throw new BadRequestException("API ini hanya bisa di akses oleh pengurus");
    }
}
