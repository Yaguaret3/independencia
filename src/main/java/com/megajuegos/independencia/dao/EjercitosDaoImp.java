package com.megajuegos.independencia.dao;

import com.megajuegos.independencia.models.EjercitosModel;
import com.megajuegos.independencia.models.RecursosModel;
import com.megajuegos.independencia.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class EjercitosDaoImp implements EjercitosDao{

    @Autowired
    JWTUtil jwtUtil;

    @PersistenceContext
    private EntityManager entityManager;

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
    public EjercitosModel recursosEjercito(String ciudad) {

        EjercitosModel recursos = entityManager.find(EjercitosModel.class, ciudad);

        return recursos;

    }

    @Override
    public void movimientos(String ciudad, EjercitosModel traido) {

        // Primero tomar recursos
        EjercitosModel recursos = entityManager.find(EjercitosModel.class, ciudad);

        // Segundo: sobreescribir movimiento y destinos
        recursos.setMovimiento(traido.getMovimiento());
        recursos.setDestino_1(traido.getDestino_1());
        recursos.setDestino_2(traido.getDestino_2());
        recursos.setDestino_3(traido.getDestino_3());

    }

    @Override
    public void asignarUnidades(String ciudad, EjercitosModel traido) {

        // Primero: Tomar recursos

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


}
