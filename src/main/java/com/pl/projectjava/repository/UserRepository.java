package com.pl.projectjava.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pl.projectjava.models.Uzytkownik;

@Repository
public interface UserRepository extends JpaRepository<Uzytkownik, Long> {
    Optional<Uzytkownik> findByEmail(String email);

    Boolean existsByEmail(String email);

}