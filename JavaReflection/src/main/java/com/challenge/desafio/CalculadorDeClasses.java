package com.challenge.desafio;

import com.challenge.annotation.Somar;
import com.challenge.annotation.Subtrair;
import com.challenge.interfaces.Calculavel;

import java.lang.reflect.Field;
import java.math.BigDecimal;

public class CalculadorDeClasses implements Calculavel {
    @Override
    public BigDecimal somar(Object object) {
        return somaAtributos(object, Somar.class);
    }

    @Override
    public BigDecimal subtrair(Object object) {
        return somaAtributos(object, Subtrair.class);
    }

    @Override
    public BigDecimal totalizar(Object object) {
        return somar(object).subtract(subtrair(object));
    }


    private BigDecimal somaAtributos(Object object, Class annotation){
        BigDecimal soma = BigDecimal.ZERO;
        Field[] atributos = object.getClass().getDeclaredFields();

        for (Field atributo : atributos) {
            if (atributo.isAnnotationPresent(annotation)) {
                try {
                    atributo.setAccessible(true);
                    soma = soma.add((BigDecimal) atributo.get(object));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return soma;
    }

}
