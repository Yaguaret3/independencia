package com.megajuegos.independencia.dao;

import com.megajuegos.independencia.models.RecursosModel;

import java.util.List;

public interface RecursosDao {

    RecursosModel listarRecursos(String ciudad);

    void aumentarIndustria(String ciudad);

    void aumentarMisionComercial(String ciudad);

    void reclutarUnidades(String ciudad);

    void contratarOficiales(String ciudad);

    void enviarUnidades(String ciudad);

    boolean pagar(RecursosModel traido, String ciudad, int recursosAPagar);

    String corroborarCiudad(String token);

    boolean misionMenosAEstatus(String ciudad);

    void comerciar(RecursosModel traido, String ciudad);

    int valorOficial(RecursosModel traido);

    int valorAumentarEstatus(RecursosModel traido);

    void aumentarEstatus(String ciudad);

    void crecerActorPolitico(String ciudad, int recursosAPagar);
}
