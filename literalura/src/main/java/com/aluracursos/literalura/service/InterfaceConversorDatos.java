package com.aluracursos.literalura.service;

public interface InterfaceConversorDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
