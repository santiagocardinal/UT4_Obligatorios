package com.example;

import com.example.edge.WeightedEdge;
import com.example.result.Path;

import java.util.List;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //System.out.println( "Hello World!" );
        DirectedGraph<String, WeightedEdge> grafo = cargarGrafo();
        DirectedGraphAlgorithms algoritmos = new DirectedGraphAlgorithms();

        System.out.println("TODOS LOS CAMINOS");

        // el caso que pide la letra
        mostrarCaminos(grafo, algoritmos, "Montevideo", "Porto Alegre");

        // otros casos para probar
        mostrarCaminos(grafo, algoritmos, "Asuncion", "Montevideo");
        mostrarCaminos(grafo, algoritmos, "Rio de Janeiro", "Montevideo");
    }

    // grafo del ejercicio 4 (cuadro 3)
    private static DirectedGraph<String, WeightedEdge> cargarGrafo() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();

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

        return grafo;
    }

    private static void mostrarCaminos(DirectedGraph<String, WeightedEdge> grafo, DirectedGraphAlgorithms algoritmos,
                                       String origen, String destino) {
        System.out.println();
        System.out.println("Caminos de " + origen + " a " + destino + ":");

        List<Path<String>> caminos = algoritmos.obtenerTodosLosCaminos(
                grafo.construirComparable(origen), grafo.construirComparable(destino), grafo);

        if (caminos.isEmpty()) {
            System.out.println("  No hay ningun camino posible.");
            return;
        }

        Path<String> masBarato = caminos.get(0);
        int numero = 1;
        for (Path<String> camino : caminos) {
            System.out.println("  " + numero + ") " + String.join(" -> ", camino.getPath()));
            System.out.println("     costo: " + detalleCosto(grafo, camino.getPath()) + " = " + camino.getCost());

            if (camino.getCost() < masBarato.getCost()) {
                masBarato = camino;
            }
            numero++;
        }

        System.out.println("  Total de caminos: " + caminos.size());
        System.out.println("  El mas barato es " + String.join(" -> ", masBarato.getPath()) + " con costo " + masBarato.getCost());
    }

    // arma el texto "200 + 1600 + 700" con el costo de cada vuelo del camino
    private static String detalleCosto(DirectedGraph<String, WeightedEdge> grafo, List<String> camino) {
        String detalle = "";
        for (int i = 0; i < camino.size() - 1; i++) {
            double costo = grafo.obtenerArista(camino.get(i), camino.get(i + 1)).dato().getWeight();
            if (i > 0) {
                detalle += " + ";
            }
            detalle += (int) costo;
        }
        return detalle;
    }
}

