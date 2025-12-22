package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "paramcommiss")
public class Paramcommiss {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "mntDepart", nullable = false, precision = 10, scale = 2)
    private double mntDepart;

    @NotNull
    @Column(name = "mntFin", nullable = false, precision = 10, scale = 2)
    private double mntFin;

    @NotNull
    @Column(name = "pourc", nullable = false, precision = 10, scale = 2)
    private double pourc;

}