package com.example;

import com.example.edge.WeightedEdge;
import com.example.result.CaminoCriticoResult;
import com.example.result.Path;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class DirectedGraphAlgorithmsTest {

    private DirectedGraph<String, WeightedEdge> proyecto;
    private DirectedGraphAlgorithms algoritmos;

    @Before
    public void setUp() {
        algoritmos = new DirectedGraphAlgorithms();

        // un proyecto chico: las aristas son tareas con su duracion en dias
        proyecto = new DirectedGraph<>();
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
    }

    //------------------------------PARTE 1 - CICLOS----------------------------------------

    @Test
    public void SinCiclostest() {
        // el proyecto no tiene ciclos
        assertFalse(proyecto.tieneCiclos());
    }

    @Test
    public void ConCiclotest() {
        // si agrego una arista de vuelta aparece el ciclo D -> B -> D
        proyecto.agregarArista("D", "B", new WeightedEdge(1));
        assertTrue(proyecto.tieneCiclos());
    }

    //------------------------------PARTE 2 - CAMINO CRITICO----------------------------------------

    @Test
    public void CaminoCriticotest() {
        CaminoCriticoResult<String> res = algoritmos.calcularCaminoCritico(proyecto);

        // Inicio-C-D-Fin = 2 + 6 + 2 = 10, es el mas largo
        assertEquals(List.of("Inicio", "C", "D", "Fin"), res.getCaminoCritico().getPath());
        assertEquals(10, res.getCostoCritico(), 0.001);
    }

    @Test
    public void Holguratest() {
        CaminoCriticoResult<String> res = algoritmos.calcularCaminoCritico(proyecto);

        // las otras dos secuencias son Inicio-B-D-Fin (9) y Inicio-C-Fin (3)
        assertEquals(2, res.getOtrasSecuencias().size());
        for (Path<String> secuencia : res.getOtrasSecuencias()) {
            if (secuencia.getPath().equals(List.of("Inicio", "B", "D", "Fin"))) {
                assertEquals(1, res.getHolgura(secuencia), 0.001);
            } else {
                // esta deberia ser Inicio-C-Fin con 7 dias de holgura
                assertEquals(7, res.getHolgura(secuencia), 0.001);
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void CaminoCriticoConCiclotest() {
        // con un ciclo no se puede calcular
        proyecto.agregarArista("Fin", "Inicio", new WeightedEdge(1));
        algoritmos.calcularCaminoCritico(proyecto);
    }

    //------------------------------PARTE 3 - CLASIFICACION TOPOLOGICA----------------------------------------

    @Test
    public void ClasificacionTopologicatest() {
        List<String> orden = algoritmos.calcularClasificacionTopologica(proyecto);

        assertEquals(5, orden.size());
        // cada tarea tiene que aparecer antes que las que dependen de ella
        assertTrue(orden.indexOf("Inicio") < orden.indexOf("B"));
        assertTrue(orden.indexOf("Inicio") < orden.indexOf("C"));
        assertTrue(orden.indexOf("B") < orden.indexOf("D"));
        assertTrue(orden.indexOf("C") < orden.indexOf("D"));
        assertTrue(orden.indexOf("D") < orden.indexOf("Fin"));
    }

    @Test
    public void ClasificacionTopologicaConCiclotest() {
        proyecto.agregarArista("Fin", "Inicio", new WeightedEdge(1));
        // con ciclos no hay orden posible, devuelve la lista vacia
        assertTrue(algoritmos.calcularClasificacionTopologica(proyecto).isEmpty());
    }
}