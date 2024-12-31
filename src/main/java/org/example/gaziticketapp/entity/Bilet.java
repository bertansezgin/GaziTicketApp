package org.example.gaziticketapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "biletler")
public class Bilet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long biletId;

    @ManyToOne
    @JoinColumn(name = "ogrenci_id", nullable = false)
    @NotNull(message = "Öğrenci bilgisi boş olamaz!")
    private Ogrenci ogrenci;

    @ManyToOne
    @JoinColumn(name = "etkinlik_id", nullable = false)
    @NotNull(message = "Etkinlik bilgisi boş olamaz!")
    private Etkinlik etkinlik;

    @Column(name = "bilet_tarihi", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message = "Bilet tarihi boş olamaz!")
    private Date biletTarihi;

    @Column(name = "fiyat", nullable = false)
    @Min(value = 1, message = "Fiyat 1'den küçük olamaz!")
    @NotNull(message = "Fiyat boş olamaz!")
    private Double fiyat;

    @Column(name = "durum", nullable = false)
    @NotNull(message = "Durum boş olamaz!")
    private String durum; // Örnek: "Geçerli", "İptal Edildi"

    // Getters ve Setters
    public Long getBiletId() {
        return biletId;
    }

    public void setBiletId(Long biletId) {
        this.biletId = biletId;
    }

    public Ogrenci getOgrenci() {
        return ogrenci;
    }

    public void setOgrenci(Ogrenci ogrenci) {
        this.ogrenci = ogrenci;
    }

    public Etkinlik getEtkinlik() {
        return etkinlik;
    }

    public void setEtkinlik(Etkinlik etkinlik) {
        this.etkinlik = etkinlik;
    }

    public Date getBiletTarihi() {
        return biletTarihi;
    }

    public void setBiletTarihi(Date biletTarihi) {
        this.biletTarihi = biletTarihi;
    }

    public Double getFiyat() {
        return fiyat;
    }

    public void setFiyat(Double fiyat) {
        this.fiyat = fiyat;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }
}
