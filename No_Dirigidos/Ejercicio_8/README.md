# Ejercicio 8 – Algoritmo de Prim

Una compañía de reforestación sembrará árboles en seis zonas en la misma área. Para esto debe desarrollar un sistema de caminos de tierra para tener acceso a cualquier zona desde cualquier otra. La distancia (en kilómetros) entre cada par de zonas está dada en la siguiente tabla:

| Zona | Zona 1 | Zona 2 | Zona 3 | Zona 4 | Zona 5 | Zona 6 |
|---|---|---|---|---|---|---|
| Zona 1 | x | 2 | 5 | 1 | 7 | 2 |
| Zona 2 | 2 | x | 1 | 3 | 5 | 6 |
| Zona 3 | 5 | 1 | x | 2 | 3 | 5 |
| Zona 4 | 1 | 3 | 2 | x | 7 | 1 |
| Zona 5 | 7 | 5 | 3 | 7 | x | 4 |
| Zona 6 | 2 | 6 | 5 | 1 | 4 | x |

Se desea determinar la menor y más barata (cuanto más corto, menos costoso) cantidad de caminos que permita conectar todas las zonas entre sí.

### Parte 1

1. Describe en lenguaje natural qué estructuras, conceptos y algoritmos de la Unidad Temática pueden aplicarse para resolver este problema práctico.
2. Representa la estructura elegida mediante **Lista de Adyacencias**.
3. Representa gráficamente la red de zonas y distancias.
4. Determina la mejor opción para la construcción de los caminos de acuerdo con el escenario, utilizando el **algoritmo de Prim**, mostrando cada paso realizado.

### Parte 2

1. Partiendo de la base del pseudocódigo abstracto del algoritmo de Prim visto en clase, desarrollar el algoritmo en pseudocódigo detallado (¡listo para ser implementado en JAVA!).

   Puedes utilizar como estructuras auxiliares para el desarrollo del algoritmo solamente las vistas hasta el momento en el curso (listas, árboles, grafos, colecciones de JAVA, etc.).

   Tener en cuenta los criterios estándar para la escritura de pseudocódigo publicados.

2. Prueba paso a paso la correcta ejecución del algoritmo, utilizando un escenario reducido (por ejemplo, solo 4 zonas).
3. Desarrolla en pseudocódigo y lenguaje natural la especificación de los casos de prueba que estimes necesarios para una segura implementación del algoritmo.
4. Preparar **PÓSTER** con algoritmo y casos de prueba.

> **Nota:** Este algoritmo y los casos de prueba, en pseudocódigo, serán necesarios para la realización del Trabajo de Aplicación 2 en la próxima clase.

---

## Parte 1

### 1. Estructuras, conceptos y algoritmos que se pueden aplicar

Para representar las zonas y los caminos usamos un **grafo no dirigido y ponderado**:

- **Vértices:** las 6 zonas.
- **Aristas:** los caminos posibles entre dos zonas.
- **Pesos:** la distancia en kilómetros de cada camino (que es lo que determina el costo).

Es no dirigido porque un camino de tierra se puede recorrer en los dos sentidos (por eso la matriz es simétrica), y es ponderado porque cada camino tiene una distancia. 


Los algoritmos vistos en la unidad para encontrar el arbol abarcador minimo son:

- **Prim:** arranca en un vértice cualquiera y va haciendo crecer un solo árbol. En cada paso agrega la arista más barata que une un vértice que ya está en el árbol con uno que todavía no está.
- **Kruskal:** ordena todas las aristas de menor a mayor y las va agregando siempre que no formen un ciclo.

### 2. Representación mediante Lista de Adyacencias

Como el grafo es no dirigido, cada arista aparece **dos veces** (una en la lista de cada extremo). Formato: `(destino, costo)`.

```
Zona 1 → (Zona 2, 2) → (Zona 3, 5) → (Zona 4, 1) → (Zona 5, 7) → (Zona 6, 2)
Zona 2 → (Zona 1, 2) → (Zona 3, 1) → (Zona 4, 3) → (Zona 5, 5) → (Zona 6, 6)
Zona 3 → (Zona 1, 5) → (Zona 2, 1) → (Zona 4, 2) → (Zona 5, 3) → (Zona 6, 5)
Zona 4 → (Zona 1, 1) → (Zona 2, 3) → (Zona 3, 2) → (Zona 5, 7) → (Zona 6, 1)
Zona 5 → (Zona 1, 7) → (Zona 2, 5) → (Zona 3, 3) → (Zona 4, 7) → (Zona 6, 4)
Zona 6 → (Zona 1, 2) → (Zona 2, 6) → (Zona 3, 5) → (Zona 4, 1) → (Zona 5, 4)
```

### 3. Representación gráfica de la red

Imagen 1 (imagen de arriba).


### 4. Aplicación del algoritmo de Prim

Comenzamos desde la Zona 1. En cada paso miramos **todas** las zonas que ya están en el árbol (U) y elegimos el camino más corto que va desde alguna de ellas hacia una zona que todavía no está. Para cada zona fuera del árbol anotamos la menor distancia con la que se puede conectar y desde qué zona (`✔` = ya está en el árbol).

**Paso 0 – Inicio.** U = {1}

| Zona | 2 | 3 | 4 | 5 | 6 |
|---|---|---|---|---|---|
| Distancia mínima | 2 | 5 | **1** | 7 | 2 |
| Desde | 1 | 1 | 1 | 1 | 1 |

El menor es 1 → agregamos **Zona 1 – Zona 4 (1 km)**.

**Paso 1.** U = {1, 4}. Costo acumulado: 1 km.

Actualizamos con los caminos de la Zona 4: hacia la 3 cuesta 2 (mejor que 5) y hacia la 6 cuesta 1 (mejor que 2).

| Zona | 2 | 3 | 4 | 5 | 6 |
|---|---|---|---|---|---|
| Distancia mínima | 2 | 2 | ✔ | 7 | **1** |
| Desde | 1 | 4 | | 1 | 4 |

El menor es 1 → agregamos **Zona 4 – Zona 6 (1 km)**.

**Paso 2.** U = {1, 4, 6}. Costo acumulado: 2 km.

Con los caminos de la Zona 6, la Zona 5 pasa a costar 4 (mejor que 7).

| Zona | 2 | 3 | 4 | 5 | 6 |
|---|---|---|---|---|---|
| Distancia mínima | **2** | 2 | ✔ | 4 | ✔ |
| Desde | 1 | 4 | | 6 | |

Hay un empate de 2 km entre Zona 1 – Zona 2 y Zona 4 – Zona 3. Elegimos **Zona 1 – Zona 2 (2 km)**, porque es la primera que encontramos.

**Paso 3.** U = {1, 2, 4, 6}. Costo acumulado: 4 km.

Con los caminos de la Zona 2, la Zona 3 pasa a costar 1 (mejor que 2).

| Zona | 2 | 3 | 4 | 5 | 6 |
|---|---|---|---|---|---|
| Distancia mínima | ✔ | **1** | ✔ | 4 | ✔ |
| Desde | | 2 | | 6 | |

El menor es 1 → agregamos **Zona 2 – Zona 3 (1 km)**.

**Paso 4.** U = {1, 2, 3, 4, 6}. Costo acumulado: 5 km.

Con los caminos de la Zona 3, la Zona 5 pasa a costar 3 (mejor que 4).

| Zona | 2 | 3 | 4 | 5 | 6 |
|---|---|---|---|---|---|
| Distancia mínima | ✔ | ✔ | ✔ | **3** | ✔ |
| Desde | | | | 3 | |

Agregamos **Zona 3 – Zona 5 (3 km)**.

**Paso 5.** U = {1, 2, 3, 4, 5, 6}. Ya están todas las zonas, así que termina el algoritmo.


### Árbol abarcador de costo mínimo obtenido

El resultado final de aplicar Prim es la Imagen 2 (la de abajo).

El árbol usa 5 caminos para conectar las 6 zonas, sin generar ciclos. La mejor opción es construir los caminos **1–4, 4–6, 1–2, 2–3 y 3–5**, con un total de **8 km**.
---


## Parte 2

### 1. Pseudocódigo detallado de Prim

**Descripción en lenguaje natural:**

Primero separamos los vértices en dos grupos: los que ya están conectados al árbol (U) y los que todavía faltan (resto). Al principio, U tiene solamente el vértice de inicio. En cada paso buscamos la conexión más corta entre los dos grupos y la agregamos al árbol. Después pasamos el vértice nuevo al grupo U. Repetimos esto hasta que todos los vértices estén conectados. Si no encontramos una conexión entre los dos grupos, significa que el grafo no es conexo y no se pueden conectar todos los vértices.


**Pseudocódigo:**

```text
prim(grafo, origen): IUndirectedGraph

Precondiciones:
    - grafo no es nulo
    - grafo es no dirigido y los pesos de las aristas son mayores o iguales a 0
Postcondiciones:
    - Devuelve un grafo nuevo que tiene todos los vértices de grafo.
    - Si grafo es conexo y origen existe, el resultado tiene |V| - 1 aristas,
      no tiene ciclos y la suma de sus pesos es mínima.
    - Si grafo no es conexo, el resultado solo tiene las aristas del árbol mínimo
      de la componente donde está origen (los demás vértices quedan sin aristas).
    - Si origen no existe en grafo, el resultado no tiene aristas.
    - El grafo original no se modifica.

COMIENZO

    result <- nuevo UndirectedGraph()
    result.agregarVertices(grafo.vertices())

    inicio <- grafo.buscarVertice(origen)
    SI inicio = nulo ENTONCES
        DEVOLVER result                          // el origen no existe
    FIN SI

    U <- nuevo HashSet()
    resto <- nuevo HashSet(grafo.vertices())
    U.add(inicio)
    resto.remove(inicio)

    MIENTRAS NO resto.isEmpty() HACER
        aristaMin <- searchMinEdge(grafo, U, resto)
        SI aristaMin = nulo ENTONCES
            SALIR DEL MIENTRAS                   // no es conexo, no se puede seguir
        FIN SI
        result.agregarArista(aristaMin.source(), aristaMin.target(), aristaMin.dato())
        U.add(aristaMin.target())
        resto.remove(aristaMin.target())
    FIN MIENTRAS

    DEVOLVER result
FIN
```

```text
searchMinEdge(grafo, U, resto): Edge

Precondiciones:
    - grafo.adyacencias(u) devuelve aristas cuyo source() es u
Postcondiciones:
    - Devuelve la arista de menor peso con source() en U y target() en resto.
    - Si no existe ninguna, devuelve nulo.
    - En caso de empate devuelve la primera que encuentra.

COMIENZO
    aristaMin <- nulo
    pesoMin <- INFINITO

    PARA CADA u EN U HACER
        PARA CADA arista EN grafo.adyacencias(u) HACER
            SI resto.contains(arista.target()) ENTONCES
                SI arista.dato().getWeight() < pesoMin ENTONCES
                    pesoMin <- arista.dato().getWeight()
                    aristaMin <- arista
                FIN SI
            FIN SI
        FIN PARA CADA
    FIN PARA CADA

    DEVOLVER aristaMin
FIN
```

**Orden del tiempo de ejecución:**

En total queda **O(V · A)**.

### 2. Prueba paso a paso con un escenario reducido (4 zonas)

Tomamos solamente las zonas 1 a 4:

|      | Z1 | Z2 | Z3 | Z4 |
|------|----|----|----|----|
| Z1   | x  | 2  | 5  | 1  |
| Z2   | 2  | x  | 1  | 3  |
| Z3   | 5  | 1  | x  | 2  |
| Z4   | 1  | 3  | 2  | x  |

Ejecutamos `prim(grafo, Z1)`. Para hacerlo a mano suponemos que `U` se recorre en el orden en que se fueron agregando los vértices.

**Inicio:** `U = {Z1}`, `resto = {Z2, Z3, Z4}`, `result` tiene los 4 vértices y ninguna arista.

**Vuelta 1:** `searchMinEdge` revisa las adyacencias de Z1 que van a `resto`:

| Arista | Peso | ¿Mejora pesoMin? | aristaMin |
|---|---|---|---|
| Z1 → Z2 | 2 | sí (2 < ∞) | Z1 → Z2 |
| Z1 → Z3 | 5 | no | Z1 → Z2 |
| Z1 → Z4 | 1 | sí (1 < 2) | **Z1 → Z4** |

Se agrega Z1 – Z4 (1). `U = {Z1, Z4}`, `resto = {Z2, Z3}`. Costo acumulado: 1.

**Vuelta 2:** se revisan las adyacencias de Z1 y de Z4 que van a `resto`:

| Arista | Peso | ¿Mejora pesoMin? | aristaMin |
|---|---|---|---|
| Z1 → Z2 | 2 | sí (2 < ∞) | **Z1 → Z2** |
| Z1 → Z3 | 5 | no | Z1 → Z2 |
| Z4 → Z2 | 3 | no | Z1 → Z2 |
| Z4 → Z3 | 2 | no (2 no es < 2) | Z1 → Z2 |

Se agrega Z1 – Z2 (2). `U = {Z1, Z4, Z2}`, `resto = {Z3}`. Costo acumulado: 3.

Acá hubo un empate entre Z1 → Z2 y Z4 → Z3. Como la comparación es `<` estricta, queda la primera que se encontró.

**Vuelta 3:** solo queda Z3 en `resto`:

| Arista | Peso | ¿Mejora pesoMin? | aristaMin |
|---|---|---|---|
| Z1 → Z3 | 5 | sí (5 < ∞) | Z1 → Z3 |
| Z4 → Z3 | 2 | sí (2 < 5) | Z4 → Z3 |
| Z2 → Z3 | 1 | sí (1 < 2) | **Z2 → Z3** |

Se agrega Z2 – Z3 (1). `U = {Z1, Z4, Z2, Z3}`, `resto = {}`. Costo acumulado: 4.

**Fin:** `resto` está vacío, así que sale del `MIENTRAS`.

**Resultado:** árbol con Z1 – Z4, Z1 – Z2 y Z2 – Z3, es decir 3 aristas (4 − 1), con una distancia total de **4 km**. Verificamos a mano que no hay otra combinación más barata (por ejemplo Z1–Z4, Z4–Z3, Z3–Z2 también da 4, y Z1–Z2, Z2–Z3, Z2–Z4 da 6), así que el algoritmo funciona correctamente.

### 3. Casos de prueba

| # | Caso | Entrada | Resultado esperado |
|---|---|---|---|
| 1 | Origen que no existe | Grafo A–B (1), origen "Z" | Grafo con A y B, sin aristas |
| 2 | Un solo vértice | {A}, origen A | Grafo con A, sin aristas, costo 0 |
| 3 | Dos vértices | A–B (5), origen A | Árbol {A–B}, costo 5 |
| 4 | Grafo que ya es un árbol | A–B (1), B–C (2), C–D (3) | Las mismas 3 aristas, costo 6 |
| 5 | Grafo no conexo | A–B (1), C–D (1), origen A | Solo la arista A–B; C y D quedan sin aristas (menos de \|V\| − 1 aristas) |
| 6 | Grafo con un ciclo | A–B (1), B–C (2), A–C (3) | {A–B, B–C}, costo 3 (descarta la arista de 3) |
| 7 | Aristas con el mismo peso | 4 vértices, todas las aristas de costo 1 | Cualquier árbol válido: 3 aristas, costo 3 |
| 8 | Aristas de costo 0 | A–B (0), B–C (4), A–C (2) | {A–B, A–C}, costo 2 |

