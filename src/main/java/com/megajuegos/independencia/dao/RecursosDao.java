package com.megajuegos.independencia.dao;

import com.megajuegos.independencia.models.EjercitosModel;
import com.megajuegos.independencia.models.RecursosModel;

import java.util.List;

public interface RecursosDao {

    RecursosModel listarRecursos(String ciudad);

    EjercitosModel cargarNivelMisionComercial(String ciudad);

    void aumentarIndustria(String ciudad);

    String condicionesValidas(String ciudad);

    void aumentarMisionComercial(String ciudad);

    void reclutarUnidades(String ciudad);

    void contratarOficiales(String ciudad, RecursosModel traido);

    void enviarUnidades(String ciudad);

    boolean pagar(RecursosModel traido, String ciudad, int recursosAPagar);

    boolean misionMenosAEstatus(String ciudad);

    void comerciar(RecursosModel traido, String ciudad);

    int valorAumentarEstatus(RecursosModel traido);

    void aumentarEstatus(String ciudad);

    void crecerActorPolitico(String ciudad, int recursosAPagar);

    boolean pausa();
}
