package com.example.grafo;

import com.example.grafo.model.IGraph;
import com.example.grafo.model.edge.Edge;

import java.util.*;

public class KevinBacon {

    public int numBacon(IGraph<String, String> grafo, String actor) {

        // Verificamos que el actor exista
        if (!grafo.existeVertice(grafo.construirComparable(actor))) {
            return -1;
        }

        Queue<String> cola = new LinkedList<>();
        Set<String> visitados = new HashSet<>();
        Map<String, Integer> distancias = new HashMap<>();

        // Comenzamos desde el actor consultado
        cola.add(actor);
        visitados.add(actor);
        distancias.put(actor, 0);

        while (!cola.isEmpty()) {

            String actual = cola.poll();

            // Si llegamos a Kevin Bacon, retornamos la distancia
            if (actual.equals("Kevin_Bacon")) {
                return distancias.get(actual);
            }

            // Recorremos los actores relacionados
            for (Edge<String, String> arista :
                    grafo.adyacencias(grafo.construirComparable(actual))) {

                String vecino = arista.target();

                if (!visitados.contains(vecino)) {

                    visitados.add(vecino);

                    distancias.put(
                            vecino,
                            distancias.get(actual) + 1
                    );

                    cola.add(vecino);
                }
            }
        }

        // No existe un camino hasta Kevin Bacon
        return -1;
    }
}