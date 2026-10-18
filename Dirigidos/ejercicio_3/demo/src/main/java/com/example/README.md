## Análisis de las estructuras de datos usadas

Estuvimos viendo qué estructuras de datos de Java nos servían para representar el grafo, teniendo en cuenta cómo funciona cada una y el costo de sus operaciones.

## Comparación y elección

En nuestra implementación del grafo dirigido usamos un Map con otro Map adentro para guardar los vértices y sus conexiones. En concreto, usamos HashMap, que permite buscar elementos por clave en O(1) promedio.

El Map principal guarda los vértices y, para cada uno, hay otro Map con los vértices a los que está conectado y las aristas correspondientes. De esta forma, podemos acceder a las conexiones de un vértice sin tener que recorrer todas las aristas del grafo.

Esta estructura también nos permite representar las listas de adyacencia, guardando solamente las conexiones que existen. Además, como las conexiones se guardan desde cada vértice hacia sus sucesores, podemos representar que existe una conexión en un sentido sin que necesariamente exista en el sentido contrario.

Para el conjunto de vértices aprovechamos las claves del Map principal, ya que no se pueden repetir. Así, no necesitamos guardar los vértices en otra estructura por separado.

Elegimos esta representación porque nos permite acceder a los vértices y sus conexiones de forma eficiente. Además, ocupa aproximadamente O(V + A) de memoria, donde V es la cantidad de vértices y A la cantidad de aristas, ya que solo guardamos los vértices y las conexiones que existen.