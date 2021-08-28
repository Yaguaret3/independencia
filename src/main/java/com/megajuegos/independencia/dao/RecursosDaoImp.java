package com.megajuegos.independencia.dao;

import com.megajuegos.independencia.models.RecursosModel;
import com.megajuegos.independencia.models.UsuarioModel;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class RecursosDaoImp implements RecursosDao{

    @PersistenceContext
    //Sirve para hacer la conexión con la base de datos.
    private EntityManager entityManager;

    @Transactional
    @Override
    public List<RecursosModel> listarRecursos(String ciudad) {
        String query = "FROM RecursosModel WHERE ciudad = ciudad";
        List<RecursosModel> resultado = entityManager.createQuery(query).getResultList();
        return resultado;
    }
}
