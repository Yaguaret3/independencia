package com.megajuegos.independencia.controllers;

import com.megajuegos.independencia.dao.RecursosDao;
import com.megajuegos.independencia.models.RecursosModel;
import com.megajuegos.independencia.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "api/recursos", method = RequestMethod.POST)
    public RecursosModel listarRecursos(@RequestBody RecursosModel traido){

        String ciudad = recursoDao.corroborarCiudad(traido);

        return recursoDao.listarRecursos(ciudad);
    }

    @RequestMapping(value = "api/aumentarIndustria", method = RequestMethod.POST)
    public RecursosModel aumentarIndustria(@RequestBody RecursosModel traido){

        // Primero: corroborar permiso (token)
        String ciudad = recursoDao.corroborarCiudad(traido);

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
    public RecursosModel aumentarMisionComercial(@RequestBody RecursosModel traido){

        // Primero: corroborar permiso y vincular con ciudad (token)
        String ciudad = recursoDao.corroborarCiudad(traido);

        // Segundo: corroborar condiciones
        boolean condicionesValidas = recursoDao.industriaMenosAEstatus(ciudad);

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
    public RecursosModel reclutarUnidades (@RequestBody RecursosModel traido){

        // Primero: corroborar permiso y vincular con ciudad (token)
        String ciudad = recursoDao.corroborarCiudad(traido);

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
    public RecursosModel contratarOficiales (@RequestBody RecursosModel traido){

        // Primero: corroborar permiso y vincular con ciudad (token)
        String ciudad = recursoDao.corroborarCiudad(traido);

        // Segundo: determinar si paga un nivel B o un nivel C.
        int recursosAPagar = recursoDao.valorOficial(traido);

        // Tercero: pagar y cobrar. El Front-End tiene que mandar 1 de cada recurso elegido, si no le va a restar lo que manden. Hay que escribir la reacción si no alcanza.
        boolean pagado = recursoDao.pagar(traido, ciudad, recursosAPagar);

        if(pagado){

            // Cuarto: sumar oficiales a la base
            recursoDao.contratarOficiales(ciudad);

            // Quinto: devolver oficiales y recursos para que el explorador actualice.
            return recursoDao.listarRecursos(ciudad);
        }

        // Sexto: Si los recursos no alcanzaron, no devolver nada.
        return new RecursosModel();
    }

    @RequestMapping(value = "api/enviarUnidades", method = RequestMethod.POST)
    public RecursosModel enviarUnidades (@RequestBody RecursosModel traido){

        // Primero: corroborar permiso y vincular con ciudad (token)
        String ciudad = recursoDao.corroborarCiudad(traido);

        // Segundo: enviar unidades al capitán y limpiarlas. Hecho. Falta la parte de la tabla del capitán.
        recursoDao.enviarUnidades(ciudad);

        // Tercero: devolver unidades para que el explorador actualice.
        return recursoDao.listarRecursos(ciudad);

    }

    @RequestMapping(value = "api/enviarOficiales", method = RequestMethod.POST)
    public String enviarOficiales (@RequestBody RecursosModel traido){

        // Primero: corroborar permiso y vincular con ciudad (token)
        String ciudad = recursoDao.corroborarCiudad(traido);

        // Segundo: enviar unidades al capitán y limpiarlas. Hecho. Falta la parte de la tabla del capitán.
        recursoDao.enviarOficiales(ciudad);

        // Tercero: devolver unidades para que el explorador actualice.
        return "Corroborá con el Capitán que se haya enviado";

    }

    @RequestMapping(value = "api/elegirCiudad", method = RequestMethod.POST)
    public String devolucion (@RequestBody String ciudad){

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1, 1024, 1, ciudad);
        return hash;

    }

    @RequestMapping(value = "api/comerciar", method = RequestMethod.POST)
    public RecursosModel comerciar (@RequestBody RecursosModel traido){

        // Primero: corroborar permiso y vincular con ciudad (token)
        String ciudad = recursoDao.corroborarCiudad(traido);

        // Segundo: comerciar (restarle a uno y sumarle al otro)
        recursoDao.comerciar(traido, ciudad);

        // Tercero: devolver unidades para que el explorador actualice.
        return recursoDao.listarRecursos(ciudad);

    }

    @RequestMapping(value = "api/aumentarEstatus", method = RequestMethod.POST)
    public RecursosModel aumentarEstatus (@RequestBody RecursosModel traido){

        // Primero: corroborar permiso y vincular con ciudad (token)
        String ciudad = recursoDao.corroborarCiudad(traido);

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