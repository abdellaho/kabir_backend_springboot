package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comptecaisse")
public class CompteCaisse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Size(max = 191)
    @NotNull
    @Column(name = "designation", nullable = false, length = 191)
    private String designation;

    @Size(max = 191)
    @NotNull
    @Column(name = "numFacture", nullable = false, length = 191)
    private String numFacture;

    @NotNull
    @Column(name = "montant", nullable = false, precision = 10)
    private double montant;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "compteCaisse", nullable = false)
    private boolean compteCaisse;

}