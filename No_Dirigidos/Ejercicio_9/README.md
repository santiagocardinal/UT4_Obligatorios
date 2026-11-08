# Ejercicio 9 – Algoritmo de Kruskal

Una compañía de reforestación sembrará árboles en seis zonas en la misma área. Para esto debe desarrollar un sistema de caminos de tierra para tener acceso a cualquier zona desde cualquier otra.

La distancia (en kilómetros) entre cada par de zonas está dada en la siguiente tabla:

### Matriz de distancias

| Zona | Zona 1 | Zona 2 | Zona 3 | Zona 4 | Zona 5 | Zona 6 |
|---|---|---|---|---|---|---|
| Zona 1 | x | 3 | 5 | 1 | 7 | 2 |
| Zona 2 | 3 | x | 1 | 3 | 5 | 6 |
| Zona 3 | 5 | 1 | x | 2 | 3 | 5 |
| Zona 4 | 1 | 3 | 2 | x | 7 | 1 |
| Zona 5 | 7 | 5 | 3 | 7 | x | 4 |
| Zona 6 | 2 | 6 | 5 | 1 | 4 | x |

Se desea determinar la menor y más barata (cuanto más corto, menos costoso) cantidad de caminos que permita conectar todas las zonas entre sí.

### Parte 1

1. Representa gráficamente la red de zonas y distancias.
2. Determina la mejor opción para la construcción de los caminos de acuerdo con el escenario, utilizando el **algoritmo de Kruskal**, mostrando la ejecución paso a paso.

---


### 1. Representación del problema como un grafo

Para representar las seis zonas y los caminos que las conectan, utilizamos un **grafo no dirigido y ponderado**.

- **Vértices:** representan las seis zonas del terreno.
- **Aristas:** representan los posibles caminos entre dos zonas.
- **Pesos:** representan la distancia en kilómetros de cada camino.

El grafo es no dirigido porque se puede circular por los caminos en ambos sentidos, y es ponderado porque cada camino tiene una distancia asociada.

Para conectar todas las zonas utilizando la menor cantidad total de kilómetros posible, sin generar ciclos. Para resolverlo utilizamos el algoritmo de Kruskal.

### 2. Matriz de distancias

La matriz de distancias representa los kilómetros que hay entre cada par de zonas.

| Zona | Zona 1 | Zona 2 | Zona 3 | Zona 4 | Zona 5 | Zona 6 |
|---|---:|---:|---:|---:|---:|---:|
| Zona 1 | X | 3 | 5 | 1 | 7 | 2 |
| Zona 2 | 3 | X | 1 | 3 | 5 | 6 |
| Zona 3 | 5 | 1 | X | 2 | 3 | 5 |
| Zona 4 | 1 | 3 | 2 | X | 7 | 1 |
| Zona 5 | 7 | 5 | 3 | 7 | X | 4 |
| Zona 6 | 2 | 6 | 5 | 1 | 4 | X |

### 3. Aplicación del algoritmo de Kruskal

El algoritmo de Kruskal busca construir un árbol de expansión mínima seleccionando las aristas de menor peso.

A diferencia de Prim, Kruskal no comienza desde una zona determinada. Primero ordena todos los caminos de menor a mayor distancia y luego los va agregando, siempre que no formen un ciclo.

**Primero ordenamos todos los caminos según su distancia:**

| Orden | Camino | Distancia |
|---:|---|---:|
| 1 | Zona 1 – Zona 4 | 1 km |
| 2 | Zona 2 – Zona 3 | 1 km |
| 3 | Zona 4 – Zona 6 | 1 km |
| 4 | Zona 1 – Zona 6 | 2 km |
| 5 | Zona 3 – Zona 4 | 2 km |
| 6 | Zona 1 – Zona 2 | 3 km |
| 7 | Zona 2 – Zona 4 | 3 km |
| 8 | Zona 3 – Zona 5 | 3 km |
| 9 | Zona 5 – Zona 6 | 4 km |
| 10 | Zona 1 – Zona 3 | 5 km |
| 11 | Zona 2 – Zona 5 | 5 km |
| 12 | Zona 3 – Zona 6 | 5 km |
| 13 | Zona 2 – Zona 6 | 6 km |
| 14 | Zona 1 – Zona 5 | 7 km |
| 15 | Zona 4 – Zona 5 | 7 km |

**Ejecución paso a paso:**

1. Seleccionamos Zona 1 – Zona 4, con una distancia de 1 km. Se agrega porque todavía no existe ningún camino.

2. Seleccionamos Zona 2 – Zona 3, con una distancia de 1 km. Se agrega porque conecta dos zonas que todavía no estaban conectadas.

3. Seleccionamos Zona 4 – Zona 6, con una distancia de 1 km. Se agrega porque conecta una nueva zona al árbol.

4. Evaluamos Zona 1 – Zona 6, con una distancia de 2 km. No se agrega porque las dos zonas ya están conectadas mediante Zona 1 – Zona 4 – Zona 6. Si la agregáramos, formaríamos un ciclo.

5. Evaluamos Zona 3 – Zona 4, con una distancia de 2 km. Se agrega porque conecta los dos grupos de zonas que teníamos separados.

6. Evaluamos Zona 1 – Zona 2, con una distancia de 3 km. No se agrega porque ya existe un camino entre ambas zonas.

7. Evaluamos Zona 2 – Zona 4, con una distancia de 3 km. Tampoco se agrega porque formaría un ciclo.

8. Seleccionamos Zona 3 – Zona 5, con una distancia de 3 km. Se agrega porque permite conectar la Zona 5, que todavía no estaba conectada con las demás.


### 4. Árbol de expansión mínima obtenido

Las conexiones seleccionadas por Kruskal son:

| Camino | Distancia |
|---|---:|
| Zona 1 – Zona 4 | 1 km |
| Zona 2 – Zona 3 | 1 km |
| Zona 4 – Zona 6 | 1 km |
| Zona 3 – Zona 4 | 2 km |
| Zona 3 – Zona 5 | 3 km |
| **Total** | **8 km** |

La distancia total se calcula sumando los pesos de los caminos seleccionados:

**1 + 1 + 1 + 2 + 3 = 8 km**

La distancia mínima necesaria para conectar todas las zonas es de **8 kilómetros**, utilizando 5 caminos.

