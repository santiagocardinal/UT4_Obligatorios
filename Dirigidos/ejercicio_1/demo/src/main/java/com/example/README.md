# Ejercicio 1

Una compañía de aviación nacional necesita representar los vuelos que opera en diferentes aeropuertos y calcular cuáles son los viajes de menor costo que permiten unir esos aeropuertos. Los aeropuertos en los que opera son: **Artigas, Canelones, Colonia, Durazno, Florida, Montevideo, Punta del Este y Rocha**.

Los vuelos operados son (origen, destino, distancia):

| Origen | Destino | Distancia |
|---|---|:---:|
| Artigas | Rocha | 400 |
| Canelones | Artigas | 500 |
| Canelones | Colonia | 200 |
| Canelones | Durazno | 170 |
| Canelones | Punta del Este | 90 |
| Colonia | Montevideo | 180 |
| Florida | Durazno | 60 |
| Montevideo | Artigas | 700 |
| Montevideo | Canelones | 30 |
| Montevideo | Punta del Este | 130 |
| Punta del Este | Rocha | 90 |
| Rocha | Montevideo | 270 |

El analista a quien se le ha encargado la tarea ha decidido que la mejor forma de solucionar el problema es mediante el uso de **grafos dirigidos**.

---

## Parte 1

1. Represente el escenario mediante:
   - **a)** Matriz de adyacencias (ponderada por minutos; usar 0/∞ donde corresponda).
   - **b)** Lista de adyacencias indicando, para cada vértice, sus aristas salientes con pesos.
2. Dibuje el grafo dirigido resultante, rotulando vértices y pesos de las aristas.

## Parte 2

Aplicando paso a paso el método de **Dijkstra**, calcular el vuelo de menor costo (distancia) entre el aeropuerto de Montevideo y cada uno de los otros aeropuertos. ¿Cuál es el trayecto recorrido? (salida – escalas – destino)

## Parte 3

1. Descargar el proyecto de la unidad temática e incluirlo en el proyecto del curso.
2. Analice las interfaces:
   - `IGraph`
   - `IDirectedIGraph`
   - `IDirectedGraphAlgorithms`
   - `IGraphAlgorithms`
3. Evaluar y considerar la API de colecciones de Java para implementar la interfaz `IDirectedIGraph`. Considerar el uso de distintas estructuras de datos y evaluar el consumo de memoria y el orden de tiempo de ejecución.
---
# Resolución – Parte 3

## Análisis de `IGraph`

La interfaz `IGraph<V, D>` tiene las operaciones básicas para trabajar con un grafo. Permite agregar, buscar y eliminar vértices y aristas, ver las conexiones entre los vértices y consultar la cantidad de vértices y aristas que tiene. También permite saber si el grafo es conexo o si tiene ciclos.

## Análisis de `IDirectedIGraph`

La interfaz `IDirectedIGraph<V, D>` extiende de `IGraph` y agrega operaciones para trabajar con grafos dirigidos.

Permite obtener los **sucesores** de un vértice, que son los vértices a los que llega, y los **predecesores**, que son aquellos desde los que recibe conexiones. También permite saber el **grado de entrada** y de **salida** de un vértice, es decir, cuántas aristas entran y salen de él.

## Análisis de `IDirectedGraphAlgorithms`

Esta interfaz tiene varios algoritmos para trabajar con grafos dirigidos. Algunos de ellos son **Dijkstra**, **Floyd** y **Warshall**. También incluye los recorridos en profundidad y amplitud, la clasificación topológica y los métodos para calcular el centro y la excentricidad del grafo. Además, permite obtener los distintos caminos entre dos vértices.

---

## Evaluación de la API de Colecciones de Java

Estuvimos viendo qué colecciones de Java nos podrían servir para implementar `IDirectedIGraph`, teniendo en cuenta cómo funciona cada una y cuánto cuestan sus operaciones.

- **`Set`**: sirve para guardar elementos sin que se repitan. Por ejemplo, podríamos usar un `HashSet` para guardar los sucesores de un vértice, ya que permite comprobar si un elemento existe en O(1) promedio. También está `LinkedHashSet`, que mantiene el orden en el que se agregan los elementos, y `TreeSet`, que los mantiene ordenados y tiene operaciones de O(log n).

- **`List`**: permite guardar elementos en una lista y recorrerlos. Con un array (`ArrayList`) se puede acceder a un elemento por su índice en O(1), pero buscar uno en particular cuesta O(n). Mientras que usar una lista enlazada (`LinkedList`) puede servir para agregar o eliminar elementos cuando ya se conoce la posición, aunque acceder a una posición o buscar un elemento también cuesta O(n).

- **`Map`**: permite relacionar una clave con un valor. En este caso, podríamos usar un `HashMap` para relacionar cada vértice con sus sucesores, ya que buscar por clave cuesta O(1) promedio. También está `TreeMap`, que mantiene las claves ordenadas y tiene operaciones de O(log n).

### Resumen de costos

| Colección | Buscar | Agregar | Eliminar | Mantiene orden |
|---|:---:|:---:|:---:|---|
| `HashSet` / `HashMap` | O(1) prom. | O(1) prom. | O(1) prom. | No |
| `LinkedHashSet` / `LinkedHashMap` | O(1) prom. | O(1) prom. | O(1) prom. | Sí, de inserción |
| `TreeSet` / `TreeMap` | O(log n) | O(log n) | O(log n) | Sí, ordenado |
| `ArrayList` | O(n) (O(1) por índice) | O(1) al final | O(n) | Sí, de inserción |
| `LinkedList` | O(n) | O(1) en los extremos | O(1) si se conoce la posición | Sí, de inserción |

---

## Comparación y elección

En nuestra implementación usamos un `Map` con otro `Map` adentro para guardar los vértices y sus conexiones. En concreto, usamos `HashMap`, que permite buscar elementos por clave en O(1) promedio.

```java
Map<V, Map<V, Edge<V, D>>> adjacencyList;
```

El `Map` principal guarda los vértices y, para cada uno, hay otro `Map` con los vértices a los que está conectado y las aristas correspondientes. De esta forma, podemos acceder a las conexiones de un vértice sin tener que recorrer todas las aristas del grafo.

También usamos otras colecciones en distintos métodos:

| Colección | Para qué la usamos |
|---|---|
| `Set` | Guardar los vértices visitados y evitar repetirlos durante los recorridos |
| `ArrayList` | Devolver las aristas de un vértice |
| `LinkedList` + `Queue` | Hacer el recorrido en amplitud |

En cuanto a la memoria, la implementación necesita aproximadamente **O(V + A)** utilizando un `HashMap`, porque guarda los vértices y sus aristas.