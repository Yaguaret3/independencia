package com.megajuegos.independencia.dao;

import com.megajuegos.independencia.models.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
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
        pausa.setValor_int(1);
    }

    @Override
    public void despausar() {
        OtrosModel pausa = entityManager.find(OtrosModel.class, "pausa");
        pausa.setValor_int(0);
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
        otros.setValor_int(fase.getValor_int());
        entityManager.merge(otros);
    }

    @Override
    public void avanzarTurno() {
        OtrosModel otros = entityManager.find(OtrosModel.class, "turno");
        otros.setValor_int(otros.getValor_int() + 1);
        entityManager.merge(otros);
    }

    @Override
    public String getSistemaDeGobierno() {
        OtrosModel otros = entityManager.find(OtrosModel.class, "sistema_de_gobierno");
        return otros.getValor_char();
    }

    @Override
    public String getPresidente() {
        OtrosModel otros = entityManager.find(OtrosModel.class, "presidente");
        return otros.getValor_char();
    }

    @Override
    public String getSistemaEconomico() {
        OtrosModel otros = entityManager.find(OtrosModel.class, "sistema_economico");
        return otros.getValor_char();
    }



    @Override
    public void editarSistemaDeGobierno(OtrosModel nuevoSistema) {
        OtrosModel otros = entityManager.find(OtrosModel.class, nuevoSistema.getAccion());
        otros.setValor_char(nuevoSistema.getValor_char());
        entityManager.merge(otros);
    }

    @Override
    public void editarPresidente(OtrosModel nuevoPresidente) {
        OtrosModel otros = entityManager.find(OtrosModel.class, nuevoPresidente.getAccion());
        otros.setValor_char(nuevoPresidente.getValor_char());
        entityManager.merge(otros);
    }

    @Override
    public void editarSistemaEconomico(OtrosModel nuevoSistema) {
        OtrosModel otros = entityManager.find(OtrosModel.class, nuevoSistema.getAccion());
        otros.setValor_char(nuevoSistema.getValor_char());
        entityManager.merge(otros);
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

        for(int i = 0; i<10; i++) {
            int id = i + 1;
            RecursosModel ciudad = entityManager.find(RecursosModel.class, id);
            EjercitosModel ejercito = entityManager.find(EjercitosModel.class, id);

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
    }

    @Override
    public void repartirRecursos(String sistemaDeGobierno, String presidente, String sistemaEconomico) {

        switch (sistemaDeGobierno) {

            // Primero: para federalismo politico
            case "federalismo":
                switch (sistemaEconomico){

                    // Segundo: para default y liberalismo economico
                    case "default":
                    case "liberalismo":

                        for(int i = 0; i<10; i++){
                            int id= i+1;
                            RecursosModel ciudad = entityManager.find(RecursosModel.class, id);

                            // Tercero: corroborar improductividad
                            if (ciudad.getImproductividad() == 0) {

                                // Cuarto: corroborar recurso que produce y producirlo (liberalismo sólo +1)
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
                            // Quinto: sumar recursos de mapa
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


                            //Sexto: actualizar tabla
                            entityManager.merge(ciudad);
                        }
                        break;

                    // Séptimo: para proteccionismo
                    case "proteccionismo":

                        for(int i = 0; i<10; i++){
                            int id= i+1;
                            RecursosModel ciudad = entityManager.find(RecursosModel.class, id);

                            // Octavo: corroborar improductividad
                            if (ciudad.getImproductividad() == 0) {

                                // Noveno: corroborar recurso que produce y producirlo (proteccionismo: pueden subir 2)
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
                            // Décimo: sumar recursos de mapa
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

                            //Décimo primero: actualizar tabla
                            entityManager.merge(ciudad);
                        }
                        break;
                }
                break;

            // Décimo segundo: para centralismo
            case "centralismo":

                int totalCaballos = 0;
                int totalVacas = 0;
                int totalHierro = 0;
                int totalVino = 0;
                int totalYerba = 0;

                // Décimo tercero: para default y proteccionismo economico
                switch (sistemaEconomico){
                    case "default":
                    case "liberalismo":
                        for(int i = 0; i<10; i++) {
                            int id = i + 1;
                            RecursosModel ciudad = entityManager.find(RecursosModel.class, id);

                            // Décimo cuarto: corroborar improductividad
                            if (ciudad.getImproductividad() == 0) {

                                // Décimo Quinto: corroborar recurso que produce y producirlo (liberalismo sólo +1)
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
                            // Décimo Sexto: sumar recursos de mapa
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
                        //Décimo Séptimo: pasarle recursos al presidente
                        RecursosModel ciudad = entityManager.find(RecursosModel.class, presidente);

                        ciudad.setCaballos(ciudad.getCaballos() + totalCaballos);
                        ciudad.setVacas(ciudad.getVacas() + totalVacas);
                        ciudad.setHierro(ciudad.getHierro() + totalHierro);
                        ciudad.setVino(ciudad.getVino() + totalVino);
                        ciudad.setYerba(ciudad.getYerba() + totalYerba);

                        //Décimo Octavo: actualizar tabla
                        entityManager.merge(ciudad);

                        break;

                    // Décimo Noveno: para proteccionismo economico
                    case "proteccionismo":

                        for(int i = 0; i<10; i++) {
                            int id = i + 1;
                            RecursosModel ciudad2 = entityManager.find(RecursosModel.class, id);

                            // Vigésimo: corroborar improductividad
                            if (ciudad2.getImproductividad() == 0) {

                                // Vigésimo primero: corroborar recurso que produce y producirlo (proteccionismo: según industria)
                                switch (ciudad2.getRecurso_que_produce()) {
                                    case "caballos":
                                        totalCaballos = totalCaballos + ciudad2.getNivel_industria();
                                        break;
                                    case "vacas":
                                        totalVacas = totalVacas + ciudad2.getNivel_industria();
                                        break;
                                    case "hierro":
                                        totalHierro = totalHierro + ciudad2.getNivel_industria();
                                        break;
                                    case "vino":
                                        totalVino = totalVino + ciudad2.getNivel_industria();
                                        break;
                                    case "yerba":
                                        totalYerba = totalYerba + ciudad2.getNivel_industria();
                                        break;
                                }
                            }
                            // Vigésimo segundo: sumar recursos de mapa
                            totalCaballos = totalCaballos + ciudad2.getCaballos_m();
                            ciudad2.setCaballos_m(0);
                            totalVacas = totalVacas + ciudad2.getVacas_m();
                            ciudad2.setVacas_m(0);
                            totalHierro = totalHierro + ciudad2.getHierro_m();
                            ciudad2.setHierro_m(0);
                            totalVino = totalVino + ciudad2.getVino_m();
                            ciudad2.setVino_m(0);
                            totalYerba = totalYerba + ciudad2.getYerba_m();
                            ciudad2.setYerba_m(0);

                        }
                        // Vigésimo tercero: pasarle recursos al presidente
                        RecursosModel ciudad2 = entityManager.find(RecursosModel.class, presidente);

                        ciudad2.setCaballos(ciudad2.getCaballos() + totalCaballos);
                        ciudad2.setVacas(ciudad2.getVacas() + totalVacas);
                        ciudad2.setHierro(ciudad2.getHierro() + totalHierro);
                        ciudad2.setVino(ciudad2.getVino() + totalVino);
                        ciudad2.setYerba(ciudad2.getYerba() + totalYerba);

                        //Décimo Octavo: actualizar tabla
                        entityManager.merge(ciudad2);

                        break;
                }
                break;
        }
    }

    @Override
    public void actualizarOficiales() {

        for(int i = 0; i<10; i++) {
            int id = i + 1;
            EjercitosModel ciudad = entityManager.find(EjercitosModel.class, id);
            switch (ciudad.getOficial_a()){
                case "1":
                    switch (ciudad.getNuevo_oficial_a()){
                        case "2":
                            ciudad.setOficial_a("2");
                            break;
                        case "3":
                            ciudad.setOficial_a("3");
                            break;
                    }
                case "2":
                    if(ciudad.getNuevo_oficial_a().equals("3")){
                        ciudad.setOficial_a("3");
                    }
            }

            switch (ciudad.getOficial_b()){
                case "1":
                    switch (ciudad.getNuevo_oficial_b()){
                        case "2":
                            ciudad.setOficial_b("2");
                            break;
                        case "3":
                            ciudad.setOficial_c("3");
                            break;
                    }
                case "2":
                    if(ciudad.getNuevo_oficial_b().equals("3")){
                        ciudad.setOficial_b("3");
                    }
            }

            switch (ciudad.getOficial_c()){
                case "1":
                    switch (ciudad.getNuevo_oficial_c()){
                        case "2":
                            ciudad.setOficial_c("2");
                            break;
                        case "3":
                            ciudad.setOficial_c("3");
                            break;
                    }
                case "2":
                    if(ciudad.getNuevo_oficial_c().equals("3")){
                        ciudad.setOficial_c("3");
                    }
            }

            switch (ciudad.getOficial_d()){
                case "1":
                    switch (ciudad.getNuevo_oficial_d()){
                        case "2":
                            ciudad.setOficial_d("2");
                            break;
                        case "3":
                            ciudad.setOficial_d("3");
                            break;
                    }
                case "2":
                    if(ciudad.getNuevo_oficial_d().equals("3")){
                        ciudad.setOficial_d("3");
                    }
            }

            switch (ciudad.getOficial_e()){
                case "1":
                    switch (ciudad.getNuevo_oficial_e()){
                        case "2":
                            ciudad.setOficial_e("2");
                            break;
                        case "3":
                            ciudad.setOficial_e("3");
                            break;
                    }
                case "2":
                    if(ciudad.getNuevo_oficial_e().equals("3")){
                        ciudad.setOficial_e("3");
                    }
            }
        }
    }

    @Override
    public void permitirActualizarListaCapitanes(OtrosModel nuevo) {
        OtrosModel permitir = entityManager.find(OtrosModel.class, "actualizar_capitanes");
        permitir.setValor_int(nuevo.getValor_int());
    }

    /*@Override
    public List<DeterminandoConflictosModel> moverAntesDeConflictos() {

        List<DeterminandoConflictosModel> conflictos = new ArrayList<>();

        // Segundo: Buscar caminos que tengan los mismos puntos.

        String query = "FROM EjercitosModel";
        List<EjercitosModel> listadeEjercitos = entityManager.createQuery(query).getResultList();

        for (EjercitosModel ejercito : listadeEjercitos) {

            // Seteo ruta a partir de ubicacion y destino
            ejercito.setRuta_a_usar(buscarCaminos(ejercito.getUbicacion_militar(), ejercito.getDestino_1()));

            // Asegurar que destino queda a 1 casillero de distancia
            boolean movimientoLegal = false;

            int ordenUbicacion = 0;
            int ordenDestino = 0;
            for (String punto:
                    ejercito.getRuta_a_usar()) {
                ordenUbicacion++;
                if(punto.equals(ejercito.getUbicacion_militar())){
                    break;
                }
            }
            for (String punto:
                    ejercito.getRuta_a_usar()) {
                ordenDestino++;
                if(punto.equals(ejercito.getDestino_1())){
                    break;
                }
            }
            if((ordenUbicacion == ordenDestino+1) || (ordenUbicacion == ordenDestino-1)){
                movimientoLegal = true;
            }

            // Conflictos en destino
            for (EjercitosModel segundoEjercito: listadeEjercitos
                 ) {
                if(ejercito.getDestino_1().equals(segundoEjercito.getDestino_1())){

                    DeterminandoConflictosModel conflicto = new DeterminandoConflictosModel();
                    conflicto.setUbicacion(ejercito.getDestino_1());
                    conflicto.setEjercito_1(ejercito.getCiudad());
                    if(conflicto.getEjercito_2().equals("")){
                        conflicto.setEjercito_2(segundoEjercito.getCiudad());
                    } else if(conflicto.getEjercito_3().equals("")){
                        conflicto.setEjercito_3(segundoEjercito.getCiudad());
                    } else{
                        conflicto.setEjercito_4(segundoEjercito.getCiudad());
                    }
                    conflictos.add(conflicto);
                }
            }

            // Conflictos cruzados
            for (EjercitosModel segundoEjercito:
                 listadeEjercitos) {
                if((ejercito.getDestino_1().equals(segundoEjercito.getUbicacion_militar())) &&
                        (ejercito.getUbicacion_militar().equals(segundoEjercito.getDestino_1()))){

                    DeterminandoConflictosModel conflicto = new DeterminandoConflictosModel();
                    conflicto.setUbicacion(ejercito.getDestino_1());
                    conflicto.setEjercito_1(ejercito.getCiudad());
                    if(conflicto.getEjercito_2().equals("")){
                        conflicto.setEjercito_2(segundoEjercito.getCiudad());
                    } else if(conflicto.getEjercito_3().equals("")){
                        conflicto.setEjercito_3(segundoEjercito.getCiudad());
                    } else{
                        conflicto.setEjercito_4(segundoEjercito.getCiudad());
                    }
                    conflictos.add(conflicto);
                }
            }
        }
        return conflictos;
    }
     */

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
