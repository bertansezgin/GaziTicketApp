package org.example.gaziticketapp.controller;

import org.example.gaziticketapp.entity.Rol;
import org.example.gaziticketapp.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roller")
public class RolController {

    @Autowired
    private RolRepository rolRepository;

    // Yeni rol ekle
    @PostMapping("/ekle")
    public Rol rolEkle(@RequestBody Rol rol) {
        return rolRepository.save(rol);
    }

    // Tüm rolleri listele
    @GetMapping("/listele")
    public List<Rol> rolListele() {
        return rolRepository.findAll();
    }
}
