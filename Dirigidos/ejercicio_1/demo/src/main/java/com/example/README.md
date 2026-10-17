# PARTE 3

## Análisis de IGraph

La interfaz IGraph<V, D> tiene las operaciones básicas para trabajar con un grafo. Permite agregar, buscar y eliminar vértices y aristas, ver las conexiones entre los vértices y consultar la cantidad de vértices y aristas que tiene. También permite saber si el grafo es conexo o si tiene ciclos.


## Análisis de IDirectedIGraph

La interfaz IDirectedIGraph<V, D> extiende de IGraph y agrega operaciones para trabajar con grafos dirigidos.

Permite obtener los sucesores de un vértice, que son los vértices a los que llega, y los predecesores, que son aquellos desde los que recibe conexiones. También permite saber el grado de entrada y de salida de un vértice, es decir, cuántas aristas entran y salen de él.

## Análisis de IDirectedGraphAlgorithms

Esta interfaz tiene varios algoritmos para trabajar con grafos dirigidos. Algunos de ellos son Dijkstra, Floyd y Warshall. También incluye los recorridos en profundidad y amplitud, la clasificación topológica y los métodos para calcular el centro y la excentricidad del grafo. Además, permite obtener los distintos caminos entre dos vértices.

## Evaluación de la API de Colecciones de Java

Estuvimos viendo qué colecciones de Java nos podrían servir para implementar IDirectedIGraph, teniendo en cuenta cómo funciona cada una y cuánto cuestan sus operaciones.

-Set: sirve para guardar elementos sin que se repitan. Por ejemplo, podríamos usar un HashSet para guardar los sucesores de un vértice, ya que permite comprobar si un elemento existe en O(1) promedio. También está LinkedHashSet, que mantiene el orden en el que se agregan los elementos, y TreeSet, que los mantiene ordenados y tiene operaciones de O(log n).

-List: permite guardar elementos en una lista y recorrerlos. Con un array se puede acceder a un elemento por su índice en O(1), pero buscar uno en particular cuesta O(n). Mientras que usar una lista enlazada puede servir para agregar o eliminar elementos cuando ya se conoce la posición, aunque acceder a una posición o buscar un elemento también cuesta O(n).

-Map: permite relacionar una clave con un valor. En este caso, podríamos usar un HashMap para relacionar cada vértice con sus sucesores, ya que buscar por clave cuesta O(1) promedio. También está TreeMap, que mantiene las claves ordenadas y tiene operaciones de O(log n).

## Comparación y elección

En nuestra implementación usamos un Map con otro Map adentro para guardar los vértices y sus conexiones. En concreto, usamos HashMap, que permite buscar elementos por clave en O(1) promedio.

El Map principal guarda los vértices y, para cada uno, hay otro Map con los vértices a los que está conectado y las aristas correspondientes. De esta forma, podemos acceder a las conexiones de un vértice sin tener que recorrer todas las aristas del grafo.

También usamos otras colecciones en distintos métodos, como Set para guardar los vértices visitados y evitar repetirlos durante los recorridos, ArrayList para devolver las aristas de un vértice y LinkedList junto con Queue para hacer el recorrido en amplitud.

En cuanto a la memoria, la implementación necesita aproximadamente O(V + A) utilizando un hashmap, porque guarda los vértices y sus aristas.
