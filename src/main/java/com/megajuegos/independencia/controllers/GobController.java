package com.megajuegos.independencia.controllers;

import com.megajuegos.independencia.dao.ControlDao;
import com.megajuegos.independencia.dao.RecursosDao;
import com.megajuegos.independencia.models.ActoresPoliticosModel;
import com.megajuegos.independencia.models.EjercitosModel;
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

    @RequestMapping(value = "api/gobernadores/aumentarIndustria", method = RequestMethod.POST)
    public void aumentarIndustria(@RequestHeader(value = "Authorization") String token, @RequestBody RecursosModel traido){

        // Primero: Corroborar que el pedido lo hace un gobernador
        if(!jwtUtil.getKey(token).equals("gobernador")){
            return;
        }

        // Segundo: Corroborar ciudad
        String ciudad = jwtUtil.getValue(token);

        // Tercero: corroborar pausa
        if(recursoDao.pausa()){
            return;
        }

        // Cuarto: corroborar condiciones
        boolean condicionesValidas = true;

        if(condicionesValidas){

            // Quinto: pagar y cobrar.
            int recursosAPagar = 2;
            boolean pagado = recursoDao.pagar(traido, ciudad, recursosAPagar);

            if(pagado){

            /* Sexto: aumentar el nivel de industria en la base
             * Esto anda, nomás hay que devolverle al usuario qué onda si hubo un inconveniente por recursos o ley o nivel ya subido*/
                recursoDao.aumentarIndustria(ciudad);
            }
        }
    }

    @RequestMapping(value = "api/gobernadores/aumentarMisionComercial", method = RequestMethod.POST)
    public void aumentarMisionComercial(@RequestHeader(value = "Authorization") String token, @RequestBody RecursosModel traido){

        // Primero: Corroborar que el pedido lo hace un gobernador
        if(!jwtUtil.getKey(token).equals("gobernador")){
            return;
        }

        // Segundo: Corroborar ciudad
        String ciudad = jwtUtil.getValue(token);

        // Tercero: corroborar pausa
        if(recursoDao.pausa()){
            return;
        }

        // Cuarto: corroborar condiciones
        boolean condicionesValidas = recursoDao.misionMenosAEstatus(ciudad);

        if(condicionesValidas){

            // Quinto: pagar y cobrar. El Front-End tiene que mandar 1 de cada recurso elegido, si no le va a restar lo que manden. Hay que escribir la reacción si no alcanza.
            int recursosAPagar = 1;
            boolean pagado = recursoDao.pagar(traido, ciudad, recursosAPagar);

            if(pagado){

                // Sexto: aumentar el nivel de misión comercial en la base. Fijarse antes ley al respecto.
                recursoDao.aumentarMisionComercial(ciudad);
            }
        }
    }

    @RequestMapping(value = "api/gobernadores/reclutarUnidades", method = RequestMethod.POST)
    public void reclutarUnidades (@RequestHeader(value = "Authorization") String token, @RequestBody RecursosModel traido){

        // Primero: Corroborar que el pedido lo hace un gobernador
        if(!jwtUtil.getKey(token).equals("gobernador")){
            return;
        }

        // Segundo: Corroborar ciudad
        String ciudad = jwtUtil.getValue(token);

        // Tercero: corroborar pausa
        if(recursoDao.pausa()){
            return;
        }

        // Cuarto: pagar y cobrar. El Front-End tiene que mandar 1 de cada recurso elegido, si no le va a restar lo que manden. Hay que escribir la reacción si no alcanza.
        int recursosAPagar = 1;
        boolean pagado = recursoDao.pagar(traido, ciudad, recursosAPagar);

        if(pagado){

            // Quinto: sumar unidades a la base
            recursoDao.reclutarUnidades(ciudad);
        }
    }

    @RequestMapping(value = "api/gobernadores/contratarOficiales", method = RequestMethod.POST)
    public void contratarOficiales (@RequestHeader(value = "Authorization") String token, @RequestBody RecursosModel traido){

        // Primero: Corroborar que el pedido lo hace un gobernador
        if(!jwtUtil.getKey(token).equals("gobernador")){
            return;
        }

        // Segundo: Corroborar ciudad
        String ciudad = jwtUtil.getValue(token);

        // Tercero: corroborar pausa
        if(recursoDao.pausa()){
            return;
        }

        // Cuarto: determinar si paga un nivel B o un nivel C.
        int recursosAPagar = traido.getNivel_oficial_pedido();

        // Quinto: pagar y cobrar. El Front-End tiene que mandar 1 de cada recurso elegido, si no le va a restar lo que manden. Hay que escribir la reacción si no alcanza.
        boolean pagado = recursoDao.pagar(traido, ciudad, recursosAPagar);

        if(pagado){

            /* Sexto: sumar oficiales a la base. Habría que mandar esos oficiales a la tabla de capitán, pero no presentes
             * sino potenciales, para que al cambiar del turno se actualicen.
             */
            recursoDao.contratarOficiales(ciudad, traido);
        }
    }

    @RequestMapping(value = "api/gobernadores/enviarUnidades", method = RequestMethod.POST)
    public void enviarUnidades (@RequestHeader(value = "Authorization") String token){

        // Primero: Corroborar que el pedido lo hace un gobernador
        if(!jwtUtil.getKey(token).equals("gobernador")){
            return;
        }

        // Segundo: Corroborar ciudad
        String ciudad = jwtUtil.getValue(token);

        // Tercero: corroborar pausa
        if(recursoDao.pausa()){
            return;
        }

        // Cuarto: enviar unidades al capitán y limpiarlas. Hecho. Falta la parte de la tabla del capitán.
        recursoDao.enviarUnidades(ciudad);
    }

    @RequestMapping(value = "api/gobernadores/comerciar", method = RequestMethod.POST)
    public void comerciar (@RequestHeader(value = "Authorization") String token, @RequestBody RecursosModel traido){

        // Primero: Corroborar que el pedido lo hace un gobernador
        if(!jwtUtil.getKey(token).equals("gobernador")){
            return;
        }

        // Segundo: Corroborar ciudad
        String ciudad = jwtUtil.getValue(token);

        // Tercero: corroborar pausa
        if(recursoDao.pausa()){
            return;
        }

        // Cuarto: comerciar (restarle a uno y sumarle al otro)
        recursoDao.comerciar(traido, ciudad);
    }

    @RequestMapping(value = "api/gobernadores/aumentarEstatus", method = RequestMethod.POST)
    public void aumentarEstatus (@RequestHeader(value = "Authorization") String token, @RequestBody RecursosModel traido){

        // Primero: Corroborar que el pedido lo hace un gobernador
        if(!jwtUtil.getKey(token).equals("gobernador")){
            return;
        }

        // Segundo: Corroborar ciudad
        String ciudad = jwtUtil.getValue(token);

        // Tercero: corroborar pausa
        if(recursoDao.pausa()){
            return;
        }

        /* Cuarto: determinar el actor político pedido. El Front End tiene que mandarme un int.
         * 1: para actor político 1, 2 para actor político 2, y 3 para actor político 3 (gobierno nacional)*/
        int recursosAPagar = recursoDao.valorAumentarEstatus(traido);

        // Quinto: pagar. El Front-End tiene que mandar 1 de cada recurso elegido, si no le va a restar lo que manden. Hay que escribir la reacción si no alcanza.
        boolean pagado = recursoDao.pagar(traido, ciudad, recursosAPagar);

        if(pagado){

            // Sexto: aumentar estatus en la base.
            recursoDao.aumentarEstatus(ciudad);

            // Séptimo: aumentar nivel de actor político.
            recursoDao.crecerActorPolitico(ciudad, recursosAPagar);
        }
    }
}