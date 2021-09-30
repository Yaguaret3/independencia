package com.megajuegos.independencia.controllers;

import com.megajuegos.independencia.dao.ControlDao;
import com.megajuegos.independencia.dao.EjercitosDao;
import com.megajuegos.independencia.models.*;
import com.megajuegos.independencia.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ConController {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private ControlDao controlDao;

    @Autowired
    private EjercitosDao ejercitosDao;

    @RequestMapping(value = "api/control/listarRecursos")
    public List<RecursosModel> listarRecursos(@RequestHeader(value = "Authorization") String token){

        // Primero: Corroborar que el pedido lo hace Control

        if(!jwtUtil.getKey(token).equals("control")){
            return new ArrayList<>();
        };

        // Segundo: Listar recursos y devolver

        return controlDao.listarRecursos();
    }

    @RequestMapping(value = "api/control/listarEjercitos")
    public List<EjercitosModel> listarEjercitos(@RequestHeader(value = "Authorization") String token){

        // Primero: Corroborar que el pedido lo hace Control

        if(!jwtUtil.getKey(token).equals("control")){
            return new ArrayList<>();
        };

        // Segundo: Listar recursos y devolver

        return controlDao.listarEjercitos();
    }

    @RequestMapping(value = "api/control/listarActoresPoliticos")
    public List<ActoresPoliticosModel> listarActoresPoliticos(@RequestHeader(value = "Authorization") String token){

        // Primero: Corroborar que el pedido lo hace Control

        if(!jwtUtil.getKey(token).equals("control")){
            return new ArrayList<>();
        };

        // Segundo: Listar recursos y devolver

        return controlDao.listarActoresPoliticos();
    }

    @RequestMapping(value = "api/control/listarCongresos")
    public List<CongresoModel> listarCongresos(@RequestHeader(value = "Authorization") String token){

        // Primero: Corroborar que el pedido lo hace Control

        if(!jwtUtil.getKey(token).equals("control")){
            return new ArrayList<>();
        };

        // Segundo: Listar recursos y devolver

        return controlDao.listarCongresos();
    }

    @RequestMapping(value = "api/control/pausar")
    public void pausar(@RequestHeader(value = "Authorization") String token){

        // Primero: Corroborar que el pedido lo hace Control

        if(!jwtUtil.getKey(token).equals("control")){
            return;
        }

        // Segundo: Pausar

        controlDao.pausar();
    }

    @RequestMapping(value = "api/control/despausar")
    public void despausar(@RequestHeader(value = "Authorization") String token){

        // Primero: Corroborar que el pedido lo hace Control

        if(!jwtUtil.getKey(token).equals("control")){
            return;
        }

        // Segundo: Despausar

        controlDao.despausar();
    }

    @RequestMapping(value = "api/control/editarCiudad")
    public void editarCiudad(@RequestHeader(value = "Authorization") String token, @RequestBody RecursosModel actualizacion){

        // Primero: Corroborar que el pedido lo hace Control

        if(!jwtUtil.getKey(token).equals("control")){
            return;
        }

        // Segundo: Seleccionar ciudad a editar
        String ciudad = actualizacion.getCiudad();

        // Tercero: Editar ciudad

        controlDao.editarCiudad(ciudad, actualizacion);
    }

    @RequestMapping(value = "api/control/editarEjercito")
    public void editarEjercito(@RequestHeader(value = "Authorization") String token, @RequestBody EjercitosModel actualizacion){

        // Primero: Corroborar que el pedido lo hace Control

        if(!jwtUtil.getKey(token).equals("control")){
            return;
        }

        // Segundo: Seleccionar ciudad a editar
        String ciudad = actualizacion.getCiudad();

        // Tercero: Editar ciudad

        controlDao.editarEjercito(ciudad, actualizacion);
    }

    @RequestMapping(value = "api/control/editarActorPolitico")
    public void editarActoresPoliticos(@RequestHeader(value = "Authorization") String token, @RequestBody ActoresPoliticosModel actualizacion){

        // Primero: Corroborar que el pedido lo hace Control

        if(!jwtUtil.getKey(token).equals("control")){
            return;
        }

        // Segundo: Seleccionar actor a editar
        String actor = actualizacion.getActor();

        // Tercero: Editar actor

        controlDao.editarActorPolitico(actor, actualizacion);
    }

    @RequestMapping(value = "api/control/seleccionarFase")
    public void seleccionarFase(@RequestHeader(value = "Authorization") String token, @RequestBody OtrosModel fase){

        // Primero: Corroborar que el pedido lo hace Control

        if(!jwtUtil.getKey(token).equals("control")){
            return;
        }

        // Segundo: Avanzar fase. CONTROL ELIGE LA FASE, NO LA AVANZA.

        controlDao.seleccionarFase(fase);
    }

    @RequestMapping(value = "api/control/avanzarTurno")
    public void avanzarTurno(@RequestHeader(value = "Authorization") String token){

        // Primero: Corroborar que el pedido lo hace Control

        if(!jwtUtil.getKey(token).equals("control")){
            return;
        }

        // Segundo: Corroborar improductividad y ganar recursos en mapa.
        controlDao.recursosMapa();

        // Tercero: Avanzar turno
        controlDao.avanzarTurno();

        // Cuarto: Repartir recursos.
        controlDao.repartirRecursos();

        // Quinto: actualizar oficiales en capitanes
        controlDao.actualizarOficiales();
    }

    @RequestMapping(value = "api/control/improductividad")
    public void improductividad(@RequestHeader(value = "Authorization") String token, @RequestBody ActoresPoliticosModel actorPolitico) {

        // Primero: Corroborar que el pedido lo hace Control

        if(!jwtUtil.getKey(token).equals("control")){
            return;
        }

        // Segundo: Generar improductividad

        controlDao.improductividad(actorPolitico);
    }

    @RequestMapping(value = "api/control/editarCongreso")
    public void editarCongreso(@RequestHeader(value = "Authorization") String token, @RequestBody CongresoModel nuevoCongreso) {

        // Primero: Corroborar que el pedido lo hace Control

        if(!jwtUtil.getKey(token).equals("control")){
            return;
        }

        //Segundo: Editar sistema de gobierno
        controlDao.editarCongreso(nuevoCongreso);
    }

    @RequestMapping(value = "api/control/permitirActualizarListaCapitanes")
    public void permitirActualizarListaCapitanes(@RequestHeader(value = "Authorization") String token, @RequestBody OtrosModel nuevo) {

        // Primero: Corroborar que el pedido lo hace Control

        if(!jwtUtil.getKey(token).equals("control")){
            return;
        }

        controlDao.permitirActualizarListaCapitanes(nuevo);

    }


    //TODO LO QUE ES IA
}
