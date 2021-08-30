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
}
