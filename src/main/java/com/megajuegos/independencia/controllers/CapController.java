package com.megajuegos.independencia.controllers;

import com.megajuegos.independencia.dao.EjercitosDao;
import com.megajuegos.independencia.models.EjercitosModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class CapController {

    @Autowired
    private EjercitosDao ejercitosDao;

    @RequestMapping(value = "/api/capitanes/listarRecursos", method = RequestMethod.POST)
    public EjercitosModel listarRecursos(@RequestHeader(value = "Authorization") String token){

        // Primero determinar ciudad
        String ciudad = ejercitosDao.corroborarCiudad(token);

        //Segundo devolver estado de recursos
        return ejercitosDao.recursosEjercito(ciudad);
    }

    @RequestMapping(value = "/api/capitanes/movimientos", method = RequestMethod.POST)
    public void movimientos(@RequestHeader(value = "Authorization") String token, @RequestBody EjercitosModel traido){

        // Primero determinar ciudad
        String ciudad = ejercitosDao.corroborarCiudad(token);

        //Segundo enviar movimientos a base
        ejercitosDao.movimientos(ciudad, traido);
    }

    @RequestMapping(value = "/api/capitanes/asignarUnidades", method = RequestMethod.POST)
    public void asignarUnidades(@RequestHeader(value = "Authorization") String token, @RequestBody EjercitosModel traido){

        // Primero: Corroborar momento de juego

        // Segundo: Determinar ciudad
        String ciudad = ejercitosDao.corroborarCiudad(token);

        // Tercero: Asignar unidades
        ejercitosDao.asignarUnidades(ciudad, traido);
    }
}
