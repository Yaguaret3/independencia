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

        String[] ciudades = {"Buenos Aires", "Montevideo", "Asunción", "Santa Fe", "Córdoba", "Mendoza", "Tucumán", "Salta",
                            "Potosí", "La Paz"};

        for(int i = 0; i<10; i++) {

            RecursosModel ciudad = entityManager.find(RecursosModel.class, ciudades[i]);
            EjercitosModel ejercito = entityManager.find(EjercitosModel.class, ciudades[i]);

            if(ejercito.getUbicacion_comercial().equals("Río Cuarto") && !improductividadCaballos){
                ciudad.setCaballos_m(1);
            }
            if(ejercito.getUbicacion_comercial().equals("Tacuarembó") && !improductividadVacas){
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
             case "Capital Inglés":
             case "Campaña":
                 RecursosModel buenosAires = entityManager.find(RecursosModel.class, "Buenos Aires");
                 buenosAires.setImproductividad(1);
                 RecursosModel tucuman = entityManager.find(RecursosModel.class, "Tucumán");
                 tucuman.setImproductividad(1);
                 break;
             case "Capital Francés":
             case "Cultura":
                 RecursosModel montevideo = entityManager.find(RecursosModel.class, "Montevideo");
                 montevideo.setImproductividad(1);
                 RecursosModel cordoba = entityManager.find(RecursosModel.class, "Córdoba");
                 cordoba.setImproductividad(1);
                 break;
             case "Esclavos":
             case "Banca":
                 RecursosModel laPaz = entityManager.find(RecursosModel.class, "La Paz");
                 laPaz.setImproductividad(1);
                 RecursosModel potosi = entityManager.find(RecursosModel.class, "Potosí");
                 potosi.setImproductividad(1);
                 break;
             case "Iglesia":
             case "Elite":
                 RecursosModel santaFe = entityManager.find(RecursosModel.class, "Santa Fe");
                 santaFe.setImproductividad(1);
                 RecursosModel asuncion = entityManager.find(RecursosModel.class, "Asunción");
                 asuncion.setImproductividad(1);
                 break;
             case "Gremios":
             case "Minería":
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

        String[] ciudadesArray = {"Buenos Aires", "Montevideo", "Asunción", "Santa Fe", "Córdoba", "Mendoza", "Tucumán", "Salta",
                "Potosí", "La Paz"};

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

                    // Cuarto: comparamos su sistema económico
                    switch (sistemaEconomico){

                        // Quinto: para default y liberalismo economico
                        case "default":
                        case "liberalismo":

                            // Sexto: tomamos todas las ciudades y nos quedamos con las que participen de ese congreso.
                            for(int j = 0; j<10; j++){

                                RecursosModel ciudad = entityManager.find(RecursosModel.class, ciudadesArray[j]);

                                if(ciudad.getCongreso() == congreso.getId()){

                                    // Séptimo: corroborar improductividad
                                    if (ciudad.getImproductividad() == 0) {

                                        // Octavo: corroborar recurso que produce y producirlo (liberalismo sólo +1)
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

                                    // Noveno: sumar recursos de mapa
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

                                    //Décimo: actualizar tabla
                                    entityManager.merge(ciudad);
                                }
                            }
                            break;

                        // Décimo primero: para proteccionismo
                        case "proteccionismo":

                            // Décimo segundo: tomamos todas las ciudades y nos quedamos con las que participen de ese congreso.
                            for(int j = 0; j<10; j++){
                                int id= j+1;
                                RecursosModel ciudad = entityManager.find(RecursosModel.class, ciudadesArray[j]);

                                if(ciudad.getCongreso() == congreso.getId()){

                                    // Décimo tercero: corroborar improductividad
                                    if (ciudad.getImproductividad() == 0) {

                                        // Décimo cuarto: corroborar recurso que produce y producirlo (proteccionismo: pueden subir 2)
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
                                    // Décimo quinto: sumar recursos de mapa
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

                                    //Décimo sexto: actualizar tabla
                                    entityManager.merge(ciudad);
                                }
                            }
                            break;
                    }
                    break;

                // Décimo séptimo: para centralismo declaramos totales que recibirá el ejecutivo
                case "centralismo":

                    int totalCaballos = 0;
                    int totalVacas = 0;
                    int totalHierro = 0;
                    int totalVino = 0;
                    int totalYerba = 0;

                    // Décimo octavo: comparamos su sistema económico
                    switch (sistemaEconomico){

                        // Décimo noveno: para default y liberalismo economico
                        case "default":
                        case "liberalismo":

                            // Vigésimo: tomamos todas las ciudades y nos quedamos con las que participen de ese congreso.
                            for(int j = 0; j<10; j++){
                                RecursosModel ciudad = entityManager.find(RecursosModel.class, ciudadesArray[j]);

                                if(ciudad.getCongreso() == congreso.getId()){

                                    // Vigésimo primero: corroborar improductividad
                                    if (ciudad.getImproductividad() == 0) {

                                        // Vigésimo segundo: corroborar recurso que produce y producirlo (liberalismo sólo +1)
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
                                    // Vigésimo tercero: sumar recursos de mapa
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
                                //Vigésimo cuarto: pasarle recursos al presidente
                                RecursosModel ejecutivo = entityManager.find(RecursosModel.class, presidente);

                                ejecutivo.setCaballos(ciudad.getCaballos() + totalCaballos);
                                ejecutivo.setVacas(ciudad.getVacas() + totalVacas);
                                ejecutivo.setHierro(ciudad.getHierro() + totalHierro);
                                ejecutivo.setVino(ciudad.getVino() + totalVino);
                                ejecutivo.setYerba(ciudad.getYerba() + totalYerba);

                                //Vigésimo quinto: actualizar tabla
                                entityManager.merge(ejecutivo);
                                }
                            break;

                        // Vigésimo sexto: para proteccionismo economico
                        case "proteccionismo":

                            // Vigésimo séptimo: tomamos todas las ciudades y nos quedamos con las que participen de ese congreso.
                            for(int j = 0; j<10; j++){
                                RecursosModel ciudad = entityManager.find(RecursosModel.class, ciudadesArray[j]);

                                if(ciudad.getCongreso() == congreso.getId()){

                                    // Vigésimo octavo: corroborar improductividad
                                    if (ciudad.getImproductividad() == 0) {

                                        // Vigésimo noveno: corroborar recurso que produce y producirlo (proteccionismo: según industria)
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
                                    // Trigésimo: sumar recursos de mapa
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
                                // Trigésimo primero: pasarle recursos al presidente
                                RecursosModel ejecutivo = entityManager.find(RecursosModel.class, presidente);

                                ejecutivo.setCaballos(ejecutivo.getCaballos() + totalCaballos);
                                ejecutivo.setVacas(ejecutivo.getVacas() + totalVacas);
                                ejecutivo.setHierro(ejecutivo.getHierro() + totalHierro);
                                ejecutivo.setVino(ejecutivo.getVino() + totalVino);
                                ejecutivo.setYerba(ejecutivo.getYerba() + totalYerba);

                                // Trigésimo segundo: actualizar tabla
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

        String[] ciudadesArray = {"Buenos Aires", "Montevideo", "Asunción", "Santa Fe", "Córdoba", "Mendoza", "Tucumán", "Salta",
                "Potosí", "La Paz"};

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
        String[] caminoRealData = {"Buenos Aires", "2", "Pergamino", "4", "5", "6", "7", "Córdoba", "9", "10", "11",
                "Santiago del Estero", "13", "Tucumán", "15", "Salta", "San Salvador de Jujuy",
                "18", "19", "Suipacha", "21", "22", "Potosí", "24", "25", "26", "La Paz", "28",
                "29", "30", "31", "Arequipa"};
        String[] pergaminoLaRiojaSaltaData = {"Pergamino", "33", "34", "Río Cuarto", "38", "39", "San Luis", "41", "42",
                "Mendoza", "47", "San Juan", "49", "50", "LaRioja", "55", "56", "Catamarca",
                "Tucumán", "58", "Esteco Nuevo", "60", "Salta"};
        String[] jujuySuipachaData = {"San Salvador de Jujuy", "61", "62", "63", "San Bernardo de Tarija", "65",
                "Suipacha"};
        String[] chuquisacaCochabambaData = {"Potosí", "Chuquisaca", "106", "Cochabamba", "108", "109", "La Paz"};
        String[] cordobaRioCuartoData = {"Córdoba", "37", "36", "Río Cuarto"};
        String[] cordobaSanLuisData = {"Córdoba", "44", "45", "46", "San Luis"};
        String[] laRiojaPAlegreData = {"La Rioja", "54", "53", "52", "Córdoba", "66", "67", "Santa Fe", "69",
                "Concepción del Uruguay", "71", "72", "73", "Tacuarembó", "94", "95",
                "Porto Alegre"};
        String[] rioParanaParaguayData = {"Montevideo", "Buenos Aires", "Santa Fe", "Corrientes", "Asunción",
                "Coimbra", "102", "103", "Xerez"};
        String[] rioParanaMisionesData = {"Montevideo", "Buenos Aires", "Santa Fe", "Corrientes", "Misiones"};
        String[] rioUruguayPortoAlegreData = {"Montevideo", "Buenos Aires", "Concepción del Uruguay", "Salto Chico",
                "92", "91", "90", "San Borja", "100", "99", "98", "97", "Porto Alegre"};
        String[] montevideoAsuncionData = {"Montevideo", "76", "77", "78", "79", "Tacuarembó", "80", "81", "82",
                "83", "San Borja", "Misiones", "86", "87", "Asunción"};


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

        // Determinamos si en una ruta coinciden los dos puntos y se envía

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
