package com.example.result;

import java.util.List;

/**
 * Resultado de calcular el camino critico de un grafo aciclico.
 * Guarda el camino critico (el de mayor costo) y todas las demas secuencias posibles.
 */
public class CaminoCriticoResult<V> {
    private final Path<V> caminoCritico;
    private final List<Path<V>> otrasSecuencias;

    public CaminoCriticoResult(Path<V> caminoCritico, List<Path<V>> otrasSecuencias) {
        this.caminoCritico = caminoCritico;
        this.otrasSecuencias = otrasSecuencias;
    }

    public Path<V> getCaminoCritico() {
        return caminoCritico;
    }

    public double getCostoCritico() {
        return caminoCritico.getCost();
    }

    public List<Path<V>> getOtrasSecuencias() {
        return otrasSecuencias;
    }

    /**
     * La holgura de una secuencia es cuanto se puede atrasar sin atrasar el proyecto:
     * costo del camino critico - costo de la secuencia
     */
    public double getHolgura(Path<V> secuencia) {
        return caminoCritico.getCost() - secuencia.getCost();
    }
}