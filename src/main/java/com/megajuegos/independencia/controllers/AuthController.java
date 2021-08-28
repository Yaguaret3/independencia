package com.megajuegos.independencia.controllers;

import com.megajuegos.independencia.dao.UsuariosDao;
import com.megajuegos.independencia.models.UsuarioModel;
import com.megajuegos.independencia.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UsuariosDao usuarioDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public String login(@RequestBody UsuarioModel usuario){

        UsuarioModel usuarioALoguear = usuarioDao.obtenerUsuarioPorCredenciales(usuario);

        if(usuarioALoguear != null){
            String token = jwtUtil.create(String.valueOf(usuarioALoguear.getId()), usuarioALoguear.getEmail());
            return token;
        }

        return "FAIL";
    }

}
