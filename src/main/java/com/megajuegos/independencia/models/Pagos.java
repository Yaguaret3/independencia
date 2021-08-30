package com.megajuegos.independencia.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Pagos {

    @Id
    @Getter @Setter
    private int PagoNumero;

    @Getter @Setter
    private int Caballos;

    @Getter @Setter
    private int Vacas;

    @Getter @Setter
    private int Hierro;

    @Getter @Setter
    private int Vino;

    @Getter @Setter
    private int Yerba;
}
