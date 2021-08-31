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

    @RequestMapping(value = "recursos", method = RequestMethod.POST)
    public RecursosModel traerRecursos(@RequestBody RecursosModel traido){

        String ciudad = recursoDao.corroborarCiudad(traido);

        return recursoDao.listarRecursos(ciudad);
    }

    @RequestMapping(value = "aumentarIndustria", method = RequestMethod.POST)
    public RecursosModel aumentarIndustria(@RequestBody RecursosModel traido){

        // Primero: corroborar permiso (token)
        String ciudad = recursoDao.corroborarCiudad(traido);

        // Segundo: corroborar condiciones
        boolean condicionesValidas = true;

        if(condicionesValidas){

            // Tercero: pagar y cobrar.
            boolean pagado = recursoDao.pagar(traido, ciudad);

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

    @RequestMapping(value = "aumentarMisionComercial", method = RequestMethod.POST)
    public RecursosModel aumentarMisionComercial(@RequestBody RecursosModel traido){

        // Primero: corroborar permiso y vincular con ciudad (token)
        String ciudad = recursoDao.corroborarCiudad(traido);

        // Segundo: corroborar condiciones
        boolean condicionesValidas = recursoDao.industriaMenosAEstatus(ciudad);

        if(condicionesValidas){

            // Tercero: pagar y cobrar. El Front-End tiene que mandar 1 de cada recurso elegido, si no le va a restar lo que manden. Hay que escribir la reacción si no alcanza.
            boolean pagado = recursoDao.pagar(traido, ciudad);

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

    @RequestMapping(value = "reclutarUnidades", method = RequestMethod.POST)
    public RecursosModel reclutarUnidades (@RequestBody RecursosModel traido){

        // Primero: corroborar permiso y vincular con ciudad (token)
        String ciudad = recursoDao.corroborarCiudad(traido);

        // Segundo: pagar y cobrar. El Front-End tiene que mandar 1 de cada recurso elegido, si no le va a restar lo que manden. Hay que escribir la reacción si no alcanza.
        boolean pagado = recursoDao.pagar(traido, ciudad);

        if(pagado){

            // Tercero: sumar unidades a la base
            recursoDao.reclutarUnidades(ciudad);

            // Cuarto: devolver unidades y recursos para que el explorador actualice.
            return recursoDao.listarRecursos(ciudad);
        }

        // Quinto: Si los recursos no alcanzaron, no devolver nada.
        return new RecursosModel();
    }

    @RequestMapping(value = "contratarOficiales", method = RequestMethod.POST)
    public RecursosModel contratarOficiales (@RequestBody RecursosModel traido){

        // Primero: corroborar permiso y vincular con ciudad (token)
        String ciudad = recursoDao.corroborarCiudad(traido);

        // Segundo: pagar y cobrar. El Front-End tiene que mandar 1 de cada recurso elegido, si no le va a restar lo que manden. Hay que escribir la reacción si no alcanza.
        boolean pagado = recursoDao.pagar(traido, ciudad);

        if(pagado){

            // Tercero: sumar oficiales a la base
            recursoDao.contratarOficiales(ciudad);

            // Cuarto: devolver oficiales y recursos para que el explorador actualice.
            return recursoDao.listarRecursos(ciudad);
        }

        // Quinto: Si los recursos no alcanzaron, no devolver nada.
        return new RecursosModel();
    }

    @RequestMapping(value = "enviarUnidades", method = RequestMethod.POST)
    public RecursosModel enviarUnidades (@RequestBody RecursosModel traido){

        // Primero: corroborar permiso y vincular con ciudad (token)
        String ciudad = recursoDao.corroborarCiudad(traido);

        // Segundo: enviar unidades al capitán y limpiarlas. Hecho. Falta la parte de la tabla del capitán.
        recursoDao.enviarUnidades(ciudad);

        // Tercero: devolver unidades para que el explorador actualice.
        return recursoDao.listarRecursos(ciudad);

    }

    @RequestMapping(value = "enviarOficiales", method = RequestMethod.POST)
    public String enviarOficiales (@RequestBody RecursosModel traido){

        // Primero: corroborar permiso y vincular con ciudad (token)
        String ciudad = recursoDao.corroborarCiudad(traido);

        // Segundo: enviar unidades al capitán y limpiarlas. Hecho. Falta la parte de la tabla del capitán.
        recursoDao.enviarOficiales(ciudad);

        // Tercero: devolver unidades para que el explorador actualice.
        return "Corroborá con el Capitán que se haya enviado";

    }

    @RequestMapping(value = "pruebaDameHashDeCiudad", method = RequestMethod.POST)
    public String devolucion (){

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1, 1024, 1, "Asunción");
        return hash;

    }
}