package com.sistem.sistem_data_pondok_be.repository;

import com.sistem.sistem_data_pondok_be.model.Akun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AkunRepository extends JpaRepository<Akun, Long> {
    Optional<Akun> findByUsername(String username);

    Boolean existsByUsername(String username);
}
