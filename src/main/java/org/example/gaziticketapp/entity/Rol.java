package org.example.gaziticketapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "roller")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rolId;

    @Column(name = "rol_adi", nullable = false, unique = true)
    private String rolAdi;

    // Getters and Setters
    public Long getRolId() {
        return rolId;
    }

    public void setRolId(Long rolId) {
        this.rolId = rolId;
    }

    public String getRolAdi() {
        return rolAdi;
    }

    public void setRolAdi(String rolAdi) {
        this.rolAdi = rolAdi;
    }
}
