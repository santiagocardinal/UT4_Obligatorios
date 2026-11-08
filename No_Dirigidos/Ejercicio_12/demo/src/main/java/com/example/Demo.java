package com.example.grafo;

import com.example.grafo.model.IGraph;
import com.example.grafo.model.edge.Edge;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Demo {

    public static void main(String[] args) throws IOException {

        // Creamos el grafo no dirigido
        Graph<String, String> grafo = new Graph<>();

        // Cargamos los vértices
        cargarActores(grafo, "actores.csv");

        // Cargamos las aristas
        cargarPeliculas(grafo, "en_películas.csv");

        // Creamos el objeto que calcula el número de Bacon
        KevinBacon algoritmo = new KevinBacon();

        // Actores que debemos consultar
        List<String> actores = Arrays.asList(
                "John_Goodman",
                "Tom_Cruise",
                "Jason_Statham",
                "Lukas_Haas",
                "Djimon_Hounsou"
        );

        // Ejecutamos las consultas y guardamos los resultados
        try (BufferedWriter writer =
                     Files.newBufferedWriter(Path.of("salida.txt"))) {

            writer.write("Número de Bacon");
            writer.newLine();

            for (String actor : actores) {

                int resultado = algoritmo.numBacon(grafo, actor);

                String linea = actor + ": " + resultado;

                System.out.println(linea);

                writer.write(linea);
                writer.newLine();
            }
        }

        System.out.println("Resultados guardados en salida.txt");
    }

    private static void cargarActores(
            IGraph<String, String> grafo,
            String archivo) throws IOException {

        try (BufferedReader reader = Files.newBufferedReader(
                Path.of(archivo))) {

            String linea;

            // Saltamos el encabezado
            reader.readLine();

            while ((linea = reader.readLine()) != null) {

                String[] datos = linea.split(",");

                String actor = datos[0].trim();

                grafo.agregarVertice(actor);
            }
        }
    }

    private static void cargarPeliculas(
            IGraph<String, String> grafo,
            String archivo) throws IOException {

        try (BufferedReader reader = Files.newBufferedReader(
                Path.of(archivo))) {

            String linea;

            // Saltamos el encabezado
            reader.readLine();

            while ((linea = reader.readLine()) != null) {

                String[] datos = linea.split(",", 3);

                String actor1 = datos[0].trim();
                String actor2 = datos[1].trim();
                String pelicula = datos[2].trim();

                grafo.agregarArista(actor1, actor2, pelicula);
            }
        }
    }
}