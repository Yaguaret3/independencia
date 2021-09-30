package com.megajuegos.independencia.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "congresos")
public class CongresoModel {

    @Getter @Setter @Id @Column(name = "id")
    private int id;

    @Getter @Setter @Column(name = "capital")
    private String capital;

    @Getter @Setter @Column(name = "cuantos")
    private int cuantos;

    @Getter @Setter @Column(name = "sistema_de_gobierno")
    private String sistema_de_gobierno;

    @Getter @Setter @Column(name = "presidente")
    private String presidente;

    @Getter @Setter @Column(name = "sistema_economico")
    private String sistema_economico;
}
