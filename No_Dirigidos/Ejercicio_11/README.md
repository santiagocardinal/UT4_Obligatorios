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