package org.example.gaziticketapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ogrenciler")
public class Ogrenci {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ogrenciId;

    @Column(name = "ad_soyad", nullable = false)
    private String adSoyad;

    @Column(name = "ogrenci_no", nullable = false, unique = true)
    private String ogrenciNo;

    @Column(name = "sifre", nullable = false)
    private String sifre;

    @Column(name = "telefon")
    private String telefon;

    @Column(name = "email")
    private String email;

    // Getters and Setters
    public Long getOgrenciId() {
        return ogrenciId;
    }

    public void setOgrenciId(Long ogrenciId) {
        this.ogrenciId = ogrenciId;
    }

    public String getAdSoyad() {
        return adSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        this.adSoyad = adSoyad;
    }

    public String getOgrenciNo() {
        return ogrenciNo;
    }

    public void setOgrenciNo(String ogrenciNo) {
        this.ogrenciNo = ogrenciNo;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
