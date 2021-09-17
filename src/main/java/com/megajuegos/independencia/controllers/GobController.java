package com.megajuegos.independencia.controllers;

import com.megajuegos.independencia.dao.ControlDao;
import com.megajuegos.independencia.dao.RecursosDao;
import com.megajuegos.independencia.models.ActoresPoliticosModel;
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

    @Autowired
    private ControlDao controlDao;


    @RequestMapping(value = "api/gobernadores/cargarRecursos", method = RequestMethod.POST)
    public RecursosModel listarRecursos(@RequestHeader(value = "Authorization") String token){

        String ciudad = recursoDao.corroborarCiudad(token);

        return recursoDao.listarRecursos(ciudad);
    }

    @RequestMapping(value = "api/gobernadores/listarActoresPoliticos")
    public List<ActoresPoliticosModel> listarActoresPoliticos(@RequestHeader(value = "Authorization") String token){

        // Primero: Corroborar que el pedido lo hace un gobernador

        if(!jwtUtil.getKey(token).equals("gobernador")){
            return new ArrayList<>();
        };

        // Segundo: Listar recursos y devolver

        return controlDao.listarActoresPoliticos();
    }

    @RequestMapping(value = "api/aumentarIndustria", method = RequestMethod.POST)
    public RecursosModel aumentarIndustria(@RequestHeader(value = "Authorization") String token, @RequestBody RecursosModel traido){

        // Primero: corroborar permiso (token)
        String ciudad = recursoDao.corroborarCiudad(token);

        // Segundo: corroborar condiciones
        boolean condicionesValidas = true;

        if(condicionesValidas){

            // Tercero: pagar y cobrar.
            int recursosAPagar = 2;
            boolean pagado = recursoDao.pagar(traido, ciudad, recursosAPagar);

            if(pagado){

            /* Cuarto: aumentar el nivel de industria en la base
             * Esto anda, nomás hay que devolverle al usuario qué onda si hubo un inconveniente por recursos o ley o nivel ya subido*/
                recursoDao.aumentarIndustria(ciudad);

                // Quinto: devolver nivel de industria y recursos para que el explorador actualice.
                return recursoDao.listarRecursos(ciudad);
            }

            // Sexto: Si los recursos no alcanzaron, no devolver nada.
            return new RecursosModel();
        }
        // Séptimo: Si las condiciones no son válidas, no devolver nada.
        return new RecursosModel();
    }

    @RequestMapping(value = "api/aumentarMisionComercial", method = RequestMethod.POST)
    public RecursosModel aumentarMisionComercial(@RequestHeader(value = "Authorization") String token, @RequestBody RecursosModel traido){

        // Primero: corroborar permiso y vincular con ciudad (token)
        String ciudad = recursoDao.corroborarCiudad(token);

        // Segundo: corroborar condiciones
        boolean condicionesValidas = recursoDao.misionMenosAEstatus(ciudad);

        if(condicionesValidas){

            // Tercero: pagar y cobrar. El Front-End tiene que mandar 1 de cada recurso elegido, si no le va a restar lo que manden. Hay que escribir la reacción si no alcanza.
            int recursosAPagar = 1;
            boolean pagado = recursoDao.pagar(traido, ciudad, recursosAPagar);

            if(pagado){

                // Cuarto: aumentar el nivel de misión comercial en la base. Fijarse antes ley al respecto.
                recursoDao.aumentarMisionComercial(ciudad);

                // Quinto: devolver nivel de industria y recursos para que el explorador actualice.
                return recursoDao.listarRecursos(ciudad);
            }
            // Sexto: Si los recursos no alcanzaron, no devolver nada.
            return new RecursosModel();
        }
        // Séptimo: Si las condiciones no son válidas, no devolver nada.
        return new RecursosModel();
    }

    @RequestMapping(value = "api/reclutarUnidades", method = RequestMethod.POST)
    public RecursosModel reclutarUnidades (@RequestHeader(value = "Authorization") String token, @RequestBody RecursosModel traido){

        // Primero: corroborar permiso y vincular con ciudad (token)
        String ciudad = recursoDao.corroborarCiudad(token);

        // Segundo: pagar y cobrar. El Front-End tiene que mandar 1 de cada recurso elegido, si no le va a restar lo que manden. Hay que escribir la reacción si no alcanza.
        int recursosAPagar = 1;
        boolean pagado = recursoDao.pagar(traido, ciudad, recursosAPagar);

        if(pagado){

            // Tercero: sumar unidades a la base
            recursoDao.reclutarUnidades(ciudad);

            // Cuarto: devolver unidades y recursos para que el explorador actualice.
            return recursoDao.listarRecursos(ciudad);
        }

        // Quinto: Si los recursos no alcanzaron, no devolver nada.
        return new RecursosModel();
    }

    @RequestMapping(value = "api/contratarOficiales", method = RequestMethod.POST)
    public RecursosModel contratarOficiales (@RequestHeader(value = "Authorization") String token, @RequestBody RecursosModel traido){

        // Primero: corroborar permiso y vincular con ciudad (token)
        String ciudad = recursoDao.corroborarCiudad(token);

        // Segundo: determinar si paga un nivel B o un nivel C.
        int recursosAPagar = recursoDao.valorOficial(traido);

        // Tercero: pagar y cobrar. El Front-End tiene que mandar 1 de cada recurso elegido, si no le va a restar lo que manden. Hay que escribir la reacción si no alcanza.
        boolean pagado = recursoDao.pagar(traido, ciudad, recursosAPagar);

        if(pagado){

            /* Cuarto: sumar oficiales a la base. Habría que mandar esos oficiales a la tabla de capitán, pero no presentes
             * sino potenciales, para que al cambiar del turno se actualicen.
             */
            recursoDao.contratarOficiales(ciudad);

            // Quinto: devolver oficiales y recursos para que el explorador actualice.
            return recursoDao.listarRecursos(ciudad);
        }

        // Sexto: Si los recursos no alcanzaron, no devolver nada.
        return new RecursosModel();
    }

    @RequestMapping(value = "api/enviarUnidades", method = RequestMethod.POST)
    public RecursosModel enviarUnidades (@RequestHeader(value = "Authorization") String token){

        // Primero: corroborar permiso y vincular con ciudad (token)
        String ciudad = recursoDao.corroborarCiudad(token);

        // Segundo: enviar unidades al capitán y limpiarlas. Hecho. Falta la parte de la tabla del capitán.
        recursoDao.enviarUnidades(ciudad);

        // Tercero: devolver unidades para que el explorador actualice.
        return recursoDao.listarRecursos(ciudad);

    }

    @RequestMapping(value = "api/comerciar", method = RequestMethod.POST)
    public RecursosModel comerciar (@RequestHeader(value = "Authorization") String token, @RequestBody RecursosModel traido){

        // Primero: corroborar permiso y vincular con ciudad (token)
        String ciudad = recursoDao.corroborarCiudad(token);

        // Segundo: comerciar (restarle a uno y sumarle al otro)
        recursoDao.comerciar(traido, ciudad);

        // Tercero: devolver unidades para que el explorador actualice.
        return recursoDao.listarRecursos(ciudad);

    }

    @RequestMapping(value = "api/aumentarEstatus", method = RequestMethod.POST)
    public RecursosModel aumentarEstatus (@RequestHeader(value = "Authorization") String token, @RequestBody RecursosModel traido){

        // Primero: corroborar permiso y vincular con ciudad (token)
        String ciudad = recursoDao.corroborarCiudad(token);

        /* Segundo: determinar el actor político pedido. El Front End tiene que mandarme un int.
         * 1: para actor político 1, 2 para actor político 2, y 3 para actor político 3 (gobierno nacional)*/
        int recursosAPagar = recursoDao.valorAumentarEstatus(traido);

        // Tercero: pagar. El Front-End tiene que mandar 1 de cada recurso elegido, si no le va a restar lo que manden. Hay que escribir la reacción si no alcanza.
        boolean pagado = recursoDao.pagar(traido, ciudad, recursosAPagar);

        if(pagado){

            // Cuarto: aumentar estatus en la base.
            recursoDao.aumentarEstatus(ciudad);

            // Quinto: aumentar nivel de actor político.
            recursoDao.crecerActorPolitico(ciudad, recursosAPagar);
        }

        // Sexto: devolver unidades para que el explorador actualice.
        return recursoDao.listarRecursos(ciudad);

    }




}