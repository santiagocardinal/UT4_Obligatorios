package com.example.model;

import com.example.model.edge.Edge;
import com.example.model.edge.WeightedEdge;
import com.example.model.result.DijkstraResult;
import com.example.model.result.FloydWarshallResult;
import com.example.model.result.IDijkstraResult;
import com.example.model.result.IFloydWarshallResult;
import com.example.model.result.Path;

import java.util.*;
import java.util.function.Consumer;

public class DirectedGraphAlgorithms implements IDirectedGraphAlgorithms {

    // ----------------------------------------------------------------- Dijkstra

    @Override
    public <V, D extends WeightedEdge> IDijkstraResult<V> dijkstra(
            Comparable<V> source, IDirectedIGraph<V, D> grafo) {

        V sourceVertex = grafo.buscarVertice(source);
        Map<V, Double> distances = new HashMap<>();
        Map<V, V> predecessors = new HashMap<>();

        for (V v : grafo.vertices()) {
            distances.put(v, Double.POSITIVE_INFINITY);
        }

        if (sourceVertex == null) {
            return new DijkstraResult<>(distances, new HashMap<>());
        }

        distances.put(sourceVertex, 0.0);

        PriorityQueue<VertexDistance<V>> pq =
                new PriorityQueue<>(Comparator.comparingDouble(vd -> vd.distance));

        pq.add(new VertexDistance<>(sourceVertex, 0.0));
        Set<V> visited = new HashSet<>();

        while (!pq.isEmpty()) {
            V u = pq.poll().vertex;

            if (!visited.add(u)) {
                continue;
            }

            for (Edge<V, D> edge :
                    grafo.adyacencias(grafo.construirComparable(u))) {

                V v = edge.target();

                if (visited.contains(v)) {
                    continue;
                }

                double newDist =
                        distances.get(u) + edge.dato().getWeight();

                if (newDist < distances.get(v)) {
                    distances.put(v, newDist);
                    predecessors.put(v, u);
                    pq.add(new VertexDistance<>(v, newDist));
                }
            }
        }

        // Reconstruir el camino de cada vértice alcanzable
        Map<V, List<V>> paths = new HashMap<>();

        for (V v : grafo.vertices()) {
            if (distances.get(v) == Double.POSITIVE_INFINITY) {
                continue;
            }

            LinkedList<V> path = new LinkedList<>();

            for (V cur = v; cur != null; cur = predecessors.get(cur)) {
                path.addFirst(cur);

                if (cur.equals(sourceVertex)) {
                    break;
                }
            }

            paths.put(v, path);
        }

        return new DijkstraResult<>(distances, paths);
    }

    // ------------------------------------------------ Imprimir camino de Dijkstra

    public <V> void imprimirCamino(
            Map<V, V> predecessors,
            V origen,
            V destino) {

        LinkedList<V> camino = new LinkedList<>();
        V actual = destino;

        while (!actual.equals(origen)) {
            if (actual == null || !predecessors.containsKey(actual)) {
                System.out.println("No existe camino");
                return;
            }

            camino.addFirst(actual);
            actual = predecessors.get(actual);
        }

        camino.addFirst(origen);

        System.out.println(camino);
    }

    private static class VertexDistance<V> {
        final V vertex;
        final double distance;

        VertexDistance(V vertex, double distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }

    // -------------------------------------------------------------------- Floyd

    @Override
    public <V, D extends WeightedEdge> IFloydWarshallResult<V> floyd(
            IDirectedIGraph<V, D> grafo) {

        List<V> vertexList = new ArrayList<>(grafo.vertices());

        Map<V, Map<V, Double>> costMatrix = new HashMap<>();
        Map<V, Map<V, List<V>>> pathMatrix = new HashMap<>();

        inicializarMatrices(vertexList, costMatrix, pathMatrix);

        for (Edge<V, D> edge : grafo.aristas()) {
            V s = edge.source();
            V t = edge.target();
            double w = edge.dato().getWeight();

            // Un lazo no debe pisar el cero de la diagonal.
            if (w < costMatrix.get(s).get(t)) {
                costMatrix.get(s).put(t, w);
                pathMatrix.get(s).put(
                        t, new ArrayList<>(List.of(s, t)));
            }
        }

        for (V k : vertexList) {
            for (V i : vertexList) {
                double costIK = costMatrix.get(i).get(k);

                if (costIK == Double.POSITIVE_INFINITY) {
                    continue;
                }

                for (V j : vertexList) {
                    double costKJ = costMatrix.get(k).get(j);

                    if (costKJ == Double.POSITIVE_INFINITY) {
                        continue;
                    }

                    double newCost = costIK + costKJ;

                    if (newCost < costMatrix.get(i).get(j)) {
                        costMatrix.get(i).put(j, newCost);

                        pathMatrix.get(i).put(
                                j,
                                concatenar(
                                        pathMatrix.get(i).get(k),
                                        pathMatrix.get(k).get(j)
                                )
                        );
                    }
                }
            }
        }

        return new FloydWarshallResult<>(costMatrix, pathMatrix);
    }

    // ----------------------------------------- Floyd con predecesores

    private <V, D extends WeightedEdge> Map<V, Map<V, V>>
    calcularPredecesoresFloyd(IDirectedIGraph<V, D> grafo) {

        List<V> vertices = new ArrayList<>(grafo.vertices());

        Map<V, Map<V, Double>> distancias = new HashMap<>();
        Map<V, Map<V, V>> predecesores = new HashMap<>();

        // Inicializar las matrices.
        for (V i : vertices) {
            Map<V, Double> filaDistancias = new HashMap<>();
            Map<V, V> filaPredecesores = new HashMap<>();

            for (V j : vertices) {
                filaDistancias.put(
                        j,
                        i.equals(j)
                                ? 0.0
                                : Double.POSITIVE_INFINITY
                );

                filaPredecesores.put(j, null);
            }

            distancias.put(i, filaDistancias);
            predecesores.put(i, filaPredecesores);
        }

        // Registrar las aristas directas.
        for (Edge<V, D> edge : grafo.aristas()) {
            V origen = edge.source();
            V destino = edge.target();
            double peso = edge.dato().getWeight();

            if (peso < distancias.get(origen).get(destino)) {
                distancias.get(origen).put(destino, peso);
                predecesores.get(origen).put(destino, origen);
            }
        }

        for (V k : vertices) {
            for (V i : vertices) {
                double distanciaIK = distancias.get(i).get(k);

                if (distanciaIK == Double.POSITIVE_INFINITY) {
                    continue;
                }

                for (V j : vertices) {
                    double distanciaKJ = distancias.get(k).get(j);

                    if (distanciaKJ == Double.POSITIVE_INFINITY) {
                        continue;
                    }

                    double nuevaDistancia = distanciaIK + distanciaKJ;

                    if (nuevaDistancia < distancias.get(i).get(j)) {
                        distancias.get(i).put(j, nuevaDistancia);
                        predecesores.get(i).put(
                                j,
                                predecesores.get(k).get(j)
                        );
                    }
                }
            }
        }

        return predecesores;
    }

    // ----------------------------------------- Recuperar ruta con predecesores

    public <V, D extends WeightedEdge> List<V> recuperarRutaConPredecesores(
            Comparable<V> origenCriteria,
            Comparable<V> destinoCriteria,
            IDirectedIGraph<V, D> grafo) {

        V origen = grafo.buscarVertice(origenCriteria);
        V destino = grafo.buscarVertice(destinoCriteria);

        if (origen == null || destino == null) {
            return Collections.emptyList();
        }

        if (origen.equals(destino)) {
            return new ArrayList<>(List.of(origen));
        }

        Map<V, Map<V, V>> predecesores =
                calcularPredecesoresFloyd(grafo);

        Map<V, V> fila = predecesores.get(origen);

        if (fila == null || fila.get(destino) == null) {
            return Collections.emptyList();
        }

        LinkedList<V> camino = new LinkedList<>();
        V actual = destino;
        camino.addFirst(actual);

        while (!actual.equals(origen)) {
            actual = fila.get(actual);

            if (actual == null) {
                return Collections.emptyList();
            }

            camino.addFirst(actual);
        }

        return camino;
    }

    // ------------------------------------------------------------------ Warshall

    /**
     * Cierre transitivo. El costo es 0 para (v,v), 1 si hay camino
     * y +infinito si no lo hay.
     */
    @Override
    public <V, D extends WeightedEdge> IFloydWarshallResult<V> warshall(
            IDirectedIGraph<V, D> grafo) {

        List<V> vertexList = new ArrayList<>(grafo.vertices());

        Map<V, Map<V, Double>> reachMatrix = new HashMap<>();
        Map<V, Map<V, List<V>>> pathMatrix = new HashMap<>();

        inicializarMatrices(vertexList, reachMatrix, pathMatrix);

        for (Edge<V, D> edge : grafo.aristas()) {
            V s = edge.source();
            V t = edge.target();

            if (!s.equals(t)) {
                reachMatrix.get(s).put(t, 1.0);
                pathMatrix.get(s).put(
                        t, new ArrayList<>(List.of(s, t)));
            }
        }

        for (V k : vertexList) {
            for (V i : vertexList) {
                if (reachMatrix.get(i).get(k)
                        == Double.POSITIVE_INFINITY) {
                    continue;
                }

                for (V j : vertexList) {
                    if (reachMatrix.get(i).get(j)
                            == Double.POSITIVE_INFINITY
                            && reachMatrix.get(k).get(j)
                            != Double.POSITIVE_INFINITY) {

                        reachMatrix.get(i).put(j, 1.0);

                        pathMatrix.get(i).put(
                                j,
                                concatenar(
                                        pathMatrix.get(i).get(k),
                                        pathMatrix.get(k).get(j)
                                )
                        );
                    }
                }
            }
        }

        return new FloydWarshallResult<>(reachMatrix, pathMatrix);
    }

    // ----------------------------------------------------- Inicializar matrices

    private <V> void inicializarMatrices(
            List<V> vertexList,
            Map<V, Map<V, Double>> costMatrix,
            Map<V, Map<V, List<V>>> pathMatrix) {

        for (V u : vertexList) {
            Map<V, Double> fila = new HashMap<>();
            Map<V, List<V>> filaPaths = new HashMap<>();

            for (V v : vertexList) {
                if (u.equals(v)) {
                    fila.put(v, 0.0);
                    filaPaths.put(v, new ArrayList<>(List.of(u)));
                } else {
                    fila.put(v, Double.POSITIVE_INFINITY);
                    filaPaths.put(v, new ArrayList<>());
                }
            }

            costMatrix.put(u, fila);
            pathMatrix.put(u, filaPaths);
        }
    }

    /** Une i -> k con k -> j sin repetir k. */
    private <V> List<V> concatenar(
            List<V> pathIK, List<V> pathKJ) {

        List<V> combined = new ArrayList<>(pathIK);
        combined.addAll(pathKJ.subList(1, pathKJ.size()));

        return combined;
    }

    // ------------------------------------------------- Centro y excentricidad

    /**
     * Centro del grafo: vértice de menor excentricidad de llegada.
     */
    @Override
    public <V, D extends WeightedEdge> V obtenerCentroGrafo(
            IDirectedIGraph<V, D> grafo) {

        IFloydWarshallResult<V> floyd = floyd(grafo);

        V center = null;
        double minEccentricity = Double.POSITIVE_INFINITY;

        for (V vertex : grafo.vertices()) {
            double eccentricity = excentricidad(grafo, floyd, vertex);

            if (eccentricity < minEccentricity) {
                minEccentricity = eccentricity;
                center = vertex;
            }
        }

        return center;
    }

    /**
     * Excentricidad de llegada:
     * máximo costo mínimo desde cualquier vértice hasta vertex.
     */
    @Override
    public <V, D extends WeightedEdge> double obtenerExcentricidad(
            IDirectedIGraph<V, D> grafo,
            Comparable<V> vertexCriteria) {

        V vertex = grafo.buscarVertice(vertexCriteria);

        if (vertex == null) {
            return Double.POSITIVE_INFINITY;
        }

        return excentricidad(grafo, floyd(grafo), vertex);
    }

    private <V> double excentricidad(
            IGraph<V, ?> grafo,
            IFloydWarshallResult<V> floyd,
            V vertex) {

        double max = 0.0;

        for (V w : grafo.vertices()) {
            double cost = floyd.getCost(w, vertex);

            if (cost > max) {
                max = cost;
            }
        }

        return max;
    }

    // ------------------------------------------------------- Todos los caminos

    @Override
    public <V, D extends WeightedEdge> List<Path<V>> obtenerTodosLosCaminos(
            Comparable<V> sourceCriteria,
            Comparable<V> targetCriteria,
            IGraph<V, D> grafo) {

        V source = grafo.buscarVertice(sourceCriteria);
        V target = grafo.buscarVertice(targetCriteria);

        List<Path<V>> allPaths = new ArrayList<>();

        if (source == null || target == null) {
            return allPaths;
        }

        dfsAllPaths(
                source, target, grafo,
                new ArrayList<>(), new HashSet<>(),
                0.0, allPaths
        );

        return allPaths;
    }

    private <V, D extends WeightedEdge> void dfsAllPaths(
            V current,
            V target,
            IGraph<V, D> grafo,
            List<V> currentPath,
            Set<V> visited,
            double currentCost,
            List<Path<V>> allPaths) {

        visited.add(current);
        currentPath.add(current);

        if (current.equals(target)) {
            allPaths.add(
                    new Path<>(new ArrayList<>(currentPath), currentCost)
            );
        } else {
            for (Edge<V, D> edge :
                    grafo.adyacencias(grafo.construirComparable(current))) {

                V neighbor = edge.target();

                if (!visited.contains(neighbor)) {
                    dfsAllPaths(
                            neighbor, target, grafo, currentPath,
                            visited,
                            currentCost + edge.dato().getWeight(),
                            allPaths
                    );
                }
            }
        }

        currentPath.remove(currentPath.size() - 1);
        visited.remove(current);
    }

    // --------------------------------------------------------------- Recorridos

    @Override
    public <V, D> void recorridoEnProfundidad(
            IGraph<V, D> grafo,
            Comparable<V> sourceCriteria,
            Consumer<V> consumer) {

        V start = grafo.buscarVertice(sourceCriteria);

        if (start == null) {
            return;
        }

        dfsUtil(start, grafo, new HashSet<>(), consumer);
    }

    private <V, D> void dfsUtil(
            V current,
            IGraph<V, D> grafo,
            Set<V> visited,
            Consumer<V> consumer) {

        visited.add(current);
        consumer.accept(current);

        for (Edge<V, D> edge :
                grafo.adyacencias(grafo.construirComparable(current))) {

            V neighbor = edge.target();

            if (!visited.contains(neighbor)) {
                dfsUtil(neighbor, grafo, visited, consumer);
            }
        }
    }

    @Override
    public <V, D> void recorridoEnAmplitud(
            IGraph<V, D> grafo,
            Comparable<V> sourceCriteria,
            Consumer<V> consumer) {

        V start = grafo.buscarVertice(sourceCriteria);

        if (start == null) {
            return;
        }

        Set<V> visited = new HashSet<>();
        Queue<V> queue = new LinkedList<>();

        visited.add(start);
        queue.add(start);

        while (!queue.isEmpty()) {
            V current = queue.poll();
            consumer.accept(current);

            for (Edge<V, D> edge :
                    grafo.adyacencias(grafo.construirComparable(current))) {

                V neighbor = edge.target();

                if (visited.add(neighbor)) {
                    queue.add(neighbor);
                }
            }
        }
    }

    // -------------------------------------------------- Clasificación topológica

    /**
     * Retorna el orden topológico. Si hay ciclos, retorna una lista vacía.
     */
    @Override
    public <V, D> List<V> calcularClasificacionTopologica(
            IDirectedIGraph<V, D> grafo) {

        if (grafo.tieneCiclos()) {
            return Collections.emptyList();
        }

        Deque<V> stack = new ArrayDeque<>();
        Set<V> visited = new HashSet<>();

        for (V vertex : grafo.vertices()) {
            if (!visited.contains(vertex)) {
                topologicalSortUtil(vertex, grafo, visited, stack);
            }
        }

        return new ArrayList<>(stack);
    }

    private <V, D> void topologicalSortUtil(
            V vertex,
            IDirectedIGraph<V, D> grafo,
            Set<V> visited,
            Deque<V> stack) {

        visited.add(vertex);

        for (V successor :
                grafo.successors(grafo.construirComparable(vertex))) {

            if (!visited.contains(successor)) {
                topologicalSortUtil(successor, grafo, visited, stack);
            }
        }

        stack.push(vertex);
    }
}
