package com.megajuegos.independencia.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "otras")
public class OtrosModel {

    @Getter @Setter @Column(name = "id")
    private int id;

    @Id
    @Getter @Setter @Column(name ="accion")
    private String accion;

    @Getter @Setter @Column(name = "valor_int")
    private int valor_int;

    @Getter @Setter @Column(name = "valor_char")
    private String valor_char;

}
