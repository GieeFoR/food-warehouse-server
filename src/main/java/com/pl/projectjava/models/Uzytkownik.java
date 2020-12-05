package com.pl.projectjava.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(	name = "uzytkownik",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id"),
                @UniqueConstraint(columnNames = "email")
        })
public class Uzytkownik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 70)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String haslo;

    @NotBlank
    private int poziomUprawnien;

    public Uzytkownik() {
    }

    public Uzytkownik(String email, String haslo, int poziomUprawnien) {
        this.email = email;
        this.haslo = haslo;
        this.poziomUprawnien = poziomUprawnien;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public int getPoziomUprawnien() {
        return poziomUprawnien;
    }

    public void setPoziomUprawnien(int poziomUprawnien) {
        this.poziomUprawnien = poziomUprawnien;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
