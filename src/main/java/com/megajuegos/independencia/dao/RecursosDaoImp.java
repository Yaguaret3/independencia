package com.megajuegos.independencia.dao;

import com.megajuegos.independencia.models.RecursosModel;
import com.megajuegos.independencia.models.UsuarioModel;
import com.sun.jna.StringArray;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Array;
import java.util.List;
import java.util.Random;

@Repository
@Transactional
public class RecursosDaoImp implements RecursosDao{

    @PersistenceContext
    //Sirve para hacer la conexión con la base de datos.
    private EntityManager entityManager;


    @Override
    public RecursosModel listarRecursos(String ciudad) {

        RecursosModel recursos = entityManager.find(RecursosModel.class, ciudad);

        return recursos;

    }

    @Override
    public void aumentarIndustria(String ciudad) {

        RecursosModel industria = entityManager.find(RecursosModel.class, ciudad);

        /* Primero: corroborar que está en nivel 1,
         *      else no puede subir más que a nivel 2.*/
        if(industria.getNivel_industria() != 1){
                        return;
        }
        // Segundo: corroborar que la ley de proteccionismo o promoción industrial o la wea exista,

        // Tercero: corroborar que el nivel de estatus sea el adecuado.

        // Tercero: sumar nivel de industria
        industria.setNivel_industria(2);
        entityManager.merge(industria);
    }

    @Override
    public void aumentarMisionComercial(String ciudad) {
        RecursosModel misionComercial = entityManager.find(RecursosModel.class, ciudad);

        int misionPrevia = misionComercial.getNivel_mision_comercial();

        misionComercial.setNivel_mision_comercial(misionPrevia+1);
        entityManager.merge(misionComercial);
    }

    @Override
    public void reclutarUnidades(String ciudad) {
        RecursosModel unidades = entityManager.find(RecursosModel.class, ciudad);

        int unidadesPrevias = unidades.getUnidades();
        unidades.setUnidades(unidadesPrevias+3);
        entityManager.merge(unidades);
    }

    @Override
    public void contratarOficiales(String ciudad) {

        RecursosModel oficiales = entityManager.find(RecursosModel.class, ciudad);

        String oficialNuevo = "";

        // Primero: determinar si pide nivel B o nivel C
        if (oficiales.getNivel_oficial_pedido() == "B"){

            // Segundo: elegir por azar un oficial
            String[] oficialesB = {"B1", "B2", "B3", "B4", "B5"};
            int randomB = new Random().nextInt(oficialesB.length);
            oficialNuevo = oficialesB[randomB];

        } else if (oficiales.getNivel_oficial_pedido() == "C"){

            String[] oficialesC = {"C1", "C2", "C3", "C4", "C5"};
            int randomC = new Random().nextInt(oficialesC.length);
            oficialNuevo = oficialesC[randomC];
        }

        /* Tercero: sumar oficiales*/
        String oficialesPrevios = oficiales.getOficiales();
        if(oficialesPrevios == ""){
            oficiales.setOficiales(oficialNuevo);
        } else {
            oficiales.setOficiales(oficialesPrevios + ", " + oficialNuevo);
        }
        entityManager.merge(oficiales);
    }

    @Override
    public void enviarUnidades(String ciudad) {

        // Primero: Tomar unidades reclutadas y guardarlas

        RecursosModel unidades = entityManager.find(RecursosModel.class, ciudad);
        int unidadesEnviadas = unidades.getUnidades();

        // Segundo: Eliminar unidades reclutadas y sumar unidades enviadas

        unidades.setUnidades(0);
        int unidadesEnviadasTotal = unidades.getUnidades_enviadas();
        unidadesEnviadasTotal = unidadesEnviadasTotal + unidadesEnviadas;
        entityManager.merge(unidades);

        // Tercero: Sumar unidades enviadas a la tabla del Capitán

    }

    @Override
    public boolean pagar(RecursosModel traido, String ciudad) {

        RecursosModel ciudadTotal = entityManager.find(RecursosModel.class, ciudad);

        boolean banderaRecurso1 = false;
        boolean banderaRecurso2 = false;

        if(ciudadTotal.getCaballos() >= traido.getCaballos() && traido.getCaballos() > 0){
            ciudadTotal.setCaballos(ciudadTotal.getCaballos() - traido.getCaballos());
            if(banderaRecurso1){
                banderaRecurso2 = true;
            } else{
                banderaRecurso1 = true;
            }
        }
        if(banderaRecurso2){
            entityManager.merge(ciudadTotal);
            return true;
        }
        if(ciudadTotal.getVacas() >= traido.getVacas() && traido.getVacas() > 0){
            ciudadTotal.setVacas(ciudadTotal.getVacas() - traido.getVacas());
            if(banderaRecurso1){
                banderaRecurso2 = true;
            } else{
                banderaRecurso1 = true;
            }
        }
        if(banderaRecurso2){
            entityManager.merge(ciudadTotal);
            return true;
        }
        if(ciudadTotal.getHierro() >= traido.getHierro() && traido.getHierro() > 0){
            ciudadTotal.setHierro(ciudadTotal.getHierro() - traido.getHierro());
            if(banderaRecurso1){
                banderaRecurso2 = true;
            } else{
                banderaRecurso1 = true;
            }
        }
        if(banderaRecurso2){
            entityManager.merge(ciudadTotal);
            return true;
        }
        if(ciudadTotal.getVino() >= traido.getVino() && traido.getVino() > 0){
            ciudadTotal.setVino(ciudadTotal.getVino() - traido.getVino());
            if(banderaRecurso1){
                banderaRecurso2 = true;
            } else{
                banderaRecurso1 = true;
            }
        }
        if(banderaRecurso2){
            entityManager.merge(ciudadTotal);
            return true;
        }
        if(ciudadTotal.getYerba() >= traido.getYerba() && traido.getYerba() > 0){
            ciudadTotal.setYerba(ciudadTotal.getYerba() - traido.getYerba());
            if(banderaRecurso1){
                banderaRecurso2 = true;
            } else{
                banderaRecurso1 = true;
            }
        }
        if(banderaRecurso2){
            entityManager.merge(ciudadTotal);
            return true;
        }

        return false;

    }

    @Override
    public String corroborarCiudad(RecursosModel traido) {

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

        String[] ciudad = {"Buenos Aires", "Montevideo", "Asunción", "Santa Fe", "Córdoba", "Mendoza", "Tucumán", "Salta", "Potosí", "La Paz"};

        for(int i=0; i < ciudad.length; i++){
           if (argon2.verify(traido.getCiudad(), ciudad[i])) {
                    return ciudad[i];
           }
        }
        return "Error al corroborar ciudad";


    }

    @Override
    public void enviarOficiales(String ciudad) {
        //Laburar con la tabla de Capitanes
    }

    @Override
    public boolean industriaMenosAEstatus(String ciudad) {
        // Primero: Identificar ciudad
        RecursosModel recursos = entityManager.find(RecursosModel.class, ciudad);
        int industria = recursos.getNivel_industria();
        int estatus = recursos.getEstatus();

        if(estatus > industria){
            return true;
        }
        return false;
    }
}
