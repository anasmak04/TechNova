package com.technova.usermanagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String firstName;

    @Column(name = "identification_document", nullable = false, unique = true)
    private String identificationDocument;

    @Column(nullable = false)
    private String nationality;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    @PrePersist
    protected void onCreate() {
        registrationDate = LocalDate.now();
        expirationDate = registrationDate.plusYears(1);
    }
}