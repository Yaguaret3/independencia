package com.megajuegos.independencia.dao;

import com.megajuegos.independencia.models.*;

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
    void avanzarFase();
    void avanzarTurno();
    String getSistemaDeGobierno();
    String getPresidente();
    String getSistemaEconomico();
    void recursosMapa();
    void improductividad(ActoresPoliticosModel actorPolitico);
    void editarSistemaDeGobierno(OtrosModel nuevoSistema);
    void editarPresidente(OtrosModel nuevoPresidente);
    void editarSistemaEconomico(OtrosModel nuevoSistema);
    void repartirRecursos(String sistemaDeGobierno, String presidente, String sistemaEconomico);
    void actualizarOficiales();

    void permitirActualizarListaCapitanes(OtrosModel nuevo);
    //List<DeterminandoConflictosModel> moverAntesDeConflictos();
    //List<String> buscarCaminos(String ubicacion, String destino);
}
