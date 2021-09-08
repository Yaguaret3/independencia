package com.megajuegos.independencia.dao;

import com.megajuegos.independencia.models.ActoresPoliticosModel;
import com.megajuegos.independencia.models.RecursosModel;
import com.megajuegos.independencia.models.EjercitosModel;

import java.util.List;

public interface ControlDao {

    List<RecursosModel> listarRecursos();
    List<EjercitosModel> listarEjercitos();
    List<ActoresPoliticosModel> listarActoresPoliticos();
    void pausar();
    void despausar();
    void editarCiudad(String ciudad, RecursosModel actualizacion);
    void editarEjercito(String ciudad, EjercitosModel actualizacion);
    void editarActorPolitico(String actor, ActoresPoliticosModel actualizacion);
}
