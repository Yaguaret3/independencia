package com.megajuegos.independencia.controllers;

import com.megajuegos.independencia.dao.UsuariosDao;
import com.megajuegos.independencia.models.UsuarioModel;
import com.megajuegos.independencia.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UsuariosDao usuarioDao;

    private boolean validarToken(String token){

        //getKey: es para tomar el primer dato enviado (en este caso el rol)
        //getValue: es para tomar el segundo dato enviado (en este caso la ciudad)
        String usuarioID = jwtUtil.getValue(token);
        return usuarioID != null;

    }

    @RequestMapping(value = "api/listarUsuarios", method = RequestMethod.GET)
    public List<UsuarioModel> listarUsuarios(@RequestHeader(value = "Authorization") String token){

        if(!validarToken(token)){
            return new ArrayList<>();
        }

        return usuarioDao.recorrerUsuarios();

    }

    @RequestMapping(value = "editarUsuario/{ID}")
    public UsuarioModel editarUsuario(@PathVariable Long id){
        UsuarioModel usuario = new UsuarioModel();
        usuario.setId(id);
        usuario.setNombre("Nicolás");
        usuario.setApellido("Binkis");
        usuario.setNick("Nickelaos");
        usuario.setPassword("megajuegos");

        return usuario;
    }

    @RequestMapping(value = "eliminarUsuario/{id}", method = RequestMethod.DELETE)
    public void eliminarUsuario(@PathVariable Long id,
                                @RequestHeader(value = "Authorization") String token){

        if(!validarToken(token)){
            return;
        }

        usuarioDao.eliminarUsuario(id);
    }

    @RequestMapping(value = "registrarUsuario", method = RequestMethod.POST)
    public void registrarUsuario(@RequestBody UsuarioModel usuario){

        //Se hashea acá en el Controller.
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1, 1024, 1, usuario.getPassword());

        usuario.setPassword(hash);
        usuarioDao.registrarUsuario(usuario);
    }

}
