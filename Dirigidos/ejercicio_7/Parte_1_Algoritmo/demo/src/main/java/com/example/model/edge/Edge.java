package com.example.model.edge;

public interface Edge<V, D> {
    V source();

    V target();

    D dato();

    boolean directed();
}