# Ejercicio 12 – Número de Bacon

El objeto del juego Kevin Bacon consiste en vincular a un actor con Kevin Bacon a través de películas en las que hayan trabajado juntos.

El número mínimo de vínculos es el **número de Bacon** de un actor.

Por ejemplo:

- **Tom Hanks** tiene un número de Bacon de 1, pues trabajó con Kevin Bacon en *Apolo 13*.
- **Sally Field** tiene un número de Bacon de 2 porque trabajó en *Forrest Gump* con Tom Hanks, que a su vez trabajó en *Apolo 13* con Kevin Bacon.

Casi todos los actores más conocidos tienen un número de Bacon de 1 o 2.

Supongamos que hemos decidido representar estas relaciones mediante un **grafo no dirigido**, y contamos con una lista de actores y otra de relaciones entre los mismos (la relación entre dos actores directa sería una película y, para simplificar, añadiremos la restricción que dos actores solo pueden estar directamente relacionados en una película).

## Parte 1

Dado el TDA de grafo no dirigido con las características y operaciones habituales, donde:

- Los vértices representan actores (con tipo `String`).
- Las aristas indican la película en que trabajan dos actores (con tipo `String` e indicando el nombre de la película).

Se deberá:

1. Implementar un método `numBacon` que, dado un cierto actor (tipo `String`), encuentre su número de Bacon (existe un actor representado en el grafo con nombre `Kevin_Bacon`).

2. Implementar un programa principal que:

   a. Cargue el grafo con los vértices contenidos en el archivo `actores.csv`.

   b. Cargue las aristas contenidas en el archivo `en_películas.csv`.

   c. Invoque la operación `numBacon` del grafo, con los parámetros indicados más abajo, emitiendo por consola el resultado.

3. Ejecuta el programa y anota en esta hoja el resultado de las invocaciones.

   Registra este mismo resultado en el archivo `salida.txt` que has de adjuntar al código completo.

4. Desarrolla los casos de prueba necesarios para verificar la corrección de los métodos implementados.

## Actores a evaluar

Calcular el número de Bacon para los siguientes actores:

- `John_Goodman`
- `Tom_Cruise`
- `Jason_Statham`
- `Lukas_Haas`
- `Djimon_Hounsou`