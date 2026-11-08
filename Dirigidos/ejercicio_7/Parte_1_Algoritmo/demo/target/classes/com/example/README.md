# Ejercicio 7

## Parte 1

Dada la siguiente matriz de adyacencias:

|   | A | B | C | D | E |
|:---:|:---:|:---:|:---:|:---:|:---:|
| **A** | - | 1 | 2 | 7 | - |
| **B** | 7 | - | 1 | 2 | - |
| **C** | - | - | - | 3 | - |
| **D** | 6 | - | 4 | - | 4 |
| **E** | - | 2 | - | 8 | - |

1. Dibuja el grafo dirigido.
2. Halla los caminos de menor costo, aplicando el algoritmo de **Dijkstra** paso a paso, desde el vértice **B** hasta todos los otros vértices.
3. Indica cuáles son los caminos de menor costo utilizando un **vector de predecesores**.
4. Escribe un algoritmo para, dado el vector de predecesores, **imprimir el camino** entre el vértice origen y un vértice destino indicado como parámetro.

## Parte 2

Dado el grafo de la figura, y utilizando el algoritmo de **FLOYD**:

- Dibuja la representación mediante lista de adyacencias.
- Encuentra los caminos de menor costo entre los vértices del grafo de la figura.
- Utilizando una matriz de predecesores, muestra cómo recuperar el camino entre un par de vértices dados.
- ¿Cuál es el camino de menor costo entre los vértices A y E? (utiliza el algoritmo, ejecutando paso a paso, para resolver).
- ¿Cuáles son las excentricidades de los vértices del grafo?
- ¿Qué vértice

--- 

## Resolución – Parte 1

### 4. Algoritmo para imprimir el camino a partir del vector de predecesores

> Este método ya lo agregamos a la clase `DirectedGraphAlgorithms`, pero por las dudas de que no se encuentre fácilmente, lo dejamos también acá.

El método recibe el **vector de predecesores** (un `Map` donde cada vértice apunta al vértice anterior en su camino mínimo), el **origen** y el **destino**:

1. Arranca desde el destino y va "para atrás", pidiendo el predecesor de cada vértice.
2. Cada vértice que visita lo agrega **al principio** de la lista, así el camino queda en orden.
3. Cuando llega al origen, lo agrega y muestra el camino completo.
4. Si en algún momento un vértice no tiene predecesor, quiere decir que no hay camino, y lo avisa.

```java
public <V> void imprimirCamino(Map<V, V> predecessors, V origen, V destino) {
    LinkedList<V> camino = new LinkedList<>();
    V actual = destino;

    while (!actual.equals(origen)) {
        if (actual == null || !predecessors.containsKey(actual)) {
            System.out.println("No existe camino");
            return;
        }

        camino.addFirst(actual);
        actual = predecessors.get(actual);
    }

    camino.addFirst(origen);

    System.out.println(camino);
}
```

**Ejemplo:** si los predecesores desde B son `C ← B` y `D ← B`, al llamar a `imprimirCamino(predecesores, B, D)` se recorre `D → B` y se imprime `[B, D]`.