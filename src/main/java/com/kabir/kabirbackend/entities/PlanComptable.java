package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "plancomptable")
public class PlanComptable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private int code;

    @Size(max = 191)
    @NotNull
    @Column(name = "designation", nullable = false, length = 191)
    private String designation;

    @Size(max = 191)
    @NotNull
    @Column(name = "nature", nullable = false, length = 191)
    private String nature;

    @Size(max = 191)
    @NotNull
    @Column(name = "formule", nullable = false, length = 191)
    private String formule;

    @Size(max = 191)
    @NotNull
    @Column(name = "codeString", nullable = false, length = 191)
    private String codeString;

}