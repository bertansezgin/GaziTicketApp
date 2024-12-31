package org.example.gaziticketapp.repository;

import org.example.gaziticketapp.entity.Bilet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BiletRepository extends JpaRepository<Bilet, Long> {

    // Belirli bir öğrenciye ait biletleri listelemek
    List<Bilet> findAllByOgrenciOgrenciId(Long ogrenciId);

    // Admin Bilet Görünümü üzerinden katılımcıları listelemek
    // Admin Bilet Görünümü üzerinden katılımcıları listelemek
    @Query(value = "SELECT * FROM admin_bilet_gorunum WHERE \"etkinlikId\" = :etkinlikId AND durum = 'Geçerli'",
           nativeQuery = true)
    List<Map<String, Object>> findKatilimcilarByEtkinlikId(@Param("etkinlikId") Long etkinlikId);

    int countByEtkinlikEtkinlikIdAndDurum(Long etkinlikId, String durum);
}
