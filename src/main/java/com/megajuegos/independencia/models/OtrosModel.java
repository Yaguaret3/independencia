package com.megajuegos.independencia.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "otras")
public class OtrosModel {

    @Getter
    @Setter
    @Column(name = "id")
    @Id
    private int id;

    @Getter @Setter @Column(name = "accion")
    private String accion;

    @Getter @Setter @Column(name = "valor")
    private int valor;
}
