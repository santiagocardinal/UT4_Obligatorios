# Ejercicio 3

La aerolínea ha expandido sus operaciones y ahora tiene los siguientes vuelos, de acuerdo a la Tabla 2.

### Cuadro 2: Matriz de costos

| Origen / destino | Asunción | Buenos Aires | Curitiba | Montevideo | Porto Alegre | Rio de Janeiro | San Pablo | Santos |
|---|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
| **Asunción** | x | 1600 | 800 | - | 700 | - | - | - |
| **Buenos Aires** | 1600 | x | - | 200 | 1250 | - | - | - |
| **Curitiba** | - | - | x | - | - | 1500 | - | - |
| **Montevideo** | - | 200 | - | x | 1000 | - | 980 | 1200 |
| **Porto Alegre** | - | - | - | - | x | - | - | - |
| **Rio de Janeiro** | - | - | 1500 | - | - | x | 1800 | 1900 |
| **San Pablo** | 1200 | - | 600 | 2000 | 980 | 1800 | x | 220 |
| **Santos** | - | - | - | - | 1200 | - | 220 | x |

---

## Parte 1

Se provee un conjunto de clases e interfaces básicas para implementar un Grafo Dirigido. Dicha interface provee los métodos necesarios para agregar vértices y aristas.

1. En equipo se deberá:
   - Analizar las estructuras de datos diseñadas. ¿Cuáles pueden ser las razones para la utilización de las diferentes estructuras de la API de colecciones para representar el grafo, el conjunto de vértices, las listas de adyacencias?
   - Una vez decidida la estructura de datos para representar el Grafo Dirigido, implementar agregar vértice y arista.
   - Dadas estas estructuras de datos, construir un programa Java que permita:
     - crear un grafo para representar las conexiones aéreas de la aerolínea «VUELE SEGURO»
     - cargar este grafo utilizando los archivos provistos «aeropuertos.txt» y «conexiones.txt»

2. **Floyd**: Implementar el algoritmo bajo la interfaz `IDirectedGraphAlgorithms` y que permita calcular los caminos mínimos entre todos los pares de aeropuertos posibles y:
   - contestar interactivamente preguntas del tipo *¿cuál es la menor distancia posible para ir desde una ciudad x a otra y?*
   - contestar interactivamente preguntas del tipo *«indique el itinerario de menor costo para ir de la ciudad x a la ciudad y»*
   - contestar interactivamente preguntas del tipo *«si la aerolínea decide instalar un nuevo centro de mantenimiento y logística para sus aviones, ¿cuál sería el aeropuerto más apropiado para ello?»*

## Parte 2

Implemente el algoritmo **Warshall** bajo la interfaz `IDirectedGraphAlgorithms`.
---

## Resolución – Parte 1

### Análisis de las estructuras de datos usadas

Estuvimos viendo qué estructuras de datos de Java nos servían para representar el grafo, teniendo en cuenta cómo funciona cada una y el costo de sus operaciones.

### Comparación y elección

En nuestra implementación del grafo dirigido usamos un `Map` con otro `Map` adentro para guardar los vértices y sus conexiones. En concreto, usamos `HashMap`, que permite buscar elementos por clave en **O(1) promedio**.

```java
Map<V, Map<V, Edge<V, D>>> adjacencyList;
```

El `Map` principal guarda los vértices y, para cada uno, hay otro `Map` con los vértices a los que está conectado y las aristas correspondientes. De esta forma, podemos acceder a las conexiones de un vértice sin tener que recorrer todas las aristas del grafo.

Esta estructura también nos permite representar las **listas de adyacencia**, guardando solamente las conexiones que existen. Además, como las conexiones se guardan desde cada vértice hacia sus sucesores, podemos representar que existe una conexión en un sentido sin que necesariamente exista en el sentido contrario.

Para el **conjunto de vértices** aprovechamos las claves del `Map` principal, ya que no se pueden repetir. Así, no necesitamos guardar los vértices en otra estructura por separado.

### Resumen

| Qué representa | Estructura usada | Por qué |
|---|---|---|
| **El grafo** | `Map<V, Map<V, Edge<V, D>>>` | Acceso directo a cada vértice y a sus conexiones |
| **El conjunto de vértices** | Las claves del `Map` principal | No se repiten, no hace falta otra estructura |
| **Las listas de adyacencias** | El `Map` interno de cada vértice | Solo guarda las conexiones que existen y respeta el sentido de la arista |

| Operación | Costo |
|---|:---:|
| Buscar un vértice / sus adyacentes | O(1) promedio |
| Agregar un vértice | O(1) promedio |
| Agregar una arista | O(1) promedio |
| Memoria | O(V + A) |

### Conclusión

Elegimos esta representación porque nos permite acceder a los vértices y sus conexiones de forma eficiente. Además, ocupa aproximadamente **O(V + A)** de memoria, donde **V** es la cantidad de vértices y **A** la cantidad de aristas, ya que solo guardamos los vértices y las conexiones que existen.