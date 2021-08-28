package com.megajuegos.independencia.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ciudadesrecursos")
public class RecursosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter @Column(name = "id")
    private int id;
    @Getter @Setter @Column(name = "ciudad")
    private String ciudad;
    @Getter @Setter @Column(name = "estatus")
    private String estatus;
    @Getter @Setter @Column(name = "caballos")
    private String caballos;
    @Getter @Setter @Column(name = "vacas")
    private String vacas;
    @Getter @Setter @Column(name = "hierro")
    private String hierro;
    @Getter @Setter @Column(name = "vino")
    private String vino;
    @Getter @Setter @Column(name = "yerba")
    private String yerba;
    @Getter @Setter @Column(name = "nivelIndustria")
    private String nivelIndustria;
    @Getter @Setter @Column(name = "nivelMisionComercial")
    private String nivelMisionComercial;
    @Getter @Setter @Column(name = "unidades")
    private String unidades;
    @Getter @Setter @Column(name = "oficiales")
    private String oficiales;

}