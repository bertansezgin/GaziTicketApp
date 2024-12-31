package org.example.gaziticketapp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "etkinlikler")
public class Etkinlik {


    @Column(name = "fiyat", nullable = false)
    private Double fiyat;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long etkinlikId;

    @Column(name = "etkinlik_adi", nullable = false)
    private String etkinlikAdi;

    @Column(name = "tarih", nullable = false)
    private LocalDate tarih;

    @Column(name = "saat", nullable = false)
    private String saat;

    @Column(name = "yer", nullable = false)
    private String yer;

    @Column(name = "kontenjan", nullable = false)
    private int kontenjan;

    // Getters and Setters
    public Long getEtkinlikId() {
        return etkinlikId;
    }

    public void setEtkinlikId(Long etkinlikId) {
        this.etkinlikId = etkinlikId;
    }

    public String getEtkinlikAdi() {
        return etkinlikAdi;
    }

    public void setEtkinlikAdi(String etkinlikAdi) {
        this.etkinlikAdi = etkinlikAdi;
    }

    public LocalDate getTarih() {
        return tarih;
    }

    public void setTarih(LocalDate tarih) {
        this.tarih = tarih;
    }

    public String getSaat() {
        return saat;
    }

    public void setSaat(String saat) {
        this.saat = saat;
    }

    public String getYer() {
        return yer;
    }

    public void setYer(String yer) {
        this.yer = yer;
    }

    public int getKontenjan() {
        return kontenjan;
    }

    public void setKontenjan(int kontenjan) {
        this.kontenjan = kontenjan;
    }
    public Double getFiyat() {
        return fiyat;
    }

    public void setFiyat(Double fiyat) {
        this.fiyat = fiyat;
    }
}
