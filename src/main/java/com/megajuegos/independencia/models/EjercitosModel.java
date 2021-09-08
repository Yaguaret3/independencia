package com.megajuegos.independencia.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ejercitos")
public class EjercitosModel {


    @Getter @Setter @Column(name = "id")
    private int id;

    @Id
    @Getter @Setter @Column(name = "ciudad")
    private String ciudad;

    @Getter @Setter @Column(name = "unidades_agrupadas")
    private int unidades_agrupadas;

    @Getter @Setter @Column(name = "unidades_recien_llegadas")
    private int unidades_recien_llegadas;

    @Getter @Setter @Column(name = "unidades_a_asignar")
    private int unidades_a_asignar;

    @Getter @Setter @Column(name = "dar_unidades_a")
    private String dar_unidades_a;

    @Getter @Setter @Column(name = "oficial_1")
    private String oficial_1;

    @Getter @Setter @Column(name = "oficial_2")
    private String oficial_2;

    @Getter @Setter @Column(name = "oficial_3")
    private String oficial_3;

    @Getter @Setter @Column(name = "oficial_4")
    private String oficial_4;

    @Getter @Setter @Column(name = "oficial_5")
    private String oficial_5;

    @Getter @Setter @Column(name = "ubicacion_militar")
    private int ubicacion_militar;

    @Getter @Setter @Column(name = "movimiento")
    private String movimiento;

    @Getter @Setter @Column(name = "destino_1")
    private int destino_1;

    @Getter @Setter @Column(name = "destino_2")
    private int destino_2;

    @Getter @Setter @Column(name = "destino_3")
    private int destino_3;

    @Getter @Setter @Column(name = "ubicacion_comercial")
    private int ubicacion_comercial;

    @Getter @Setter @Column(name = "nivel_mision_comercial")
    private int nivel_mision_comercial;

    @Getter @Setter @Column(name = "ciudades_aliadas")
    private String ciudades_aliadas;

    @Getter @Setter @Column(name = "asediada")
    private String asediada;

    @Getter @Setter @Column(name = "nuevo_oficial_1")
    private String nuevo_oficial_1;

    @Getter @Setter @Column(name = "nuevo_oficial_2")
    private String nuevo_oficial_2;

    @Getter @Setter @Column(name = "nuevo_oficial_3")
    private String nuevo_oficial_3;

    @Getter @Setter @Column(name = "nuevo_oficial_4")
    private String nuevo_oficial_4;

    @Getter @Setter @Column(name = "nuevo_oficial_5")
    private String nuevo_oficial_5;


}