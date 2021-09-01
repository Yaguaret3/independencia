package com.megajuegos.independencia.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "actorespoliticos")
public class ActoresPoliticosModel {

    @Id
    @Getter @Setter @Column(name = "actor")
    private String actor;
    @Getter @Setter @Column(name = "valor")
    private int valor;
}
