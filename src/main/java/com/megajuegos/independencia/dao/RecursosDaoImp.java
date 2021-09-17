package com.megajuegos.independencia.dao;

import com.megajuegos.independencia.models.ActoresPoliticosModel;
import com.megajuegos.independencia.models.EjercitosModel;
import com.megajuegos.independencia.models.RecursosModel;
import com.megajuegos.independencia.models.UsuarioModel;
import com.megajuegos.independencia.utils.JWTUtil;
import com.sun.jna.StringArray;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    JWTUtil jwtUtil;

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

        // : sumar nivel de industria
        industria.setNivel_industria(2);
        entityManager.merge(industria);
    }

    @Override
    public void aumentarMisionComercial(String ciudad) {

        // Primero: aumentar mision comercial en tabla gobernadores

        RecursosModel misionComercial = entityManager.find(RecursosModel.class, ciudad);
        misionComercial.setNivel_mision_comercial(misionComercial.getNivel_mision_comercial()+1);;
        entityManager.merge(misionComercial);

        // Segundo: aumentar mision comercial en tabla capitanes

        EjercitosModel tablaEjercitos = entityManager.find(EjercitosModel.class, ciudad);
        tablaEjercitos.setNivel_mision_comercial(misionComercial.getNivel_mision_comercial());
        entityManager.merge(tablaEjercitos);

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

        // Primero: determinar si pide nivel 2 o nivel 3
        if (oficiales.getNivel_oficial_pedido() == "2"){

            // Segundo: elegir por azar un oficial
            String[] oficialesB = {"A2", "B2", "C2", "D2", "E2"};
            int randomB = new Random().nextInt(oficialesB.length);
            oficialNuevo = oficialesB[randomB];

        } else if (oficiales.getNivel_oficial_pedido() == "3"){

            String[] oficialesC = {"A3", "B3", "C3", "D3", "E3"};
            int randomC = new Random().nextInt(oficialesC.length);
            oficialNuevo = oficialesC[randomC];
        }

        // Tercero: sumar oficiales a la lista para que vea el gobernador
        String oficialesPrevios = oficiales.getOficiales();
        if(oficialesPrevios == ""){
            oficiales.setOficiales(oficialNuevo);
        } else {
            oficiales.setOficiales(oficialesPrevios + ", " + oficialNuevo);
        }
        entityManager.merge(oficiales);

        // Cuarto: sumar oficial (en potencia hasta el comienzo del próximo turno) al capitán
        EjercitosModel oficialAlCapitan = entityManager.find(EjercitosModel.class, ciudad);

        switch (oficialNuevo) {
            case "A2":
                oficialAlCapitan.setNuevo_oficial_a("2");
                break;
            case "B2":
                oficialAlCapitan.setNuevo_oficial_b("2");
                break;
            case "C2":
                oficialAlCapitan.setNuevo_oficial_c("2");
                break;
            case "D2":
                oficialAlCapitan.setNuevo_oficial_d("2");
                break;
            case "E2":
                oficialAlCapitan.setNuevo_oficial_e("2");
                break;
            case "A3":
                oficialAlCapitan.setNuevo_oficial_a("3");
                break;
            case "B3":
                oficialAlCapitan.setNuevo_oficial_b("3");
                break;
            case "C3":
                oficialAlCapitan.setNuevo_oficial_c("3");
                break;
            case "D3":
                oficialAlCapitan.setNuevo_oficial_d("3");
                break;
            case "E3":
                oficialAlCapitan.setNuevo_oficial_e("3");
                break;
        }
        entityManager.merge(oficialAlCapitan);


    }

    @Override
    public void enviarUnidades(String ciudad) {

        // Primero: Tomar unidades reclutadas y guardarlas

        RecursosModel unidades = entityManager.find(RecursosModel.class, ciudad);
        int unidadesEnviadas = unidades.getUnidades();

        // Segundo: Eliminar unidades reclutadas y sumar unidades enviadas

        unidades.setUnidades(0);
        unidades.setUnidades_enviadas(unidades.getUnidades_enviadas() + unidadesEnviadas);
        entityManager.merge(unidades);

        // Tercero: Sumar unidades enviadas a la tabla del Capitán

        EjercitosModel unidadesNuevas = entityManager.find(EjercitosModel.class, ciudad);
        unidadesNuevas.setUnidades_recien_llegadas(unidadesNuevas.getUnidades_recien_llegadas() + unidadesEnviadas);
        entityManager.merge(unidadesNuevas);


    }

    @Override
    public boolean pagar(RecursosModel traido, String ciudad, int recursosAPagar) {

        RecursosModel ciudadTotal = entityManager.find(RecursosModel.class, ciudad);

        int recursosPagados = 0;

        if(ciudadTotal.getCaballos() >= traido.getCaballos() && traido.getCaballos() > 0){
            ciudadTotal.setCaballos(ciudadTotal.getCaballos() - traido.getCaballos());
            recursosPagados++;
        }
        if(recursosPagados == recursosAPagar){
            entityManager.merge(ciudadTotal);
            return true;
        }
        if(ciudadTotal.getVacas() >= traido.getVacas() && traido.getVacas() > 0){
            ciudadTotal.setVacas(ciudadTotal.getVacas() - traido.getVacas());
            recursosPagados++;
        }
        if(recursosPagados == recursosAPagar){
            entityManager.merge(ciudadTotal);
            return true;
        }
        if(ciudadTotal.getHierro() >= traido.getHierro() && traido.getHierro() > 0){
            ciudadTotal.setHierro(ciudadTotal.getHierro() - traido.getHierro());
            recursosPagados++;
        }
        if(recursosPagados == recursosAPagar){
            entityManager.merge(ciudadTotal);
            return true;
        }
        if(ciudadTotal.getVino() >= traido.getVino() && traido.getVino() > 0){
            ciudadTotal.setVino(ciudadTotal.getVino() - traido.getVino());
            recursosPagados++;
        }
        if(recursosPagados == recursosAPagar){
            entityManager.merge(ciudadTotal);
            return true;
        }
        if(ciudadTotal.getYerba() >= traido.getYerba() && traido.getYerba() > 0){
            ciudadTotal.setYerba(ciudadTotal.getYerba() - traido.getYerba());
            recursosPagados++;
        }
        if(recursosPagados == recursosAPagar){
            entityManager.merge(ciudadTotal);
            return true;
        }

        return false;

    }

    @Override
    public String corroborarCiudad(String token) {

        String ciudadTraida = jwtUtil.getValue(token);

        String[] ciudadReal = {"Buenos Aires", "Montevideo", "Asunción", "Santa Fe", "Córdoba", "Mendoza", "Tucumán", "Salta", "Potosí", "La Paz"};

        for(int i=0; i < ciudadReal.length; i++){
           if (ciudadTraida.equals(ciudadReal[i])) {
                    return ciudadReal[i];
           }
        }
        return "Error al corroborar ciudad";


    }

    @Override
    public boolean misionMenosAEstatus(String ciudad) {

        // Primero: Identificar ciudad
        RecursosModel recursos = entityManager.find(RecursosModel.class, ciudad);
        int misionComercial = recursos.getNivel_mision_comercial();
        int estatus = recursos.getEstatus();

        if(estatus > misionComercial){
            return true;
        }
        return false;
    }

    @Override
    public void comerciar(RecursosModel traido, String ciudad) {

        boolean caballos = false;
        boolean vacas = false;
        boolean hierro = false;
        boolean vino = false;
        boolean yerba = false;

        // Primero: Identificar ciudad y destino
        RecursosModel emisora = entityManager.find(RecursosModel.class, ciudad);
        RecursosModel destino = entityManager.find(RecursosModel.class, traido.getDestino_comercial());

        // Segundo: Corroborar que alcancen los recursos
        if(emisora.getCaballos() >= traido.getCaballos() && traido.getCaballos() > 0){
            caballos = true;
        }
        if(emisora.getVacas() >= traido.getVacas() && traido.getVacas() > 0){
            vacas = true;
        }
        if(emisora.getHierro() >= traido.getHierro() && traido.getHierro() > 0){
            hierro = true;
        }
        if(emisora.getVino() >= traido.getVino() && traido.getVino() > 0){
            vino = true;
        }
        if(emisora.getYerba() >= traido.getYerba() && traido.getYerba() > 0){
            yerba = true;
        }

        // Tercero: Sumar a uno, restar al otro y sumarlo al historial de cada.
        if (caballos) {
            destino.setCaballos(destino.getCaballos() + traido.getCaballos());
            emisora.setCaballos(emisora.getCaballos() - traido.getCaballos());
            destino.setHistorial_comercial(destino.getHistorial_comercial() + "<p>Has recibido "+ traido.getCaballos() +" caballos de "+ ciudad +".</p></br>");
            emisora.setHistorial_comercial(emisora.getHistorial_comercial() + "<p>Has enviado "+ traido.getCaballos() +" caballos a "+ destino.getCiudad() +".</p></br>");
        }
        if (vacas) {
            destino.setVacas(destino.getVacas() + traido.getVacas());
            emisora.setVacas(emisora.getVacas() - traido.getVacas());
            destino.setHistorial_comercial(destino.getHistorial_comercial() + "<p>Has recibido "+ traido.getVacas() +" vaca(s) de "+ ciudad +".</p></br>");
            emisora.setHistorial_comercial(emisora.getHistorial_comercial() + "<p>Has enviado "+ traido.getVacas() +" vaca(s) a "+ destino.getCiudad() +".</p></br>");
        }
        if (hierro) {
            destino.setHierro(destino.getHierro() + traido.getHierro());
            emisora.setHierro(emisora.getHierro() - traido.getHierro());
            destino.setHistorial_comercial(destino.getHistorial_comercial() + "<p>Has recibido "+ traido.getHierro() +" unidades de hierro de "+ ciudad +".</p></br>");
            emisora.setHistorial_comercial(emisora.getHistorial_comercial() + "<p>Has enviado "+ traido.getHierro() +" unidades de hierro a "+ destino.getCiudad() +".</p></br>");
        }
        if (vino) {
            destino.setVino(destino.getVino() + traido.getVino());
            emisora.setVino(emisora.getVino() - traido.getVino());
            destino.setHistorial_comercial(destino.getHistorial_comercial() + "<p>Has recibido "+ traido.getVino() +" unidades de vino de "+ ciudad +".</p></br>");
            emisora.setHistorial_comercial(emisora.getHistorial_comercial() + "<p>Has enviado "+ traido.getVino() +" unidades de vino a "+ destino.getCiudad() +".</p></br>");
        }
        if (yerba) {
            destino.setYerba(destino.getYerba() + traido.getYerba());
            emisora.setYerba(emisora.getYerba() - traido.getYerba());
            destino.setHistorial_comercial(destino.getHistorial_comercial() + "<p>Has recibido "+ traido.getYerba() +" unidades de yerba de "+ ciudad +".</p></br>");
            emisora.setHistorial_comercial(emisora.getHistorial_comercial() + "<p>Has enviado "+ traido.getYerba() +" unidades de yerba a "+ destino.getCiudad() +".</p></br>");
        }

        // Cuarto: actualizar base.
        entityManager.merge(emisora);
        entityManager.merge(destino);

    }

    @Override
    public int valorOficial(RecursosModel traido) {

        int recursosAPagar = 10;
        if (traido.getNivel_oficial_pedido() == "B") {
            recursosAPagar = 2;
        } else if (traido.getNivel_oficial_pedido() == "C") {
            recursosAPagar = 3;
        }
        return recursosAPagar;
    }

    @Override
    public int valorAumentarEstatus(RecursosModel traido) {
        return traido.getActor_politico_pedido();
    }

    @Override
    public void aumentarEstatus(String ciudad) {

        RecursosModel nuevoEstatus = entityManager.find(RecursosModel.class, ciudad);
        nuevoEstatus.setEstatus(nuevoEstatus.getEstatus() +1);
    }

    @Override
    public void crecerActorPolitico(String ciudad, int recursosAPagar) {

        // Primero: determinar actor político usado
        RecursosModel recursosModel = entityManager.find(RecursosModel.class, ciudad);

        String actorBuscado = "";

        if (recursosAPagar == 1){
            actorBuscado = recursosModel.getActor_politico_1();
        } else if (recursosAPagar == 2){
            actorBuscado = recursosModel.getActor_politico_2();
        } else if (recursosAPagar == 3){
            actorBuscado = "Gobierno Nacional";
        }

        // Segundo: buscar actor político usado.
        ActoresPoliticosModel actorPolitico = entityManager.find(ActoresPoliticosModel.class, actorBuscado);

        // Tercero: aumentar valor de actor político
        actorPolitico.setValor(actorPolitico.getValor() + 1);

        // Cuarto: actualizar tabla de actores políticos
        entityManager.merge(actorPolitico);
    }
}
