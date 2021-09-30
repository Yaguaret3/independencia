package com.megajuegos.independencia.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

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
    @Getter @Setter @Column(name = "oficial_a")
    private String oficial_a;
    @Getter @Setter @Column(name = "oficial_b")
    private String oficial_b;
    @Getter @Setter @Column(name = "oficial_c")
    private String oficial_c;
    @Getter @Setter @Column(name = "oficial_d")
    private String oficial_d;
    @Getter @Setter @Column(name = "oficial_e")
    private String oficial_e;
    @Getter @Setter @Column(name = "ubicacion_militar")
    private String ubicacion_militar;
    @Getter @Setter @Column(name = "movimiento")
    private String movimiento;
    @Getter @Setter @Column(name = "destino_1")
    private String destino_1;
    @Getter @Setter @Column(name = "destino_2")
    private String destino_2;
    @Getter @Setter @Column(name = "destino_3")
    private String destino_3;
    @Getter @Setter @Column(name = "ubicacion_comercial")
    private String ubicacion_comercial;
    @Getter @Setter @Column(name = "nivel_mision_comercial")
    private int nivel_mision_comercial;
    @Getter @Setter @Column(name = "ciudades_aliadas")
    private String ciudades_aliadas;
    @Getter @Setter @Column(name = "asediada")
    private String asediada;
    @Getter @Setter @Column(name = "nuevo_oficial_a")
    private String nuevo_oficial_a;
    @Getter @Setter @Column(name = "nuevo_oficial_b")
    private String nuevo_oficial_b;
    @Getter @Setter @Column(name = "nuevo_oficial_c")
    private String nuevo_oficial_c;
    @Getter @Setter @Column(name = "nuevo_oficial_d")
    private String nuevo_oficial_d;
    @Getter @Setter @Column(name = "nuevo_oficial_e")
    private String nuevo_oficial_e;
    @Getter @Setter @Column(name = "ruta_a_usar")
    private String ruta_a_usar;
}