package com.sistem.sistem_data_pondok_be.model;

import com.sistem.sistem_data_pondok_be.auditing.DateConfig;

import javax.persistence.*;

@Entity
@Table(name = "tagihan")
public class Tagihan extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nama_tagihan")
    private String nama_tagihan;

    @Column(name = "jumlah_tagihan")
    private Integer jumlah_tagihan;

    @Column(name = "total_tagihan")
    private Integer total_tagihan;

    @Column(name = "jenis_tagihan")
    private String jenis_tagihan;

    @Column(name = "id_santri")
    private Long id_santri;

    @Column(name = "status")
    private String status;

    @Column(name = "transaksi_id")
    private Long transaksi_id;

    public String getJenis_tagihan() {
        return jenis_tagihan;
    }

    public void setJenis_tagihan(String jenis_tagihan) {
        this.jenis_tagihan = jenis_tagihan;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama_tagihan() {
        return nama_tagihan;
    }

    public void setNama_tagihan(String nama_tagihan) {
        this.nama_tagihan = nama_tagihan;
    }

    public Integer getJumlah_tagihan() {
        return jumlah_tagihan;
    }

    public void setJumlah_tagihan(Integer jumlah_tagihan) {
        this.jumlah_tagihan = jumlah_tagihan;
    }

    public Integer getTotal_tagihan() {
        return total_tagihan;
    }

    public void setTotal_tagihan(Integer total_tagihan) {
        this.total_tagihan = total_tagihan;
    }

    public Long getId_santri() {
        return id_santri;
    }

    public void setId_santri(Long id_santri) {
        this.id_santri = id_santri;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTransaksi_id() {
        return transaksi_id;
    }

    public void setTransaksi_id(Long transaksi_id) {
        this.transaksi_id = transaksi_id;
    }
}
