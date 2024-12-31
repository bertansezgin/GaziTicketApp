package org.example.gaziticketapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.example.gaziticketapp.entity.Bilet;
import org.example.gaziticketapp.entity.Etkinlik;
import org.example.gaziticketapp.entity.Ogrenci;
import org.example.gaziticketapp.repository.BiletRepository;
import org.example.gaziticketapp.repository.EtkinlikRepository;
import org.example.gaziticketapp.repository.OgrenciRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/biletler")
public class BiletController {

    @Autowired
    private BiletRepository biletRepository;

    @Autowired
    private OgrenciRepository ogrenciRepository;

    @Autowired
    private EtkinlikRepository etkinlikRepository;

    @Transactional
    @PostMapping("/ekle")
    public Bilet biletEkle(@RequestBody Map<String, Object> biletVerisi) {
        Long ogrenciId = Long.valueOf(biletVerisi.get("ogrenciId").toString());
        Long etkinlikId = Long.valueOf(biletVerisi.get("etkinlikId").toString());
        Double fiyat = Double.valueOf(biletVerisi.get("fiyat").toString());

        // Öğrenci kontrolü
        Ogrenci ogrenci = ogrenciRepository.findById(ogrenciId)
                .orElseThrow(() -> new RuntimeException("Öğrenci bulunamadı!"));

        // Etkinlik kontrolü
        Etkinlik etkinlik = etkinlikRepository.findById(etkinlikId)
                .orElseThrow(() -> new RuntimeException("Etkinlik bulunamadı!"));

        // Kontenjan kontrolü
        if (etkinlik.getKontenjan() <= 0) {
            throw new RuntimeException("Etkinlik kontenjanı dolmuş!");
        }

        // Kontenjanı düşür
        etkinlik.setKontenjan(etkinlik.getKontenjan() - 1);
        etkinlikRepository.save(etkinlik);

        // Bilet oluşturma
        Bilet bilet = new Bilet();
        bilet.setOgrenci(ogrenci);
        bilet.setEtkinlik(etkinlik);
        bilet.setBiletTarihi(new Date());
        bilet.setFiyat(fiyat);
        bilet.setDurum("Geçerli");

        return biletRepository.save(bilet);
    }

    @GetMapping("/listele/{ogrenciId}")
    public List<Bilet> ogrenciBiletListele(@PathVariable Long ogrenciId) {
        return biletRepository.findAllByOgrenciOgrenciId(ogrenciId);
    }

    // Tüm biletleri listelemek için
    @GetMapping("/listele")
    public List<Bilet> biletListele() {
        return biletRepository.findAll();
    }

    // Bilet iptal etmek için
    @DeleteMapping("/sil/{id}")
    public String biletSil(@PathVariable Long id) {
        Bilet bilet = biletRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bilet bulunamadı!"));
        bilet.setDurum("İptal Edildi");
        biletRepository.save(bilet);
        return "Bilet başarıyla iptal edildi.";
    }

    @GetMapping("/etkinlik-katilimcilar/{etkinlikId}")
    public List<Map<String, Object>> getKatilimcilar(@PathVariable Long etkinlikId) {
        return biletRepository.findKatilimcilarByEtkinlikId(etkinlikId);
    }

    @PutMapping("/iptal/{id}")
    public ResponseEntity<?> biletIptal(@PathVariable Long id) {
        try {
            // Bileti bul
            Bilet bilet = biletRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Bilet bulunamadı!"));

            // Bilet zaten iptal edilmişse hata fırlat
            if ("İptal Edildi".equals(bilet.getDurum())) {
                return ResponseEntity.badRequest()
                        .body("Bu bilet zaten iptal edilmiş!");
            }

            // Bileti iptal et
            bilet.setDurum("İptal Edildi");
            biletRepository.save(bilet);

            return ResponseEntity.ok("Bilet başarıyla iptal edildi.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Bilet iptal edilirken bir hata oluştu: " + e.getMessage());
        }
    }
}
