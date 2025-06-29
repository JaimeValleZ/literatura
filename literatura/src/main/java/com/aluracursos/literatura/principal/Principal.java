package com.aluracursos.literatura.principal;

import com.aluracursos.literatura.dto.Datos;
import com.aluracursos.literatura.dto.DatosAutor;
import com.aluracursos.literatura.dto.DatosLibro;
import com.aluracursos.literatura.modelo.Autor;
import com.aluracursos.literatura.modelo.Libro;
import com.aluracursos.literatura.repository.AutorRepository;
import com.aluracursos.literatura.repository.LibroRepository;
import com.aluracursos.literatura.service.ConsumoAPI;
import com.aluracursos.literatura.service.ConvierteDatos;


import java.util.*;

public class Principal {

    private final LibroRepository libroRepository;

    private final AutorRepository autorRepository;

    private static Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    public String json;

    private String menu ="""
                Elija la opcion a traves de un numero
                ---------------------------
                1 - Buscar libro por titulo
                2 - Listas libros registrados
                3 - Listar autores registrados
                4 - Listar autores vivos en un determinado año
                5 - Listar libros por idioma
                6 - Top 10 libros mas descargados
                7 - Buscar autores por nombre
                0 - Salir
                ---------------------------
                """;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraMenu(){

        int numero = -1;
        while(numero!=0){
            json = consumoApi.obtenerDatos(URL_BASE);
            System.out.println(menu);
            numero = teclado.nextInt();
            switch (numero) {
                case 1 -> buscarLibroPorTitulo();
                case 2 -> listarLibros();
                case 3 -> listarAutoresRegistrados();
                case 4 -> autoresVivosEnDeterminadoAno();
                case 5 -> listarLibrosPorIdioma();
                case 6 -> top10LibrosMasDescargados();
                case 7 -> buscarAutorPorNombre();
                case 0 -> System.out.println("Cerrando la aplicacion...");
                default -> System.out.println("Ingrese una opcion valida");

            }

        }
    }

    private void buscarLibroPorTitulo() {
        DatosLibro datosLibro = obtenerDatosDelLibro();
        if(datosLibro!=null){
            Libro libro;
            DatosAutor datosAutor = datosLibro.autor().get(0);
            Autor autorExistente = autorRepository.findByNombreContainingIgnoreCase(datosAutor.nombre());
            if(autorExistente!=null){
                libro = new Libro(datosLibro, autorExistente);
            } else {
                //en caso que no exista el autor
                Autor nuevoAutor = new Autor(datosAutor);
                libro = new Libro(datosLibro, nuevoAutor);
                autorRepository.save(nuevoAutor);
            }

            try{
                libroRepository.save(libro);
                System.out.println(libro);
            } catch (Exception e) {
                System.out.println("El libro ya esta registrado");
            }

        }else {
            System.out.println("No se encontro el libro");
        }
    }

    //obtener datos del libro
    private DatosLibro obtenerDatosDelLibro() {
        System.out.println("Escribe el nombre del libro que deseas buscar");
        String nombreLibro = teclado.next();
        json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        Datos datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibro> datosLibro = datosBusqueda.libros().stream()
                .filter(libro -> libro.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
                .findFirst();
        if(datosLibro.isPresent()){
            return datosLibro.get();
        }
        else {
            return null;
        }
    }

    private void listarLibros() {
        List<Libro> librosRegistrados = libroRepository.findAll();
        librosRegistrados.forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        autorRepository.findAll().forEach(System.out::println);
    }

    private void autoresVivosEnDeterminadoAno() {

        try {
            System.out.println("Ingrese el año vivo del autor(es) que desea buscar");
            var ano = teclado.nextInt();
            teclado.nextLine();
            List<Autor> autor = autorRepository.findByFechaFallecimientoGreaterThanEqual(ano);

            if(autor.isEmpty()){
                System.out.println("No se encontro un autor vivo en ese año");
            } else {
                autor.forEach(System.out::println);
            }
        } catch (InputMismatchException e) {
            System.out.println("Debe ingresar un año valido");
            teclado.nextLine();
        }
    }

    private void listarLibrosPorIdioma() {

        try{
            System.out.println("""
                Ingrese el idioma del libro que desea buscar:
                es - español
                en - ingles
                fr - frances
                pt - portugues
                """);
            var idioma = teclado.next();
            teclado.nextLine();
            List<Libro> librosPorIdioma = libroRepository.findByIdioma(idioma.toLowerCase( ));
            if(librosPorIdioma.isEmpty()){
                System.out.println("No se encontraron libros con ese idioma");
            }else{
                System.out.println(librosPorIdioma);
            }
        }  catch (InputMismatchException e) {
            System.out.println("Debe ingresar un idioma valido");
            teclado.nextLine();
        }
    }

    private void top10LibrosMasDescargados() {
        libroRepository.OrderByNumeroDescargasDesc().stream()
                .limit(10)
                .forEach(l -> System.out.println("Nombre: " + l.getTitulo() + " - " + " Descargas: " + l.getNumeroDescargas()));
    }

    private void buscarAutorPorNombre() {

        try{
            System.out.println("Ingrese el nombre del autor que desea buscar: ");
            String nombreAutor = teclado.next();
            teclado.nextLine();
            Autor autorBuscado = autorRepository.findByNombreContainingIgnoreCase(nombreAutor);

            if(autorBuscado == null){
                System.out.println("No se encontró el autor");
            } else {
                System.out.println(autorBuscado);
            }
        }
        catch (InputMismatchException e) {
            System.out.println("Debe ingresar un idioma valido");
            teclado.nextLine();
        }

    }



}


