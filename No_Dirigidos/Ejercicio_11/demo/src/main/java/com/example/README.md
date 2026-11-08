# Ejercicio 11 – Puntos de Articulación

Una empresa de telecomunicaciones ha establecido varios canales de intercambio de datos entre una cantidad de ciudades en África.

Los centros de cómputos de interconexión se encuentran en estas ciudades:

- Nairobi (Kenya)
- Cairo (Egipto)
- Monrovia (Liberia)
- Garoua (Camerún)
- Mekele (Etiopía)
- Praia (Cabo Verde)

El analista a cargo del desarrollo de los sistemas críticos ha entendido que una buena representación de la red puede desarrollarse utilizando la estructura de **Grafos No Dirigidos**.

Uno de los problemas más acuciantes para la empresa es poder determinar eficientemente cuáles nodos de la red, en caso de falla (por ejemplo, por falta en la alimentación eléctrica), determinan la aislación de partes de la red.

La siguiente tabla indica las conexiones existentes entre las ciudades:

### Conexiones

| Origen | Destino |
|---|---|
| Nairobi | Cairo |
| Nairobi | Monrovia |
| Nairobi | Garoua |
| Monrovia | Garoua |
| Monrovia | Mekele |
| Garoua | Mekele |
| Mekele | Praia |

### Parte 1

1. Dibuja el grafo representativo de esta red.
2. De acuerdo al método para hallar los **puntos de articulación** de un grafo, encuentra qué ciudades, en caso de faltar la energía eléctrica o los servicios de comunicaciones, podrían afectar la conectividad de la red de la empresa.

### Parte 2

1. Desarrolla en pseudocódigo un algoritmo detallado que, dado una estructura de Grafo No Dirigido, encuentre los puntos de articulación.
2. Describe el algoritmo, indicando precondiciones y lenguaje natural.
3. El algoritmo debe utilizar los métodos definidos por defecto.

### Parte 3

Implementar el algoritmo para hallar los puntos de articulación de un grafo no dirigido conexo.

La firma del método es la siguiente:

```java
// V tipo del nodo, E tipo de la arista
<V, E> List<V> puntosDeArticulacion(IGraph<V, E> grafo);

## Parte 2 - implementación

### Descripción del algoritmo

Para encontrar los puntos de articulación se utiliza una búsqueda en profundidad (BPF).

Durante el recorrido se asigna a cada vértice un número de descubrimiento (`numero_bp`), que indica el orden en que fue visitado, y se calcula un valor `bajo`, que representa el menor número de descubrimiento al que se puede llegar desde ese vértice siguiendo aristas del árbol BPF y, como máximo, una arista de retroceso.

Para calcular estos valores se utilizan tres mapas auxiliares:

- `numero_bp`: almacena el orden de descubrimiento de cada vértice.
- `bajo`: almacena el menor número de descubrimiento alcanzable.
- `padre`: almacena el padre de cada vértice dentro del árbol BPF.

Además, se utiliza un contador para asignar los números de descubrimiento y un conjunto para almacenar los puntos de articulación encontrados.

El algoritmo recorre todos los vértices del grafo, para contemplar también aquellos que pertenezcan a componentes conexas diferentes. Al visitar un vértice, se inicializan sus valores `numero_bp` y `bajo` con el valor del contador.

Luego se recorren sus adyacencias. Si un vecino todavía no fue visitado, se establece el vértice actual como su padre y se continúa recursivamente con la búsqueda. Al regresar de la llamada recursiva, se actualiza el valor `bajo` del vértice actual utilizando el menor valor entre su `bajo` actual y el `bajo` de su hijo.

Si se encuentra un vecino que ya fue visitado y que no es el padre del vértice actual, se considera una arista de retroceso y se actualiza `bajo` utilizando el número de descubrimiento de ese vecino.

Para determinar si un vértice es un punto de articulación se consideran dos casos:

- **Raíz del árbol BPF:** es un punto de articulación si tiene dos o más hijos.
- **Vértice que no es raíz:** es un punto de articulación si tiene al menos un hijo cuyo valor `bajo` es mayor o igual que su número de descubrimiento (`numero_bp`).

Esta última condición indica que desde el subárbol de ese hijo no existe una arista de retroceso que permita alcanzar un antecesor del vértice actual.

Finalmente, se retorna el conjunto con todos los puntos de articulación encontrados.

### Precondiciones
- El grafo debe estar correctamente inicializado.
- El grafo debe ser no dirigido.

### Postcondiciones
- Se retorna un conjunto (`Set`) que contiene todos los puntos de articulación del grafo.
- Cada vértice aparece una única vez en el conjunto.
- Si el grafo no contiene puntos de articulación, se retorna un conjunto vacío.
- El grafo original no se modifica.

### Pseudocódigo

```text
ALGORITMO puntosArticulacion(grafo)

    Crear conjunto puntos vacío
    Crear mapas numero_bp, bajo y padre
    contador = 0

    PARA CADA vertice EN grafo.vertices() HACER
        SI vertice no fue visitado ENTONCES
            padre[vertice] = null
            BPF(vertice)
        FIN SI
    FIN PARA

    RETORNAR puntos


PROCEDIMIENTO BPF(vertice)

    contador = contador + 1
    numero_bp[vertice] = contador

### parte 3:
la parte 3 esta implementada en la clase UndirectedDraphAlgorithm al final del todo y tiene un método adicional de bpf ya que dicho método solo se encontraba como algoritmo en los algoritmos de los grafos dirigidos.