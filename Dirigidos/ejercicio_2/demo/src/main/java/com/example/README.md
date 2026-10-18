# Ejercicio 2

La aerolínea sudamericana **Vuelo seguro** cuenta con varias líneas que conectan diversas ciudades. La Tabla 1 muestra las conexiones actualmente existentes.

Diversas agencias de viajes, que emiten boletos de esta compañía, desean tener en todo momento información sobre los **itinerarios más económicos** entre cualquier par de ciudades.

Por otro lado, la aerolínea desearía saber cuál sería la **ciudad más conveniente para instalar los servicios de mantenimiento** de sus aviones.

El Analista de Sistemas de la Empresa ha identificado el **Grafo Dirigido** como muy apropiado para la representación del problema y la resolución de diferentes requerimientos de información típicos en estas situaciones.

### Tabla 1

| Origen \ Destino | Montevideo | Colonia | Buenos Aires | Punta del Este |
|---|:---:|:---:|:---:|:---:|
| **Montevideo** | x | - | - | 150 |
| **Colonia** | 300 | x | - | 390 |
| **Buenos Aires** | 400 | 200 | x | - |
| **Punta del Este** | - | - | 410 | x |

---

## Parte 1

1. Lee cuidadosamente el escenario y dibuja el grafo dirigido correspondiente.
2. Analiza el orden temporal del algoritmo de **Floyd** visto en la cátedra (10 min). ¿Es posible reducirlo? ¿Cómo impacta su orden en operaciones reales bajo este contexto?

## Parte 2

1. Usando **Floyd** para grafo dirigido, calcula las distancias mínimas (costos mínimos) entre todas las parejas de puertos (considerando combinaciones), previendo la recuperación de caminos.
2. ¿En qué puerto sería más conveniente instalar el taller de mantenimiento?

## Parte 3

En base a lo obtenido en el Ejercicio 2, desarrolla el **algoritmo de recuperación de caminos mínimos** (predecesores/ruta).

---

### Recuperación de caminos mínimos mediante predecesores

> Por las dudas de que esta parte no se encuentre fácilmente en el código, decidimos agregarla también en este README para que quede documentada.

Nuestra implementación ya contaba con una forma de recuperar los caminos mínimos mediante la **matriz de rutas** usada en el algoritmo de Floyd-Warshall, siguiendo la firma de los métodos que nos dio la cátedra.

Sin embargo, para cumplir explícitamente con la letra del ejercicio, decidimos agregar una alternativa que nos deja recuperar los caminos mínimos utilizando una **matriz de predecesores**.

#### Métodos agregados

- **`calcularPredecesoresFloyd()`**
  Inicializa las matrices de distancias y predecesores, registra las aristas directas y ejecuta el algoritmo de Floyd-Warshall. Cada vez que encuentra un camino de menor costo, actualiza la distancia y el predecesor correspondiente.

- **`recuperarRutaConPredecesores()`**
  Recibe el origen y el destino, obtiene la matriz de predecesores y reconstruye el camino mínimo desde el destino hacia el origen. Finalmente, devuelve la ruta ordenada desde el origen hasta el destino.
  Si alguno de los vértices no existe o no hay un camino entre ellos, devuelve una lista vacía.

#### Resumen

| Forma | Cómo recupera el camino |
|---|---|
| Implementación original | Guarda las rutas completas en una matriz durante la ejecución de Floyd-Warshall |
| Implementación agregada | Guarda solo el predecesor de cada par y reconstruye la ruta al final |

De esta manera, mantenemos la implementación original y agregamos una segunda forma de recuperar los caminos mínimos, utilizando predecesores en lugar de almacenar las rutas completas durante la ejecución de Floyd-Warshall.