package com.sistem.sistem_data_pondok_be.repository;

import com.sistem.sistem_data_pondok_be.model.Tagihan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TagihanRepository extends JpaRepository<Tagihan , Long> {
    @Query(value = "SELECT * FROM tagihan WHERE id_santri = :userId ", nativeQuery = true)
    List<Tagihan> findBySantriId(String userId);
}
