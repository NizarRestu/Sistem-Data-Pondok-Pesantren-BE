package com.sistem.sistem_data_pondok_be.repository;

import com.sistem.sistem_data_pondok_be.model.Akun;
import com.sistem.sistem_data_pondok_be.model.Tagihan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AkunRepository extends JpaRepository<Akun, Long> {
    Optional<Akun> findByEmail(String email);
    @Query(value = "SELECT * FROM akun WHERE role = 'Santri' ", nativeQuery = true)
    List<Akun> findBySantri();

    Boolean existsByEmail(String email);
}
