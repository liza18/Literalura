package com.aluracursos.literalura.repository;
import com.aluracursos.literalura.models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface InterfaceAutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombre(String nombre);
    @Query("SELECT a FROM Autor a WHERE a.fechaFallecimiento <= :fechaFallecimiento")
    List<Autor> autoresPorFechaFallecimiento(int fechaFallecimiento);
}