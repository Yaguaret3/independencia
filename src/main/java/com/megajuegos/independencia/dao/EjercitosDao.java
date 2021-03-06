package com.megajuegos.independencia.dao;

import com.megajuegos.independencia.models.EjercitosModel;

import java.util.List;

public interface EjercitosDao {

    EjercitosModel recursosEjercito(String ciudad);

    void movimientos(String ciudad, EjercitosModel traido, long fase);

    void asignarUnidades(String ciudad, EjercitosModel traido);

    long fase();

    List<EjercitosModel> listarMovimientos();

    boolean actualizarCapitanes();
}
