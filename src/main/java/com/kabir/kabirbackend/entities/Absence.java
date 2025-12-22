package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "absence")
public class Absence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "dateAbsence", nullable = false)
    private LocalDate dateAbsence;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "matin", nullable = false)
    private boolean matin = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "apresMidi", nullable = false)
    private boolean apresMidi = false;

    @NotNull
    @Column(name = "dateOperation", nullable = false)
    private Instant dateOperation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "personnelOperationId")
    private Personnel personnelOperation;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "personnelId", nullable = false)
    private Personnel personnel;
}