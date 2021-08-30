package com.megajuegos.independencia.controllers;

import com.megajuegos.independencia.dao.RecursosDao;
import com.megajuegos.independencia.dao.UsuariosDao;
import com.megajuegos.independencia.models.Pagos;
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

    @RequestMapping(value = "recursos/{ciudad}", method = RequestMethod.POST)
    public RecursosModel traerRecursos(/*@RequestHeader(value = "Authorization") String token, */@PathVariable String ciudad){

        /*if(!validarToken(token)){
            return new ArrayList<>();
        }*/

        return recursoDao.listarRecursos(ciudad);
    }

    @RequestMapping(value = "aumentarIndustria/{ciudad}", method = RequestMethod.POST)
    public RecursosModel aumentarIndustria(@PathVariable String ciudad){

        // Primero: corroborar permiso (token)
        /* if(!validarToken(token)){
            return new ArrayList<>();
        }*/

        /* Segundo: aumentar el nivel de industria en la base (previas corroboraciones)
        Esto anda, nomás hay que devolverle al usuario qué onda si hubo un inconveniente por recursos o ley o nivel ya subido*/
        recursoDao.aumentarIndustria(ciudad);

        // Tercero: devolver nivel de industria y recursos para que el explorador actualice.
        return recursoDao.listarRecursos(ciudad);

    }

    @RequestMapping(value = "aumentarMisionComercial/{ciudad}", method = RequestMethod.POST)
    public RecursosModel aumentarMisionComercial(@PathVariable String ciudad){

        // Primero: corroborar permiso (token)
        /* if(!validarToken(token)){
            return new ArrayList<>();
        }*/

        // Segundo: aumentar el nivel de misión comercial en la base
        recursoDao.aumentarMisionComercial(ciudad);

        // Tercero: devolver nivel de industria y recursos para que el explorador actualice.
        return recursoDao.listarRecursos(ciudad);

    }

    @RequestMapping(value = "reclutarUnidades/{ciudad}", method = RequestMethod.POST)
    public RecursosModel reclutarUnidades (@PathVariable String ciudad){

        // Primero: corroborar permiso (token)
        /* if(!validarToken(token)){
            return new ArrayList<>();
        }*/

        // Segundo: aumentar el nivel de misión comercial en la base
        recursoDao.reclutarUnidades(ciudad);

        // Tercero: devolver nivel de industria y recursos para que el explorador actualice.
        return recursoDao.listarRecursos(ciudad);

    }

    @RequestMapping(value = "contratarOficiales/{ciudad}", method = RequestMethod.POST)
    public RecursosModel contratarOficiales (@PathVariable String ciudad){

        // Primero: corroborar permiso (token)
        /* if(!validarToken(token)){
            return new ArrayList<>();
        }*/

        // Segundo: aumentar el nivel de misión comercial en la base
        recursoDao.contratarOficiales(ciudad);

        // Tercero: devolver oficiales y recursos para que el explorador actualice.
        return recursoDao.listarRecursos(ciudad);

    }

    @RequestMapping(value = "enviarUnidades/{ciudad}", method = RequestMethod.POST)
    public RecursosModel enviarUnidades (@PathVariable String ciudad){

        // Primero: corroborar permiso (token)
        /* if(!validarToken(token)){
            return new ArrayList<>();
        }*/

        // Segundo: enviar unidades al capitán y limpiarlas
        recursoDao.enviarUnidades(ciudad);

        // Tercero: devolver unidades para que el explorador actualice.
        return recursoDao.listarRecursos(ciudad);

    }

    @RequestMapping(value = "prueba", method = RequestMethod.POST)
    public Pagos prueba (@RequestBody Pagos traido){

        Pagos pagos = new Pagos();
        pagos = traido;

        return pagos;

    }

}