package com.megajuegos.independencia.dao;

import com.megajuegos.independencia.models.UsuarioModel;

import java.util.List;

public interface UsuariosDao {

    
    List<UsuarioModel> recorrerUsuarios();

    void eliminarUsuario(Long id);

    void registrarUsuario(UsuarioModel usuario);

    UsuarioModel obtenerUsuarioPorCredenciales(UsuarioModel usuario);
}
