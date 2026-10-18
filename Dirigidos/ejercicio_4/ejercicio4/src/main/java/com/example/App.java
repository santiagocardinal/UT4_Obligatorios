package com.example;

import com.example.edge.Edge;
import com.example.edge.WeightedEdge;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //System.out.println( "Hello World!" );
        
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();

        // vertices en el mismo orden que la matriz del ejercicio
        grafo.agregarVertice("Asuncion");
        grafo.agregarVertice("Buenos Aires");
        grafo.agregarVertice("Curitiba");
        grafo.agregarVertice("Montevideo");
        grafo.agregarVertice("Porto Alegre");
        grafo.agregarVertice("Rio de Janeiro");

        grafo.agregarArista("Asuncion", "Buenos Aires", new WeightedEdge(1600));
        grafo.agregarArista("Asuncion", "Curitiba", new WeightedEdge(800));
        grafo.agregarArista("Asuncion", "Porto Alegre", new WeightedEdge(700));
        grafo.agregarArista("Buenos Aires", "Asuncion", new WeightedEdge(1600));
        grafo.agregarArista("Buenos Aires", "Montevideo", new WeightedEdge(200));
        grafo.agregarArista("Buenos Aires", "Porto Alegre", new WeightedEdge(1250));
        grafo.agregarArista("Curitiba", "Rio de Janeiro", new WeightedEdge(1500));
        grafo.agregarArista("Montevideo", "Buenos Aires", new WeightedEdge(200));
        grafo.agregarArista("Montevideo", "Porto Alegre", new WeightedEdge(1000));
        grafo.agregarArista("Porto Alegre", "Montevideo", new WeightedEdge(1000));
        grafo.agregarArista("Rio de Janeiro", "Curitiba", new WeightedEdge(1500));

        // muestro la lista de adyacencias para comparar con lo de la parte 1
        System.out.println("Lista de adyacencias:");
        for (String v : grafo.vertices()) {
            System.out.print("  " + v + " -> ");
            for (Edge<String, WeightedEdge> e : grafo.adyacencias(grafo.construirComparable(v))) {
                System.out.print("(" + e.target() + ", " + e.dato() + ") ");
            }
            System.out.println();
        }

        DirectedGraphAlgorithms algoritmos = new DirectedGraphAlgorithms();

        System.out.println();
        System.out.println("Recorrido en profundidad desde Asuncion:");
        System.out.print("  ");
        algoritmos.recorridoEnProfundidad(grafo, grafo.construirComparable("Asuncion"), v -> System.out.print(v + "  "));
        System.out.println();

        // agrego un vertice suelto para ver que el recorrido completo tambien lo visita
        grafo.agregarVertice("Canada");

        System.out.println();
        System.out.println("Recorrido en profundidad de todo el grafo (con Canada suelto):");
        System.out.print("  ");
        algoritmos.recorridoEnProfundidad(grafo, v -> System.out.print(v + "  "));
        System.out.println();

    }
}
