# Ejercicio 4

A efectos de mejorar la atención al cliente, la aerolínea ha decidido desarrollar una aplicación que permita conocer **todos los vuelos posibles** para unir un origen con un destino.

---

## Parte 1

Dada la matriz que indica las conexiones existentes entre ciudades, dibuja el grafo correspondiente y su representación por **lista de adyacencias**.

Aplicando el algoritmo de **búsqueda en profundidad**, desarrolla el **bosque abarcador** resultante de recorrer este grafo, comenzando por la ciudad **«Asunción»**.

Indica claramente los diferentes **tipos de arcos** encontrados.

### Cuadro 3: Matriz de costos

| Origen / destino   | Asunción | Buenos Aires | Curitiba | Montevideo | Porto Alegre | Rio de Janeiro |
|--------------------|:--------:|:------------:|:--------:|:----------:|:------------:|:--------------:|
| **Asunción**       |    x     |     1600     |   800    |     -      |     700      |       -        |
| **Buenos Aires**   |   1600   |      x       |    -     |    200     |     1250     |       -        |
| **Curitiba**       |    -     |      -       |    x     |     -      |      -       |      1500      |
| **Montevideo**     |    -     |     200      |    -     |     x      |     1000     |       -        |
| **Porto Alegre**   |    -     |      -       |    -     |    1000    |      x       |       -        |
| **Rio de Janeiro** |    -     |      -       |   1500   |     -      |      -       |       x        |

---

## Parte 2

Dadas las interfaces para ser implementadas en Java, se desea poder utilizar esta estructura para realizar un **recorrido sistemático en profundidad** de un grafo así representado.

Se deberán implementar los métodos necesarios para realizar este recorrido, comenzando por el **primer vértice representado**.

Implementar el método `recorridoEnProfundidad` en la interfaz `IGraphAlgorithms`.

> **Pista:** Utilizar métodos auxiliares para pasar otras estructuras auxiliares que guarden datos. Por ejemplo: `nodos visitados`.

--- 
----
### Tipos de arcos

Recorrido DFS desde Asunción, visitando los adyacentes en el orden de las columnas de la matriz.
Orden de visita: Asunción → Buenos Aires → Montevideo → Porto Alegre → Curitiba → Rio de Janeiro.

| Tipo | Arcos | Cantidad |
|---|---|:---:|
| **Árbol** | Asunción→Buenos Aires, Buenos Aires→Montevideo, Montevideo→Porto Alegre, Asunción→Curitiba, Curitiba→Rio de Janeiro | 5 |
| **Retroceso** | Buenos Aires→Asunción, Montevideo→Buenos Aires, Porto Alegre→Montevideo, Rio de Janeiro→Curitiba | 4 |
| **Avance** | Buenos Aires→Porto Alegre, Asunción→Porto Alegre | 2 |
| **Cruzados** | — | 0 |

**Total:** 11 arcos.
