package com.megajuegos.independencia.dao;

import com.megajuegos.independencia.models.*;

import java.util.List;

public interface ControlDao {

    List<RecursosModel> listarRecursos();
    List<EjercitosModel> listarEjercitos();
    List<ActoresPoliticosModel> listarActoresPoliticos();
    List<CongresoModel> listarCongresos();
    void pausar();
    void despausar();
    void editarCiudad(String ciudad, RecursosModel actualizacion);
    void editarEjercito(String ciudad, EjercitosModel actualizacion);
    void editarActorPolitico(String actor, ActoresPoliticosModel actualizacion);
    void seleccionarFase(OtrosModel fase);
    void avanzarTurno();
    void recursosMapa();
    void improductividad(ActoresPoliticosModel actorPolitico);
    void editarCongreso(CongresoModel nuevoCongreso);
    void repartirRecursos();
    void actualizarOficiales();
    void permitirActualizarListaCapitanes(OtrosModel nuevo);


    //List<DeterminandoConflictosModel> moverAntesDeConflictos();
    //List<String> buscarCaminos(String ubicacion, String destino);
}
