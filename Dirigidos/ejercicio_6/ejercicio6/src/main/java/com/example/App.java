package com.example;

import com.example.edge.WeightedEdge;
import com.example.result.CaminoCriticoResult;
import com.example.result.Path;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //System.out.println( "Hello World!" );
        DirectedGraph<String, WeightedEdge> proyecto = new DirectedGraph<>();
        DirectedGraphAlgorithms algoritmos = new DirectedGraphAlgorithms();

        // proyecto de ejemplo, las aristas son tareas con su duracion en dias
        proyecto.agregarVertice("Inicio");
        proyecto.agregarVertice("B");
        proyecto.agregarVertice("C");
        proyecto.agregarVertice("D");
        proyecto.agregarVertice("Fin");

        proyecto.agregarArista("Inicio", "B", new WeightedEdge(3));
        proyecto.agregarArista("Inicio", "C", new WeightedEdge(2));
        proyecto.agregarArista("B", "D", new WeightedEdge(4));
        proyecto.agregarArista("C", "D", new WeightedEdge(6));
        proyecto.agregarArista("C", "Fin", new WeightedEdge(1));
        proyecto.agregarArista("D", "Fin", new WeightedEdge(2));

        // parte 1
        System.out.println("Tiene ciclos: " + proyecto.tieneCiclos());

        // parte 2
        CaminoCriticoResult<String> res = algoritmos.calcularCaminoCritico(proyecto);
        System.out.println("Camino critico: " + String.join(" -> ", res.getCaminoCritico().getPath())
                + " (" + res.getCostoCritico() + " dias)");
        System.out.println("Otras secuencias:");
        for (Path<String> s : res.getOtrasSecuencias()) {
            System.out.println("  " + String.join(" -> ", s.getPath()) + " costo: " + s.getCost()
                    + " holgura: " + res.getHolgura(s));
        }

        // parte 3
        System.out.println("Orden topologico: " + algoritmos.calcularClasificacionTopologica(proyecto));
    }
}
