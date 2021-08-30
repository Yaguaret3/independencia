package com.megajuegos.independencia.dao;

import com.megajuegos.independencia.models.RecursosModel;
import com.megajuegos.independencia.models.UsuarioModel;
import com.sun.jna.StringArray;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
        /* Segundo: corroborar que la ley de proteccionismo o promoción industrial o la wea exista,
         *      else no puede subir a nivel 2.
         * Tercero: corroborar que los recursos le alcanzan (2 de cualquiera?)
         * Cuarto: restar recursos de la base
         * Quinto: sumar nivel de industria */

        industria.setNivel_industria(2);
        entityManager.merge(industria);
    }

    @Override
    public void aumentarMisionComercial(String ciudad) {
        RecursosModel misionComercial = entityManager.find(RecursosModel.class, ciudad);
        /* Primero: corroborar que los recursos le alcanzan
         * Segundo: restar recursos de la base
         * Tercero: sumar nivel de mision comercial */

        int misionPrevia = misionComercial.getNivel_mision_comercial();
        misionComercial.setNivel_mision_comercial(misionPrevia+1);
        entityManager.merge(misionComercial);
    }

    @Override
    public void reclutarUnidades(String ciudad) {
        RecursosModel unidades = entityManager.find(RecursosModel.class, ciudad);
        /* Primero: corroborar que los recursos le alcanzan
         * Segundo: restar recursos de la base
         * Tercero: sumar unidades */

        int unidadesPrevias = unidades.getUnidades();
        unidades.setUnidades(unidadesPrevias+3);
        entityManager.merge(unidades);
    }

    @Override
    public void contratarOficiales(String ciudad) {

        RecursosModel oficiales = entityManager.find(RecursosModel.class, ciudad);
        /* Tercero: corroborar que los recursos le alcanzan
         * Cuarto: restar recursos de la base
         * Quinto: determinar si pide nivel B o nivel C
         * Sexto: elegir por azar un oficial*/

        String[] oficialesB = {"B1", "B2", "B3", "B4", "B5"};
        int randomB = new Random().nextInt(oficialesB.length);
        String oficialBNuevo = oficialesB[randomB];

        String[] oficialesC = {"C1", "C2", "C3", "C4", "C5"};
        int randomC = new Random().nextInt(oficialesC.length);
        String oficialCNuevo = oficialesC[randomC];

        /* Sexto: sumar oficiales*/

        String oficialesPrevios = oficiales.getOficiales();
        oficiales.setOficiales(oficialesPrevios+ ", " + oficialBNuevo);
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
}
