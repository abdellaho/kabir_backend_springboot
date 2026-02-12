package com.kabir.kabirbackend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.kabir.kabirbackend.entities.Etablissement}
 */
@Data
public class EtablissementDTO implements Serializable {
    Long id;
    @NotNull
    @Size(max = 191)
    String nom;
    @NotNull
    @Size(max = 191)
    String cheminBD;
    @NotNull
    @Size(max = 191)
    String tel1;
    @NotNull
    @Size(max = 191)
    String tel2;
    @NotNull
    @Size(max = 191)
    String tel3;
    @NotNull
    @Size(max = 191)
    String fax;
    @NotNull
    @Size(max = 191)
    String gsm;
    @NotNull
    @Size(max = 191)
    String email;
    @NotNull
    @Size(max = 191)
    String siteweb;
    @NotNull
    @Size(max = 191)
    String cnss;
    @NotNull
    @Size(max = 191)
    String patente;
    @NotNull
    @Size(max = 191)
    String adresse;
    @NotNull
    @Size(max = 191)
    String raisonSocial;
    @NotNull
    @Size(max = 191)
    String ice;
    @NotNull
    @Size(max = 191)
    String ife;
    @NotNull
    @Size(max = 191)
    String rc;
    @NotNull
    Integer port;
    @NotNull
    @Size(max = 191)
    String hostMail;
    @NotNull
    @Size(max = 191)
    String paswordMail;
    @NotNull
    @Size(max = 191)
    String paswordMailFake;
    @NotNull
    @Size(max = 191)
    String fromMail;
    @NotNull
    @Size(max = 191)
    String userMail;
    @NotNull
    Integer capitale;
    String pourcentageLiv;
    @NotNull
    @Size(max = 191)
    String lienDbDump;
    @NotNull
    @Size(max = 191)
    String lienBackupDB;
    @NotNull
    Boolean lundi;
    @NotNull
    Boolean mardi;
    @NotNull
    Boolean mercredi;
    @NotNull
    Boolean jeudi;
    @NotNull
    Boolean vendredi;
    @NotNull
    Boolean samedi;
    @NotNull
    Boolean dimanche;
    @NotNull
    Instant dateTime;
    @NotNull
    Integer typeExec;
    @NotNull
    Integer numJour;
    Long villeId;
    String villeNomVille;
}