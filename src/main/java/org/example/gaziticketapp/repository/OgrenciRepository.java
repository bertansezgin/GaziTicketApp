package org.example.gaziticketapp.repository;

import org.example.gaziticketapp.entity.Ogrenci;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OgrenciRepository extends JpaRepository<Ogrenci, Long> {
    Optional<Ogrenci> findByOgrenciNo(String ogrenciNo);
}
