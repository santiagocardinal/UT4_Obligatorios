# Ejercicio 6

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

**Orden de tiempo:** cada vértice se visita una sola vez y cada arista se recorre una sola vez, por lo que el algoritmo es **O(V + A)**.


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
