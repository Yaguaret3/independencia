package com.megajuegos.independencia.dao;

import com.megajuegos.independencia.models.RecursosModel;

import java.util.List;

public interface RecursosDao {

    List<RecursosModel> listarRecursos(String ciudad);

}
