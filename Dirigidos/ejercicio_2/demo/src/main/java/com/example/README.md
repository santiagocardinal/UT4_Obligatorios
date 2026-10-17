### Recuperación de caminos mínimos mediante predecesores

Por las dudas de que esta parte no se encuentre fácilmente en el código, decidimos agregarla también en este README para que quede documentada.

Nuestra implementación ya contaba con una forma de recuperar los caminos mínimos mediante la matriz de rutas usada en el algoritmo de Floyd-Warshall, siguiendo la firma de los métodos que nos dió la cátedra. Sin embargo, para cumplir explícitamente con la letra del ejercicio, decidimos agregar una alternativa que nos deja recuperar los caminos mínimos utilizando una matriz de predecesores.

Para esto, agregamos los siguientes métodos:

-calcularPredecesoresFloyd(): inicializa las matrices de distancias y predecesores, registra las aristas directas y ejecuta el algoritmo de Floyd-Warshall. Cada vez que encuentra un camino de menor costo, actualiza la distancia y el predecesor correspondiente.

-recuperarRutaConPredecesores(): recibe el origen y el destino, obtiene la matriz de predecesores y reconstruye el camino mínimo desde el destino hacia el origen. Finalmente, devuelve la ruta ordenada desde el origen hasta el destino. Si alguno de los vértices no existe o no hay un camino entre ellos, devuelve una lista vacía.

De esta manera, mantenemos la implementación original y agregamos una segunda forma de recuperar los caminos mínimos, utilizando predecesores en lugar de almacenar las rutas completas durante la ejecución de Floyd-Warshall.

