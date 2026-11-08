# Ejercicio 8 – Algoritmo de Prim

Una compañía de reforestación sembrará árboles en seis zonas en la misma área. Para esto debe desarrollar un sistema de caminos de tierra para tener acceso a cualquier zona desde cualquier otra.

La distancia (en kilómetros) entre cada par de zonas está dada en la siguiente tabla:

### Matriz de distancias

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

## Parte 1 – Representación del problema

### 1. Modelado del problema como un grafo

Para representar las zonas del parque y los caminos que las conectan, utilizamos un grafo no dirigido y ponderado.

- **Vértices:** representan las distintas zonas del parque.
- **Aristas:** representan los caminos que conectan dos zonas.
- **Pesos:** representan la distancia en kilómetros de cada camino.

El grafo es no dirigido porque se puede circular por los caminos en ambos sentidos. Además, es ponderado porque cada camino tiene una distancia asociada.

Para conectar todas las zonas utilizando la menor cantidad total de kilómetros posible, sin generar ciclos. Para esto utilizamos un árbol de expansión mínima (MST).

### 2. Matriz de adyacencia

La matriz de adyacencia representa las distancias entre las distintas zonas. Cuando no existe un camino entre dos zonas, se utiliza una X.

|      | Z1 | Z2 | Z3 | Z4 | Z5 | Z6 |
|------|----|----|----|----|----|----|
| Z1   | X  | 2  | 5  | 1  | 7  | 2  |
| Z2   | 2  | X  | 1  | 3  | 5  | 6  |
| Z3   | 5  | 1  | X  | 2  | 3  | 5  |
| Z4   | 1  | 3  | 2  | X  | 7  | 1  |
| Z5   | 7  | 5  | 3  | 7  | X  | 4  |
| Z6   | 2  | 6  | 5  | 1  | 4  | X  |

### 3. Representación visual del grafo

Imagen 1 (imagen de arriba).

### 4. Aplicación del algoritmo de Prim

Para encontrar el árbol de expansión mínima utilizamos el algoritmo de Prim.

Comenzamos desde la Zona 1 y buscamos, en cada paso, el camino de menor distancia que conecte una zona ya visitada con otra que todavía no fue visitada.

Los pasos son los siguientes:

1. Comenzamos en la Zona 1. El camino más corto disponible es el que conecta la Zona 1 con la Zona 4, con una distancia de 1 km.

2. Desde la Zona 4, el camino más corto hacia una zona no visitada es el que conecta con la Zona 6, también de 1 km.

3. Ahora tenemos las zonas 1, 4 y 6 conectadas. Entre los caminos disponibles de menor distancia hay un empate de 2 km. Elegimos conectar la Zona 1 con la Zona 2.

4. Desde la Zona 2 encontramos un camino de 1 km hacia la Zona 3, por lo que agregamos esa conexión.

5. Finalmente, el camino más corto para conectar la Zona 5 es el que viene desde la Zona 3, con una distancia de 3 km.

De esta manera, todas las zonas quedan conectadas.

### 5. Árbol de expansión mínima obtenido

El resultado final de aplicar Prim es la Imagen 2 (la de abajo).

El árbol utiliza 5 caminos para conectar las 6 zonas, sin generar ciclos.

Por lo tanto, la distancia mínima necesaria para conectar todas las zonas es de **8 km**.

---

## Parte 2 – Implementación del algoritmo

### 1. Pseudocódigo de Prim

Para implementar Prim utilizamos una matriz de adyacencia. También necesitamos tres estructuras auxiliares:

- `visitados`: indica qué vértices ya fueron agregados al árbol.
- `distancia`: guarda el menor costo conocido para conectar cada vértice al árbol.
- `padre`: guarda desde qué vértice se realiza esa conexión.

**Pseudocódigo:**

\`\`\`text
Prim(matriz, n, inicio)

    Crear arreglo visitados[n]
    Crear arreglo distancia[n]
    Crear arreglo padre[n]

    Para i desde 0 hasta n-1
        visitados[i] = falso
        distancia[i] = infinito
        padre[i] = -1
    Fin Para

    distancia[inicio] = 0

    Para i desde 0 hasta n-1

        minimo = infinito
        u = -1

        Para j desde 0 hasta n-1
            Si visitados[j] = falso Y distancia[j] < minimo
                minimo = distancia[j]
                u = j
            Fin Si
        Fin Para

        Si u = -1
            Error: el grafo no es conexo
        Fin Si

        visitados[u] = verdadero

        Para v desde 0 hasta n-1

            Si matriz[u][v] existe Y visitados[v] = falso
                Si matriz[u][v] < distancia[v]
                    distancia[v] = matriz[u][v]
                    padre[v] = u
                Fin Si
            Fin Si

        Fin Para

    Fin Para

    Para i desde 0 hasta n-1
        Si i != inicio
            Mostrar padre[i], i, distancia[i]
        Fin Si
    Fin Para

Fin Prim
\`\`\`

### 2. Complejidad temporal

La complejidad temporal de este algoritmo es O(V²), utilizando una matriz de adyacencia.

Esto se debe a que, en cada iteración, se busca el vértice no visitado con menor distancia y luego se recorren los posibles vecinos para actualizar sus costos.

Como ambas operaciones recorren los vértices, el costo total es cuadrático.

### 3. Escenario reducido

Ahora consideramos un escenario con solamente cuatro zonas.

La matriz de adyacencia es la siguiente:

|      | Z1 | Z2 | Z3 | Z4 |
|------|----|----|----|----|
| Z1   | X  | 2  | 5  | 1  |
| Z2   | 2  | X  | 1  | 3  |
| Z3   | 5  | 1  | X  | 2  |
| Z4   | 1  | 3  | 2  | X  |

Comenzando desde la Zona 1, Prim realiza los siguientes pasos:

1. Conecta la Zona 1 con la Zona 4, porque es el camino más corto (1 km).
2. Conecta la Zona 1 con la Zona 2 (2 km).
3. Desde la Zona 2 conecta con la Zona 3 (1 km).

La distancia total es:

1 + 2 + 1 = **4 km**.

### 4. Casos de prueba

Para comprobar que el algoritmo funciona correctamente, podemos probar diferentes situaciones:

| Caso | Resultado esperado |
|------|--------------------|
| Grafo original de 6 zonas | MST de 8 km |
| Grafo reducido de 4 zonas | MST de 4 km |
| Grafo con un solo vértice | Árbol sin aristas y costo 0 |
| Grafo no conexo | No se puede construir un árbol de expansión |
| Grafo con varios caminos del mismo peso | Debe encontrar algún MST válido |
| Grafo con varias soluciones mínimas | Puede devolver cualquiera de ellas |


