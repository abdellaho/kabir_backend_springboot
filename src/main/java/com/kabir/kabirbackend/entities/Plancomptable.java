package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "plancomptable")
public class Plancomptable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private Integer code;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getFormule() {
        return formule;
    }

    public void setFormule(String formule) {
        this.formule = formule;
    }

    public String getCodeString() {
        return codeString;
    }

    public void setCodeString(String codeString) {
        this.codeString = codeString;
    }

}