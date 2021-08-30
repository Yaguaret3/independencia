package com.megajuegos.independencia.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ciudadesrecursos")
public class RecursosModel {

    @Getter @Setter @Column(name = "id")
    private int id;
    @Id
    @Getter @Setter @Column(name = "ciudad")
    private String ciudad;
    @Getter @Setter @Column(name = "estatus")
    private int estatus;
    @Getter @Setter @Column(name = "caballos")
    private int caballos;
    @Getter @Setter @Column(name = "vacas")
    private int vacas;
    @Getter @Setter @Column(name = "hierro")
    private int hierro;
    @Getter @Setter @Column(name = "vino")
    private int vino;
    @Getter @Setter @Column(name = "yerba")
    private int yerba;
    @Getter @Setter @Column(name = "nivel_industria")
    private int nivel_industria;
    @Getter @Setter @Column(name = "nivel_mision_comercial")
    private int nivel_mision_comercial;
    @Getter @Setter @Column(name = "unidades")
    private int unidades;
    @Getter @Setter @Column(name = "oficiales")
    private String oficiales;
    @Getter @Setter @Column(name = "unidades_enviadas")
    private int unidades_enviadas;

}