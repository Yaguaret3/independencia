package com.megajuegos.independencia.dao;

import com.megajuegos.independencia.models.ActoresPoliticosModel;
import com.megajuegos.independencia.models.OtrosModel;
import com.megajuegos.independencia.models.RecursosModel;
import com.megajuegos.independencia.models.EjercitosModel;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class ControlDaoImp implements ControlDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<RecursosModel> listarRecursos() {
        String query = "FROM RecursosModel";
        List<RecursosModel> resultado = entityManager.createQuery(query).getResultList();
        return resultado;
    }

    @Transactional
    @Override
    public List<EjercitosModel> listarEjercitos() {
        String query = "FROM EjercitosModel";
        List<EjercitosModel> resultado = entityManager.createQuery(query).getResultList();
        return resultado;
    }

    @Transactional
    @Override
    public List<ActoresPoliticosModel> listarActoresPoliticos() {
        String query = "FROM ActoresPoliticosModel";
        List<ActoresPoliticosModel> resultado = entityManager.createQuery(query).getResultList();
        return resultado;
    }

    @Override
    public void pausar() {
        OtrosModel pausa = entityManager.find(OtrosModel.class, "pausa");
        pausa.setValor(1);
    }

    @Override
    public void despausar() {
        OtrosModel pausa = entityManager.find(OtrosModel.class, "pausa");
        pausa.setValor(0);
    }

    @Override
    public void editarCiudad(String ciudad, RecursosModel actualizacion) {
        RecursosModel editado = entityManager.find(RecursosModel.class, ciudad);
        editado.setEstatus(actualizacion.getEstatus());
        editado.setCaballos(actualizacion.getCaballos());
        editado.setVacas(actualizacion.getVacas());
        editado.setHierro(actualizacion.getHierro());
        editado.setVino(actualizacion.getVino());
        editado.setYerba(actualizacion.getYerba());
        editado.setNivel_industria(actualizacion.getNivel_industria());
        editado.setNivel_mision_comercial(actualizacion.getNivel_mision_comercial());
        entityManager.merge(editado);
    }
    @Override
    public void editarEjercito(String ciudad, EjercitosModel actualizacion) {
        EjercitosModel editado = entityManager.find(EjercitosModel.class, ciudad);
        editado.setUnidades_agrupadas(actualizacion.getUnidades_agrupadas());
        editado.setUnidades_recien_llegadas(actualizacion.getUnidades_recien_llegadas());
        editado.setUbicacion_militar(actualizacion.getUbicacion_militar());
        editado.setUbicacion_comercial(actualizacion.getUbicacion_comercial());
        editado.setOficial_1(actualizacion.getOficial_1());
        editado.setOficial_2(actualizacion.getOficial_2());
        editado.setOficial_3(actualizacion.getOficial_3());
        editado.setOficial_4(actualizacion.getOficial_4());
        editado.setOficial_5(actualizacion.getOficial_5());
        entityManager.merge(editado);
    }

    @Override
    public void editarActorPolitico(String actor, ActoresPoliticosModel actualizacion) {
        ActoresPoliticosModel editado = entityManager.find(ActoresPoliticosModel.class, actor);
        editado.setValor(actualizacion.getValor());
        entityManager.merge(editado);
    }


}
