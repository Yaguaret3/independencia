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
    @Getter @Setter @Column(name = "unidades")
    private Integer unidades;
    @Getter @Setter @Column(name = "oficiales")
    private String oficiales;
    @Getter @Setter @Column(name = "actor_politico_1")
    private String actor_politico_1;
    @Getter @Setter @Column(name = "actor_politico_2")
    private String actor_politico_2;
    @Getter @Setter @Column(name = "unidades_enviadas")
    private int unidades_enviadas;
    @Getter @Setter @Column(name = "nivel_oficial_pedido")
    private int nivel_oficial_pedido;
    @Getter @Setter @Column(name = "destino_comercial")
    private String destino_comercial;
    @Getter @Setter @Column(name = "historial_comercio")
    private String historial_comercial;
    @Getter @Setter @Column(name = "actor_politico_pedido")
    private int actor_politico_pedido;
    @Getter @Setter @Column(name = "improductividad")
    private int improductividad;
    @Getter @Setter @Column(name = "recurso_que_produce")
    private String recurso_que_produce;
    @Getter @Setter @Column(name = "caballos_m")
    private int caballos_m;
    @Getter @Setter @Column(name = "vacas_m")
    private int vacas_m;
    @Getter @Setter @Column(name = "hierro_m")
    private int hierro_m;
    @Getter @Setter @Column(name = "vino_m")
    private int vino_m;
    @Getter @Setter @Column(name = "yerba_m")
    private int yerba_m;
    @Getter @Setter @Column(name = "congreso")
    private int congreso;

}