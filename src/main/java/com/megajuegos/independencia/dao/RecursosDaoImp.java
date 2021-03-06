package com.megajuegos.independencia.dao;

import com.megajuegos.independencia.models.*;
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
    public EjercitosModel cargarNivelMisionComercial(String ciudad) {

        //Inventamos un nuevo ejercitomodel
        EjercitosModel devolver = new EjercitosModel();

        //Buscamos la ciudad
        EjercitosModel ejercito = entityManager.find(EjercitosModel.class, ciudad);

        //Pasamos el valor
        devolver.setNivel_mision_comercial(ejercito.getNivel_mision_comercial());

        //Devolver
        return devolver;
    }

    @Override
    public void aumentarIndustria(String ciudad) {

        RecursosModel industria = entityManager.find(RecursosModel.class, ciudad);

        // : sumar nivel de industria
        industria.setNivel_industria(2);
        entityManager.merge(industria);
    }

    @Override
    public String condicionesValidas(String ciudad) {

        // Primero: corroborar que está en nivel 1,
        RecursosModel industria = entityManager.find(RecursosModel.class, ciudad);
        if(industria.getNivel_industria() != 1){
            return "Industria a nivel máximo";
        }
        // Segundo: corroborar que la ley de proteccionismo o promoción industrial o la wea exista,
        CongresoModel congreso = entityManager.find(CongresoModel.class, industria.getCongreso());
        if(!congreso.getSistema_economico().equals("proteccionismo")){
            return "Sistema económico proteccionista necesario";
        }

        // Tercero: corroborar que el nivel de estatus sea el adecuado.
        OtrosModel estatusRequerido = entityManager.find(OtrosModel.class, "estatus_industria");
        if(industria.getEstatus() < estatusRequerido.getValor()){
            return "Estatus insuficiente";
        }
        return null;
    }

    @Override
    public void aumentarMisionComercial(String ciudad) {

        // Aumentar mision comercial en tabla capitanes

        EjercitosModel misionComercial = entityManager.find(EjercitosModel.class, ciudad);
        misionComercial.setNivel_mision_comercial(misionComercial.getNivel_mision_comercial());
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
    public void contratarOficiales(String ciudad, RecursosModel traido) {

        RecursosModel oficiales = entityManager.find(RecursosModel.class, ciudad);
        EjercitosModel oficialAlCapitan = entityManager.find(EjercitosModel.class, ciudad);

        String oficialNuevo = "";

        // Primero: Hacer tirada de oficial
        int random = new Random().nextInt(5);

        // Segundo: Comparar el nivel pedido con el anterior.
        // Tercero: Actualizarlo, de ser mayor (para el siguiente turno)
        switch(random){
            case 0:
                oficialNuevo = "A"+random;
                if(oficialAlCapitan.getOficial_a() < traido.getNivel_oficial_pedido()){
                    oficialAlCapitan.setNuevo_oficial_a(traido.getNivel_oficial_pedido());
                }
                break;
            case 1:
                oficialNuevo = "B"+random;
                if(oficialAlCapitan.getOficial_b() < traido.getNivel_oficial_pedido()){
                    oficialAlCapitan.setNuevo_oficial_b(traido.getNivel_oficial_pedido());
                }
                break;
            case 2:
                oficialNuevo = "C"+random;
                if(oficialAlCapitan.getOficial_c() < traido.getNivel_oficial_pedido()){
                    oficialAlCapitan.setNuevo_oficial_c(traido.getNivel_oficial_pedido());
                }
                break;
            case 3:
                oficialNuevo = "D"+random;
                if(oficialAlCapitan.getOficial_d() < traido.getNivel_oficial_pedido()){
                    oficialAlCapitan.setNuevo_oficial_d(traido.getNivel_oficial_pedido());
                }
                break;
            case 4:
                oficialNuevo = "E"+random;
                if(oficialAlCapitan.getOficial_e() < traido.getNivel_oficial_pedido()){
                    oficialAlCapitan.setNuevo_oficial_e(traido.getNivel_oficial_pedido());
                }
                break;
        }
        entityManager.merge(oficialAlCapitan);

        // Cuarto: sumar oficiales a la lista para que vea el gobernador

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
    public boolean misionMenosAEstatus(String ciudad) {

        // Primero: Identificar estatus
        RecursosModel recursos = entityManager.find(RecursosModel.class, ciudad);
        int estatus = recursos.getEstatus();

        // Segundo: Identificar nivel de misión comercial
        EjercitosModel nivel = entityManager.find(EjercitosModel.class, ciudad);
        int misionComercial = nivel.getNivel_mision_comercial();

        // Comparar
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

    @Override
    public boolean pausa() {

        OtrosModel pausa = entityManager.find(OtrosModel.class, "pausa");

        if(pausa.getValor() == 1){
            return true;
        } else{
            return false;
        }

    }
}
