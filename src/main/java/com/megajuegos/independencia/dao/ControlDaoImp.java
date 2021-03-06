package com.megajuegos.independencia.dao;

import com.megajuegos.independencia.models.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Calendar;
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
    public List<CongresoModel> listarCongresos() {
        String query = "FROM CongresoModel";
        List<CongresoModel> resultado = entityManager.createQuery(query).getResultList();
        return resultado;
    }

    @Override
    public List<OtrosModel> cargarTimer() {

        String query = "FROM OtrosModel";
        List<OtrosModel> devolver = entityManager.createQuery(query).getResultList();
        return devolver;

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
        entityManager.merge(editado);
    }
    @Override
    public void editarEjercito(String ciudad, EjercitosModel actualizacion) {
        EjercitosModel editado = entityManager.find(EjercitosModel.class, ciudad);
        editado.setUnidades_agrupadas(actualizacion.getUnidades_agrupadas());
        editado.setUnidades_recien_llegadas(actualizacion.getUnidades_recien_llegadas());
        editado.setUbicacion_militar(actualizacion.getUbicacion_militar());
        editado.setUbicacion_comercial(actualizacion.getUbicacion_comercial());
        editado.setOficial_a(actualizacion.getOficial_a());
        editado.setOficial_b(actualizacion.getOficial_b());
        editado.setOficial_c(actualizacion.getOficial_c());
        editado.setOficial_d(actualizacion.getOficial_d());
        editado.setOficial_e(actualizacion.getOficial_e());
        editado.setNivel_mision_comercial(actualizacion.getNivel_mision_comercial());
        entityManager.merge(editado);
    }

    @Override
    public void editarActorPolitico(String actor, ActoresPoliticosModel actualizacion) {
        ActoresPoliticosModel editado = entityManager.find(ActoresPoliticosModel.class, actor);
        editado.setValor(actualizacion.getValor());
        entityManager.merge(editado);
    }

    @Override
    public void seleccionarFase(OtrosModel fase) {

        OtrosModel otros = entityManager.find(OtrosModel.class, "fase_militar");
        otros.setValor(fase.getValor());
        entityManager.merge(otros);
    }

    @Override
    public void avanzarTurno() {

        // Primero: Avanzar el turno
        OtrosModel turno = entityManager.find(OtrosModel.class, "turno");
        turno.setValor(turno.getValor() + 1);
        entityManager.merge(turno);

        // Segundo: Avanzar el timer
        OtrosModel timer = entityManager.find(OtrosModel.class, "timer");
        Calendar proximoFinDeTurno = Calendar.getInstance();
        proximoFinDeTurno.setTimeInMillis(proximoFinDeTurno.getTimeInMillis()+1000*60*30);
        timer.setValor(proximoFinDeTurno.getTimeInMillis());
        entityManager.merge(timer);
    }
    @Override
    public void editarCongreso(CongresoModel nuevoCongreso) {
        CongresoModel congreso = entityManager.find(CongresoModel.class, nuevoCongreso.getId());
        congreso.setCapital(nuevoCongreso.getCapital());
        congreso.setSistema_de_gobierno(nuevoCongreso.getSistema_de_gobierno());
        congreso.setSistema_economico(nuevoCongreso.getSistema_economico());
        congreso.setCuantos(nuevoCongreso.getCuantos());
        congreso.setPresidente(nuevoCongreso.getPresidente());
        entityManager.merge(congreso);
    }

    @Override
    public void recursosMapa() {

        // Primero: establecer si hay improductividad (a partir de una ciudad que produzca tal recurso)

        boolean improductividadCaballos = false;
        boolean improductividadVacas = false;
        boolean improductividadHierro = false;
        boolean improductividadVino = false;
        boolean improductividadYerba = false;

        RecursosModel ciudadCaballos = entityManager.find(RecursosModel.class, "Buenos Aires");
        if(ciudadCaballos.getImproductividad() == 1){
            improductividadCaballos = true;
        } else{
            improductividadCaballos = false;
        }
        RecursosModel ciudadVacas = entityManager.find(RecursosModel.class, "Montevideo");
        if(ciudadVacas.getImproductividad() == 1){
            improductividadVacas = true;
        } else{
            improductividadVacas = false;
        }
        RecursosModel ciudadHierro = entityManager.find(RecursosModel.class, "La Paz");
        if(ciudadHierro.getImproductividad() == 1){
            improductividadHierro = true;
        } else{
            improductividadHierro = false;
        }
        RecursosModel ciudadVino = entityManager.find(RecursosModel.class, "Salta");
        if(ciudadVino.getImproductividad() == 1){
            improductividadVino = true;
        } else{
            improductividadVino = false;
        }
        RecursosModel ciudadYerba = entityManager.find(RecursosModel.class, "Santa Fe");
        if(ciudadYerba.getImproductividad() == 1){
            improductividadYerba = true;
        } else{
            improductividadYerba = false;
        }

        // Segundo: tomar recurso de mapa

        String[] ciudades = {"Buenos Aires", "Montevideo", "Asunci??n", "Santa Fe", "C??rdoba", "Mendoza", "Tucum??n", "Salta",
                            "Potos??", "La Paz"};

        for(int i = 0; i<10; i++) {

            RecursosModel ciudad = entityManager.find(RecursosModel.class, ciudades[i]);
            EjercitosModel ejercito = entityManager.find(EjercitosModel.class, ciudades[i]);

            if(ejercito.getUbicacion_comercial().equals("R??o Cuarto") && !improductividadCaballos){
                ciudad.setCaballos_m(1);
            }
            if(ejercito.getUbicacion_comercial().equals("Tacuaremb??") && !improductividadVacas){
                ciudad.setVacas_m(1);
            }
            if(ejercito.getUbicacion_comercial().equals("Corrientes") && !improductividadYerba){
                ciudad.setYerba_m(1);
            }
            if((ejercito.getUbicacion_comercial().equals("La Rioja") || ejercito.getUbicacion_comercial().equals("Cochabamba")) &&
                    !improductividadHierro){
                ciudad.setHierro_m(1);
            }
            if((ejercito.getUbicacion_comercial().equals("Esteco Nuevo") || ejercito.getUbicacion_comercial().equals("San Bernardo de Tarija")) &&
                    !improductividadVino){
                ciudad.setVino_m(1);
            }

            entityManager.merge(ciudad);
        }
    }

    @Override
    public void improductividad(ActoresPoliticosModel actorPolitico) {

        ActoresPoliticosModel actor = entityManager.find(ActoresPoliticosModel.class, actorPolitico);
         switch (actor.getActor()){
             case "Capital Ingl??s":
             case "Campa??a":
                 RecursosModel buenosAires = entityManager.find(RecursosModel.class, "Buenos Aires");
                 buenosAires.setImproductividad(1);
                 RecursosModel tucuman = entityManager.find(RecursosModel.class, "Tucum??n");
                 tucuman.setImproductividad(1);
                 break;
             case "Capital Franc??s":
             case "Cultura":
                 RecursosModel montevideo = entityManager.find(RecursosModel.class, "Montevideo");
                 montevideo.setImproductividad(1);
                 RecursosModel cordoba = entityManager.find(RecursosModel.class, "C??rdoba");
                 cordoba.setImproductividad(1);
                 break;
             case "Esclavos":
             case "Banca":
                 RecursosModel laPaz = entityManager.find(RecursosModel.class, "La Paz");
                 laPaz.setImproductividad(1);
                 RecursosModel potosi = entityManager.find(RecursosModel.class, "Potos??");
                 potosi.setImproductividad(1);
                 break;
             case "Iglesia":
             case "Elite":
                 RecursosModel santaFe = entityManager.find(RecursosModel.class, "Santa Fe");
                 santaFe.setImproductividad(1);
                 RecursosModel asuncion = entityManager.find(RecursosModel.class, "Asunci??n");
                 asuncion.setImproductividad(1);
                 break;
             case "Gremios":
             case "Miner??a":
                 RecursosModel mendoza = entityManager.find(RecursosModel.class, "Mendoza");
                 mendoza.setImproductividad(1);
                 RecursosModel salta = entityManager.find(RecursosModel.class, "Salta");
                 salta.setImproductividad(1);
                 break;
         }
         entityManager.merge(actor);
    }

    @Override
    public void repartirRecursos() {

        OtrosModel turno = entityManager.find(OtrosModel.class, "turno");

        String[] ciudadesArray = {"Buenos Aires", "Montevideo", "Asunci??n", "Santa Fe", "C??rdoba", "Mendoza", "Tucum??n", "Salta",
                "Potos??", "La Paz"};

        // Primero: Tomamos el primer congreso y sus propiedades
        for (int i = 0; i < 10; i++) {
            int idCongreso = i+1;
            CongresoModel congreso = entityManager.find(CongresoModel.class, idCongreso);
            String sistemaDeGobierno = congreso.getSistema_de_gobierno();
            String sistemaEconomico = congreso.getSistema_economico();
            String presidente = congreso.getPresidente();

            // Segundo: comparamos su sistema de gobierno
            switch (sistemaDeGobierno) {

                // Tercero: para federalismo
                case "federalismo":

                    // Cuarto: comparamos su sistema econ??mico
                    switch (sistemaEconomico){

                        // Quinto: para default y liberalismo economico
                        case "default":
                        case "liberalismo":

                            // Sexto: tomamos todas las ciudades y nos quedamos con las que participen de ese congreso.
                            for(int j = 0; j<10; j++){

                                RecursosModel ciudad = entityManager.find(RecursosModel.class, ciudadesArray[j]);

                                if(ciudad.getCongreso() == congreso.getId()){

                                    // S??ptimo: corroborar improductividad
                                    if (ciudad.getImproductividad() == 0) {

                                        // Octavo: corroborar recurso que produce y producirlo (liberalismo s??lo +1)
                                        switch (ciudad.getRecurso_que_produce()) {
                                            case "caballos":
                                                ciudad.setCaballos(ciudad.getCaballos() + 1);
                                                break;
                                            case "vacas":
                                                ciudad.setVacas(ciudad.getVacas() + 1);
                                                break;
                                            case "hierro":
                                                ciudad.setHierro(ciudad.getHierro() + 1);
                                                break;
                                            case "vino":
                                                ciudad.setVino(ciudad.getVino() + 1);
                                                break;
                                            case "yerba":
                                                ciudad.setYerba(ciudad.getYerba() + 1);
                                                break;
                                        }
                                    }

                                    // Noveno: sumar recursos de mapa y pasar turno en comercio
                                    ciudad.setCaballos(ciudad.getCaballos() + ciudad.getCaballos_m());
                                    ciudad.setCaballos_m(0);
                                    ciudad.setVacas(ciudad.getVacas() + ciudad.getVacas_m());
                                    ciudad.setVacas_m(0);
                                    ciudad.setHierro(ciudad.getHierro() + ciudad.getHierro_m());
                                    ciudad.setHierro_m(0);
                                    ciudad.setVino(ciudad.getVino() + ciudad.getVino_m());
                                    ciudad.setVino_m(0);
                                    ciudad.setYerba(ciudad.getYerba() + ciudad.getYerba_m());
                                    ciudad.setYerba_m(0);

                                    // Noveno BIS: Pasar turno en String de Comercio

                                    ciudad.setHistorial_comercial(ciudad.getHistorial_comercial() + "Turno " + turno.getValor());

                                    //D??cimo: actualizar tabla
                                    entityManager.merge(ciudad);
                                }
                            }
                            break;

                        // D??cimo primero: para proteccionismo
                        case "proteccionismo":

                            // D??cimo segundo: tomamos todas las ciudades y nos quedamos con las que participen de ese congreso.
                            for(int j = 0; j<10; j++){
                                int id= j+1;
                                RecursosModel ciudad = entityManager.find(RecursosModel.class, ciudadesArray[j]);

                                if(ciudad.getCongreso() == congreso.getId()){

                                    // D??cimo tercero: corroborar improductividad
                                    if (ciudad.getImproductividad() == 0) {

                                        // D??cimo cuarto: corroborar recurso que produce y producirlo (proteccionismo: pueden subir 2)
                                        switch (ciudad.getRecurso_que_produce()) {
                                            case "caballos":
                                                ciudad.setCaballos(ciudad.getCaballos() + ciudad.getNivel_industria());
                                                break;
                                            case "vacas":
                                                ciudad.setVacas(ciudad.getVacas() + ciudad.getNivel_industria());
                                                break;
                                            case "hierro":
                                                ciudad.setHierro(ciudad.getHierro() + ciudad.getNivel_industria());
                                                break;
                                            case "vino":
                                                ciudad.setVino(ciudad.getVino() + ciudad.getNivel_industria());
                                                break;
                                            case "yerba":
                                                ciudad.setYerba(ciudad.getYerba() + ciudad.getNivel_industria());
                                                break;
                                        }
                                    }
                                    // D??cimo quinto: sumar recursos de mapa
                                    ciudad.setCaballos(ciudad.getCaballos() + ciudad.getCaballos_m());
                                    ciudad.setCaballos_m(0);
                                    ciudad.setVacas(ciudad.getVacas() + ciudad.getVacas_m());
                                    ciudad.setVacas_m(0);
                                    ciudad.setHierro(ciudad.getHierro() + ciudad.getHierro_m());
                                    ciudad.setHierro_m(0);
                                    ciudad.setVino(ciudad.getVino() + ciudad.getVino_m());
                                    ciudad.setVino_m(0);
                                    ciudad.setYerba(ciudad.getYerba() + ciudad.getYerba_m());
                                    ciudad.setYerba_m(0);

                                    //D??cimo sexto: actualizar tabla
                                    entityManager.merge(ciudad);
                                }
                            }
                            break;
                    }
                    break;

                // D??cimo s??ptimo: para centralismo declaramos totales que recibir?? el ejecutivo
                case "centralismo":

                    int totalCaballos = 0;
                    int totalVacas = 0;
                    int totalHierro = 0;
                    int totalVino = 0;
                    int totalYerba = 0;

                    // D??cimo octavo: comparamos su sistema econ??mico
                    switch (sistemaEconomico){

                        // D??cimo noveno: para default y liberalismo economico
                        case "default":
                        case "liberalismo":

                            // Vig??simo: tomamos todas las ciudades y nos quedamos con las que participen de ese congreso.
                            for(int j = 0; j<10; j++){
                                RecursosModel ciudad = entityManager.find(RecursosModel.class, ciudadesArray[j]);

                                if(ciudad.getCongreso() == congreso.getId()){

                                    // Vig??simo primero: corroborar improductividad
                                    if (ciudad.getImproductividad() == 0) {

                                        // Vig??simo segundo: corroborar recurso que produce y producirlo (liberalismo s??lo +1)
                                        switch (ciudad.getRecurso_que_produce()) {
                                            case "caballos":
                                                totalCaballos = totalCaballos + 1;
                                                break;
                                            case "vacas":
                                                totalVacas = totalVacas + 1;
                                                break;
                                            case "hierro":
                                                totalHierro = totalHierro + 1;
                                                break;
                                            case "vino":
                                                totalVino = totalVino + 1;
                                                break;
                                            case "yerba":
                                                totalYerba = totalYerba + 1;
                                                break;
                                        }
                                    }
                                    // Vig??simo tercero: sumar recursos de mapa
                                    totalCaballos = totalCaballos + ciudad.getCaballos_m();
                                    ciudad.setCaballos_m(0);
                                    totalVacas = totalVacas + ciudad.getVacas_m();
                                    ciudad.setVacas_m(0);
                                    totalHierro = totalHierro + ciudad.getHierro_m();
                                    ciudad.setHierro_m(0);
                                    totalVino = totalVino + ciudad.getVino_m();
                                    ciudad.setVino_m(0);
                                    totalYerba = totalYerba + ciudad.getYerba_m();
                                    ciudad.setYerba_m(0);
                                }
                                //Vig??simo cuarto: pasarle recursos al presidente
                                RecursosModel ejecutivo = entityManager.find(RecursosModel.class, presidente);

                                ejecutivo.setCaballos(ciudad.getCaballos() + totalCaballos);
                                ejecutivo.setVacas(ciudad.getVacas() + totalVacas);
                                ejecutivo.setHierro(ciudad.getHierro() + totalHierro);
                                ejecutivo.setVino(ciudad.getVino() + totalVino);
                                ejecutivo.setYerba(ciudad.getYerba() + totalYerba);

                                //Vig??simo quinto: actualizar tabla
                                entityManager.merge(ejecutivo);
                                }
                            break;

                        // Vig??simo sexto: para proteccionismo economico
                        case "proteccionismo":

                            // Vig??simo s??ptimo: tomamos todas las ciudades y nos quedamos con las que participen de ese congreso.
                            for(int j = 0; j<10; j++){
                                RecursosModel ciudad = entityManager.find(RecursosModel.class, ciudadesArray[j]);

                                if(ciudad.getCongreso() == congreso.getId()){

                                    // Vig??simo octavo: corroborar improductividad
                                    if (ciudad.getImproductividad() == 0) {

                                        // Vig??simo noveno: corroborar recurso que produce y producirlo (proteccionismo: seg??n industria)
                                        switch (ciudad.getRecurso_que_produce()) {
                                            case "caballos":
                                                totalCaballos = totalCaballos + ciudad.getNivel_industria();
                                                break;
                                            case "vacas":
                                                totalVacas = totalVacas + ciudad.getNivel_industria();
                                                break;
                                            case "hierro":
                                                totalHierro = totalHierro + ciudad.getNivel_industria();
                                                break;
                                            case "vino":
                                                totalVino = totalVino + ciudad.getNivel_industria();
                                                break;
                                            case "yerba":
                                                totalYerba = totalYerba + ciudad.getNivel_industria();
                                                break;
                                        }
                                    }
                                    // Trig??simo: sumar recursos de mapa
                                    totalCaballos = totalCaballos + ciudad.getCaballos_m();
                                    ciudad.setCaballos_m(0);
                                    totalVacas = totalVacas + ciudad.getVacas_m();
                                    ciudad.setVacas_m(0);
                                    totalHierro = totalHierro + ciudad.getHierro_m();
                                    ciudad.setHierro_m(0);
                                    totalVino = totalVino + ciudad.getVino_m();
                                    ciudad.setVino_m(0);
                                    totalYerba = totalYerba + ciudad.getYerba_m();
                                    ciudad.setYerba_m(0);

                                }
                                // Trig??simo primero: pasarle recursos al presidente
                                RecursosModel ejecutivo = entityManager.find(RecursosModel.class, presidente);

                                ejecutivo.setCaballos(ejecutivo.getCaballos() + totalCaballos);
                                ejecutivo.setVacas(ejecutivo.getVacas() + totalVacas);
                                ejecutivo.setHierro(ejecutivo.getHierro() + totalHierro);
                                ejecutivo.setVino(ejecutivo.getVino() + totalVino);
                                ejecutivo.setYerba(ejecutivo.getYerba() + totalYerba);

                                // Trig??simo segundo: actualizar tabla
                                entityManager.merge(ejecutivo);
                                }
                            break;
                    }
                    break;
            }

        }


    }

    @Override
    public void actualizarOficiales() {

        String[] ciudadesArray = {"Buenos Aires", "Montevideo", "Asunci??n", "Santa Fe", "C??rdoba", "Mendoza", "Tucum??n", "Salta",
                "Potos??", "La Paz"};

        for(int i = 0; i<10; i++) {

            EjercitosModel ciudad = entityManager.find(EjercitosModel.class, ciudadesArray[i]);
            switch (ciudad.getOficial_a()){
                case 1:
                    switch (ciudad.getNuevo_oficial_a()){
                        case 2:
                            ciudad.setOficial_a(2);
                            break;
                        case 3:
                            ciudad.setOficial_a(3);
                            break;
                    }
                case 2:
                    if(ciudad.getNuevo_oficial_a() == 3){
                        ciudad.setOficial_a(3);
                    }
            }

            switch (ciudad.getOficial_b()){
                case 1:
                    switch (ciudad.getNuevo_oficial_b()){
                        case 2:
                            ciudad.setOficial_b(2);
                            break;
                        case 3:
                            ciudad.setOficial_c(3);
                            break;
                    }
                case 2:
                    if(ciudad.getNuevo_oficial_b() == 3){
                        ciudad.setOficial_b(3);
                    }
            }

            switch (ciudad.getOficial_c()){
                case 1:
                    switch (ciudad.getNuevo_oficial_c()){
                        case 2:
                            ciudad.setOficial_c(2);
                            break;
                        case 3:
                            ciudad.setOficial_c(3);
                            break;
                    }
                case 2:
                    if(ciudad.getNuevo_oficial_c() == 3){
                        ciudad.setOficial_c(3);
                    }
            }

            switch (ciudad.getOficial_d()){
                case 1:
                    switch (ciudad.getNuevo_oficial_d()){
                        case 2:
                            ciudad.setOficial_d(2);
                            break;
                        case 3:
                            ciudad.setOficial_d(3);
                            break;
                    }
                case 2:
                    if(ciudad.getNuevo_oficial_d() == 3){
                        ciudad.setOficial_d(3);
                    }
            }

            switch (ciudad.getOficial_e()){
                case 1:
                    switch (ciudad.getNuevo_oficial_e()){
                        case 2:
                            ciudad.setOficial_e(2);
                            break;
                        case 3:
                            ciudad.setOficial_e(3);
                            break;
                    }
                case 2:
                    if(ciudad.getNuevo_oficial_e() == (3)){
                        ciudad.setOficial_e(3);
                    }
            }
        }
    }

    @Override
    public void permitirActualizarListaCapitanes(OtrosModel nuevo) {
        OtrosModel permitir = entityManager.find(OtrosModel.class, "actualizar_capitanes");
        permitir.setValor(nuevo.getValor());
    }



    /*
    @Override
    public List<String> buscarCaminos(String ubicacion, String destino) {

        // Rutas
        String[] caminoRealData = {"Buenos Aires", "2", "Pergamino", "4", "5", "6", "7", "C??rdoba", "9", "10", "11",
                "Santiago del Estero", "13", "Tucum??n", "15", "Salta", "San Salvador de Jujuy",
                "18", "19", "Suipacha", "21", "22", "Potos??", "24", "25", "26", "La Paz", "28",
                "29", "30", "31", "Arequipa"};
        String[] pergaminoLaRiojaSaltaData = {"Pergamino", "33", "34", "R??o Cuarto", "38", "39", "San Luis", "41", "42",
                "Mendoza", "47", "San Juan", "49", "50", "LaRioja", "55", "56", "Catamarca",
                "Tucum??n", "58", "Esteco Nuevo", "60", "Salta"};
        String[] jujuySuipachaData = {"San Salvador de Jujuy", "61", "62", "63", "San Bernardo de Tarija", "65",
                "Suipacha"};
        String[] chuquisacaCochabambaData = {"Potos??", "Chuquisaca", "106", "Cochabamba", "108", "109", "La Paz"};
        String[] cordobaRioCuartoData = {"C??rdoba", "37", "36", "R??o Cuarto"};
        String[] cordobaSanLuisData = {"C??rdoba", "44", "45", "46", "San Luis"};
        String[] laRiojaPAlegreData = {"La Rioja", "54", "53", "52", "C??rdoba", "66", "67", "Santa Fe", "69",
                "Concepci??n del Uruguay", "71", "72", "73", "Tacuaremb??", "94", "95",
                "Porto Alegre"};
        String[] rioParanaParaguayData = {"Montevideo", "Buenos Aires", "Santa Fe", "Corrientes", "Asunci??n",
                "Coimbra", "102", "103", "Xerez"};
        String[] rioParanaMisionesData = {"Montevideo", "Buenos Aires", "Santa Fe", "Corrientes", "Misiones"};
        String[] rioUruguayPortoAlegreData = {"Montevideo", "Buenos Aires", "Concepci??n del Uruguay", "Salto Chico",
                "92", "91", "90", "San Borja", "100", "99", "98", "97", "Porto Alegre"};
        String[] montevideoAsuncionData = {"Montevideo", "76", "77", "78", "79", "Tacuaremb??", "80", "81", "82",
                "83", "San Borja", "Misiones", "86", "87", "Asunci??n"};


        ArrayList<ArrayList<String>> rutas = new ArrayList<ArrayList<String>>();

        ArrayList<String> caminoReal = new ArrayList<String>();
        ArrayList<String> pergaminoLaRiojaSalta = new ArrayList<String>();
        ArrayList<String> jujuySuipacha = new ArrayList<String>();
        ArrayList<String> chuquisacaCochabamba = new ArrayList<String>();
        ArrayList<String> cordobaRioCuarto = new ArrayList<String>();
        ArrayList<String> cordobaSanLuis = new ArrayList<String>();
        ArrayList<String> laRiojaPAlegre = new ArrayList<String>();
        ArrayList<String> rioParanaParaguay = new ArrayList<String>();
        ArrayList<String> rioParanaMisiones = new ArrayList<String>();
        ArrayList<String> rioUruguayPortoAlegre = new ArrayList<String>();
        ArrayList<String> montevideoAsuncion = new ArrayList<String>();

        for (int i = 0; i < caminoRealData.length; i++) {
            caminoReal.add(caminoRealData[i]);
        }
        for (int i = 0; i < pergaminoLaRiojaSaltaData.length; i++) {
            pergaminoLaRiojaSalta.add(pergaminoLaRiojaSaltaData[i]);
        }
        for (int i = 0; i < chuquisacaCochabambaData.length; i++) {
            chuquisacaCochabamba.add(chuquisacaCochabambaData[i]);
        }
        for (int i = 0; i < jujuySuipachaData.length; i++) {
            jujuySuipacha.add(jujuySuipachaData[i]);
        }
        for (int i = 0; i < cordobaRioCuartoData.length; i++) {
            cordobaRioCuarto.add(cordobaRioCuartoData[i]);
        }
        for (int i = 0; i < cordobaSanLuisData.length; i++) {
            cordobaSanLuis.add(cordobaSanLuisData[i]);
        }
        for (int i = 0; i < laRiojaPAlegreData.length; i++) {
            laRiojaPAlegre.add(laRiojaPAlegreData[i]);
        }
        for (int i = 0; i < rioParanaParaguayData.length; i++) {
            rioParanaParaguay.add(rioParanaParaguayData[i]);
        }
        for (int i = 0; i < rioParanaMisionesData.length; i++) {
            rioParanaMisiones.add(rioParanaMisionesData[i]);
        }
        for (int i = 0; i < rioUruguayPortoAlegreData.length; i++) {
            rioUruguayPortoAlegre.add(rioUruguayPortoAlegreData[i]);
        }
        for (int i = 0; i < montevideoAsuncionData.length; i++) {
            montevideoAsuncion.add(montevideoAsuncionData[i]);
        }
        rutas.add(caminoReal);
        rutas.add(pergaminoLaRiojaSalta);
        rutas.add(chuquisacaCochabamba);
        rutas.add(jujuySuipacha);
        rutas.add(cordobaRioCuarto);
        rutas.add(cordobaSanLuis);
        rutas.add(laRiojaPAlegre);
        rutas.add(rioParanaParaguay);
        rutas.add(rioParanaMisiones);
        rutas.add(rioUruguayPortoAlegre);
        rutas.add(montevideoAsuncion);

        // Determinamos si en una ruta coinciden los dos puntos y se env??a

        int coinciden = 0;

        for (List<String> ruta : rutas) {

            for (String punto : ruta) {

                if (punto.equals(ubicacion)) {
                    coinciden++;
                }
                if (punto.equals(destino)) {
                    coinciden++;
                }
                if (coinciden == 2) {
                    return ruta;
                }
                coinciden = 0;
            }
        }
        return new ArrayList<>();
    }

     */
}
