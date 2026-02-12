package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "etablissement")
public class Etablissement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 191)
    @NotNull
    @Column(name = "nom", nullable = false, length = 191)
    private String nom;

    @Size(max = 191)
    @NotNull
    @Column(name = "cheminBD", nullable = false, length = 191)
    private String cheminBD;

    @Size(max = 191)
    @NotNull
    @Column(name = "tel1", nullable = false, length = 191)
    private String tel1;

    @Size(max = 191)
    @NotNull
    @Column(name = "tel2", nullable = false, length = 191)
    private String tel2;

    @Size(max = 191)
    @NotNull
    @Column(name = "tel3", nullable = false, length = 191)
    private String tel3;

    @Size(max = 191)
    @NotNull
    @Column(name = "fax", nullable = false, length = 191)
    private String fax;

    @Size(max = 191)
    @NotNull
    @Column(name = "gsm", nullable = false, length = 191)
    private String gsm;

    @Size(max = 191)
    @NotNull
    @Column(name = "email", nullable = false, length = 191)
    private String email;

    @Size(max = 191)
    @NotNull
    @Column(name = "siteweb", nullable = false, length = 191)
    private String siteweb;

    @Size(max = 191)
    @NotNull
    @Column(name = "cnss", nullable = false, length = 191)
    private String cnss;

    @Size(max = 191)
    @NotNull
    @Column(name = "patente", nullable = false, length = 191)
    private String patente;

    @Size(max = 191)
    @NotNull
    @Column(name = "adresse", nullable = false, length = 191)
    private String adresse;

    @Size(max = 191)
    @NotNull
    @Column(name = "raisonSocial", nullable = false, length = 191)
    private String raisonSocial;

    @Size(max = 191)
    @NotNull
    @Column(name = "ice", nullable = false, length = 191)
    private String ice;

    @Size(max = 191)
    @NotNull
    @Column(name = "ife", nullable = false, length = 191)
    private String ife;

    @Size(max = 191)
    @NotNull
    @Column(name = "rc", nullable = false, length = 191)
    private String rc;

    @NotNull
    @Column(name = "port", nullable = false)
    private int port;

    @Size(max = 191)
    @NotNull
    @Column(name = "hostMail", nullable = false, length = 191)
    private String hostMail;

    @Size(max = 191)
    @NotNull
    @Column(name = "paswordMail", nullable = false, length = 191)
    private String paswordMail;

    @Size(max = 191)
    @NotNull
    @Column(name = "paswordMailFake", nullable = false, length = 191)
    private String paswordMailFake;

    @Size(max = 191)
    @NotNull
    @Column(name = "fromMail", nullable = false, length = 191)
    private String fromMail;

    @Size(max = 191)
    @NotNull
    @Column(name = "userMail", nullable = false, length = 191)
    private String userMail;

    @NotNull
    @Column(name = "capitale", nullable = false)
    private int capitale;

    @Column(name = "pourcentageLiv")
    private String pourcentageLiv;

    @Size(max = 191)
    @NotNull
    @Column(name = "lienDbDump", nullable = false, length = 191)
    private String lienDbDump;

    @Size(max = 191)
    @NotNull
    @Column(name = "lienBackupDB", nullable = false, length = 191)
    private String lienBackupDB;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "lundi", nullable = false)
    private boolean lundi = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "mardi", nullable = false)
    private boolean mardi = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "mercredi", nullable = false)
    private boolean mercredi = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "jeudi", nullable = false)
    private boolean jeudi = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "vendredi", nullable = false)
    private boolean vendredi = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "samedi", nullable = false)
    private boolean samedi = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "dimanche", nullable = false)
    private boolean dimanche = false;

    @NotNull
    @Column(name = "dateTime", nullable = false)
    private Instant dateTime;

    @NotNull
    @Column(name = "typeExec", nullable = false)
    private int typeExec;

    @NotNull
    @Column(name = "numJour", nullable = false)
    private int numJour;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "villeId")
    private Ville ville;

}