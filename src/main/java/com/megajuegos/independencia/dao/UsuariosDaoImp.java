package com.megajuegos.independencia.dao;

import com.megajuegos.independencia.models.UsuarioModel;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class UsuariosDaoImp implements UsuariosDao{

    @PersistenceContext
    //Sirve para hacer la conexión con la base de datos.
    private EntityManager entityManager;


    @Override
    @Transactional
    public List<UsuarioModel> recorrerUsuarios() {
        String query = "FROM UsuarioModel";
        List<UsuarioModel> resultado = entityManager.createQuery(query).getResultList();
        return resultado;

    }

    @Override
    public void eliminarUsuario(Long id) {
        UsuarioModel usuario = entityManager.find(UsuarioModel.class, id);
        entityManager.remove(usuario);
    }

    @Override
    public void registrarUsuario(UsuarioModel usuario) {

        entityManager.merge(usuario);
    }

    @Override
    public UsuarioModel obtenerUsuarioPorCredenciales(UsuarioModel usuario) {
        String query = "FROM UsuarioModel WHERE email = :email";
        List<UsuarioModel> lista = entityManager.createQuery(query)
                .setParameter("email", usuario.getEmail())
                .getResultList();

        if (lista.isEmpty()) {
            return null;
        }

        String passwordHashedEnBase = lista.get(0).getPassword();

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if (argon2.verify(passwordHashedEnBase, usuario.getPassword())) {
            return lista.get(0);
        } else {
            return null;
        }

    }
}
