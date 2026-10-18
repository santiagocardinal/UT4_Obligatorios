# Ejercicio 6

---

## Parte 1

1. Desarrollar en **lenguaje natural** y **seudocódigo de alto nivel** un algoritmo sencillo que permita determinar si un grafo contiene ciclos (15 minutos).
2. Dada la interfaz y las clases auxiliares, implementar el método `tieneCiclos`, que retorna `true` si el grafo tiene ciclos (20 minutos).
3. Dados los archivos `conexiones_2.txt` y `aeropuertos_2.txt`, ejecutar el programa e indicar si el grafo resultante contiene ciclos o no (5 minutos).

## Parte 2 – Camino crítico

La ordenación topológica o sort topológico es ampliamente utilizado en planificación y gestión de proyectos o procesos que tienen una gran cantidad de tareas. Las tareas suelen tener recursos asignados (tiempo, horas hombre, materia prima necesaria, etc.). Las tareas pueden verse como un cambio de estado de un cierto elemento. Existen naturalmente precedencias entre las tareas (ejemplo: antes de pintar la pared es necesario revocarla, y antes construirla, etc.).

De todos los posibles «caminos» que representan las diferentes secuencias de tareas, existe al menos uno que, si cualquiera de las tareas que lo constituyen se atrasa, entonces todo el proyecto se atrasará. Este es el llamado **«camino crítico»** del proyecto (una herramienta muy utilizada en planificación es el PERT, «diagrama de camino crítico»).

Las demás secuencias (que no son camino crítico) podrán tener ciertas demoras en algunas de sus tareas, hasta alcanzar la magnitud del camino crítico. Por ejemplo, si una secuencia de tareas que es camino crítico tiene un costo total de 23 días (suma de los costos de las tareas o arcos), y otra secuencia tiene un costo o duración total de 15 días, esto significará que existe una **«holgura»** de 8 días, es decir, algunas de las tareas que componen esta secuencia podrán empezar y terminar en diferentes fechas, siempre que no excedan la final. Esto nos permite, al planificar los trabajos, buscar formas de distribuir los recursos existentes de manera más homogénea, evitar picos de necesidad de recursos o espacios ociosos.

Se desea desarrollar entonces un algoritmo que, dado un grafo (que debería ser acíclico) permita:

- calcular el **costo total del camino crítico** e indicar cómo está compuesto este camino, y
- **listar todas las demás secuencias** de tareas posibles, sus costos totales y la **holgura** existente
- deberán establecerse las estructuras apropiadas con las modificaciones que sean necesarias

## Parte 3

Los grafos dirigidos acíclicos son modelos adecuados para representar órdenes parciales. Por ejemplo, el sistema de previaturas de las asignaturas de una carrera universitaria o un orden necesario en el que se deben ejecutar las tareas de un proyecto. En un proyecto modelado con un grafo dirigido en el que los vértices son las tareas y las aristas dirigidas representan las precedencias (existe un vértice de Fin del proyecto), se puede obtener una lista de vértices cuyo orden representa un orden posible de ejecución de las tareas del proyecto (contiene todas las tareas del proyecto).

**Tarea:** Desarrollar el algoritmo `calcularClasificacionTopologica` definido en la interfaz `IDirectedGraphAlgorithms`.

---

## Resolución – Parte 1

### 1. Algoritmo en lenguaje natural

Para saber si un grafo dirigido tiene ciclos hacemos una búsqueda en profundidad desde cada vértice que todavía no fue visitado. Mientras bajamos por el recorrido, vamos guardando en una lista aparte los vértices que forman el camino actual, o sea, los que todavía no terminamos de recorrer.

Cada vez que llegamos a un vértice miramos sus sucesores: si alguno de ellos está en ese camino actual, quiere decir que encontramos una arista que vuelve hacia atrás (un arco de retroceso), y eso significa que hay un ciclo, así que devolvemos verdadero. Si el sucesor ya fue visitado antes pero no está en el camino actual, no pasa nada, porque ya sabemos que desde ahí no hay ciclo y lo salteamos.

Cuando terminamos con todos los sucesores de un vértice, lo sacamos del camino actual y volvemos para atrás. Si recorremos todo el grafo sin encontrar ningún arco de retroceso, el grafo no tiene ciclos.

### 1. Seudocódigo de alto nivel

```
tieneCiclos(grafo): booleano
    visitados ← conjunto vacío
    enCamino  ← conjunto vacío
    PARA CADA vértice v DE grafo HACER
        SI tieneCiclosDesde(v, visitados, enCamino) ENTONCES
            DEVOLVER verdadero
        FIN SI
    FIN PARA
    DEVOLVER falso
FIN

tieneCiclosDesde(v, visitados, enCamino): booleano
    SI v pertenece a enCamino ENTONCES        // volví a un vértice del camino actual → ciclo
        DEVOLVER verdadero
    FIN SI
    SI v pertenece a visitados ENTONCES       // ya lo revisé antes y no tenía ciclo
        DEVOLVER falso
    FIN SI

    agregar v a visitados
    agregar v a enCamino

    PARA CADA sucesor w DE v HACER
        SI tieneCiclosDesde(w, visitados, enCamino) ENTONCES
            DEVOLVER verdadero
        FIN SI
    FIN PARA

    quitar v de enCamino                      // termino con v, vuelvo atrás
    DEVOLVER falso
FIN
```

**Orden de tiempo:** cada vértice se visita una sola vez y cada arista se recorre una sola vez, por lo que el algoritmo es **O(V + E)**.


### 2. Implementación de `tieneCiclos`

El método está implementado en `DirectedGraph`:

- `tieneCiclos()`: recorre todos los vértices del grafo y, por cada uno, llama al método auxiliar.

- `tieneCiclosUtil(...)`: realiza la búsqueda en profundidad. Usa el conjunto `visited` (vértices ya revisados) y el conjunto `recStack` (vértices del camino actual). Si encuentra un sucesor que está en `recStack`, hay un ciclo.

### 3. Ejecución con `conexiones_2.txt` y `aeropuertos_2.txt`

Para cargar el grafo usamos la clase `GraphLoader`, que lee un aeropuerto por línea de `aeropuertos_2.txt` y las conexiones con el formato `origen,destino,costo` de `conexiones_2.txt`. Después, desde el `App (main)`, llamamos a `tieneCiclos()`.

Se cargaron **9 aeropuertos** y **15 conexiones**, y el resultado fue:

```
El grafo SI tiene ciclos
Ciclo encontrado: Florianopolis -> Rio_de_Janeiro -> San_Pablo -> Florianopolis
```

**El grafo sí contiene ciclos.** Además del que muestra el programa, hay otros más chicos porque hay vuelos de ida y vuelta, por ejemplo `San_Pablo → Santos → San_Pablo`.

---

## Resolución – Parte 2

### Cómo lo pensamos

Lo primero que hacemos es fijarnos que el grafo no tenga ciclos, porque si tiene ciclos no tiene sentido hablar de camino crítico (nunca terminaría). Para eso usamos el `tieneCiclos()` de la parte 1.

Después buscamos dónde empieza y dónde termina el proyecto. Los vértices de inicio son los que no tienen ninguna tarea antes (grado de entrada 0) y los de fin son los que no tienen nada después (grado de salida 0).

Para sacar todas las secuencias de tareas reutilizamos el `obtenerTodosLosCaminos` que hicimos en el ejercicio 5, buscando todos los caminos desde cada inicio hasta cada fin. Como cada camino ya viene con su costo, el camino crítico es simplemente el que tiene el costo más grande, porque es el que marca cuánto tarda el proyecto en total. A las demás secuencias les calculamos la holgura restando su costo al del camino crítico.

### Lo que agregamos

Para guardar el resultado hicimos una clase nueva `CaminoCriticoResult` en la carpeta `result`. Tiene el camino crítico, su costo, la lista con las otras secuencias y un método `getHolgura(secuencia)` que devuelve cuánto se puede atrasar esa secuencia.

### Prueba con los datos de `conexiones_2.txt`

Con el grafo completo **no se puede calcular el camino crítico** porque tiene ciclos. Por eso, se tomó el subgrafo sin ciclos que sale de **Asunción**.

| Secuencia | Costo | Holgura |
|---|:---:|:---:|
| **Asunción → Buenos Aires → Porto Alegre → Montevideo** | **3850** | **0 (crítico)** |
| Asunción → Curitiba → Montevideo | 2200 | 1650 |
| Asunción → Buenos Aires → Montevideo | 1800 | 2050 |
| Asunción → Porto Alegre → Montevideo | 1700 | 2150 |

---

## Resolución – Parte 3

El método `calcularClasificacionTopologica` está implementado en `DirectedGraphAlgorithms`:

- Si el grafo tiene ciclos, no existe un orden topológico y se retorna una lista vacía.

- Si es acíclico, se realiza una búsqueda en profundidad desde cada vértice no visitado. Cada vértice se apila **cuando termina** de recorrer todos sus sucesores.


- Al final, la pila contiene los vértices en orden topológico: cada tarea aparece antes que las tareas que dependen de ella.


### Prueba con los datos de `conexiones_2.txt`

Con el grafo completo devuelve una lista vacía, porque tiene ciclos y no existe un orden topológico. Con la parte del grafo que sale de Asunción nos dio:

```
Asuncion -> Curitiba -> Buenos_Aires -> Porto_Alegre -> Montevideo
```

Cada aeropuerto aparece antes que todos aquellos a los que tiene vuelos.