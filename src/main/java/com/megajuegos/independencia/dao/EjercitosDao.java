package com.megajuegos.independencia.dao;

import com.megajuegos.independencia.models.EjercitosModel;

public interface EjercitosDao {

    String corroborarCiudad(String token);

    EjercitosModel recursosEjercito(String ciudad);

    void movimientos(String ciudad, EjercitosModel traido);

    void asignarUnidades(String ciudad, EjercitosModel traido);




}
