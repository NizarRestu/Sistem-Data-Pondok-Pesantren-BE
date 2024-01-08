package com.sistem.sistem_data_pondok_be.model;

import com.sistem.sistem_data_pondok_be.auditing.DateConfig;

import javax.persistence.*;

@Entity
@Table(name = "transaksi")
public class Transaksi extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tagihan_id")
    private Long tagihan_id;

    @Lob
    @Column(name = "image")
    private String image;


    @Column(name = "id_santri")
    private Long id_santri;

    @Lob
    @Column(name = "keterangan")
    private String keterangan;

    @Column(name = "status")
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTagihan_id() {
        return tagihan_id;
    }

    public void setTagihan_id(Long tagihan_id) {
        this.tagihan_id = tagihan_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
