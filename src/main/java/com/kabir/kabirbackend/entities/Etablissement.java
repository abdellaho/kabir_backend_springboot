package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.Instant;

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
    private Integer port;

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
    private Integer capitale;

    @NotNull
    @Column(name = "pourcentageLiv", nullable = false, precision = 10, scale = 2)
    private BigDecimal pourcentageLiv;

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
    private Boolean lundi = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "mardi", nullable = false)
    private Boolean mardi = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "mercredi", nullable = false)
    private Boolean mercredi = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "jeudi", nullable = false)
    private Boolean jeudi = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "vendredi", nullable = false)
    private Boolean vendredi = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "samedi", nullable = false)
    private Boolean samedi = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "dimanche", nullable = false)
    private Boolean dimanche = false;

    @NotNull
    @Column(name = "dateTime", nullable = false)
    private Instant dateTime;

    @NotNull
    @Column(name = "typeExec", nullable = false)
    private Integer typeExec;

    @NotNull
    @Column(name = "numJour", nullable = false)
    private Integer numJour;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "villeId")
    private Ville ville;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCheminBD() {
        return cheminBD;
    }

    public void setCheminBD(String cheminBD) {
        this.cheminBD = cheminBD;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getTel3() {
        return tel3;
    }

    public void setTel3(String tel3) {
        this.tel3 = tel3;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getGsm() {
        return gsm;
    }

    public void setGsm(String gsm) {
        this.gsm = gsm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSiteweb() {
        return siteweb;
    }

    public void setSiteweb(String siteweb) {
        this.siteweb = siteweb;
    }

    public String getCnss() {
        return cnss;
    }

    public void setCnss(String cnss) {
        this.cnss = cnss;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getRaisonSocial() {
        return raisonSocial;
    }

    public void setRaisonSocial(String raisonSocial) {
        this.raisonSocial = raisonSocial;
    }

    public String getIce() {
        return ice;
    }

    public void setIce(String ice) {
        this.ice = ice;
    }

    public String getIfe() {
        return ife;
    }

    public void setIfe(String ife) {
        this.ife = ife;
    }

    public String getRc() {
        return rc;
    }

    public void setRc(String rc) {
        this.rc = rc;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getHostMail() {
        return hostMail;
    }

    public void setHostMail(String hostMail) {
        this.hostMail = hostMail;
    }

    public String getPaswordMail() {
        return paswordMail;
    }

    public void setPaswordMail(String paswordMail) {
        this.paswordMail = paswordMail;
    }

    public String getPaswordMailFake() {
        return paswordMailFake;
    }

    public void setPaswordMailFake(String paswordMailFake) {
        this.paswordMailFake = paswordMailFake;
    }

    public String getFromMail() {
        return fromMail;
    }

    public void setFromMail(String fromMail) {
        this.fromMail = fromMail;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public Integer getCapitale() {
        return capitale;
    }

    public void setCapitale(Integer capitale) {
        this.capitale = capitale;
    }

    public BigDecimal getPourcentageLiv() {
        return pourcentageLiv;
    }

    public void setPourcentageLiv(BigDecimal pourcentageLiv) {
        this.pourcentageLiv = pourcentageLiv;
    }

    public String getLienDbDump() {
        return lienDbDump;
    }

    public void setLienDbDump(String lienDbDump) {
        this.lienDbDump = lienDbDump;
    }

    public String getLienBackupDB() {
        return lienBackupDB;
    }

    public void setLienBackupDB(String lienBackupDB) {
        this.lienBackupDB = lienBackupDB;
    }

    public Boolean getLundi() {
        return lundi;
    }

    public void setLundi(Boolean lundi) {
        this.lundi = lundi;
    }

    public Boolean getMardi() {
        return mardi;
    }

    public void setMardi(Boolean mardi) {
        this.mardi = mardi;
    }

    public Boolean getMercredi() {
        return mercredi;
    }

    public void setMercredi(Boolean mercredi) {
        this.mercredi = mercredi;
    }

    public Boolean getJeudi() {
        return jeudi;
    }

    public void setJeudi(Boolean jeudi) {
        this.jeudi = jeudi;
    }

    public Boolean getVendredi() {
        return vendredi;
    }

    public void setVendredi(Boolean vendredi) {
        this.vendredi = vendredi;
    }

    public Boolean getSamedi() {
        return samedi;
    }

    public void setSamedi(Boolean samedi) {
        this.samedi = samedi;
    }

    public Boolean getDimanche() {
        return dimanche;
    }

    public void setDimanche(Boolean dimanche) {
        this.dimanche = dimanche;
    }

    public Instant getDateTime() {
        return dateTime;
    }

    public void setDateTime(Instant dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getTypeExec() {
        return typeExec;
    }

    public void setTypeExec(Integer typeExec) {
        this.typeExec = typeExec;
    }

    public Integer getNumJour() {
        return numJour;
    }

    public void setNumJour(Integer numJour) {
        this.numJour = numJour;
    }

    public Ville getVille() {
        return ville;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
    }

}