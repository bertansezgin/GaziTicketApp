package org.example.gaziticketapp.repository;

import org.example.gaziticketapp.entity.Etkinlik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface EtkinlikRepository extends JpaRepository<Etkinlik, Long> {

    @Query(value = "SELECT o.ogrenci_no AS ogrenciNo, " +
            "o.ad_soyad AS adSoyad, " +
            "o.email AS email, " +
            "b.bilet_id AS biletId, " +
            "b.bilet_tarihi AS biletTarihi, " +
            "e.etkinlik_adi AS etkinlikAdi " +
            "FROM biletler b " +
            "JOIN ogrenciler o ON b.ogrenci_id = o.ogrenci_id " +
            "JOIN etkinlikler e ON b.etkinlik_id = e.etkinlik_id " +
            "WHERE b.etkinlik_id = :etkinlikId", nativeQuery = true)
    List<Map<String, Object>> findKatilimcilarByEtkinlikId(@Param("etkinlikId") Long etkinlikId);

    @Query(value = "CALL calculate_event_occupancy(:etkinlikId, :dolulukOrani);", nativeQuery = true)
    void calculateEventOccupancy(@Param("etkinlikId") Long etkinlikId, @Param("dolulukOrani") Double dolulukOrani);
}