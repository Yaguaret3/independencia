package com.megajuegos.independencia.dao;

import com.megajuegos.independencia.models.EjercitosModel;

import java.util.List;

public interface EjercitosDao {

    String corroborarCiudad(String token);

    EjercitosModel recursosEjercito(String ciudad);

    void movimientos(String ciudad, EjercitosModel traido);

    void asignarUnidades(String ciudad, EjercitosModel traido);

    boolean fase1();

    List<EjercitosModel> listarMovimientos();

    boolean actualizarCapitanes();
}
