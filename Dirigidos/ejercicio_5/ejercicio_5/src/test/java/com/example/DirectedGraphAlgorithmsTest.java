package com.example;

import com.example.edge.WeightedEdge;
import com.example.result.IDijkstraResult;
import com.example.result.IFloydWarshallResult;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DirectedGraphAlgorithmsTest {

    private DirectedGraph<String, WeightedEdge> grafo;
    private DirectedGraphAlgorithms algoritmos;

    @Before
    public void setUp() {
        grafo = new DirectedGraph<>();
        algoritmos = new DirectedGraphAlgorithms();

        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarVertice("D");

        grafo.agregarArista("A", "B", new WeightedEdge(1));
        grafo.agregarArista("B", "C", new WeightedEdge(2));
        grafo.agregarArista("A", "C", new WeightedEdge(5));
        grafo.agregarArista("C", "D", new WeightedEdge(1));
    }

    @Test
    public void Dijkstratest() {
        IDijkstraResult<String> res = algoritmos.dijkstra(grafo.construirComparable("A"), grafo);

        // A-B-C-D sale 4, mas barato que ir directo A-C
        assertEquals(4.0, res.getCost("D"), 0.001);
        assertEquals(List.of("A", "B", "C", "D"), res.getPath("D"));
    }

    @Test
    public void DijkstraNoAlcanzabletest() {
        IDijkstraResult<String> res = algoritmos.dijkstra(grafo.construirComparable("D"), grafo);
        // desde D no se llega a ningun lado
        assertEquals(Double.POSITIVE_INFINITY, res.getCost("A"), 0.001);
    }

    @Test
    public void Floydtest() {
        IFloydWarshallResult<String> res = algoritmos.floyd(grafo);

        assertEquals(3.0, res.getCost("A", "C"), 0.001);
        assertEquals(List.of("A", "B", "C"), res.getPath("A", "C"));
        assertEquals(0.0, res.getCost("B", "B"), 0.001);
    }

    @Test
    public void RecorridoEnProfundidadtest() {
        List<String> visitados = new ArrayList<>();
        algoritmos.recorridoEnProfundidad(grafo, grafo.construirComparable("A"), visitados::add);

        assertEquals(4, visitados.size());
        assertEquals("A", visitados.get(0));
    }

    @Test
    public void ClasificacionTopologicatest() {
        List<String> orden = algoritmos.calcularClasificacionTopologica(grafo);
        assertEquals(List.of("A", "B", "C", "D"), orden);
    }

    //------------------------------EJERCICIO 4----------------------------------------

    private DirectedGraph<String, WeightedEdge> grafoEjercicio4() {
        DirectedGraph<String, WeightedEdge> g = new DirectedGraph<>();
        g.agregarVertice("Asuncion");
        g.agregarVertice("Buenos Aires");
        g.agregarVertice("Curitiba");
        g.agregarVertice("Montevideo");
        g.agregarVertice("Porto Alegre");
        g.agregarVertice("Rio de Janeiro");

        g.agregarArista("Asuncion", "Buenos Aires", new WeightedEdge(1600));
        g.agregarArista("Asuncion", "Curitiba", new WeightedEdge(800));
        g.agregarArista("Asuncion", "Porto Alegre", new WeightedEdge(700));
        g.agregarArista("Buenos Aires", "Asuncion", new WeightedEdge(1600));
        g.agregarArista("Buenos Aires", "Montevideo", new WeightedEdge(200));
        g.agregarArista("Buenos Aires", "Porto Alegre", new WeightedEdge(1250));
        g.agregarArista("Curitiba", "Rio de Janeiro", new WeightedEdge(1500));
        g.agregarArista("Montevideo", "Buenos Aires", new WeightedEdge(200));
        g.agregarArista("Montevideo", "Porto Alegre", new WeightedEdge(1000));
        g.agregarArista("Porto Alegre", "Montevideo", new WeightedEdge(1000));
        g.agregarArista("Rio de Janeiro", "Curitiba", new WeightedEdge(1500));
        return g;
    }

    @Test
    public void ProfundidadEjercicio4test() {
        DirectedGraph<String, WeightedEdge> g = grafoEjercicio4();
        List<String> visitados = new ArrayList<>();
        algoritmos.recorridoEnProfundidad(g, g.construirComparable("Asuncion"), visitados::add);

        // tiene que dar el mismo orden que hice a mano en la parte 1
        assertEquals(List.of("Asuncion", "Buenos Aires", "Montevideo", "Porto Alegre", "Curitiba", "Rio de Janeiro"), visitados);
    }

    @Test
    public void ProfundidadTodoElGrafotest() {
        DirectedGraph<String, WeightedEdge> g = grafoEjercicio4();
        g.agregarVertice("Canada");
        List<String> visitados = new ArrayList<>();
        algoritmos.recorridoEnProfundidad(g, visitados::add);

        // Santiago no tiene aristas, igual tiene que aparecer y cada vertice una sola vez
        assertEquals(7, visitados.size());
        assertEquals("Asuncion", visitados.get(0));
        assertEquals("Canada", visitados.get(6));
    }
}