package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employe")
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "numEmp", nullable = false)
    private int numEmp;

    @NotNull
    @Column(name = "livrerNonLivrerDroit", nullable = false)
    private int livrerNonLivrerDroit;

    @Size(max = 191)
    @NotNull
    @Column(name = "codeEmp", nullable = false, length = 191)
    private String codeEmp;

    @Size(max = 191)
    @NotNull
    @Column(name = "nom", nullable = false, length = 191)
    private String nom;

    @Size(max = 191)
    @NotNull
    @Column(name = "prenom", nullable = false, length = 191)
    private String prenom;

    @Size(max = 191)
    @NotNull
    @Column(name = "login", nullable = false, length = 191)
    private String login;

    @Size(max = 191)
    @NotNull
    @Column(name = "motpass", nullable = false, length = 191)
    private String motpass;

    @Size(max = 191)
    @NotNull
    @Column(name = "email", nullable = false, length = 191)
    private String email;

    @NotNull
    @Column(name = "typeUser", nullable = false)
    private int typeUser;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "etatCompte", nullable = false)
    private boolean etatCompte = false;

    @NotNull
    @Column(name = "validationMnt", nullable = false, precision = 10)
    private double validationMnt;

    @Size(max = 191)
    @NotNull
    @Column(name = "motPassFake", nullable = false, length = 191)
    private String motPassFake;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "commercial", nullable = false)
    private boolean commercial = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "pvLibre", nullable = false)
    private boolean pvLibre = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "gerant", nullable = false)
    private boolean gerant = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "magasinier", nullable = false)
    private boolean magasinier = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "bulltinPaie", nullable = false)
    private boolean bulltinPaie = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "imprimStockSimple", nullable = false)
    private boolean imprimStockSimple = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "livraisonLimite", nullable = false)
    private boolean livraisonLimite = false;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "repertoireId", nullable = false)
    private Repertoire repertoire;

}