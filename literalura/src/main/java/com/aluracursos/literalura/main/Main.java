package com.aluracursos.literalura.main;
import com.aluracursos.literalura.models.*;
import com.aluracursos.literalura.repository.*;
import com.aluracursos.literalura.service.ConversorDatos;
import com.aluracursos.literalura.service.ConsumoAPI;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConversorDatos conversorDatos = new ConversorDatos();
    private Scanner teclado = new Scanner(System.in);
    private InterfaceLibroRepository libroRepository;
    private InterfaceAutorRepository autorRepository;
    private List<Libro> libros;
    private List<Autor> autores;
    private List<String> idiomas;


    public Main(InterfaceLibroRepository libroRepository, InterfaceAutorRepository autorRepository){
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void viewMenu() {
        int opcion = 1;
        while (opcion != 0) {
            var menu = """
                    -------------------------------------------------
                    1. Agregar libro
                    2. Lista de libros registrados
                    3. Listar por autores registrados
                    4. Listar por autores vivos en algun determinado año
                    5. Listar libros por idioma
                    0- Salir
                    --------------------------------------------------
                    Selecciona alguna opcion para poder continuar
                    """;
            System.out.println(menu);
            if (teclado.hasNextInt()) {
                opcion = teclado.nextInt();
                teclado.nextLine();

                switch (opcion) {
                    case 1:
                        buscarLibro();
                        break;
                    case 2:
                        listaLibrosRegistrados();
                        break;
                    case 3:
                        listaAutoresRegistrados();
                        break;
                    case 4:
                        listaAutoresVivos();
                        break;
                    case 5:
                        listaLibrosPorIdioma();
                        break;
                    case 0:
                        System.out.println("Cerrando la aplicacion");
                        break;
                    default:
                        System.out.println("Opcion no valida");
                }
            } else {
                System.out.println("Opción no válida");
                teclado.next();
            }
        }
    }

    private void buscarLibro(){
        System.out.println("Ingrese el nombre del libro que desea agregar:");
        var tituloLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + tituloLibro.replace(" ", "+"));
        guardarDatos(json);
    }

    private void guardarDatos(String json) {
        try {
            DatosAutor datosAutor = conversorDatos.obtenerDatos(json, DatosAutor.class);
            DatosLibros datosLibro = conversorDatos.obtenerDatos(json, DatosLibros.class);
            //Verifica si el autor ya existe
            Autor autor = autorRepository.findByNombre(datosAutor.nombre())
                    .orElseGet(() -> autorRepository.save(new Autor(datosAutor)));
            //Verifica si el libro ya existe
            if (libroRepository.findByTitulo(datosLibro.titulo()).isEmpty()) {
                Libro libro = new Libro(datosLibro);
                libro.setAutor(autor);
                libroRepository.save(libro);
                System.out.println(libro);
                System.out.println("Libro agregado con exito");

            }else {
                System.out.printf("---------------------------------------------\n");
                System.out.println("El libro ya se encuntra registrado");
            }
        }catch (NullPointerException e) {
            System.out.printf("---------------------------------------------\n");
            System.out.println("Libro no encontrado");
        }
    }

    private void listaLibrosRegistrados() {
        libros = libroRepository.findAll();
        libros.stream().forEach(System.out::println);
    }

    private void listaAutoresRegistrados() {
        autores = autorRepository.findAll();
        autores.stream().forEach(System.out::println);
    }

    private void listaAutoresVivos() {
        System.out.println("Indica el año limite: ");
        int fecha = teclado.nextInt();
        autores = autorRepository.autoresPorFechaFallecimiento(fecha);
        autores.stream().forEach(System.out::println);
    }

    public void listaLibrosPorIdioma() {
        idiomas = libroRepository.idiomasLibros();
        System.out.printf("------------------IDIOMAS--------------------\n");
        idiomas.stream().forEach(System.out::println);
        System.out.printf("---------------------------------------------\n");
        System.out.println("Ingresa el idioma por el que deseas buscar: ");
        var idiomaSeleccionado = teclado.nextLine().toLowerCase();
        libros = libroRepository.librosPoridioma(idiomaSeleccionado);
        if (libros.isEmpty()) {
            System.out.println("Opcion no valida");
        }else {
            libros.stream().forEach(System.out::println);
        }
    }
}