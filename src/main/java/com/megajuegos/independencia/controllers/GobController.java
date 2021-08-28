package com.megajuegos.independencia.controllers;

import com.megajuegos.independencia.dao.RecursosDao;
import com.megajuegos.independencia.dao.UsuariosDao;
import com.megajuegos.independencia.models.RecursosModel;
import com.megajuegos.independencia.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GobController {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private RecursosDao recursoDao;

    private boolean validarToken(String token){

        String usuarioID = jwtUtil.getKey(token);
        return usuarioID != null;

    }

    @RequestMapping(value = "recursos/{ciudad}", method = RequestMethod.GET)
    public List<RecursosModel> traerRecursos(@RequestHeader(value = "Authorization") String token, String ciudad){

        if(!validarToken(token)){
            return new ArrayList<>();
        }

        return recursoDao.listarRecursos(ciudad);

    }

}

