# Ejercicio 10 – Búsqueda en Amplitud (BFS)

Una empresa productora de lácteos desea realizar un proceso para asegurarse de que recoge la leche de todos los productores que se encuentran en su red.

Para ello debe asegurarse de que los visita sistemáticamente, y una vez a cada uno.

El analista de la empresa ha considerado que el problema puede representarse mediante un **grafo no dirigido**, y que este se puede recorrer tanto utilizando método de búsqueda en profundidad como en amplitud.

### Parte 1

Dado el siguiente grafo no dirigido de ejemplo, realiza el recorrido del mismo mediante **búsqueda en amplitud**, indicando el árbol abarcador resultante, a partir del vértice con etiqueta `E`.

### Parte 2

Desarrolla un algoritmo, en pseudocódigo, para recorrer la estructura mediante búsqueda en amplitud.

> NOTA: Observa con cuidado las precondiciones.

### Parte 3

Utilizando las estructuras de datos ya implementadas, desarrolla la **búsqueda en amplitud**.

---

## Parte 1 – Recorrido en amplitud desde E

### 1. Representación del problema

Para representar la red de productores utilizamos un **grafo no dirigido**.

- **Vértices:** representan a los productores que debe visitar la empresa.
- **Aristas:** representan las conexiones existentes entre los productores.

Como el grafo es no dirigido, las conexiones se pueden recorrer en ambos sentidos.


### 2. Grafo del problema

Los vértices del grafo son:

A, B, C, D, E, F, G y H.

Las aristas del grafo de la letra (con sus pesos) son:

A–B (2), A–C (3), B–D (2), C–D (1), D–E (2), D–F (4), E–F (1), E–G (2), F–G (2), F–H (1), G–H (3)


La búsqueda en amplitud **no usa los pesos**: solo le importa qué vértices están conectados. Por eso, para el recorrido alcanza con la lista de adyacencias:

| Vértice | Vecinos |
|---|---|
| A | B, C |
| B | A, D |
| C | A, D |
| D | B, C, E, F |
| E | D, F, G |
| F | D, E, G, H |
| G | E, F, H |
| H | F, G |


### 3. Aplicación del algoritmo BFS

La búsqueda en amplitud utiliza una **cola (FIFO)** para recorrer el grafo.

Esto significa que el primer vértice que se agrega a la cola es el primero que se procesa. De esta manera, se visitan primero los vértices más cercanos al punto de partida y luego los que se encuentran a mayor distancia.

Comenzamos el recorrido desde el vértice **E**.

Para evitar visitar un mismo vértice más de una vez, utilizamos un conjunto de visitados. Un vértice se marca como visitado **en el momento en que se encola**, no cuando se procesa; así no puede entrar dos veces a la cola.

**Inicio:** visitados = {E}, cola = E

**Ejecución paso a paso:**

| Paso | Vértice procesado | Vecinos revisados | Nuevos vértices agregados a la cola | Cola después del paso | Visitados |
|---:|---|---|---|---|---|
| 1 | E | D, F, G | D, F, G | D, F, G | E, D, F, G |
| 2 | D | B, C, E, F | B, C | F, G, B, C | E, D, F, G, B, C |
| 3 | F | D, E, G, H | H | G, B, C, H | E, D, F, G, B, C, H |
| 4 | G | E, F, H | Ninguno | B, C, H | sin cambios |
| 5 | B | A, D | A | C, H, A | E, D, F, G, B, C, H, A |
| 6 | C | A, D | Ninguno | H, A | sin cambios |
| 7 | H | F, G | Ninguno | A | sin cambios |
| 8 | A | B, C | Ninguno | Vacía | sin cambios |

El recorrido obtenido es:

**E → D → F → G → B → C → H → A**

### 4. Árbol abarcador resultante

El árbol abarcador se construye utilizando únicamente las aristas que permitieron descubrir nuevos vértices durante el recorrido (la arista entre el vértice que se estaba procesando y el vecino nuevo).

Las conexiones seleccionadas son:

| Orden | Arista |
|---:|---|
| 1 | E – D |
| 2 | E – F |
| 3 | E – G |
| 4 | D – B |
| 5 | D – C |
| 6 | F – H |
| 7 | B – A |

Ordenado por niveles (distancia en cantidad de aristas desde E):

| Nivel | Vértices |
|---:|---|
| 0 | E |
| 1 | D, F, G |
| 2 | B, C, H |
| 3 | A |

El árbol abarcador tiene 8 vértices y 7 aristas (8 − 1).


---

## Parte 2 – Pseudocódigo de búsqueda en amplitud



### 1. Sobre las precondiciones

**Precondiciones:**

- El grafo no es nulo.
- El vértice de origen pertenece al grafo (si no, no se recorre nada).
- El grafo permite obtener los vecinos (adyacencias) de cada vértice.
- **No** hace falta que el grafo sea conexo.

**Postcondiciones:**

- Todos los vértices del grafo fueron visitados exactamente una vez.
- Los vértices de la componente del origen se visitan primero, en orden de amplitud (por niveles) desde el origen.
- Las aristas guardadas forman un árbol abarcador de cada componente (un bosque abarcador del grafo). Si el grafo es conexo, es un único árbol con |V| − 1 aristas.
- El grafo original no se modifica.

### 3. Pseudocódigo

```text
BEA(grafo, origen): (recorrido, arbol)

Precondiciones: las de la sección anterior
Postcondiciones: las de la sección anterior

### 3. Pseudocódigo
 
```text
BEA(grafo, origen): (recorrido, arbol)
 
Precondiciones: las de la sección anterior
Postcondiciones: las de la sección anterior
 
COMIENZO
    recorrido <- nueva Lista vacía
    arbol <- nueva Lista vacía
    visitados <- nuevo Conjunto vacío
 
    inicio <- grafo.buscarVertice(origen)
    SI inicio = nulo ENTONCES
        DEVOLVER (recorrido, arbol)                  // el origen no existe
    FIN SI
 
    // Primero se recorre la componente del origen
    BEADesde(grafo, inicio, visitados, recorrido, arbol)
 
    // Después las componentes que hayan quedado sin visitar (grafo no conexo)
    PARA CADA v EN grafo.vertices() HACER
        SI NO visitados.contiene(v) ENTONCES
            BEADesde(grafo, v, visitados, recorrido, arbol)
        FIN SI
    FIN PARA CADA
 
    DEVOLVER (recorrido, arbol)
FIN
```
 
```text
BEADesde(grafo, inicio, visitados, recorrido, arbol)
 
COMIENZO
    cola <- nueva Cola vacía
    visitados.agregar(inicio)
    cola.encolar(inicio)
 
    MIENTRAS NO cola.esVacia() HACER
        actual <- cola.desencolar()
        recorrido.agregar(actual)
 
        PARA CADA arista EN grafo.adyacencias(actual) HACER
            vecino <- arista.destino()
            SI NO visitados.contiene(vecino) ENTONCES
                visitados.agregar(vecino)            // se marca al encolar
                cola.encolar(vecino)
                arbol.agregar((actual, vecino))
            FIN SI
        FIN PARA CADA
    FIN MIENTRAS
FIN
```

### 4. Orden del tiempo de ejecución

- Cada vértice entra a la cola **una sola vez** (porque se marca al encolarlo): O(V).
- Cuando se procesa un vértice se recorren sus adyacencias. Sumando todos los vértices, cada arista se mira dos veces (una desde cada extremo, porque es no dirigido): O(A).
- El `PARA CADA` de `BEA` que busca vértices sin visitar recorre los vértices una vez: O(V).
- `contiene`, `agregar`, `encolar` y `desencolar` son O(1).
En total: **O(V + A)** usando listas de adyacencia.

---

## Parte 3 – Implementación en Java

Del proyecto del curso dejamos solamente lo necesario para el grafo no dirigido y la búsqueda en amplitud:
 
```
demo/
├── pom.xml
└── src/
    ├── main/java/com/example/
    │   ├── App.java                               ← main del ejercicio 10 (ver más abajo)
    │   └── grafo/
    │       ├── Graph.java                         ← implementación del grafo (vértices y aristas)
    │       ├── IUndirectedGraph.java              ← TDA grafo no dirigido
    │       ├── IUndirectedGraphAlgorithm.java     ← interfaz de los algoritmos (bea, prim, kruskal)
    │       ├── UndirectedGraphAlgorithmImpl.java  ← acá está implementada la BEA
    │       └── model/
    │           ├── IGraph.java                    ← operaciones básicas de un grafo
    │           └── edge/
    │               ├── Edge.java                  ← arista (source, target, dato)
    │               ├── UndirectedEdge.java        ← arista no dirigida
    │               └── WeightedEdge.java          ← peso de la arista
    └── test/java/com/example/grafo/
        ├── GraphTest.java
        ├── UndirectedGraphAlgorithmImplTest.java  ← tests de la BEA
        └── model/edge/
            ├── UndirectedEdgeTest.java
            └── WeightedEdgeTest.java
```
### 2. Implementación de la BEA
 
Usamos las estructuras ya implementadas del proyecto (`IUndirectedGraph`, `Edge`) y las colecciones de Java (`HashSet` para visitados y `LinkedList` como cola). En `UndirectedGraphAlgorithmImpl` dejamos dos versiones:
 
- `bea(graph, consumer)`: es la de la interfaz, recorre todo el grafo empezando por el primer vértice.
- `bea(graph, origen, consumer)`: empieza por el vértice que le indicamos (por ejemplo `E`, como pide la Parte 1) y después sigue con las componentes que hayan quedado sin visitar.
Las dos usan el mismo método auxiliar `beaDesde`, que hace el BFS de una componente. En lugar de devolver una lista, cada vértice visitado se le pasa a un `Consumer`, así quien llama decide qué hacer con él (imprimirlo, guardarlo en una lista, etc.).
 
```java
// ---------------------------------------------------------------------- BEA
 
@Override
public <V, D> void bea(IUndirectedGraph<V, D> graph, Consumer<V> consumer) {
    Set<V> visited = new HashSet<>();
 
    // Se recorre cada componente conexa
    for (V start : graph.vertices()) {
        if (!visited.contains(start)) {
            beaDesde(graph, start, visited, consumer);
        }
    }
}
 
/**
 * BEA empezando desde un vértice dado. Si el grafo no es conexo, después de recorrer
 * la componente del origen sigue con los vértices que hayan quedado sin visitar.
 */
public <V, D> void bea(IUndirectedGraph<V, D> graph, Comparable<V> origen, Consumer<V> consumer) {
    V start = graph.buscarVertice(origen);
    if (start == null) {
        return; // el origen no existe en el grafo
    }
 
    Set<V> visited = new HashSet<>();
    beaDesde(graph, start, visited, consumer);
 
    for (V v : graph.vertices()) {
        if (!visited.contains(v)) {
            beaDesde(graph, v, visited, consumer);
        }
    }
}
 
/** BFS de una sola componente, a partir de start. */
private <V, D> void beaDesde(IUndirectedGraph<V, D> graph, V start,
                             Set<V> visited, Consumer<V> consumer) {
    Queue<V> queue = new LinkedList<>();
    visited.add(start);
    queue.add(start);
 
    while (!queue.isEmpty()) {
        V current = queue.poll();
        consumer.accept(current);
 
        for (Edge<V, D> edge : graph.adyacencias(graph.construirComparable(current))) {
            V neighbor = edge.target();
            if (visited.add(neighbor)) { // add devuelve false si ya estaba
                queue.add(neighbor);
            }
        }
    }
}
```
 
Si se quiere que `bea(graph, origen, consumer)` también se pueda usar desde la interfaz, hay que agregar su firma en `IUndirectedGraphAlgorithm` y ponerle `@Override`.
 
### 3. Programa principal (`App.java`)
 
Para probar el recorrido de la Parte 1 armamos el grafo de la letra y llamamos a la BEA empezando en `E`:
 
```java
package com.example;
 
import com.example.grafo.Graph;
import com.example.grafo.IUndirectedGraph;
import com.example.grafo.UndirectedGraphAlgorithmImpl;
import com.example.grafo.model.edge.WeightedEdge;
 
import java.util.ArrayList;
import java.util.List;
 
public class App {
 
    // Grafo no dirigido: hereda todo de Graph y agrega el tipo IUndirectedGraph
    static class GrafoNoDirigido<V, D> extends Graph<V, D> implements IUndirectedGraph<V, D> {
    }
 
    public static void main(String[] args) {
        GrafoNoDirigido<String, WeightedEdge> grafo = new GrafoNoDirigido<>();
 
        for (String v : new String[] { "A", "B", "C", "D", "E", "F", "G", "H" }) {
            grafo.agregarVertice(v);
        }
 
        // Aristas del grafo de la letra, cargadas en orden alfabético
        grafo.agregarArista("A", "B", new WeightedEdge(2));
        grafo.agregarArista("A", "C", new WeightedEdge(3));
        grafo.agregarArista("B", "D", new WeightedEdge(2));
        grafo.agregarArista("C", "D", new WeightedEdge(1));
        grafo.agregarArista("D", "E", new WeightedEdge(2));
        grafo.agregarArista("D", "F", new WeightedEdge(4));
        grafo.agregarArista("E", "F", new WeightedEdge(1));
        grafo.agregarArista("E", "G", new WeightedEdge(2));
        grafo.agregarArista("F", "G", new WeightedEdge(2));
        grafo.agregarArista("F", "H", new WeightedEdge(1));
        grafo.agregarArista("G", "H", new WeightedEdge(3));
 
        UndirectedGraphAlgorithmImpl algoritmos = new UndirectedGraphAlgorithmImpl();
 
        List<String> recorrido = new ArrayList<>();
        algoritmos.bea(grafo, grafo.construirComparable("E"), recorrido::add);
 
        System.out.println("Recorrido BEA desde E: " + recorrido);
        // Esperado: [E, D, F, G, B, C, H, A]
    }
}
```

**Casos de prueba :**

| Test | Qué verifica |
|---|---|
| `beaRecorreTodosLosVerticesTest` | En un grafo conexo visita todos los vértices y ninguno se repite |
| `beaNoConexoRecorreTodasLasComponentesTest` | En un grafo no conexo (A–B y C–D) visita los 4 vértices |
| `beaGrafoVacioTest` | En un grafo sin vértices no visita nada |