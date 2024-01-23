package com.sistem.sistem_data_pondok_be.repository;

import com.sistem.sistem_data_pondok_be.model.Tagihan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TagihanRepository extends JpaRepository<Tagihan , Long> {
    @Query(value = "SELECT * FROM tagihan WHERE id_santri = :userId ", nativeQuery = true)
    List<Tagihan> findBySantriId(String userId);
    @Query(value = "SELECT * FROM tagihan WHERE id_santri = :userId AND (status = 'Proses' OR status = 'Selesai') AND id_santri IS NOT NULL", nativeQuery = true)
    List<Tagihan> findByStatus(String userId);

    @Query(value = "SELECT * FROM tagihan WHERE status = 'Proses' OR status = 'Selesai' ", nativeQuery = true)
    List<Tagihan> findByHistory();


    @Query(value = "SELECT * FROM tagihan WHERE id_santri = :userId AND MONTH(created_date) = :bulan", nativeQuery = true)
    List<Tagihan> findBySantriIdAndMonth(@Param("userId") String userId, @Param("bulan") int bulan);

    @Query(value = "SELECT * FROM tagihan WHERE id_santri = :userId AND jenis_tagihan = :tagihan", nativeQuery = true)
    List<Tagihan> findBySantriIdAndJenisTagihan(@Param("userId") String userId, @Param("tagihan") String tagihan);

    @Query(value = "SELECT * FROM tagihan WHERE id_santri = :userId AND jenis_tagihan = :tagihan AND status = :status", nativeQuery = true)
    List<Tagihan> findBySantriIdAndJenisTagihanAndStatus(@Param("userId") String userId, @Param("tagihan") String tagihan , @Param("status") String status);

    @Query(value = "SELECT * FROM tagihan WHERE jenis_tagihan = :tagihan", nativeQuery = true)
    List<Tagihan> findByJenisTagihan(@Param("tagihan") String tagihan);

    @Query(value = "SELECT * FROM tagihan WHERE jenis_tagihan =:tagihan AND status =:status", nativeQuery = true)
    List<Tagihan> findByJenisTagihanAndStatus(@Param("tagihan") String tagihan , @Param("status") String status);

    @Query(value = "SELECT * FROM tagihan WHERE status = 'Proses' ", nativeQuery = true)
    List<Tagihan> findByStatusProses();
    @Query(value = "SELECT * FROM tagihan WHERE status = 'Belum' ", nativeQuery = true)
    List<Tagihan> findByStatusBelum();

    @Query(value = "SELECT * FROM tagihan WHERE MONTH(created_date) = :bulan", nativeQuery = true)
    List<Tagihan> findByMonth(@Param("bulan") int bulan);




}
