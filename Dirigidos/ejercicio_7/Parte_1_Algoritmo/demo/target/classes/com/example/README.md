ya agregamos este método a la clase DirectedGraphAlgorithms, pero por las dudas que no se encuentre ese el siguiente:

public <V> void imprimirCamino(
            Map<V, V> predecessors,
            V origen,
            V destino) 
        {

        LinkedList<V> camino = new LinkedList<>();
        V actual = destino;

        while (!actual.equals(origen)) 
        {
            if (actual == null || !predecessors.containsKey(actual)) 
            {
                System.out.println("No existe camino");
                return;
            }

            camino.addFirst(actual);
            actual = predecessors.get(actual);
        }

        camino.addFirst(origen);

        System.out.println(camino);
    }