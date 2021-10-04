package com.megajuegos.independencia.controllers;

import com.megajuegos.independencia.dao.UsuariosDao;
import com.megajuegos.independencia.models.LoginModel;
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
    public LoginModel login(@RequestBody UsuarioModel usuario){

        LoginModel respuesta = new LoginModel();
        UsuarioModel usuarioALoguear = usuarioDao.obtenerUsuarioPorCredenciales(usuario);

        if(usuarioALoguear != null){
            String token = jwtUtil.create(String.valueOf(usuarioALoguear.getRol()), usuarioALoguear.getCiudad());
            respuesta.setToken(token);
            if(usuarioALoguear.getRol().equals("control")){
                respuesta.setUrl("/control.html");
            } else if(usuarioALoguear.getRol().equals("gobernador")){
                respuesta.setUrl("/gobernadores.html");
            } else if(usuarioALoguear.getRol().equals("capitan")){
                respuesta.setUrl("/capitanes.html");
            }
            return respuesta;
        }
        return null;

        /*String targetUrl = "";
        if(role.contains("client")) {
            targetUrl = "/client/index";
        } else if(role.contains("agency")) {
            targetUrl = "/agency/index";
        }
        return targetUrl;

        @RequestMapping(value = "/redirect", method = RequestMethod.GET)
            public ModelAndView method() {
            return new ModelAndView("redirect:" + projectUrl);
        }

        @RequestMapping(value = "/redirect", method = RequestMethod.GET)
        public void method(HttpServletResponse httpServletResponse) {
            httpServletResponse.setHeader("Location", projectUrl);
            httpServletResponse.setStatus(302);
        }

        @RequestMapping("/to-be-redirected")
        public RedirectView localRedirect() {
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("http://www.yahoo.com");
            return redirectView;
}
        */
    }

}
