## Resolución – Parte 1

### Algoritmo en lenguaje natural

Para encontrar todos los vuelos posibles entre una ciudad origen y una ciudad destino, la idea es hacer una búsqueda en profundidad pero que vaya guardando el camino que lleva hasta el momento. Arrancamos en la ciudad origen con el costo en 0 y desde ahí vamos probando cada una de las ciudades a las que se puede ir directo. Cada vez que vamos a una ciudad nueva la agregamos al camino y le sumamos el costo del vuelo, pero solo si esa ciudad no está ya en el camino.

Cuando una ciudad ya no tiene más vuelos para probar, volvemos para atrás. Al ir para atrás, la sacamos del camino y le restamos su costo, para poder seguir probando las otras opciones desde la ciudad anterior.

Si en algún momento llegamos a la ciudad destino, guardamos ese camino con su costo total en una lista de resultados.


### Grafo utilizado 


| Vértice | Adyacentes (destino, costo) |
|---|---|
| **Asunción** | (Buenos Aires, 1600), (Curitiba, 800), (Porto Alegre, 700) |
| **Buenos Aires** | (Asunción, 1600), (Montevideo, 200), (Porto Alegre, 1250) |
| **Curitiba** | (Rio de Janeiro, 1500) |
| **Montevideo** | (Buenos Aires, 200), (Porto Alegre, 1000) |
| **Porto Alegre** | (Montevideo, 1000) |
| **Rio de Janeiro** | (Curitiba, 1500) |


### Resultado: conexiones entre Montevideo y Porto Alegre

| # | Itinerario | Cálculo | Costo total |
|:---:|---|---|:---:|
| 1 | Montevideo → Buenos Aires → Asunción → Porto Alegre | 200 + 1600 + 700 | **2500** |
| 2 | Montevideo → Buenos Aires → Porto Alegre | 200 + 1250 | **1450** |
| 3 | Montevideo → Porto Alegre | 1000 | **1000** |

El itinerario de menor costo es **Montevideo → Porto Alegre (vuelo directo), con un costo de 1000**.

---

## Resolución – Parte 2

El método `obtenerTodosLosCaminos` está implementado en `DirectedGraphAlgorithms`:
