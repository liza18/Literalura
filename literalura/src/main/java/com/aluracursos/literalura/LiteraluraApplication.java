package com.aluracursos.literalura;

import com.aluracursos.literalura.repository.InterfaceAutorRepository;
import com.aluracursos.literalura.repository.InterfaceLibroRepository;

import com.aluracursos.literalura.repository.InterfaceLibroRepository;
import com.aluracursos.literalura.repository.InterfaceAutorRepository;
import com.aluracursos.literalura.main.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private InterfaceAutorRepository autorRepository;
	@Autowired
	private InterfaceLibroRepository libroRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(libroRepository, autorRepository);
		main.viewMenu();
		System.out.println("Pruebas---->");
	}
}

