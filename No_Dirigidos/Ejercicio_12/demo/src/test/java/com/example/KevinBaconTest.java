package com.example.grafo;

import com.example.grafo.model.IGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KevinBaconTest {

    private IGraph<String, String> grafo;
    private KevinBacon algoritmo;

    @BeforeEach
    void setUp() {

        grafo = new Graph<>();
        algoritmo = new KevinBacon();

        // Agregamos los actores
        grafo.agregarVertice("Kevin_Bacon");
        grafo.agregarVertice("Tom_Hanks");
        grafo.agregarVertice("Tom_Cruise");
        grafo.agregarVertice("John_Goodman");
        grafo.agregarVertice("Lukas_Haas");
        grafo.agregarVertice("Djimon_Hounsou");
        grafo.agregarVertice("Jason_Statham");
        grafo.agregarVertice("Actor_Sin_Conexion");

        // Agregamos las relaciones
        grafo.agregarArista("Kevin_Bacon", "Tom_Hanks", "Apolo 13");
        grafo.agregarArista("Kevin_Bacon", "Tom_Cruise", "A Few Good Men");
        grafo.agregarArista("Tom_Cruise", "John_Goodman", "Película de prueba 1");
        grafo.agregarArista("John_Goodman", "Lukas_Haas", "Película de prueba 2");
        grafo.agregarArista("Lukas_Haas", "Djimon_Hounsou", "Película de prueba 3");
        grafo.agregarArista("Djimon_Hounsou", "Jason_Statham", "Película de prueba 4");
    }

    @Test
    void kevinBaconTieneNumeroCero() {
        assertEquals(0, algoritmo.numBacon(grafo, "Kevin_Bacon"));
    }

    @Test
    void actorConRelacionDirectaTieneNumeroUno() {
        assertEquals(1, algoritmo.numBacon(grafo, "Tom_Cruise"));
    }

    @Test
    void actorConDosVinculosTieneNumeroDos() {
        assertEquals(2, algoritmo.numBacon(grafo, "John_Goodman"));
    }

    @Test
    void actorConVariosVinculosTieneNumeroCorrecto() {
        assertEquals(5, algoritmo.numBacon(grafo, "Jason_Statham"));
    }

    @Test
    void actorSinConexionDevuelveMenosUno() {
        assertEquals(-1, algoritmo.numBacon(grafo, "Actor_Sin_Conexion"));
    }

    @Test
    void actorInexistenteDevuelveMenosUno() {
        assertEquals(-1, algoritmo.numBacon(grafo, "Actor_Inexistente"));
    }
}