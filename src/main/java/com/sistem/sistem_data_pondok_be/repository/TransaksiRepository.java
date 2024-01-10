package com.sistem.sistem_data_pondok_be.repository;


import com.sistem.sistem_data_pondok_be.model.Transaksi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransaksiRepository extends JpaRepository<Transaksi , Long> {
    @Query(value = "SELECT * FROM transaksi WHERE id_santri = :userId ", nativeQuery = true)
    List<Transaksi> findBySantriId(String userId);

    @Query(value = "SELECT * FROM transaksi WHERE id_santri = :userId AND MONTH(created_date) = :bulan", nativeQuery = true)
    List<Transaksi> findBySantriIdAndMonth(@Param("userId") String userId, @Param("bulan") int bulan);

    @Query(value = "SELECT * FROM transaksi WHERE MONTH(created_date) = :bulan", nativeQuery = true)
    List<Transaksi> findByMonth(@Param("bulan") int bulan);
}
