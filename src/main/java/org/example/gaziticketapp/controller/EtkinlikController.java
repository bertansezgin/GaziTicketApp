package org.example.gaziticketapp.controller;

import org.example.gaziticketapp.entity.Etkinlik;
import org.example.gaziticketapp.repository.EtkinlikRepository;
import org.example.gaziticketapp.repository.BiletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/etkinlikler")
public class EtkinlikController {

    @Autowired
    private EtkinlikRepository etkinlikRepository;

    @Autowired
    private BiletRepository biletRepository;

    // Yeni etkinlik ekleme
    @PostMapping("/ekle")
    public Etkinlik etkinlikEkle(@RequestBody Map<String, Object> etkinlikVerisi) {
        String etkinlikAdi = (String) etkinlikVerisi.get("etkinlikAdi");
        String yer = (String) etkinlikVerisi.get("yer");
        String saat = (String) etkinlikVerisi.get("saat");
        int kontenjan = Integer.parseInt(etkinlikVerisi.get("kontenjan").toString());
        Double fiyat = Double.parseDouble(etkinlikVerisi.get("fiyat").toString());
        int gun = Integer.parseInt(etkinlikVerisi.get("gun").toString());
        int ay = Integer.parseInt(etkinlikVerisi.get("ay").toString());
        int yil = Integer.parseInt(etkinlikVerisi.get("yil").toString());

        LocalDate tarih = LocalDate.of(yil, ay, gun);

        Etkinlik etkinlik = new Etkinlik();
        etkinlik.setEtkinlikAdi(etkinlikAdi);
        etkinlik.setYer(yer);
        etkinlik.setTarih(tarih);
        etkinlik.setSaat(saat);
        etkinlik.setKontenjan(kontenjan);
        etkinlik.setFiyat(fiyat);

        return etkinlikRepository.save(etkinlik);
    }

    // Tüm etkinlikleri listeleme
    @GetMapping("/listele")
    public List<Etkinlik> etkinlikListele() {
        return etkinlikRepository.findAll();
    }

    // Etkinlik silme
    @DeleteMapping("/sil/{id}")
    public String etkinlikSil(@PathVariable Long id) {
        etkinlikRepository.deleteById(id);
        return "Etkinlik başarıyla silindi.";
    }

    // Etkinlik güncelleme
    @PutMapping("/guncelle/{id}")
    public Etkinlik etkinlikGuncelle(@PathVariable Long id, @RequestBody Map<String, Object> etkinlikVerisi) {
        Etkinlik mevcutEtkinlik = etkinlikRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Etkinlik bulunamadı"));

        String etkinlikAdi = (String) etkinlikVerisi.get("etkinlikAdi");
        String yer = (String) etkinlikVerisi.get("yer");
        String saat = (String) etkinlikVerisi.get("saat");
        int kontenjan = Integer.parseInt(etkinlikVerisi.get("kontenjan").toString());
        Double fiyat = Double.parseDouble(etkinlikVerisi.get("fiyat").toString());
        int gun = Integer.parseInt(etkinlikVerisi.get("gun").toString());
        int ay = Integer.parseInt(etkinlikVerisi.get("ay").toString());
        int yil = Integer.parseInt(etkinlikVerisi.get("yil").toString());

        LocalDate tarih = LocalDate.of(yil, ay, gun);

        mevcutEtkinlik.setEtkinlikAdi(etkinlikAdi);
        mevcutEtkinlik.setYer(yer);
        mevcutEtkinlik.setSaat(saat);
        mevcutEtkinlik.setKontenjan(kontenjan);
        mevcutEtkinlik.setFiyat(fiyat);
        mevcutEtkinlik.setTarih(tarih);

        return etkinlikRepository.save(mevcutEtkinlik);
    }

    // Etkinlik katılımcıları listeleme
    @GetMapping("/katilimcilar/{etkinlikId}")
    public List<Map<String, Object>> getKatilimcilar(@PathVariable Long etkinlikId) {
        return etkinlikRepository.findKatilimcilarByEtkinlikId(etkinlikId);
    }

    @GetMapping("/{id}/doluluk")
    public ResponseEntity<?> getEtkinlikDoluluk(@PathVariable Long id) {
        try {
            // Önce etkinliği kontrol et
            Etkinlik etkinlik = etkinlikRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Etkinlik bulunamadı"));

            // Geçerli biletlerin sayısını al
            int satilmisbiletSayisi = biletRepository.countByEtkinlikEtkinlikIdAndDurum(id, "Geçerli");
            
            // Doluluk oranını hesapla
            double dolulukOrani = ((double) satilmisbiletSayisi / etkinlik.getKontenjan()) * 100;

            return ResponseEntity.ok(Map.of(
                    "etkinlikId", id,
                    "dolulukOrani", Math.round(dolulukOrani * 100.0) / 100.0
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Doluluk oranı hesaplanırken hata oluştu: " + e.getMessage());
        }
    }
}
