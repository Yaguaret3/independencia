package com.megajuegos.independencia.controllers;

import com.megajuegos.independencia.dao.ControlDao;
import com.megajuegos.independencia.dao.RecursosDao;
import com.megajuegos.independencia.models.ActoresPoliticosModel;
import com.megajuegos.independencia.models.EjercitosModel;
import com.megajuegos.independencia.models.OtrosModel;
import com.megajuegos.independencia.models.RecursosModel;
import com.megajuegos.independencia.utils.JWTUtil;
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

    @Autowired
    private ControlDao controlDao;


    @RequestMapping(value = "api/gobernadores/cargarRecursos", method = RequestMethod.POST)
    public RecursosModel listarRecursos(@RequestHeader(value = "Authorization") String token){

        // Corroborar que el pedido lo hace un gobernador
        if(!jwtUtil.getKey(token).equals("gobernador")){
            return new RecursosModel();
        }

        String ciudad = jwtUtil.getValue(token);

        return recursoDao.listarRecursos(ciudad);
    }

    @RequestMapping(value = "api/gobernadores/cargarNivelMisionComercial", method = RequestMethod.POST)
    public EjercitosModel cargarNivelMisionComercial(@RequestHeader(value = "Authorization") String token){

        // Corroborar que el pedido lo hace un gobernador
        if(!jwtUtil.getKey(token).equals("gobernador")){
            return new EjercitosModel();
        }

        String ciudad = jwtUtil.getValue(token);

        return recursoDao.cargarNivelMisionComercial(ciudad);
    }

    @RequestMapping(value = "api/gobernadores/listarActoresPoliticos")
    public List<ActoresPoliticosModel> listarActoresPoliticos(@RequestHeader(value = "Authorization") String token){

        // Primero: Corroborar que el pedido lo hace un gobernador
        if(!jwtUtil.getKey(token).equals("gobernador")){
            return new ArrayList<>();
        }

        // Segundo: Listar recursos y devolver
        return controlDao.listarActoresPoliticos();
    }

    @RequestMapping(value = "api/gobernadores/cargarTimer")
    public List<OtrosModel> cargarTimer(@RequestHeader(value = "Authorization") String token) {

        // Primero: Corroborar que el pedido lo hace un gobernador
        if(!jwtUtil.getKey(token).equals("gobernador")){
            return null;
        }
        return controlDao.cargarTimer();
    }

    @RequestMapping(value = "api/gobernadores/aumentarIndustria", method = RequestMethod.POST)
    public String aumentarIndustria(@RequestHeader(value = "Authorization") String token, @RequestBody RecursosModel traido){

        // Primero: Corroborar que el pedido lo hace un gobernador
        if(!jwtUtil.getKey(token).equals("gobernador")){
            return "Permiso denegado";
        }

        // Segundo: Corroborar ciudad
        String ciudad = jwtUtil.getValue(token);

        // Tercero: corroborar pausa
        if(recursoDao.pausa()){
            return "Juego pausado";
        }

        // Cuarto: corroborar condiciones
        if(!recursoDao.condicionesValidas(ciudad).equals(null)){
            return recursoDao.condicionesValidas(ciudad);
        } else{
            // Quinto: pagar y cobrar.
            int recursosAPagar = 2;
            boolean pagado = recursoDao.pagar(traido, ciudad, recursosAPagar);
            if(!pagado) {
                return "Recursos insuficientes";
            } else {
                // Sexto: aumentar el nivel de industria en la base
                recursoDao.aumentarIndustria(ciudad);
                return null;
            }
        }
    }

    @RequestMapping(value = "api/gobernadores/aumentarMisionComercial", method = RequestMethod.POST)
    public String aumentarMisionComercial(@RequestHeader(value = "Authorization") String token, @RequestBody RecursosModel traido){

        // Primero: Corroborar que el pedido lo hace un gobernador
        if(!jwtUtil.getKey(token).equals("gobernador")){
            return "Permiso denegado";
        }

        // Segundo: Corroborar ciudad
        String ciudad = jwtUtil.getValue(token);

        // Tercero: corroborar pausa
        if(recursoDao.pausa()){
            return "Juego pausado";
        }

        // Cuarto: corroborar condiciones
        boolean condicionesValidas = recursoDao.misionMenosAEstatus(ciudad);

        if(!condicionesValidas) {
            return "Estatus insuficiente";
        }

        // Quinto: pagar y cobrar. El Front-End tiene que mandar 1 de cada recurso elegido, si no le va a restar lo que manden. Hay que escribir la reacción si no alcanza.
        int recursosAPagar = 1;
        boolean pagado = recursoDao.pagar(traido, ciudad, recursosAPagar);

        if(!pagado) {
            return "Recursos insuficientes";
        } else{
            // Sexto: aumentar el nivel de misión comercial en la base. Fijarse antes ley al respecto.
            recursoDao.aumentarMisionComercial(ciudad);
            return null;
        }
    }

    @RequestMapping(value = "api/gobernadores/reclutarUnidades", method = RequestMethod.POST)
    public String reclutarUnidades (@RequestHeader(value = "Authorization") String token, @RequestBody RecursosModel traido){

        // Primero: Corroborar que el pedido lo hace un gobernador
        if(!jwtUtil.getKey(token).equals("gobernador")){
            return "Permiso denegado";
        }

        // Segundo: Corroborar ciudad
        String ciudad = jwtUtil.getValue(token);

        // Tercero: corroborar pausa
        if(recursoDao.pausa()){
            return "Juego pausado";
        }

        // Cuarto: pagar y cobrar. El Front-End tiene que mandar 1 de cada recurso elegido, si no le va a restar lo que manden. Hay que escribir la reacción si no alcanza.
        int recursosAPagar = 1;
        boolean pagado = recursoDao.pagar(traido, ciudad, recursosAPagar);

        if(pagado){

            // Quinto: sumar unidades a la base
            recursoDao.reclutarUnidades(ciudad);
            return null;
        }
        return "Recursos insuficientes";
    }

    @RequestMapping(value = "api/gobernadores/contratarOficiales", method = RequestMethod.POST)
    public String contratarOficiales (@RequestHeader(value = "Authorization") String token, @RequestBody RecursosModel traido){

        // Primero: Corroborar que el pedido lo hace un gobernador
        if(!jwtUtil.getKey(token).equals("gobernador")){
            return "Permiso denegado";
        }

        // Segundo: Corroborar ciudad
        String ciudad = jwtUtil.getValue(token);

        // Tercero: corroborar pausa
        if(recursoDao.pausa()){
            return "Juego pausado";
        }

        // Cuarto: determinar si paga un nivel B o un nivel C.
        int recursosAPagar = traido.getNivel_oficial_pedido();

        // Quinto: pagar y cobrar. El Front-End tiene que mandar 1 de cada recurso elegido, si no le va a restar lo que manden. Hay que escribir la reacción si no alcanza.
        boolean pagado = recursoDao.pagar(traido, ciudad, recursosAPagar);

        if(!pagado) {
            return "Recursos insuficientes";
        } else{
            // Sexto: sumar oficiales a la base.
            recursoDao.contratarOficiales(ciudad, traido);
            return null;
        }
    }

    @RequestMapping(value = "api/gobernadores/enviarUnidades", method = RequestMethod.POST)
    public String enviarUnidades (@RequestHeader(value = "Authorization") String token){

        // Primero: Corroborar que el pedido lo hace un gobernador
        if(!jwtUtil.getKey(token).equals("gobernador")){
            return "Permiso denegado";
        }

        // Segundo: Corroborar ciudad
        String ciudad = jwtUtil.getValue(token);

        // Tercero: corroborar pausa
        if(recursoDao.pausa()){
            return "Juego pausado";
        }

        // Cuarto: enviar unidades al capitán y limpiarlas. Hecho. Falta la parte de la tabla del capitán.
        recursoDao.enviarUnidades(ciudad);
        return null;
    }

    @RequestMapping(value = "api/gobernadores/comerciar", method = RequestMethod.POST)
    public String comerciar (@RequestHeader(value = "Authorization") String token, @RequestBody RecursosModel traido){

        // Primero: Corroborar que el pedido lo hace un gobernador
        if(!jwtUtil.getKey(token).equals("gobernador")){
            return "Permiso denegado";
        }

        // Segundo: Corroborar ciudad
        String ciudad = jwtUtil.getValue(token);

        // Tercero: corroborar pausa
        if(recursoDao.pausa()){
            return "Juego pausado";
        }

        // Cuarto: comerciar (restarle a uno y sumarle al otro)
        recursoDao.comerciar(traido, ciudad);
        return null;
    }

    @RequestMapping(value = "api/gobernadores/aumentarEstatus", method = RequestMethod.POST)
    public String aumentarEstatus (@RequestHeader(value = "Authorization") String token, @RequestBody RecursosModel traido){

        // Primero: Corroborar que el pedido lo hace un gobernador
        if(!jwtUtil.getKey(token).equals("gobernador")){
            return "Permiso denegado";
        }

        // Segundo: Corroborar ciudad
        String ciudad = jwtUtil.getValue(token);

        // Tercero: corroborar pausa
        if(recursoDao.pausa()){
            return "Juego pausado";
        }

        /* Cuarto: determinar el actor político pedido. El Front End tiene que mandarme un int.
         * 1: para actor político 1, 2 para actor político 2, y 3 para actor político 3 (gobierno nacional)*/
        int recursosAPagar = recursoDao.valorAumentarEstatus(traido);

        // Quinto: pagar. El Front-End tiene que mandar 1 de cada recurso elegido, si no le va a restar lo que manden. Hay que escribir la reacción si no alcanza.
        boolean pagado = recursoDao.pagar(traido, ciudad, recursosAPagar);

        if(!pagado) {
            return "Recursos insuficientes";
        } else{
            // Sexto: aumentar estatus en la base.
            recursoDao.aumentarEstatus(ciudad);

            // Séptimo: aumentar nivel de actor político.
            recursoDao.crecerActorPolitico(ciudad, recursosAPagar);
            return null;
        }
    }
}