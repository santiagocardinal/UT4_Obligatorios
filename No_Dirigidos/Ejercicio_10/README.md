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


### 1. Representación del problema

Para representar la red de productores utilizamos un **grafo no dirigido**.

- **Vértices:** representan a los productores que debe visitar la empresa.
- **Aristas:** representan las conexiones existentes entre los productores.

Como el grafo es no dirigido, las conexiones se pueden recorrer en ambos sentidos.

El objetivo es visitar todos los vértices una sola vez, utilizando el algoritmo de búsqueda en amplitud (BFS).

### 2. Grafo del problema

Los vértices del grafo son:

A, B, C, D, E, F, G y H.

Las conexiones existentes son:

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

Para evitar visitar un mismo vértice más de una vez, utilizamos un conjunto de visitados.

**Ejecución paso a paso:**

| Paso | Vértice procesado | Nuevos vértices agregados a la cola | Cola después del paso |
|---:|---|---|---|
| 1 | E | D, F, G | D, F, G |
| 2 | D | B, C | F, G, B, C |
| 3 | F | H | G, B, C, H |
| 4 | G | Ninguno | B, C, H |
| 5 | B | A | C, H, A |
| 6 | C | Ninguno | H, A |
| 7 | H | Ninguno | A |
| 8 | A | Ninguno | Vacía |

El recorrido obtenido es:

**E → D → F → G → B → C → H → A**

Todos los vértices fueron visitados una sola vez.

### 4. Árbol abarcador resultante

El árbol abarcador se construye utilizando únicamente las aristas que permitieron descubrir nuevos vértices durante el recorrido.

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

El árbol abarcador tiene 8 vértices y 7 aristas.

No se incluyen las demás conexiones porque sus vértices ya habían sido descubiertos y agregarlas no sería necesario para construir el árbol.

---

## Parte 2 – Pseudocódigo de búsqueda en amplitud

### 1. Estructuras utilizadas

Para implementar BFS necesitamos las siguientes estructuras:

- **Cola:** permite procesar los vértices en el orden en que fueron descubiertos (FIFO).
- **Conjunto de visitados:** permite saber qué vértices ya fueron recorridos y evitar repetirlos.
- **Lista de recorrido:** almacena el orden en que se visitaron los vértices.
- **Lista de aristas:** almacena las conexiones que forman el árbol abarcador.

### 2. Precondiciones y postcondiciones

**Precondiciones:**

- El grafo debe existir y estar correctamente inicializado.
- El vértice de inicio debe pertenecer al grafo.
- El grafo debe permitir obtener los vecinos de cada vértice.
- Si se desea visitar todos los vértices comenzando desde uno solo, el grafo debe ser conexo.

**Postcondiciones:**

- Todos los vértices alcanzables desde el vértice inicial fueron visitados una sola vez.
- El recorrido devuelve los vértices en orden de amplitud.
- Las aristas seleccionadas forman un árbol abarcador del componente conectado al vértice inicial.
- El grafo original no se modifica.

### 3. Pseudocódigo

```text
BFS(grafo, inicio)

    Crear cola
    Crear conjunto visitados
    Crear lista recorrido
    Crear lista arbol

    Agregar inicio a visitados
    Encolar inicio

    Mientras cola no esté vacía

        actual = Desencolar()

        Agregar actual a recorrido

        Para cada vecino de actual

            Si vecino no pertenece a visitados

                Agregar vecino a visitados
                Encolar vecino
                Agregar (actual, vecino) a arbol

            Fin Si

        Fin Para

    Fin Mientras

    Devolver recorrido y arbol

Fin BFS
```
