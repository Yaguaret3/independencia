package com.megajuegos.independencia.dao;

import com.megajuegos.independencia.models.EjercitosModel;
import com.megajuegos.independencia.models.OtrosModel;
import com.megajuegos.independencia.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class EjercitosDaoImp implements EjercitosDao{

    @Autowired
    JWTUtil jwtUtil;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public EjercitosModel recursosEjercito(String ciudad) {

        EjercitosModel recursos = entityManager.find(EjercitosModel.class, ciudad);
        return recursos;

    }

    @Override
    public void movimientos(String ciudad, EjercitosModel traido, int fase) {

        // Primero seleccionar ciudad
        EjercitosModel ejercito = entityManager.find(EjercitosModel.class, ciudad);

        // Segundo: sobreescribir movimiento y destinos
        ejercito.setMovimiento(traido.getMovimiento());
        ejercito.setDestino_1(traido.getDestino_1());

        // Tercero: ubicación comercial
        if(fase == 3){
            ejercito.setUbicacion_comercial(traido.getDestino_1());
        }
    }

    @Override
    public void asignarUnidades(String ciudad, EjercitosModel traido) {

        // Primero: Tomar ejercito

        EjercitosModel recursos = entityManager.find(EjercitosModel.class, ciudad);

        // Segundo: Comparar que las unidades a pasar alcancen

        if(recursos.getUnidades_recien_llegadas() >= traido.getUnidades_a_asignar()){

            // Tercero: Tomar ejército destino

            EjercitosModel destino = entityManager.find(EjercitosModel.class, traido.getDar_unidades_a());

            // Cuarto: Sumar unidades en el ejército destino y restarlas a las disponibles propias

            destino.setUnidades_agrupadas(destino.getUnidades_agrupadas() + traido.getUnidades_a_asignar());
            recursos.setUnidades_recien_llegadas(recursos.getUnidades_recien_llegadas() - traido.getUnidades_a_asignar());

            // Quinto: Actualizar tabla

            entityManager.merge(destino);
            entityManager.merge(recursos);
        }
    }

    @Override
    public int fase() {

        //Tomamos la fase en la que estamos
        OtrosModel fase = entityManager.find(OtrosModel.class, "fase_militar");

        return fase.getValor();
    }

    @Override
    public List<EjercitosModel> listarMovimientos() {

        List<EjercitosModel> lista = new ArrayList<>();

        String[] ciudades = {"Buenos Aires", "Montevideo", "Asunción", "Santa Fe", "Córdoba", "Mendoza",
                            "Salta", "Tucumán", "Potosí", "La Paz"};
        // Ciclo para meter info a la lista
        for (int i = 0; i < 10; i++) {

            EjercitosModel vacio = new EjercitosModel();
            EjercitosModel ejercito = entityManager.find(EjercitosModel.class, ciudades[i]);

            vacio.setCiudad(ejercito.getCiudad());
            vacio.setMovimiento(ejercito.getMovimiento());
            vacio.setDestino_1(ejercito.getDestino_1());
            vacio.setNivel_mision_comercial(ejercito.getNivel_mision_comercial());
            vacio.setUnidades_agrupadas(ejercito.getUnidades_agrupadas());

            lista.add(vacio);
        }

        return lista;
    }

    @Override
    public boolean actualizarCapitanes() {

        // Primero: Tomamos de la base el dato
        OtrosModel actualizar = entityManager.find(OtrosModel.class, "actualizar_capitanes");

        // Segundo: Si pueden actualizar, devolvemos true, si no false.
        if(actualizar.getValor() == 1){
            return true;
        }
        return false;
    }


}
