package org.example.gaziticketapp.controller;

import org.example.gaziticketapp.entity.Kullanici;
import org.example.gaziticketapp.entity.Rol;
import org.example.gaziticketapp.repository.KullaniciRepository;
import org.example.gaziticketapp.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/kullanicilar")
public class KullaniciController {

    @Autowired
    private KullaniciRepository kullaniciRepository;

    @Autowired
    private RolRepository rolRepository;

    // Yeni kullanıcı kaydı
    @PostMapping("/kayit")
    public ResponseEntity<?> kullaniciKayit(@RequestBody Kullanici kullanici) {
        // Kullanıcı adı benzersiz olmalı
        if (kullaniciRepository.findByKullaniciAdi(kullanici.getKullaniciAdi()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Kullanıcı adı zaten mevcut!");
        }

        // Varsayılan rol ataması (örnek: "USER")
        Optional<Rol> varsayilanRol = rolRepository.findByRolAdi("USER");
        if (varsayilanRol.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Varsayılan rol bulunamadı!");
        }

        // Rol eklenerek kullanıcı kaydedilir
        kullanici.setRol(varsayilanRol.get());
        Kullanici yeniKullanici = kullaniciRepository.save(kullanici);
        return ResponseEntity.ok(yeniKullanici);
    }

    // Kullanıcı giriş işlemi
    @PostMapping("/giris")
    public ResponseEntity<?> kullaniciGiris(@RequestBody Map<String, String> bilgiler) {
        String kullaniciAdi = bilgiler.get("kullaniciAdi");
        String sifre = bilgiler.get("sifre");

        try {
            Kullanici kullanici = kullaniciRepository.findByKullaniciAdi(kullaniciAdi)
                    .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı!"));

            if (!kullanici.getSifre().equals(sifre)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Geçersiz şifre!");
            }

            // Başarılı giriş durumunda bir yanıt döndür
            return ResponseEntity.ok("Giriş başarılı! Hoş geldiniz " + kullaniciAdi);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hata: " + e.getMessage());
        }
    }
    // Tüm kullanıcıları listele
    @GetMapping("/listele")
    public List<Kullanici> kullaniciListele() {
        return kullaniciRepository.findAll();
    }

    // Kullanıcı sil
    @DeleteMapping("/sil/{id}")
    public String kullaniciSil(@PathVariable Long id) {
        kullaniciRepository.deleteById(id);
        return "Kullanıcı başarıyla silindi.";
    }
}
