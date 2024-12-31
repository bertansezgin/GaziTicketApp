package org.example.gaziticketapp.controller;

import org.example.gaziticketapp.entity.Ogrenci;
import org.example.gaziticketapp.repository.OgrenciRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ogrenciler")
public class OgrenciController {

    @Autowired
    private OgrenciRepository ogrenciRepository;

    // Yeni öğrenci ekle
    @PostMapping("/ekle")
    public Ogrenci ogrenciEkle(@RequestBody Ogrenci ogrenci) {
        return ogrenciRepository.save(ogrenci);
    }

    // Tüm öğrencileri listele
    @GetMapping("/listele")
    public List<Ogrenci> ogrenciListele() {
        return ogrenciRepository.findAll();
    }

    // Öğrenci giriş işlemi
    @PostMapping("/giris")
    public ResponseEntity<?> ogrenciGiris(@RequestBody Map<String, String> bilgiler) {
        String ogrenciNo = bilgiler.get("ogrenciNo");
        String sifre = bilgiler.get("sifre");

        try {
            Ogrenci ogrenci = ogrenciRepository.findByOgrenciNo(ogrenciNo)
                    .orElseThrow(() -> new RuntimeException("Öğrenci bulunamadı!"));

            if (!ogrenci.getSifre().equals(sifre)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Geçersiz şifre!");
            }

            return ResponseEntity.ok(ogrenci); // Öğrenci bilgilerini döner
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hata: " + e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOgrenciById(@PathVariable Long id) {
        try {
            Ogrenci ogrenci = ogrenciRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Öğrenci bulunamadı!"));

            return ResponseEntity.ok(ogrenci);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Öğrenci bilgileri getirilirken bir hata oluştu: " + e.getMessage());
        }
    }

    @PutMapping("/guncelle")
    public ResponseEntity<?> ogrenciGuncelle(@RequestBody Map<String, String> bilgiler) {
        try {
            Long ogrenciId = Long.valueOf(bilgiler.get("ogrenciId"));
            Ogrenci ogrenci = ogrenciRepository.findById(ogrenciId)
                    .orElseThrow(() -> new RuntimeException("Öğrenci bulunamadı!"));

            // Gelen bilgileri güncelle
            ogrenci.setAdSoyad(bilgiler.get("ad") + " " + bilgiler.get("soyad"));
            ogrenci.setEmail(bilgiler.get("email"));
            ogrenci.setTelefon(bilgiler.get("telefon"));

            // Eğer yeni şifre geldiyse güncelle
            String yeniSifre = bilgiler.get("sifre");
            if (yeniSifre != null && !yeniSifre.trim().isEmpty()) {
                ogrenci.setSifre(yeniSifre);
            }

            // Güncellenen öğrenciyi kaydet
            ogrenciRepository.save(ogrenci);
            
            return ResponseEntity.ok("Profil bilgileri başarıyla güncellendi.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Güncelleme sırasında bir hata oluştu: " + e.getMessage());
        }
    }
}
