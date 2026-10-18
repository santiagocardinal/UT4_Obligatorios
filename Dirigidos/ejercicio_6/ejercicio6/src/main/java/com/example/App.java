package com.example;

import com.example.edge.Edge;
import com.example.edge.WeightedEdge;
import com.example.result.CaminoCriticoResult;
import com.example.result.Path;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class App {

    public static void main(String[] args) {
        DirectedGraphAlgorithms algoritmos = new DirectedGraphAlgorithms();

        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        GraphLoader.cargarGrafoDesdeArchivos(grafo, buscarArchivo("aeropuertos_2.txt"), buscarArchivo("conexiones_2.txt"));

        System.out.println();
        System.out.println("Grafo cargado (lista de adyacencias):");
        for (String v : grafo.vertices()) {
            System.out.print("  " + v + " -> ");
            List<Edge<String, WeightedEdge>> ady = grafo.adyacencias(grafo.construirComparable(v));
            if (ady.isEmpty()) {
                System.out.print("(sin vuelos de salida)");
            }
            for (Edge<String, WeightedEdge> e : ady) {
                System.out.print(e.target() + "(" + (int) e.dato().getWeight() + ") ");
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("Ciclos ");
        if (grafo.tieneCiclos()) {
            System.out.println("El grafo SI tiene ciclos");
            List<String> ciclo = buscarUnCiclo(grafo);
            System.out.println("Ciclo encontrado: " + String.join(" -> ", ciclo));
        } else {
            System.out.println("El grafo NO tiene ciclos");
        }

                System.out.println();
        System.out.println("Camino critico");
        mostrarCaminoCritico(algoritmos, grafo);

        System.out.println();
        System.out.println("Clasificacion topologica");
        mostrarOrdenTopologico(algoritmos, grafo);

        DirectedGraph<String, WeightedEdge> desdeAsuncion = subgrafoDesde(algoritmos, grafo, "Asuncion");

        System.out.println();
        System.out.println("Sale de Asuncion ");
        System.out.println("Vertices: " + desdeAsuncion.vertices());
        System.out.println("Tiene ciclos: " + desdeAsuncion.tieneCiclos());

        System.out.println();
        System.out.println("Camino critico ");
        mostrarCaminoCritico(algoritmos, desdeAsuncion);

        System.out.println();
        System.out.println("Clasificacion topologica");
        mostrarOrdenTopologico(algoritmos, desdeAsuncion);
    }

    private static void mostrarCaminoCritico(DirectedGraphAlgorithms algoritmos, DirectedGraph<String, WeightedEdge> grafo) {
        try {
            CaminoCriticoResult<String> res = algoritmos.calcularCaminoCritico(grafo);
            System.out.println("Camino critico: " + String.join(" -> ", res.getCaminoCritico().getPath())
                    + " (costo " + res.getCostoCritico() + ")");
            System.out.println("Otras secuencias:");
            for (Path<String> s : res.getOtrasSecuencias()) {
                System.out.println("  " + String.join(" -> ", s.getPath()) + " costo: " + s.getCost()
                        + " holgura: " + res.getHolgura(s));
            }
        } catch (IllegalArgumentException e) {
            // si el grafo tiene ciclos no hay camino critico
            System.out.println("No se puede calcular: " + e.getMessage());
        }
    }

    private static void mostrarOrdenTopologico(DirectedGraphAlgorithms algoritmos, DirectedGraph<String, WeightedEdge> grafo) {
        List<String> orden = algoritmos.calcularClasificacionTopologica(grafo);
        if (orden.isEmpty()) {
            System.out.println("No hay clasificacion topologica porque el grafo tiene ciclos");
        } else {
            System.out.println("Orden topologico: " + String.join(" -> ", orden));
        }
    }

    private static DirectedGraph<String, WeightedEdge> subgrafoDesde(DirectedGraphAlgorithms algoritmos,
                                                                     DirectedGraph<String, WeightedEdge> grafo, String inicio) {
        List<String> alcanzables = new ArrayList<>();
        algoritmos.recorridoEnProfundidad(grafo, grafo.construirComparable(inicio), alcanzables::add);

        DirectedGraph<String, WeightedEdge> sub = new DirectedGraph<>();
        for (String v : grafo.vertices()) {
            if (alcanzables.contains(v)) {
                sub.agregarVertice(v);
            }
        }
        for (String v : sub.vertices()) {
            for (Edge<String, WeightedEdge> e : grafo.adyacencias(grafo.construirComparable(v))) {
                sub.agregarArista(e.source(), e.target(), e.dato());
            }
        }
        return sub;
    }

    private static List<String> buscarUnCiclo(DirectedGraph<String, WeightedEdge> grafo) {
        Set<String> visitados = new HashSet<>();
        for (String v : grafo.vertices()) {
            List<String> ciclo = buscarCicloDesde(grafo, v, visitados, new ArrayList<>());
            if (ciclo != null) {
                return ciclo;
            }
        }
        return new ArrayList<>();
    }

    private static List<String> buscarCicloDesde(DirectedGraph<String, WeightedEdge> grafo, String actual,
                                                 Set<String> visitados, List<String> camino) {
        if (camino.contains(actual)) {
            // volvi a un vertice del camino actual, el ciclo va desde ahi hasta aca
            List<String> ciclo = new ArrayList<>(camino.subList(camino.indexOf(actual), camino.size()));
            ciclo.add(actual);
            return ciclo;
        }
        if (visitados.contains(actual)) {
            return null;
        }
        visitados.add(actual);
        camino.add(actual);
        for (String sucesor : grafo.successors(grafo.construirComparable(actual))) {
            List<String> ciclo = buscarCicloDesde(grafo, sucesor, visitados, camino);
            if (ciclo != null) {
                return ciclo;
            }
        }
        camino.remove(camino.size() - 1);
        return null;
    }

    private static String buscarArchivo(String nombre) {
        if (new File(nombre).exists()) {
            return nombre;
        }
        return "../" + nombre;
    }
}
