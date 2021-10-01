package com.megajuegos.independencia.controllers;

import com.megajuegos.independencia.dao.EjercitosDao;
import com.megajuegos.independencia.models.EjercitosModel;
import com.megajuegos.independencia.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
public class CapController {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private EjercitosDao ejercitosDao;

    @RequestMapping(value = "/api/capitanes/listarRecursos", method = RequestMethod.POST)
    public EjercitosModel listarRecursos(@RequestHeader(value = "Authorization") String token){

        // Primero: Corroborar que el pedido lo hace un capitán
        if(!jwtUtil.getKey(token).equals("capitan")){
            return null;
        }

        // Segundo: Corroborar ciudad
        String ciudad = jwtUtil.getValue(token);

        //Tercero: Devolver estado de recursos
        return ejercitosDao.recursosEjercito(ciudad);
    }

    @RequestMapping(value = "/api/capitanes/enviarMovimiento", method = RequestMethod.POST)
    public void movimientos(@RequestHeader(value = "Authorization") String token, @RequestBody EjercitosModel traido){

        // Primero: Corroborar que el pedido lo hace un capitán
        if(!jwtUtil.getKey(token).equals("capitan")){
            return;
        }

        // Segundo: Corroborar ciudad
        String ciudad = jwtUtil.getValue(token);

        // Tercero: determinar fase.
        int fase = ejercitosDao.fase();

        // Cuarto: enviar movimientos a base
        ejercitosDao.movimientos(ciudad, traido, fase);
    }

    @RequestMapping(value = "/api/capitanes/asignarUnidades", method = RequestMethod.POST)
    public void asignarUnidades(@RequestHeader(value = "Authorization") String token, @RequestBody EjercitosModel traido){

        // Primero: Corroborar fase inicial
        if(ejercitosDao.fase() == 1){

            // Segundo: Corroborar que el pedido lo hace un capitán
            if(!jwtUtil.getKey(token).equals("capitan")){
                return;
            }

            // Tercero: Corroborar ciudad
            String ciudad = jwtUtil.getValue(token);

            // Cuarto: Asignar unidades
            ejercitosDao.asignarUnidades(ciudad, traido);
        }
    }

    @RequestMapping(value = "/api/capitanes/listarMovimientos", method = RequestMethod.POST)
    public List<EjercitosModel> listarMovimientos(@RequestHeader(value = "Authorization") String token){

        // Primero: Corroborar que el pedido lo hace un capitán
        if(!jwtUtil.getKey(token).equals("capitan")){
            return null;
        }
        // Segundo: Corroborar ciudad
        String ciudad = jwtUtil.getValue(token);

        // Tercero: Tiene que estar autorizado
        if(ejercitosDao.actualizarCapitanes()){

            // Cuarto: Devolver Lista.
            return ejercitosDao.listarMovimientos();
        }

        return null;

    }


}
